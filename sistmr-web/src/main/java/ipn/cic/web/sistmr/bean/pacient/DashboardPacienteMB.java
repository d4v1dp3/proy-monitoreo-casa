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
    
    private boolean showOxgWarning;
    private boolean showTempWarning;
    private boolean showFrespWarning;  
    private boolean showFcardWarning; 
    private boolean pSistWarning;
    private boolean pDiasWarning;
    private boolean showPartWarning;
     
    private LineChartModel historicoSOxigeno = new LineChartModel();
    private LineChartModel historicoTemperatura = new LineChartModel();
    private LineChartModel historicoFrecCardiaca= new LineChartModel();
    private LineChartModel historicoFrecRespiratoria = new LineChartModel();
    private LineChartModel historicoPreArterial = new LineChartModel();
    
    private DateTimeFormatter formatoFecha =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter formatoHora =  DateTimeFormatter.ofPattern("HH:mm:ss");
    
    private LocalDate fechaCalendario;
    
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
                
                showOxgWarning = pacSaturacionOxg<valoresRef.getSatOxigenoMin() || pacSaturacionOxg>valoresRef.getSatOxigenoMax();
                showTempWarning = pacTemperatura<valoresRef.getTemperaturaMin() || pacTemperatura>valoresRef.getTemperaturaMax();
                showFrespWarning = pacFrecRespiratoria<valoresRef.getFrecRespiratoriaMin() || pacFrecRespiratoria>valoresRef.getFrecRespiratoriaMax();
                showFcardWarning = pacFrecCardiaca<valoresRef.getFrecCardiacaMin() || pacFrecCardiaca>valoresRef.getFrecCardiacaMax();
                pSistWarning = pacPreArtSistolica<valoresRef.getPreArtSistolicaMin() || pacPreArtSistolica>valoresRef.getPreArtSistolicaMax();
                pDiasWarning = pacPreArtDiastolica<valoresRef.getPreArtDiastolicaMin() || pacPreArtDiastolica>valoresRef.getPreArtDiastolicaMax();
                showPartWarning = pSistWarning || pDiasWarning;  
                        
                LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of("UTC"));

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
        
        ChartSeries soxigeno = new ChartSeries();
        soxigeno.setLabel("Saturacion de oxígeno");

        for(EntMedidas medida:medidasComp){       
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of("UTC"));
         
            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                soxigeno.set(fechaMedicion.format(formatoHora), medida.getSaturacionOxigeno());
            }
        }
        
        historicoSOxigeno.addSeries(soxigeno);
        historicoSOxigeno.setTitle("Saturación de Oxígeno");
        historicoSOxigeno.setLegendPosition("se");
        historicoSOxigeno.setShowPointLabels(true);
        historicoSOxigeno.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));
                
        Axis yAxis = historicoSOxigeno.getAxis(AxisType.Y);
        yAxis.setLabel("Porcentaje (%)");
        yAxis.setTickFormat("%.1f");
        yAxis.setMin(0);
        yAxis.setMax(120);
    }
    
    public void cargaHistoricoTemperatura() {
        historicoTemperatura.clear();
        
        ChartSeries temperatura = new ChartSeries();
        temperatura.setLabel("Temperatura");
        
        for(EntMedidas medida:medidasComp){
            
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of("UTC"));
         
            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                temperatura.set(fechaMedicion.format(formatoHora), medida.getTemperatura());
            }
        }
        historicoTemperatura.addSeries(temperatura);      
        historicoTemperatura.setTitle("Temperatura");
        historicoTemperatura.setLegendPosition("se");
        historicoTemperatura.setShowPointLabels(true);
        historicoTemperatura.setStacked(true);
        historicoTemperatura.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));

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
                
        for(EntMedidas medida:medidasComp){
            
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of("UTC"));
         
            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                p1.set(fechaMedicion.format(formatoHora), medida.getFrecCardiaca());
            }
        }
        historicoFrecCardiaca.addSeries(p1);
        historicoFrecCardiaca.setTitle("Frecuencia Cardíaca");
        historicoFrecCardiaca.setLegendPosition("se");
        historicoFrecCardiaca.setShowPointLabels(true);
                
        historicoFrecCardiaca.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));
        
        Axis yAxis = historicoFrecCardiaca.getAxis(AxisType.Y);
        yAxis.setLabel("Frecuencia (LPM)");
        yAxis.setMin(0);
        yAxis.setMax(120);
    }
    
    public void cargaHistoricoFRespiratoria(){     
        historicoFrecRespiratoria.clear();
        
        ChartSeries p1 = new ChartSeries();
        p1.setLabel("Frecuencia Respiratoria");
        
        for(EntMedidas medida:medidasComp){
            
            LocalDateTime fechaMedicion = LocalDateTime.ofInstant(medida.getFechaMedicion().toInstant(), ZoneId.of("UTC"));
         
            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                p1.set(fechaMedicion.format(formatoHora), medida.getFrecRespiratoria());
            }
        }

        historicoFrecRespiratoria.addSeries(p1);
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

    public boolean isShowOxgWarning() {
        return showOxgWarning;
    }

    public void setShowOxgWarning(boolean showOxgWarning) {
        this.showOxgWarning = showOxgWarning;
    }

    public boolean isShowTempWarning() {
        return showTempWarning;
    }

    public void setShowTempWarning(boolean showTempWarning) {
        this.showTempWarning = showTempWarning;
    }

    public boolean isShowFrespWarning() {
        return showFrespWarning;
    }

    public void setShowFrespWarning(boolean showFrespWarning) {
        this.showFrespWarning = showFrespWarning;
    }

    public boolean isShowFcardWarning() {
        return showFcardWarning;
    }

    public void setShowFcardWarning(boolean showFcardWarning) {
        this.showFcardWarning = showFcardWarning;
    }

    public boolean ispSistWarning() {
        return pSistWarning;
    }

    public void setpSistWarning(boolean pSistWarning) {
        this.pSistWarning = pSistWarning;
    }

    public boolean ispDiasWarning() {
        return pDiasWarning;
    }

    public void setpDiasWarning(boolean pDiasWarning) {
        this.pDiasWarning = pDiasWarning;
    }

    public boolean isShowPartWarning() {
        return showPartWarning;
    }

    public void setShowPartWarning(boolean showPartWarning) {
        this.showPartWarning = showPartWarning;
    }
        
}