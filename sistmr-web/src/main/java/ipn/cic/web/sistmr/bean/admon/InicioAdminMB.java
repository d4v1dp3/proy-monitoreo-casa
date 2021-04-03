/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.BitacoraException;
import ipn.cic.sistmr.exception.CaretaHospitalException;
import ipn.cic.sistmr.exception.CatalogoException;
import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.exception.NoExisteHospitalException;
import ipn.cic.sistmr.exception.NoExistePersonaException;
import ipn.cic.sistmr.modelo.EntBitacora;
import ipn.cic.sistmr.modelo.EntCareta;
import ipn.cic.sistmr.modelo.EntCaretaHospital;
import ipn.cic.sistmr.modelo.EntHospital;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPersona;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.BitacoraSBLocal;
import ipn.cic.sistmr.sesion.CaretaHospitalSBLocal;
import ipn.cic.sistmr.sesion.CatalogoSBLocal;
import ipn.cic.sistmr.sesion.HospitalSBLocal;
import ipn.cic.sistmr.sesion.MedicoSBLocal;
import ipn.cic.sistmr.sesion.PersonaSBLocal;
import ipn.cic.sistmr.util.Constantes;
import ipn.cic.web.sistmr.util.Mensaje;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author J.Perez
 */
@Named(value = "inicioAdminMB")
@ViewScoped
public class InicioAdminMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(InicioAdminMB.class.getName());

    @EJB
    private MedicoSBLocal medicoSB;
    @EJB
    private PersonaSBLocal personaSB;
    @EJB
    private UtilWebSBLocal utilWebSB;
    @EJB
    private HospitalSBLocal hospitalSB;
    @EJB
    private CatalogoSBLocal catalogoSB;
    @EJB
    private CaretaHospitalSBLocal caretahospitalSB;
    @EJB
    private BitacoraSBLocal bitacoraSB;

    private EntHospital hospital;
    private List<EntMedico> medicos;
    private List<EntPaciente> pacientes;
    private List<EntCareta> caretas;
    private List<EntCaretaHospital> caretasSA;

    private List<EntBitacora> ingresosPac;
    private List<EntBitacora> altasPac;
    private List<EntBitacora> decesosPac;

    String nomSistema = "";
    String nomAdmin = "";

    private BarChartModel graficoIngresosPac;
    private BarChartModel graficoAltasPac;
    private BarChartModel graficoDecesosPac;
    
    private String anio = "2021";
    private String mes = "Enero";
    
    ArrayList<String> meses = new ArrayList();
        

    @PostConstruct
    public void cargarDatos() {
        FacesMessage msg = null;

        logger.log(Level.INFO, "Entra a cargar datos.");
        nomSistema = Constantes.getInstance().getString("NOMBRE_SISTEMA");

        medicos = new ArrayList();
        pacientes = new ArrayList();
        caretas = new ArrayList();

        ingresosPac = new ArrayList();
        altasPac = new ArrayList();
        
        meses.add("Enero");
        meses.add("Febrero");
        meses.add("Marzo");
        meses.add("Abril");
        meses.add("Mayo");
        meses.add("Junio");
        meses.add("Julio");
        meses.add("Agosto");
        meses.add("Septiembre");
        meses.add("Octubre");
        meses.add("Noviembre");
        meses.add("Diciembre");
        

        try {
            EntUsuario usrAdmin = utilWebSB.getUsrAutenticado();
            EntPersona persona = personaSB.getEntPersona(usrAdmin.getIdPersona());
            nomAdmin = persona.getNombre();

            medicos = medicoSB.getMedicos();
            pacientes = catalogoSB.getCatalogo("EntPaciente");
            caretas = catalogoSB.getCatalogo("EntCareta");
            caretasSA = caretahospitalSB.getCaretasNoAsignadas();
            hospital = hospitalSB.getPrimerHospital();

        } catch (MedicoException ex) {
            logger.log(Level.SEVERE, "Error al cargar medicos.");
        } catch (NoExisteHospitalException ex) {
            logger.log(Level.SEVERE, "Error al cargar hospital.");
        } catch (CatalogoException ex) {
            logger.log(Level.SEVERE, "Error al recuperar pacientes/caretas.");
        } catch (CaretaHospitalException ex) {
            logger.log(Level.SEVERE, "Error al recuperar dispositivos no asignados.");
        } catch (NoExistePersonaException ex) {
            logger.log(Level.SEVERE, "Error al recuperar datos del administrador.");
        }

        if (msg == null) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Éxito:",
                            "Pacientes cargados correctamente",
                            FacesMessage.SEVERITY_INFO);
        }
