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
import org.primefaces.model.chart.LegendPlacement;
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
    private int nmedidas;
    private int indiceMedida;
    
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
                }else{
                    satOxgColor="redBackground";
                }
    
                if(pacTemperatura>=valoresRef.getTemperaturaNormalMin() && pacTemperatura<valoresRef.getTemperaturaNormalMax()){
                    tempColor="greenBackground";
                }else if(pacTemperatura>=valoresRef.getTemperaturaWarningMin() && pacTemperatura<valoresRef.getTemperaturaWarningMax()){
                    tempColor="yellowBackground";
                }else if(pacTemperatura>=valoresRef.getTemperaturaAlertMin() && pacTemperatura<valoresRef.getTemperaturaAlertMax()){
                    tempColor="redBackground";
                }else{
                    tempColor="redBackground";
                }
                 
                if(pacFrecRespiratoria>=valoresRef.getFrecRespiratoriaNormalMin() && pacFrecRespiratoria<=valoresRef.getFrecRespiratoriaNormalMax()){
                    frespColor="greenBackground";  
                }else if(pacFrecRespiratoria>=valoresRef.getFrecRespiratoriaWarningMin() && pacFrecRespiratoria<=valoresRef.getFrecRespiratoriaWarningMax()){
                    frespColor="yellowBackground";
                }else if(pacFrecRespiratoria>=valoresRef.getFrecRespiratoriaAlertMin() && pacFrecRespiratoria<=valoresRef.getFrecRespiratoriaAlertMax()){
                    frespColor="redBackground";
                }else{
                    frespColor="redBackground";
                }
                 
                if(pacFrecCardiaca>=valoresRef.getFrecCardiacaNormalMin() && pacFrecCardiaca<=valoresRef.getFrecCardiacaNormalMax()){
                    fcardColor="greenBackground";   
                }else if(pacFrecCardiaca>=valoresRef.getFrecCardiacaWarningMin() && pacFrecCardiaca<=valoresRef.getFrecCardiacaWarningMax()){
                    fcardColor="yellowBackground";  
                }else if(pacFrecCardiaca>=valoresRef.getFrecCardiacaAlertMin() && pacFrecCardiaca<=valoresRef.getFrecCardiacaAlertMax()){
                    fcardColor="redBackground";
                }else{
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
                }else{
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
    
    public void getNumMedidasPorDia(){
       
        nmedidas=0;
        indiceMedida=0;
        boolean banderaIndice=true;
        LocalDateTime fechaMedicion;
        
        for(int i=0;i<medidasComp.size();i++){
            fechaMedicion = LocalDateTime.ofInstant(medidasComp.get(i).getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));
            if(fechaHist.equals(fechaMedicion.format(formatoFecha))){
                nmedidas+=1;
                if(banderaIndice==true){
                    indiceMedida=i;
                    banderaIndice=false;
                } 
            }
        }
    }
    
    public void cargaHistoricoSOxigeno() {
        
        float sOxgMin=300;
        float sOxgMax=0;
        float sOxgRefMin=valoresRef.getSatOxigenoNormalMin();
        float sOxgRefMax=valoresRef.getSatOxigenoNormalMax();
        float sOxg;
        
        historicoSOxigeno.clear();
        
        ChartSeries minVal = new ChartSeries();
        minVal.setLabel("Mínimo");
        ChartSeries maxVal = new ChartSeries();
        maxVal.setLabel("Máximo");
        ChartSeries soxigeno = new ChartSeries();
        soxigeno.setLabel("Saturacion de oxígeno");
        
        getNumMedidasPorDia();
        LocalDateTime fechaMedicion;
                
        for (int i=indiceMedida;i<(indiceMedida+nmedidas);i++){
            fechaMedicion = LocalDateTime.ofInstant(medidasComp.get(i).getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));

            sOxg = medidasComp.get(i).getSaturacionOxigeno();  
            sOxgMin = sOxg < sOxgMin ? sOxg : sOxgMin; 
            sOxgMax = sOxg > sOxgMax ? sOxg : sOxgMax; 
                
            soxigeno.set(fechaMedicion.format(formatoHora), sOxg);
            
            if(i==indiceMedida || i==(indiceMedida+nmedidas-1)){
                minVal.set(fechaMedicion.format(formatoHora),sOxgRefMin);
                maxVal.set(fechaMedicion.format(formatoHora),sOxgRefMax);
            }
        }

        historicoSOxigeno.addSeries(soxigeno);
        historicoSOxigeno.addSeries(maxVal);
        historicoSOxigeno.addSeries(minVal);
        historicoSOxigeno.setSeriesColors("387df5,ff8a7d,ff8a7d");
        historicoSOxigeno.setTitle("Saturación de Oxígeno");
        historicoSOxigeno.setLegendPosition("e");
        historicoSOxigeno.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        historicoSOxigeno.setShowPointLabels(true);
        historicoSOxigeno.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));
            
        sOxgMin = sOxgMin < sOxgRefMin ? sOxgMin : sOxgRefMin;
        sOxgMax = sOxgMax > sOxgRefMax ? sOxgMax : sOxgRefMax;
        
        Axis yAxis = historicoSOxigeno.getAxis(AxisType.Y);
        yAxis.setLabel("Porcentaje (%)");
        yAxis.setTickFormat("%.1f");
        yAxis.setMin(sOxgMin-2);
        yAxis.setMax(sOxgMax+2);
    }
    
    public void cargaHistoricoTemperatura() {
        
        float tempMin=60;
        float tempMax=0;
        float tempRefMin=valoresRef.getTemperaturaNormalMin();
        float tempRefMax=valoresRef.getTemperaturaNormalMax();
        float temp;
        
        historicoTemperatura.clear();
    
        ChartSeries minVal = new ChartSeries();
        minVal.setLabel("Mínimo");
        ChartSeries maxVal = new ChartSeries();
        maxVal.setLabel("Máximo");
        ChartSeries temperatura = new ChartSeries();
        temperatura.setLabel("Temperatura");
        
        getNumMedidasPorDia();
        LocalDateTime fechaMedicion;
        
        for(int i=indiceMedida;i<(indiceMedida+nmedidas);i++){    
            fechaMedicion = LocalDateTime.ofInstant(medidasComp.get(i).getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));
              
            temp = medidasComp.get(i).getTemperatura();    
            tempMin = temp < tempMin ? temp : tempMin; 
            tempMax = temp > tempMax ? temp : tempMax; 
                
            temperatura.set(fechaMedicion.format(formatoHora), temp);
            if(i==indiceMedida || i==(indiceMedida+nmedidas-1)){
                minVal.set(fechaMedicion.format(formatoHora), tempRefMin);
                maxVal.set(fechaMedicion.format(formatoHora), tempRefMax);
            }
            
        }
   
        historicoTemperatura.addSeries(temperatura);
        historicoTemperatura.addSeries(maxVal);
        historicoTemperatura.addSeries(minVal);
        historicoTemperatura.setSeriesColors("387df5,ff8a7d,ff8a7d");
        historicoTemperatura.setTitle("Temperatura");
        historicoTemperatura.setLegendPosition("e");
        historicoTemperatura.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        historicoTemperatura.setShowPointLabels(true);
        historicoTemperatura.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));

        tempMin = tempMin < tempRefMin ? tempMin : tempRefMin;
        tempMax = tempMax > tempRefMax ? tempMax : tempRefMax;
        
        Axis yAxis = historicoTemperatura.getAxis(AxisType.Y);
        yAxis.setLabel("Grados (°C)");
        yAxis.setTickFormat("%.1f");
        yAxis.setMin(tempMin-2);
        yAxis.setMax(tempMax+2);
    }

    public void cargaHistoricoFCardiaca() {
        
        short fCardMin=300;
        short fCardMax=0;
        short fCardRefMin=valoresRef.getFrecCardiacaNormalMin();
        short fCardRefMax=valoresRef.getFrecCardiacaNormalMax();
        short fCard;
        
        historicoFrecCardiaca.clear();
        
        ChartSeries minVal = new ChartSeries();
        minVal.setLabel("Mínimo");
        ChartSeries maxVal = new ChartSeries();
        maxVal.setLabel("Máximo");
        ChartSeries frecuenciaC = new ChartSeries();
        frecuenciaC.setLabel("Frecuencia cardiaca");
        
        getNumMedidasPorDia();
        LocalDateTime fechaMedicion;
        
        for(int i=indiceMedida;i<(indiceMedida+nmedidas);i++){    
            fechaMedicion = LocalDateTime.ofInstant(medidasComp.get(i).getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));
            
            fCard = medidasComp.get(i).getFrecCardiaca();
            fCardMin = fCard < fCardMin ? fCard : fCardMin; 
            fCardMax = fCard > fCardMax ? fCard : fCardMax; 
                
            frecuenciaC.set(fechaMedicion.format(formatoHora), fCard);
            if(i==indiceMedida || i==(indiceMedida+nmedidas-1)){
                minVal.set(fechaMedicion.format(formatoHora), fCardRefMin);
                maxVal.set(fechaMedicion.format(formatoHora), fCardRefMax);
            }
            
        }
        historicoFrecCardiaca.addSeries(frecuenciaC);
        historicoFrecCardiaca.addSeries(maxVal);
        historicoFrecCardiaca.addSeries(minVal);
        historicoFrecCardiaca.setSeriesColors("387df5,ff8a7d,ff8a7d");
        historicoFrecCardiaca.setTitle("Frecuencia Cardíaca");
        historicoFrecCardiaca.setLegendPosition("e");
        historicoFrecCardiaca.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        historicoFrecCardiaca.setShowPointLabels(true); 
        historicoFrecCardiaca.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));
        
        fCardMin = fCardMin < fCardRefMin ? fCardMin : fCardRefMin;
        fCardMax = fCardMax > fCardRefMax ? fCardMax : fCardRefMax;
        
        Axis yAxis = historicoFrecCardiaca.getAxis(AxisType.Y);
        yAxis.setLabel("Frecuencia (LPM)");
        yAxis.setMin(fCardMin-2);
        yAxis.setMax(fCardMax+2);
    }
    
    public void cargaHistoricoFRespiratoria() {
        
        short fRespMin=100;
        short fRespMax=0;
        short fRespRefMin=valoresRef.getFrecRespiratoriaNormalMin();
        short fRespRefMax=valoresRef.getFrecRespiratoriaNormalMax();
        short fResp;
                
        historicoFrecRespiratoria.clear();
        
        ChartSeries minVal = new ChartSeries();
        minVal.setLabel("Mínimo");
        ChartSeries maxVal = new ChartSeries();
        maxVal.setLabel("Máximo");
        ChartSeries frecuenciaR = new ChartSeries();
        frecuenciaR.setLabel("Frecuencia Respiratoria");
        
        getNumMedidasPorDia();
        LocalDateTime fechaMedicion;
                
        for (int i=indiceMedida;i<(indiceMedida+nmedidas);i++){
            fechaMedicion = LocalDateTime.ofInstant(medidasComp.get(i).getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));

            fResp = medidasComp.get(i).getFrecRespiratoria();  
            fRespMin = fResp < fRespMin ? fResp : fRespMin; 
            fRespMax = fResp > fRespMax ? fResp : fRespMax; 
               
            frecuenciaR.set(fechaMedicion.format(formatoHora), fResp);
            if(i==indiceMedida || i==(indiceMedida+nmedidas-1)){
                minVal.set(fechaMedicion.format(formatoHora), fRespRefMin);
                maxVal.set(fechaMedicion.format(formatoHora), fRespRefMax);
            }
        }

        historicoFrecRespiratoria.addSeries(frecuenciaR);
        historicoFrecRespiratoria.addSeries(maxVal);
        historicoFrecRespiratoria.addSeries(minVal);
        historicoFrecRespiratoria.setSeriesColors("387df5,ff8a7d,ff8a7d");
        historicoFrecRespiratoria.setTitle("Frecuencia Respiratoria");
        historicoFrecRespiratoria.setLegendPosition("e");
        historicoFrecRespiratoria.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        historicoFrecRespiratoria.setShowPointLabels(true);     
        historicoFrecRespiratoria.getAxes().put(AxisType.X, new CategoryAxis("Tiempo (Horas)"));
       
        fRespMin = fRespMin < fRespRefMin ? fRespMin : fRespRefMin;
        fRespMax = fRespMax > fRespRefMax ? fRespMax : fRespRefMax;
        
        Axis yAxis = historicoFrecRespiratoria.getAxis(AxisType.Y);
        yAxis.setLabel("Frecuencia (RPM)");
        yAxis.setMin(fRespMin-2);
        yAxis.setMax(fRespMax+2);
    }
    
    public void cargaHistoricoPArterial() {
        
        int diasMin=200;
        int sisMax=0;
        int diasRefMin=valoresRef.getPreArtDiastolicaNormalMin();
        int sisRefMax=valoresRef.getPreArtSistolicaNormalMax();
        int sis;
        int dias;
        
        historicoPreArterial.clear();
        
        ChartSeries sismax = new ChartSeries();
        sismax.setLabel("Máximo P. Sis.");
        ChartSeries sistolica = new ChartSeries();
        sistolica.setLabel("Presión Arterial Sistólica");
        
        ChartSeries diasmin = new ChartSeries();
        diasmin.setLabel("Mínimo P. Dias.");

        ChartSeries diastolica = new ChartSeries();
        diastolica.setLabel("Presión Arterial Diastólica");
        
        getNumMedidasPorDia();
        LocalDateTime fechaMedicion;
                
        for (int i=indiceMedida;i<(indiceMedida+nmedidas);i++){
            fechaMedicion = LocalDateTime.ofInstant(medidasComp.get(i).getFechaMedicion().toInstant(), ZoneId.of(tz.getID()));

            dias = medidasComp.get(i).getPreArtDiastolica();
            sis = medidasComp.get(i).getPreArtSistolica(); 
                
            diasMin = dias < diasMin ? dias : diasMin; 
            sisMax = sis > sisMax ? sis : sisMax; 
                
            diastolica.set(fechaMedicion.format(formatoHora),dias);
            sistolica.set(fechaMedicion.format(formatoHora), sis);
                
            if(i==indiceMedida || i==(indiceMedida+nmedidas-1)){
                diasmin.set(fechaMedicion.format(formatoHora), diasRefMin); 
                sismax.set(fechaMedicion.format(formatoHora), sisRefMax);                   
            }  
        }
               
        historicoPreArterial.addSeries(sistolica);
        historicoPreArterial.addSeries(diastolica);
        historicoPreArterial.addSeries(sismax);
        historicoPreArterial.addSeries(diasmin);
        historicoPreArterial.setSeriesColors("42cef5,425af5,ff8a7d,f5b642");
        historicoPreArterial.setTitle("Presión Arterial Sistólica y Diastólica");
        historicoPreArterial.setLegendPosition("e");
        historicoPreArterial.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        historicoPreArterial.setShowPointLabels(true);
        historicoPreArterial.getAxes().put(AxisType.X, new CategoryAxis("Tiempo(Horas)"));
        
        diasMin = diasMin < diasRefMin ? diasMin : diasRefMin;
        sisMax = sisMax > sisRefMax ? sisMax : sisRefMax;
        
        Axis yAxis = historicoPreArterial.getAxis(AxisType.Y);
        yAxis.setLabel("mmHg");
        yAxis.setMin(diasMin-2);
        yAxis.setMax(sisMax+2);
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