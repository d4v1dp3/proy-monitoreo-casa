/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.pacient;

import ipn.cic.sistmr.exception.NoExisteEstadoPacException;
import ipn.cic.sistmr.exception.NoExisteMedicionesException;
import ipn.cic.sistmr.exception.NoExistePacienteDashException;
import ipn.cic.sistmr.exception.NoExisteValoresRefException;
import ipn.cic.sistmr.modelo.EntEstadopaciente;
import ipn.cic.sistmr.modelo.EntMedidas;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntValoresReferencia;
import ipn.cic.sistmr.sesion.ValoresReferenciaSBLocal;
import ipn.cic.sistmr.sesion.dashboard.DashboardBDLocal;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

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

    @EJB
    private ValoresReferenciaSBLocal valoresSB;
    
    private EntPaciente paciente;
    private List<EntMedidas> medidasComp;
    private EntValoresReferencia valoresRef; 
    private EntEstadopaciente estadoPaciente;
    
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
    private String pacEstado;
    
    private String fechaUltMedicion;
    private String horaUltMedicion;
    private String fechaHist;   
    
    private String satOxgColor;
    private String tempColor;
    private String frespColor;
    private String fcardColor;
    private String pArtColor;
        
    private LineChartModel historicoSOxigeno = new LineChartModel();
    private LineChartModel historicoTemperatura = new LineChartModel();
    private LineChartModel historicoFrecCardiaca= new LineChartModel();
    private LineChartModel historicoFrecRespiratoria = new LineChartModel();
    private LineChartModel historicoPreArterial = new LineChartModel();
    
    private DateTimeFormatter formatoFecha =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter formatoHora =  DateTimeFormatter.ofPattern("HH:mm:ss");
    
    private LocalDate fechaCalendario;
    private TimeZone tz = TimeZone.getDefault();
        
    public void cargaInicial(){
        try{
            paciente = dashboardBD.getPaciente(Long.parseLong(pacienteId));
            medidasComp = dashboardBD.getListaMedidas(paciente);
            valoresRef = valoresSB.getValoresReferenciaId(new Short("1")); 
            estadoPaciente = dashboardBD.getEstadoPac(Long.parseLong(pacienteId));

            if(medidasComp.isEmpty()){
                logger.log(Level.SEVERE,"No hay mediciones registradas para el paciente {0}",paciente.getIdPaciente().toString());
            }else{
                EntMedidas medida = medidasComp.get(medidasComp.size()-1);
                
                pacSaturacionOxg = medida.getSaturacionOxigeno();
                pacTemperatura = medida.getTemperatura();
                pacFrecRespiratoria = medida.getFrecRespiratoria();
                pacFrecCardiaca = medida.getFrecCardiaca();
                pacPreArtSistolica = medida.getPreArtSistolica();
                pacPreArtDiastolica =medida.getPreArtDiastolica();
                pacEstado = estadoPaciente.getDescripcion(); 
                
                if(pacSaturacionOxg>=valoresRef.getSatOxigenoNormalMin() && pacSaturacionOxg<valoresRef.getSatOxigenoNormalMax()){
                    satOxgColor="greenBackground";     
                }else if(pacSaturacionOxg>=valoresRef.getSatOxigenoWarningMin() && pacSaturacionOxg<valoresRef.getSatOxigenoWarningMax()){
                     satOxgColor="yellowBackground"; 
                }else if(pacSaturacionOxg>=valoresRef.getSatOxigenoAlertMin() && pacSaturacionOxg<valoresRef.getSatOxigenoAlertMax()){
                    satOxgColor="redBackground";
                }
    
                if(pacTemperatura>=valoresRef.getTemperaturaNormalMin() && pacTemperatura<valoresRef.getTemperaturaNormalMax()){
                    tempColor="greenBackground";
                }else if(pacTemperatura>=valoresRef.getTemperaturaWarningMin() && pacTemperatura<valoresRef.getTemperaturaWarningMax()){
                    tempColor="yellowBackground";
                }else if(pacTemperatura>=valoresRef.getTemperaturaAlertMin() && pacTemperatura<valoresRef.getTemperaturaAlertMax()){
                    tempColor="redBackground";
                }
                 
                if(pacFrecRespiratoria>=valoresRef.getFrecRespiratoriaNormalMin() && pacFrecRespiratoria<=valoresRef.getFrecRespiratoriaNormalMax()){
                    frespColor="greenBackground";  
                }else if(pacFrecRespiratoria>=valoresRef.getFrecRespiratoriaWarningMin() && pacFrecRespiratoria<=valoresRef.getFrecRespiratoriaWarningMax()){
                    frespColor="yellowBackground";
                }else if(pacFrecRespiratoria>=valoresRef.getFrecRespiratoriaAlertMin() && pacFrecRespiratoria<=valoresRef.getFrecRespiratoriaAlertMax()){
                    frespColor="redBackground";
                }
                 
                if(pacFrecCardiaca>=valoresRef.getFrecCardiacaNormalMin() && pacFrecCardiaca<=valoresRef.getFrecCardiacaNormalMax()){
                    fcardColor="greenBackground";   
                }else if(pacFrecCardiaca>=valoresRef.getFrecCardiacaWarningMin() && pacFrecCardiaca<=valoresRef.getFrecCardiacaWarningMax()){
                    fcardColor="yellowBackground";  
                }else if(pacFrecCardiaca>=valoresRef.getFrecCardiacaAlertMin() && pacFrecCardiaca<=valoresRef.getFrecCardiacaAlertMax()){
                    fcardColor="redBackground";
                }
                    
                if((pacPreArtSistolica>=valoresRef.getPreArtSistolicaNormalMin() && pacPreArtSistolica<=valoresRef.getPreArtSistolicaNormalMax()) 
                    && (pacPreArtDiastolica>=valoresRef.getPreArtDiastolicaNormalMin() && pacPreArtDiastolica<=valoresRef.getPreArtDiastolicaNormalMax())){
                    pArtColor="greenBackground";
                }else if((pacPreArtSistolica>=valoresRef.getPreArtSistolicaWarningMin() && pacPreArtSistolica<=valoresRef.getPreArtSistolicaWarningMax())
                        ||(pacPreArtDiastolica>=valoresRef.getPreArtDiastolicaWarningMin() && pacPreArtDiastolica<=valoresRef.getPreArtDiastolicaWarningMax())){
                    pArtColor="yellowBackground"; 
                }else if((pacPreArtSistolica>=valoresRef.getPreArtSistolicaAlertMin() && pacPreArtSistolica<=valoresRef.getPreArtSistolicaAlertMax())
                        ||(pacPreArtDiastolica>=valoresRef.getPreArtDiastolicaAlertMin() && pacPreArtDiastolica<=valoresRef.getPreArtDiastolicaAlertMax())){
                    pArtColor="redBackground";
                }
                
                LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));

                fechaUltMedicion=fechaMedicion.format(formatoFecha);
                horaUltMedicion=fechaMedicion.format(formatoHora);

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
        }catch(NoExisteValoresRefException ex3){
            logger.log(Level.SEVERE,"Error al obtener valores de referencia.");
        }catch(NoExisteEstadoPacException ex4){
            logger.log(Level.SEVERE,"Error al obtener estado del paciente.");
        }
    }  
    
    public void updateDashboard(){
        if (fechaCalendario!=null){
            fechaHist =fechaCalendario.format(formatoFecha);
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
    
    public void cargaHistoricoSOxigeno() {
        
        historicoSOxigeno.clear();
        
        ChartSeries minVal = new ChartSeries();
        minVal.setLabel("Valor mínimo");
        ChartSeries maxVal = new ChartSeries();
        maxVal.setLabel("Valor máximo");
        ChartSeries soxigeno = new ChartSeries();
        soxigeno.setLabel("Saturacion de oxígeno");
        
        for(int i=0;i<medidasComp.size();i++){
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medidasComp.get(i).getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));

            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                soxigeno.set(fechaMedicion.format(formatoHora), medidasComp.get(i).getSaturacionOxigeno()); 
                if(i==0 || i==(medidasComp.size()-1)){
                    minVal.set(fechaMedicion.format(formatoHora), valoresRef.getSatOxigenoNormalMin());
                    maxVal.set(fechaMedicion.format(formatoHora), valoresRef.getSatOxigenoNormalMax());
                }
            }
        }
               
        historicoSOxigeno.addSeries(soxigeno);
        historicoSOxigeno.addSeries(minVal);
        historicoSOxigeno.addSeries(maxVal);
        historicoSOxigeno.setSeriesColors("387df5,ff8a7d,ff8a7d");
        historicoSOxigeno.setTitle("Saturación de Oxígeno");
        historicoSOxigeno.setLegendPosition("se");
        historicoSOxigeno.setShowPointLabels(true);
        historicoSOxigeno.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));
                
        Axis yAxis = historicoSOxigeno.getAxis(AxisType.Y);
        yAxis.setLabel("Porcentaje (%)");
        yAxis.setTickFormat("%.1f");
        yAxis.setMin(50);
        yAxis.setMax(110);
    }
    
    public void cargaHistoricoTemperatura() {
        
        historicoTemperatura.clear();
    
        ChartSeries minVal = new ChartSeries();
        minVal.setLabel("Valor mínimo");
        ChartSeries maxVal = new ChartSeries();
        maxVal.setLabel("Valor máximo");
        ChartSeries temperatura = new ChartSeries();
        temperatura.setLabel("Temperatura");
        
       for(int i=0;i<medidasComp.size();i++){
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medidasComp.get(i).getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));

            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                temperatura.set(fechaMedicion.format(formatoHora), medidasComp.get(i).getTemperatura());
                if(i==0 || i==(medidasComp.size()-1)){
                    minVal.set(fechaMedicion.format(formatoHora), valoresRef.getTemperaturaNormalMin());
                    maxVal.set(fechaMedicion.format(formatoHora), valoresRef.getTemperaturaNormalMax());
                }
            }
        }
        historicoTemperatura.addSeries(temperatura);
        historicoTemperatura.addSeries(minVal);
        historicoTemperatura.addSeries(maxVal);
        historicoTemperatura.setSeriesColors("387df5,ff8a7d,ff8a7d");
        historicoTemperatura.setTitle("Temperatura");
        historicoTemperatura.setLegendPosition("se");
        historicoTemperatura.setShowPointLabels(true);
        historicoTemperatura.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));

        Axis yAxis = historicoTemperatura.getAxis(AxisType.Y);
        yAxis.setLabel("Grados (°C)");
        yAxis.setTickFormat("%.1f");
        yAxis.setMin(30);
        yAxis.setMax(45);
    }

    public void cargaHistoricoFCardiaca() {
        historicoFrecCardiaca.clear();
        
        ChartSeries minVal = new ChartSeries();
        minVal.setLabel("Valor mínimo");
        ChartSeries maxVal = new ChartSeries();
        maxVal.setLabel("Valor máximo");
        ChartSeries frecuenciaC = new ChartSeries();
        frecuenciaC.setLabel("Frecuencia cardiaca");
                
        for(int i=0;i<medidasComp.size();i++){
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medidasComp.get(i).getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));

            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                frecuenciaC.set(fechaMedicion.format(formatoHora), medidasComp.get(i).getFrecCardiaca());
                if(i==0 || i==(medidasComp.size()-1)){
                    minVal.set(fechaMedicion.format(formatoHora), valoresRef.getFrecCardiacaNormalMin());
                    maxVal.set(fechaMedicion.format(formatoHora), valoresRef.getFrecCardiacaNormalMax());
                }
            }
        }
        historicoFrecCardiaca.addSeries(frecuenciaC);
        historicoFrecCardiaca.addSeries(minVal);
        historicoFrecCardiaca.addSeries(maxVal);
        historicoFrecCardiaca.setSeriesColors("387df5,ff8a7d,ff8a7d");
        historicoFrecCardiaca.setTitle("Frecuencia Cardíaca");
        historicoFrecCardiaca.setLegendPosition("se");
        historicoFrecCardiaca.setShowPointLabels(true); 
        historicoFrecCardiaca.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));
        
        Axis yAxis = historicoFrecCardiaca.getAxis(AxisType.Y);
        yAxis.setLabel("Frecuencia (LPM)");
        yAxis.setMin(50);
        yAxis.setMax(120);
    }
    
    public void cargaHistoricoFRespiratoria() {
        
        historicoFrecRespiratoria.clear();
        
        ChartSeries minVal = new ChartSeries();
        minVal.setLabel("Valor mínimo");
        ChartSeries maxVal = new ChartSeries();
        maxVal.setLabel("Valor máximo");
        ChartSeries frecuenciaR = new ChartSeries();
        frecuenciaR.setLabel("Frecuencia Respiratoria");
        
        for(int i=0;i<medidasComp.size();i++){
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medidasComp.get(i).getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));

            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                frecuenciaR.set(fechaMedicion.format(formatoHora), medidasComp.get(i).getFrecRespiratoria());
                if(i==0 || i==(medidasComp.size()-1)){
                    minVal.set(fechaMedicion.format(formatoHora), valoresRef.getFrecRespiratoriaNormalMin());
                    maxVal.set(fechaMedicion.format(formatoHora), valoresRef.getFrecRespiratoriaNormalMax());
                }
            }
        }

        historicoFrecRespiratoria.addSeries(frecuenciaR);
        historicoFrecRespiratoria.addSeries(minVal);
        historicoFrecRespiratoria.addSeries(maxVal);
        historicoFrecRespiratoria.setSeriesColors("387df5,ff8a7d,ff8a7d");
        historicoFrecRespiratoria.setTitle("Frecuencia Respiratoria");
        historicoFrecRespiratoria.setLegendPosition("ne");
        historicoFrecRespiratoria.setShowPointLabels(true);     
        historicoFrecRespiratoria.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));
        Axis yAxis = historicoFrecRespiratoria.getAxis(AxisType.Y);
        yAxis.setLabel("Frecuencia (RPM)");
        yAxis.setMin(0);
        yAxis.setMax(30);
    }
    
    public void cargaHistoricoPArterial() {
        historicoPreArterial.clear();
        
        ChartSeries sismin = new ChartSeries();
        sismin.setLabel("Valor mínimo P. Sis.");
        ChartSeries sismax = new ChartSeries();
        sismax.setLabel("Valor máximo P. Dias.");
        ChartSeries sistolica = new ChartSeries();
        sistolica.setLabel("Presión Arterial Sistólica");
        
        ChartSeries diasmin = new ChartSeries();
        diasmin.setLabel("Valor mínimo P. Dias.");
        ChartSeries diasmax = new ChartSeries();
        diasmax.setLabel("Valor máximo P. Dias.");
        ChartSeries diastolica = new ChartSeries();
        diastolica.setLabel("Presión Arterial Diastólica");
        
        for(int i=0;i<medidasComp.size();i++){
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medidasComp.get(i).getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));

            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                sistolica.set(fechaMedicion.format(formatoHora), medidasComp.get(i).getPreArtSistolica());
                diastolica.set(fechaMedicion.format(formatoHora), medidasComp.get(i).getPreArtDiastolica());
                if(i==0 || i==(medidasComp.size()-1)){
                    sismin.set(fechaMedicion.format(formatoHora), valoresRef.getPreArtSistolicaNormalMin());
                    sismax.set(fechaMedicion.format(formatoHora), valoresRef.getPreArtSistolicaNormalMax());
                    diasmin.set(fechaMedicion.format(formatoHora), valoresRef.getPreArtDiastolicaNormalMin());
                    diasmax.set(fechaMedicion.format(formatoHora), valoresRef.getPreArtDiastolicaNormalMax());
                    
                }
            }
        }
               
        historicoPreArterial.addSeries(sistolica);
        historicoPreArterial.addSeries(diastolica);
        historicoPreArterial.addSeries(sismin);
        historicoPreArterial.addSeries(sismax);
        historicoPreArterial.addSeries(diasmin);
        historicoPreArterial.addSeries(diasmax);
        historicoPreArterial.setSeriesColors("42cef5,425af5,ff8a7d,ff8a7d,f5b642,f5b642");
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

    public String getPacEstado() {
        return pacEstado;
    }

    public void setPacEstado(String pacEstado) {
        this.pacEstado = pacEstado;
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
        
    public LineChartModel getHistoricoSOxigeno() {
        return historicoSOxigeno;
    }

    public void setHistoricoSOxigeno(LineChartModel historicoSOxigeno) {
        this.historicoSOxigeno = historicoSOxigeno;
    }

    public LineChartModel getHistoricoTemperatura() {
        return historicoTemperatura;
    }

    public void setHistoricoTemperatura(LineChartModel historicoTemperatura) {
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
    
    public String getSatOxgColor() {
        return satOxgColor;
    }

    public void setSatOxgColor(String satOxgColor) {
        this.satOxgColor = satOxgColor;
    }
    
    public String getTempColor() {
        return tempColor;
    }

    public void setTempColor(String tempColor) {
        this.tempColor = tempColor;
    }

    public String getFrespColor() {
        return frespColor;
    }

    public void setFrespColor(String frespColor) {
        this.frespColor = frespColor;
    }

    public String getFcardColor() {
        return fcardColor;
    }

    public void setFcardColor(String fcardColor) {
        this.fcardColor = fcardColor;
    }

    public String getpArtColor() {
        return pArtColor;
    }

    public void setpArtColor(String pArtColor) {
        this.pArtColor = pArtColor;
    }

}