//        utilWebSB.addMsg("frGestPacientes:msgsIP", msg);
//        PrimeFaces.current().ajax().update("frIPacientes:msgsIP");


        /*Recuperar EVENTOS*/
        try {
            ingresosPac = bitacoraSB.getIngresosPacientes();
            altasPac = bitacoraSB.getAltasPacientes();
            decesosPac = bitacoraSB.getDecesosPacientes();
        } catch (BitacoraException ex) {
            Logger.getLogger(InicioAdminMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        cargarDatosGraficoIngresos();
        cargarDatosGraficoAltas();
        cargarDatosGraficoDecesos();

    }

    
    void cargarDatosGraficoIngresos(){
        
        graficoIngresosPac = new BarChartModel();

        ChartSeries ingresos = new ChartSeries();
        ingresos.setLabel("Ingresos");

        int ing = 0;
        int max = 0;

        for (int i = 0; i < 12; i++) {
            ing = 0;

            int ajuste = Integer.parseInt(anio) - 1900;
            Date fecha = new Date(ajuste, i, 1, 0, 0, 0);
            
            for (EntBitacora evento : ingresosPac) {
                if (evento.getFechaEntrada().getMonth() == fecha.getMonth()) {
                    ing++;
                }
            }
            ingresos.set(meses.get(i), ing);
            
            if(ing>max)
                max=ing;
        }
        max+=10;

        graficoIngresosPac.addSeries(ingresos);
        graficoIngresosPac.setTitle("Ingresos de Pacientes");
        graficoIngresosPac.setShowPointLabels(true);

        Axis xAxis = graficoIngresosPac.getAxis(AxisType.X);
        xAxis.setLabel("Periodo "+anio);

        Axis yAxis = graficoIngresosPac.getAxis(AxisType.Y);
        yAxis.setLabel("No. de Pacientes");
        yAxis.setMin(0);
        yAxis.setMax(max);

        graficoIngresosPac.setSeriesColors("00e0e0");
        graficoIngresosPac.setShadow(false);
    
    }
    
    void cargarDatosGraficoAltas(){
    
        graficoAltasPac = new BarChartModel();

        ChartSeries altas = new ChartSeries();
        altas.setLabel("Altas");

        int alt = 0;
        int max = 0;

        for (int i = 0; i < 12; i++) {
            alt = 0;

            int ajuste = Integer.parseInt(anio) - 1900;
            Date fecha = new Date(ajuste, i, 1, 0, 0, 0);
            
            for (EntBitacora evento : altasPac) {
                if (evento.getFechaEntrada().getMonth() == fecha.getMonth()) {
                    alt++;
                }
            }
            altas.set(meses.get(i), alt);
   
            if(alt>max)
                max=alt;
        }
        max+=10;

        graficoAltasPac.addSeries(altas);
        graficoAltasPac.setTitle("Altas de Pacientes");
        graficoAltasPac.setShowPointLabels(true);

        Axis xAxis = graficoAltasPac.getAxis(AxisType.X);
        xAxis.setLabel("Periodo "+anio);

        Axis yAxis = graficoAltasPac.getAxis(AxisType.Y);
        yAxis.setLabel("No. de Pacientes");
        yAxis.setMin(0);
        yAxis.setMax(max);

        graficoAltasPac.setSeriesColors("47e308");
        graficoAltasPac.setShadow(false);
    }
    
    void cargarDatosGraficoDecesos(){
    
        graficoDecesosPac = new BarChartModel();

        ChartSeries decesos = new ChartSeries();
        decesos.setLabel("Decesos");

        int dec;
        int max = 0;

        for (int i = 0; i < 12; i++) {
            dec = 0;

            int ajuste = Integer.parseInt(anio) - 1900;
            Date fecha = new Date(ajuste, i, 1, 0, 0, 0);
            
            for (EntBitacora evento : decesosPac) {
                if (evento.getFechaEntrada().getMonth() == fecha.getMonth()) {
                    dec++;
                }
            }

            decesos.set(meses.get(i), dec);
            if(dec>max)
                max=dec;
        }
        max+=10;

        graficoDecesosPac.addSeries(decesos);

        graficoDecesosPac.setTitle("Decesos de Pacientes");
        graficoDecesosPac.setShowPointLabels(true);

        Axis xAxis = graficoDecesosPac.getAxis(AxisType.X);
        xAxis.setLabel("Mes - "+anio);
        Axis yAxis = graficoDecesosPac.getAxis(AxisType.Y);
        yAxis.setLabel("No. de Pacientes");
        yAxis.setMin(0);
        yAxis.setMax(max);

        graficoDecesosPac.setSeriesColors("e31f08");
        graficoDecesosPac.setShadow(false);
    }

    public String getNomSistema() {
        return nomSistema;
    }

    public void setNomSistema(String nomSistema) {
        this.nomSistema = nomSistema;
    }

    public EntHospital getHospital() {
        return hospital;
    }

    public void setHospital(EntHospital hospital) {
        this.hospital = hospital;
    }

    public List<EntMedico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<EntMedico> medicos) {
        this.medicos = medicos;
    }

    public List<EntPaciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<EntPaciente> pacientes) {
        this.pacientes = pacientes;
    }

    public List<EntCareta> getCaretas() {
        return caretas;
    }

    public void setCaretas(List<EntCareta> caretas) {
        this.caretas = caretas;
    }

    public List<EntCaretaHospital> getCaretasSA() {
        return caretasSA;
    }

    public void setCaretasSA(List<EntCaretaHospital> caretasSA) {
        this.caretasSA = caretasSA;
    }

    public String getNomAdmin() {
        return nomAdmin;
    }

    public void setNomAdmin(String nomAdmin) {
        this.nomAdmin = nomAdmin;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public List<EntBitacora> getIngresosPac() {
        return ingresosPac;
    }

    public void setIngresosPac(List<EntBitacora> ingresosPac) {
        this.ingresosPac = ingresosPac;
    }

    public List<EntBitacora> getAltasPac() {
        return altasPac;
    }

    public void setAltasPac(List<EntBitacora> altasPac) {
        this.altasPac = altasPac;
    }

    public List<EntBitacora> getDecesosPac() {
        return decesosPac;
    }

    public void setDecesosPac(List<EntBitacora> decesosPac) {
        this.decesosPac = decesosPac;
    }

    public BarChartModel getGraficoIngresosPac() {
        return graficoIngresosPac;
    }

    public void setGraficoIngresosPac(BarChartModel graficoIngresosPac) {
        this.graficoIngresosPac = graficoIngresosPac;
    }

    public BarChartModel getGraficoAltasPac() {
        return graficoAltasPac;
    }

    public void setGraficoAltasPac(BarChartModel graficoAltasPac) {
        this.graficoAltasPac = graficoAltasPac;
    }

    public BarChartModel getGraficoDecesosPac() {
        return graficoDecesosPac;
    }

    public void setGraficoDecesosPac(BarChartModel graficoDecesosPac) {
        this.graficoDecesosPac = graficoDecesosPac;
    }

    
    
}
