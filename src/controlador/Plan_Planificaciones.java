package controlador;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

import model.SegPeriodoEncargo;
import model.SegPeriodoEncargoDAO;
import model.SegUsuario;
import model.SegUsuariogenero;
import model.SisPlanDia;
import model.SisPlanDiaDAO;
import model.SisPlanTiptarea;
import model.SisPlanTiptareaDAO;
import model.SisPlanificacion;
import model.SisPlanificacionDAO;




@SuppressWarnings({ "serial", "rawtypes" })
public class Plan_Planificaciones extends SelectorComposer{

	
	@Wire Textbox txt_contacto,txt_celular,txt_lugar,txt_direccion,txt_descripcion,hora;
	@Wire Datebox fecha;
	
	@Wire Combobox cbo_tipoTarea,cbo_dia;
	
	private SisPlanificacionDAO SisEmbTipodao = new SisPlanificacionDAO();
	private SisPlanificacion persona;
	private SegPeriodoEncargoDAO usuarioDao = new SegPeriodoEncargoDAO();
	private List<SegPeriodoEncargo> Usuarios;
	private Plan_ListaPlanificaciones personaLista;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
		
				personaLista = (Plan_ListaPlanificaciones)Executions.getCurrent().getArg().get("VentanaPadre");

				persona=null;
				if(Executions.getCurrent().getArg().get("SisPlanificacion")!=null){
					//Recupera Persona selecionada 
					persona = (SisPlanificacion)Executions.getCurrent().getArg().get("SisPlanificacion");
				}else{
					//Persona Nueva
					persona = new SisPlanificacion();
				}
	}
	


	@Listen("onClick=#grabar")
	public void grabar(){

		Messagebox.show("Desea Grabar la Información",
				"Mensaje", 
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {

					try {
								
						//
						SisEmbTipodao.getEntityManager().getTransaction().begin();
						//Almacena Datos
						if(validarDatos() == true){
							if(persona.getId_Planificacion() == 0){
								//Si es nuevo "persist"
								
								//recorre el usuario
								
								Usuarios = usuarioDao.getListaPerfiles("");
								for(SegPeriodoEncargo usu : Usuarios) {
									persona.setSegPeriodoEncargo(usu);
								}
								
								persona.setEstadoPlan("A");
								SisEmbTipodao.getEntityManager().persist(persona);
							}else{
								persona = (SisPlanificacion) SisEmbTipodao.getEntityManager().merge(persona);
							}
						}else{
							return;
						}
						//cierra la transaccion
						SisEmbTipodao.getEntityManager().getTransaction().commit();
						Clients.showNotification("Registro Almacenado");
						salir();
						personaLista.buscar();
					} catch (Exception e) {
						//Si hay error, revierte la transaccion
						SisEmbTipodao.getEntityManager().getTransaction().rollback();
					}

				}
			}
		});
	}
	
	
	
	//Boton salir
	@Wire Window winTipoActividades;

	@Listen("onClick=#salir")
	public void salir(){
		winTipoActividades.detach();

	}
	

	public SisPlanificacion getPersona() {
		return persona;
	}



	public void setPersona(SisPlanificacion persona) {
		this.persona = persona;
	}



	private boolean validarDatos() {
		try {

			if(txt_contacto.getText() == "") {
				Clients.showNotification("Debe registrar nombre de contacto");
				return false;
			}
			if(txt_lugar.getText() == "") {
				Clients.showNotification("Debe registrar lugar");
				return false;
			}
			
			if(txt_direccion.getText() == "") {
				Clients.showNotification("Debe registrar dirección");
				return false;
			}
			
			if(fecha.getText() == "") {
				Clients.showNotification("Debe registrar fecha");
				return false;
			}
		
			if(hora.getText() == "") {
				Clients.showNotification("Debe registrar hora");
				return false;
			}
			
			
			if(cbo_tipoTarea.getSelectedIndex() == -1) {
				Clients.showNotification("Debe seleccionar tarea");
				return false;
			}
			
			if(cbo_dia.getSelectedIndex() == -1) {
				Clients.showNotification("Debe seleccionar dia");
				return false;
			}
			
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	
	SisPlanDiaDAO diadao = new SisPlanDiaDAO();

	SisPlanTiptareaDAO tareadao = new SisPlanTiptareaDAO();

	public List<SisPlanDia> getListaDias(){

		return diadao.getTipoTareas("");
	}

	
	public List<SisPlanTiptarea> getListaTipoTarea(){

		return tareadao.getTipoTareas("");
	}
	
	
	
}



