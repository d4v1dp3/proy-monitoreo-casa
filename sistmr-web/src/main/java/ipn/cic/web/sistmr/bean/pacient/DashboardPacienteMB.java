/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.pacient;

import ipn.cic.sistmr.exception.NoExisteMedicionesException;
import ipn.cic.sistmr.exception.NoExistePacienteDashException;
import ipn.cic.sistmr.modelo.EntMedidas;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.sesion.dashboard.DashboardBDLocal;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author J.PEREZ
 */

@Named(value="dashboardPacienteMB")
@ViewScoped
public class DashboardPacienteMB implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DashboardPacienteMB.class.getName());
    
    @EJB
    private DashboardBDLocal dashboardBD;

    private EntPaciente paciente;
    private List<EntMedidas> medidasComp;
     
    private String pacienteNombre;
    private String pacientePrimerAp;
    private String pacienteSegundoAp;
    private String pacienteId;
    private float pacSaturacionOxg;
    private float pacTemperatura;
    private short pacFrecRespiratoria;
    private short pacFrecCardiaca;
    private int   pacPreArtSistolica;
    private int   pacPreArtDiastolica;  
    
    private String fechaUltMedicion;
    private String horaUltMedicion;
    private String fechaHist;   
     
    private PieChartModel graficoOxigeno = new PieChartModel();
    private PieChartModel graficoTemperatura = new PieChartModel();
    private PieChartModel graficoFR = new PieChartModel();
    private PieChartModel graficoFC = new PieChartModel();
    private PieChartModel graficoPA = new PieChartModel();
   
    private BarChartModel historicoSOxigeno = new BarChartModel();

    private BarChartModel historicoTemperatura = new BarChartModel();
    private LineChartModel historicoFrecCardiaca= new LineChartModel();
    private LineChartModel historicoFrecRespiratoria = new LineChartModel();
    private LineChartModel historicoPreArterial = new LineChartModel();
    
    private DateTimeFormatter formatoFecha =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter formatoHora =  DateTimeFormatter.ofPattern("HH:mm:ss");
    
    private LocalDate fechaCalendario;
    
    public void cargaInicial(){
        try{
            paciente = dashboardBD.getPaciente(Long.parseLong(pacienteId));
            logger.log(Level.INFO,"idPaciente: {0}",paciente.getIdPaciente());
            medidasComp = dashboardBD.getListaMedidas(paciente);
            logger.log(Level.INFO,"Mediciones recuperadas: {0}",medidasComp.size());
              
            if(medidasComp.isEmpty()){
                logger.log(Level.SEVERE,"No hay mediciones registradas para el paciente {0}",paciente.getIdPaciente().toString());
            }else{
                EntMedidas medida = medidasComp.get(medidasComp.size()-1);
                
                pacSaturacionOxg = medida.getSaturacionOxigeno();
                pacTemperatura = medida.getTemperatura();
                pacFrecRespiratoria = medida.getFrecRespiratoria();
                pacFrecCardiaca = medida.getFrecCardiaca();
                
                //ESTOS DATOS NO SE ENCUENTRAN EN LA BASE DE DATOS
                pacPreArtSistolica = medida.getPreArtSistolica();
                pacPreArtDiastolica =medida.getPreArtDiastolica();
              
                LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of("UTC"));

                fechaUltMedicion=fechaMedicion.format(formatoFecha);
                horaUltMedicion=fechaMedicion.format(formatoHora);
                  
                cargaGraficoOxigeno();
                cargaGraficoTemperatura();
                cargaGraficoFRespiratoria();
                cargaGarficoFCardiaca();
                cargaGarficoPArterial();  

                cargaHistoricoSOxigeno();
                cargaHistoricoTemperatura();
                cargaHistoricoFCardiaca();
                cargaHistoricoFRespiratoria();
                cargaHistoricoPArterial();
            }      
            
        }catch(NoExistePacienteDashException ex1){
            logger.log(Level.SEVERE,"Error al obtener paciente.");
        }catch(NoExisteMedicionesException ex2){
            logger.log(Level.SEVERE,"Error al obtener lista de mediciones.");
        }
    }  
    
    public void updateDashboard(){
        if (fechaCalendario!=null){
            fechaHist =fechaCalendario.format(formatoFecha);
            cargaHistoricoSOxigeno();
            cargaHistoricoSOxigeno();
            cargaHistoricoTemperatura();
            cargaHistoricoFCardiaca();
            cargaHistoricoFRespiratoria();
            cargaHistoricoPArterial();
        }
        
    }
        
    @PostConstruct
    public void DashboardPacienteMB(){
    }   
 
    public void cargaGraficoOxigeno() {
        String color;
        
        if(pacSaturacionOxg<86){
            color="ff5500";
            graficoOxigeno.set("Hipoxia Grave [Menos de 86%]", 0);
        }else if(pacSaturacionOxg<91){
            color="fcac30";
            graficoOxigeno.set("Hipoxia Moderada [86% - 90%]", 0);
        }else if(pacSaturacionOxg<95){
            color="fcf330";
            graficoOxigeno.set("Hipoxia Leve [91% - 94%]", 0);
        }else{
            color="60d319";
            graficoOxigeno.set("Normal [95% - 100%]", 0);
        }
        graficoOxigeno.setLegendPosition("se");
        graficoOxigeno.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        graficoOxigeno.setShadow(false);
        graficoOxigeno.setSeriesColors(color);   
        graficoOxigeno.setExtender("customExtender");
    }
    
    public void cargaGraficoTemperatura() {   
        String color;
        
        if(pacTemperatura>40){
            color="ff5500";
            graficoTemperatura.set("Hipertermia [Mas de 40°]", 0);
        }else if(pacTemperatura>37.5){
            color="ffc900";
            graficoTemperatura.set("Fiebre [Mas de 37.5°]", 0);
        }else if(pacTemperatura>36.2){
            color="60d319";
            graficoTemperatura.set("Normal [36.2° - 37.2°]", 0);
        }else{
            color="1984d3";
            graficoTemperatura.set("Hipotermia [Menos de 35°]", 0);
        }           
        graficoTemperatura.setLegendPosition("se");
        graficoTemperatura.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        graficoTemperatura.setShadow(false);
        graficoFR.setSeriesColors(color);        
        graficoTemperatura.setExtender("customExtender");
    }
    
    public void cargaGraficoFRespiratoria() {
        
        String color;
        if(pacFrecRespiratoria<12){
            color="ff5500";
            graficoFR.set("Bradipnea [Menos de 12]", 0);
        }else if(pacFrecRespiratoria>=12 && pacFrecRespiratoria<=20){
            color="60d319";
            graficoFR.set("Normal [12 - 20]", 0);
        }else{
            color="ffc900";
            graficoFR.set("Taquipnea [Mas de 20]", 0);
        } 
        graficoFR.setLegendPosition("se");
        graficoFR.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        graficoFR.setShadow(false);
        graficoFR.setSeriesColors(color);        
        graficoFR.setExtender("customExtender");
    }
    
    public void cargaGarficoFCardiaca() {
        
        String color;
        
        if(pacFrecCardiaca<60){
            color="ff5500";
            graficoFC.set("Bradicardia [Menos de 60]", 0);
        }else if(pacFrecCardiaca>=60 && pacFrecCardiaca<=100){
            color="60d319";
            graficoFC.set("Normal [60 - 100]", 0);
        }else{
            color="ffc900";
            graficoFC.set("Taquicardia [Mas de 100]", 0);
        }   
        graficoFC.setLegendPosition("se");
        graficoFC.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        graficoFC.setShadow(false);
        graficoFC.setSeriesColors(color);
        graficoFC.setExtender("customExtender");
    }
    
    public void cargaGarficoPArterial() {
        String color;
        if(pacPreArtSistolica<100 && pacPreArtDiastolica<60){
            color="ffc900";
            graficoPA.set("Hipotension [Menos de 100/60 mmHg]", 0);
        }else if(pacPreArtSistolica>140 && pacPreArtDiastolica>90){
            color="ffc900";
            graficoPA.set("Hipertension [Mas de 140/90 mmHg]", 0);
        }else{
            color="60d319";
            graficoPA.set("Normal [110/70 - 140/90] mmHg", 0);
        }          
        graficoPA.setLegendPosition("se");
        graficoPA.setLegendPlacement(LegendPlacement.OUTSIDEGRID);   
        graficoPA.setShadow(false);
        graficoPA.setSeriesColors(color);    
        graficoPA.setExtender("customExtender");
    }
    
    public void cargaHistoricoSOxigeno() {
        
        historicoSOxigeno.clear();
        
        ChartSeries p1 = new ChartSeries();
        p1.setLabel("Saturacion de oxígeno");
        //p1.setLabel("Normal [95% - 100%]");
                
        //ft.setTimeZone(TimeZone.getTimeZone("UTC"));
        //logger.log(Level.INFO,"fechaHist: {0}",fechaHist);

        for(EntMedidas medida:medidasComp){
            
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of("UTC"));
         
            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                p1.set(fechaMedicion.format(formatoHora), medida.getSaturacionOxigeno());
            }
        }
       
        /*ChartSeries p2 = new ChartSeries();
        p2.setLabel("Hipoxia Leve [91% - 94%]");
        
        ChartSeries p3 = new ChartSeries();
        p3.setLabel("Hipoxia Moderada [86% - 90%]");
        
        ChartSeries p4 = new ChartSeries();
        p4.setLabel("Hipoxia Grave [Menos de 85%]");*/

        historicoSOxigeno.addSeries(p1);
        //historicoSOxigeno.addSeries(p2);
        //historicoSOxigeno.addSeries(p3);
        //historicoSOxigeno.addSeries(p4);
        
        historicoSOxigeno.setTitle("Saturación de Oxígeno");
        historicoSOxigeno.setLegendPosition("se");
        historicoSOxigeno.setShowPointLabels(true);
        historicoSOxigeno.setStacked(false);
        historicoSOxigeno.setBarWidth(8);
        
        //historicoSOxigeno.setSeriesColors("60d319,fcf330,fcac30,ff5500");
        historicoSOxigeno.setSeriesColors("3492eb");
                
        Axis xAxis = historicoSOxigeno.getAxis(AxisType.X);
        xAxis.setLabel("Tiempo (Horas)");
        //xAxis.setTickAngle(-30);
        
        Axis yAxis = historicoSOxigeno.getAxis(AxisType.Y);
        yAxis.setLabel("Porcentaje (%)");
        yAxis.setTickFormat("%.1f");
        yAxis.setMin(0);
        yAxis.setMax(120);
    }
    
    public void cargaHistoricoTemperatura() {
        
        historicoTemperatura.clear();
        
        ChartSeries p1 = new ChartSeries();
        p1.setLabel("Temperatura");
        //saturacion1.setLabel("Normal [36.2° - 37.2°]");

        /*ft.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        for(EntMedidas medida:medidasComp){
            saturacion1.set(ft.format(medida.getFechaMedicion()), medida.getTemperatura());
        }*/
        
        for(EntMedidas medida:medidasComp){
            
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of("UTC"));
         
            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                p1.set(fechaMedicion.format(formatoHora), medida.getTemperatura());
            }
        }
        /*ChartSeries saturacion2 = new ChartSeries();
        saturacion2.setLabel("Hipotermia [Menos de 35°]");
        
        ChartSeries saturacion3 = new ChartSeries();
        saturacion3.setLabel("Fiebre [Mas de 37.5°]");
        
        ChartSeries saturacion4 = new ChartSeries();
        saturacion4.setLabel("Hipertermia [Mas de 40°]");
        */
        historicoTemperatura.addSeries(p1);
        //historicoTemperatura.addSeries(saturacion2);
        //historicoTemperatura.addSeries(saturacion3);
        //historicoTemperatura.addSeries(saturacion4);
        
        historicoTemperatura.setTitle("Temperatura");
        historicoTemperatura.setLegendPosition("se");
        historicoTemperatura.setShowPointLabels(true);
        historicoTemperatura.setStacked(true);
        historicoTemperatura.setBarWidth(8);
        
        //historicoTemperatura.setSeriesColors("60d319,1984d3,ffc900,ff5500");
        historicoTemperatura.setSeriesColors("3492eb");

        Axis xAxis = historicoTemperatura.getAxis(AxisType.X);
        xAxis.setLabel("Tiempo (Horas)");
        //xAxis.setTickAngle(-30);

        Axis yAxis = historicoTemperatura.getAxis(AxisType.Y);
        yAxis.setLabel("Grados (°C)");
        yAxis.setTickFormat("%.1f");
        yAxis.setMin(0);
        yAxis.setMax(50);
    }

    public void cargaHistoricoFCardiaca() {
        historicoFrecCardiaca.clear();
        
        ChartSeries p1 = new ChartSeries();
        p1.setLabel("Frecuencia cardiaca");
        
        /*ft.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        for(EntMedidas medida:medidasComp){
            p1.set(ft.format(medida.getFechaMedicion()), medida.getFrecCardiaca());
        }*/
        
        for(EntMedidas medida:medidasComp){
            
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of("UTC"));
         
            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                p1.set(fechaMedicion.format(formatoHora), medida.getFrecCardiaca());
            }
        }
        
        /*ChartSeries p2 = new ChartSeries();
        p2.setLabel("Bradicardia [Menos de 60]");
        
        ChartSeries p3 = new ChartSeries();
        p3.setLabel("Taquiardia [Mas de 100]");*/
        
        historicoFrecCardiaca.addSeries(p1);
        //historicoFrecCardiaca.addSeries(p2);
        //historicoFrecCardiaca.addSeries(p3);
        
        //historicoFrecCardiaca.setSeriesColors("60d319,ffc900,ff5500");
        historicoFrecCardiaca.setSeriesColors("3492eb");

        historicoFrecCardiaca.setTitle("Frecuencia Cardíaca");
        historicoFrecCardiaca.setLegendPosition("se");
        historicoFrecCardiaca.setShowPointLabels(true);
                
        historicoFrecCardiaca.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));
        
        Axis yAxis = historicoFrecCardiaca.getAxis(AxisType.Y);
        yAxis.setLabel("Frecuencia (LPM)");
        yAxis.setMin(0);
        yAxis.setMax(120);
    }
    
    public void cargaHistoricoFRespiratoria() {
        
        historicoFrecRespiratoria.clear();
        
        ChartSeries p1 = new ChartSeries();
        p1.setLabel("Frecuencia Respiratoria");
        
        /*ft.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        for(EntMedidas medida:medidasComp){
            p1.set(ft.format(medida.getFechaMedicion()), medida.getFrecRespiratoria());
        }*/
        for(EntMedidas medida:medidasComp){
            
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of("UTC"));
         
            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                p1.set(fechaMedicion.format(formatoHora), medida.getFrecRespiratoria());
            }
        }

        /*ChartSeries p2 = new ChartSeries();
        p2.setLabel("Bradipnea [Menos de 12]");
        
        ChartSeries p3 = new ChartSeries();
        p3.setLabel("Taquipnea [Mas de 20]");*/
        
        historicoFrecRespiratoria.addSeries(p1);
        //historicoFrecRespiratoria.addSeries(p2);
        //historicoFrecRespiratoria.addSeries(p3);
        
        //historicoFrecRespiratoria.setSeriesColors("60d319,ffc900,ff5500");
        historicoFrecRespiratoria.setSeriesColors("3492eb");

        
        historicoFrecRespiratoria.setTitle("Frecuencia Respiratoria");
        historicoFrecRespiratoria.setLegendPosition("ne");
        historicoFrecRespiratoria.setShowPointLabels(true);
        
        historicoFrecRespiratoria.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));
        Axis yAxis = historicoFrecRespiratoria.getAxis(AxisType.Y);
        yAxis.setLabel("Frecuencia (RPM)");
        yAxis.setMin(0);
        yAxis.setMax(120);
    }
    
    public void cargaHistoricoPArterial() {
        historicoPreArterial.clear();
        
        ChartSeries sistolica = new ChartSeries();
        sistolica.setLabel("Presión Arterial Sistólica");
        
        ChartSeries diastolica = new ChartSeries();
        diastolica.setLabel("Presión Arterial Distólica");
        
        for(EntMedidas medida:medidasComp){
            
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of("UTC"));
         
            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                sistolica.set(fechaMedicion.format(formatoHora), medida.getPreArtSistolica());
                diastolica.set(fechaMedicion.format(formatoHora), medida.getPreArtDiastolica());
            }
        }
        
        /*for(EntMedidas medida:medidasComp){
            sistolica.set(ft.format(medida.getFechaMedicion()), medida.getPreArtSistolica());
        }

        for(EntMedidas medida:medidasComp){
            diastolica.set(ft.format(medida.getFechaMedicion()), medida.getPreArtDiastolica());
        }*/
        
        
        historicoPreArterial.addSeries(sistolica);
        historicoPreArterial.addSeries(diastolica);
        
        historicoPreArterial.setTitle("Presión Arterial Sistólica y Diastólica");
        historicoPreArterial.setLegendPosition("se");
        historicoPreArterial.setShowPointLabels(true);
        historicoPreArterial.getAxes().put(AxisType.X, new CategoryAxis("Tiempo(Horas)"));
        
        Axis yAxis = historicoPreArterial.getAxis(AxisType.Y);
        yAxis.setLabel("mmHg");
        yAxis.setMin(0);
        yAxis.setMax(220);
    }
    

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public String getPacientePrimerAp() {
        return pacientePrimerAp;
    }

    public void setPacientePrimerAp(String pacientePrimerAp) {
        this.pacientePrimerAp = pacientePrimerAp;
    }

    public String getPacienteSegundoAp() {
        return pacienteSegundoAp;
    }

    public void setPacienteSegundoAp(String pacienteSegundoAp) {
        this.pacienteSegundoAp = pacienteSegundoAp;
    }
    
    public String getPacienteId(){
        return pacienteId;
    }
    
    public void setPacienteId(String pacienteId){
        this.pacienteId = pacienteId;
    }
    
    public float getPacSaturacionOxg() {
        return pacSaturacionOxg;
    }

    public void setPacSaturacionOxg(float pacSaturacionOxg) {
        this.pacSaturacionOxg = pacSaturacionOxg;
    }

    public float getPacTemperatura() {
        return pacTemperatura;
    }

    public void setPacTemperatura(float pacTemperatura) {
        this.pacTemperatura = pacTemperatura;
    }

    public short getPacFrecRespiratoria() {
        return pacFrecRespiratoria;
    }

    public void setPacFrecRespiratoria(short pacFrecRespiratoria) {
        this.pacFrecRespiratoria = pacFrecRespiratoria;
    }

    public short getPacFrecCardiaca() {
        return pacFrecCardiaca;
    }

    public void setPacFrecCardiaca(short pacFrecCardiaca) {
        this.pacFrecCardiaca = pacFrecCardiaca;
    }

    public int getPacPreArtSistolica() {
        return pacPreArtSistolica;
    }

    public void setPacPreArtSistolica(int pacPreArtSistolica) {
        this.pacPreArtSistolica = pacPreArtSistolica;
    }

    public int getPacPreArtDiastolica() {
        return pacPreArtDiastolica;
    }

    public void setPacPreArtDiastolica(int pacPreArtDiastolica) {
        this.pacPreArtDiastolica = pacPreArtDiastolica;
    }
    
    public String getFechaUltMedicion() {
        return fechaUltMedicion;
    }

    public void setFechaUltMedicion(String fechaUltMedicion) {
        this.fechaUltMedicion = fechaUltMedicion;
    }
    
    public String getHoraUltMedicion() {
        return horaUltMedicion;
    }

    public void setHoraUltMedicion(String horaUltMedicion) {
        this.horaUltMedicion = horaUltMedicion;
    }

    public String getFechaHist() {
        return fechaHist;
    }

    public void setFechaHist(String fechaHist) {
        this.fechaHist = fechaHist;
    }    
    
    public LocalDate getFechaCalendario() {
        return fechaCalendario;
    }

    public void setFechaCalendario(LocalDate fechaCalendario) {
        this.fechaCalendario = fechaCalendario;
    }
        
    public PieChartModel getGraficoOxigeno() {
        return graficoOxigeno;
    }

    public void setGraficoOxigeno(PieChartModel graficoOxigeno) {
        this.graficoOxigeno = graficoOxigeno;
    }

    public PieChartModel getGraficoTemperatura() {
        return graficoTemperatura;
    }

    public void setGraficoTemperatura(PieChartModel graficoTemperatura) {
        this.graficoTemperatura = graficoTemperatura;
    }

    public PieChartModel getGraficoFR() {
        return graficoFR;
    }

    public void setGraficoFR(PieChartModel graficoFR) {
        this.graficoFR = graficoFR;
    }

    public PieChartModel getGraficoFC() {
        return graficoFC;
    }

    public void setGraficoFC(PieChartModel graficoFC) {
        this.graficoFC = graficoFC;
    }

    public PieChartModel getGraficoPA() {
        return graficoPA;
    }

    public void setGraficoPA(PieChartModel graficoPA) {
        this.graficoPA = graficoPA;
    }
    
    public BarChartModel getHistoricoSOxigeno() {
        return historicoSOxigeno;
    }

    public void setHistoricoSOxigeno(BarChartModel historicoSOxigeno) {
        this.historicoSOxigeno = historicoSOxigeno;
    }

    public BarChartModel getHistoricoTemperatura() {
        return historicoTemperatura;
    }

    public void setHistoricoTemperatura(BarChartModel historicoTemperatura) {
        this.historicoTemperatura = historicoTemperatura;
    }

    public LineChartModel getHistoricoFrecCardiaca() {
        return historicoFrecCardiaca;
    }

    public void setHistoricoFrecCardiaca(LineChartModel historicoFrecCardiaca) {
        this.historicoFrecCardiaca = historicoFrecCardiaca;
    }

    public LineChartModel getHistoricoFrecRespiratoria() {
        return historicoFrecRespiratoria;
    }

    public void setHistoricoFrecRespiratoria(LineChartModel historicoFrecRespiratoria) {
        this.historicoFrecRespiratoria = historicoFrecRespiratoria;
    }

    public LineChartModel getHistoricoPreArterial() {
        return historicoPreArterial;
    }

    public void setHistoricoPreArterial(LineChartModel historicoPreArterial) {
        this.historicoPreArterial = historicoPreArterial;
    }
    
}