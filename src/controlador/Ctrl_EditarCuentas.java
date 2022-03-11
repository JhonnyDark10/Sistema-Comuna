package controlador;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import model.SegPerfil;
import model.SegPerfilDAO;
import model.SegUsuario;
import model.SegUsuarioDAO;
import model.SegUsuariogenero;
import model.SegUsuariogeneroDAO;
import util.RecuperarClave;
import util.Validar;
import util.vm.GoogleMapsVM;



@SuppressWarnings({ "serial", "rawtypes" })
public class Ctrl_EditarCuentas extends SelectorComposer {

	@Wire Textbox txt_cedula;
	@Wire Textbox usuario,clave,recupera_nombre;
	@Wire Checkbox visualizar;
	@Wire Label numero;
	@Wire Combobox combo_empleado,combo_perfil,combo_genero;
	@Wire Label nombres;
	@Wire Datebox fechanac;
	@Wire Button Bcroquis;
	
	GoogleMapsVM recup;
	
	RecuperarClave recupera = new RecuperarClave();
	Menu controlador;
	//private DepartamentoDao paisDao = new DepartamentoDao();
	Validar valida = new Validar();
	private SegPerfilDAO perfildao = new SegPerfilDAO();

	// Para poder Grabar
	SegUsuarioDAO personaDao = new SegUsuarioDAO();
	SegUsuariogeneroDAO generodao = new SegUsuariogeneroDAO();
	
	
	
	private SegUsuario persona;

	//COntiene la ventana padre para invocar a la actualizacion
	private Ctrl_ListaCuentas personaLista;

	private List<SegUsuario> UsuarioR;
	Date date = new Date();
	//CLick derecho source crear implementmetodo,  do after compose
	//Levante la ventana crear un objeto persona vacio para ingresar dato
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

		visualizar.setChecked(true);
		//Reecupera la Ventana Padre
		personaLista = (Ctrl_ListaCuentas)Executions.getCurrent().getArg().get("VentanaPadre");

		persona=null;
		if(Executions.getCurrent().getArg().get("SegUsuario")!=null){
			//Recupera Persona selecionada

			persona = (SegUsuario)Executions.getCurrent().getArg().get("SegUsuario");
			//recupera();
			//clave.setValue();
		}else{
			//Persona Nueva
			persona = new SegUsuario();
		}
	}


	@Listen("onClick=#visualizar")
	public void visualiza(){


		if (visualizar.isChecked() == true){
			clave.setType("text");

		}else{
			clave.setType("password");
		}
	}
	
	
	@Listen("onSelect=#combo_perfil")
	public void muestrabotonCroquis(){

         if(combo_perfil.getText().equals("Comunero")) {
        	 
        	 Bcroquis.setVisible(true);
         }else {
        	 Bcroquis.setVisible(false);
         }
		
	}

	@Listen("onClick=#Bcroquis")
	public void visualizaMapa(){

		//Enviar por parametros - para editar persona 
				Map<String, Object> params = new HashMap<String, Object>(); //Objeto Paramn tipo de objeto hasMap
				params.put("SegUsuario", null);
				params.put("VentanaPadre", this);


				Window ventanaCargar = (Window)Executions.createComponents("/A_Mapas/seleccionaDireccion.zul", winCuentas,params);
				ventanaCargar.doModal();	
	}
	
	
	
	
	

	@Listen("onClick=#grabar")
	public void grabar(){
		Messagebox.show("Desea Guardar la Información",
				"Mensaje", 
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
				try {
					
										
					if(validarDatos() == true){
						
					}else{
						return;
					}
					
					Time hora =  new  Time (date.getTime());
					personaDao.getEntityManager().getTransaction().begin();
					//Almacena Datos

					if(persona.getIdUsuario() == 0){
						//Si es nuevo "persist"
						
						
						
						//campos de auditoria
						persona.setFecha(date);
						persona.setHora(hora);
						persona.setControlador(1);
						
						persona.setClave(recupera.encriptar(clave.getText()));
						persona.setEstado("A");
						persona.setVerifica(0);
						
						//guardo para el mapa
						persona.setLatitud(recup.Glob_latitud);
						persona.setLongitud(recup.Glob_longitud);
						persona.setCreatedAtDate(date);
						persona.setCreatedAtTime(hora);
						
						
						personaDao.getEntityManager().persist(persona);
					}else{
						if(clave.getText() == ""){

						}else{
							if(visualizar.isChecked() == true){
								persona.setClave(recupera.encriptar(clave.getText()));
							    persona.setVerifica(1);
							}else{
								Clients.showNotification("Marque la casilla");
								return;
							}

						}
                     if(recup.Glob_verificaSelecion == 1) {
                    	 persona.setLatitud(recup.Glob_latitud);
 						 persona.setLongitud(recup.Glob_longitud);
 						persona.setCreatedAtDate(date);
						persona.setCreatedAtTime(hora);
                     }
						
						
						persona = (SegUsuario) personaDao.getEntityManager().merge(persona);
					}


					//cierra la transaccion
					personaDao.getEntityManager().getTransaction().commit();
					Clients.showNotification("Registro Almacenado");

					salir();
					personaLista.buscar();

				} catch (Exception e) {
					//Si hay error, revierte la transaccion
					personaDao.getEntityManager().getTransaction().rollback();

				}
			}
			}
		});
	}



	//Boton salir
	@Wire Window winCuentas;

	@Listen("onClick=#salir")
	public void salir(){
		winCuentas.detach();

	}



	public SegUsuario getPersona() {
		return persona;
	}


	public void setPersona(SegUsuario persona) {
		this.persona = persona;
	}


	
	public List<SegPerfil> getListaPerfiles(){

		return perfildao.getListaPerfiles("");

	}



	public List<SegUsuariogenero> getListaGeneros(){

		return generodao.getListaGeneros("");

	}


	private boolean validarDatos() {
		try {

			if(usuario.getText() == "") {
				Clients.showNotification("Debe registrar Usuario");
				return false;
			}
			/*if(clave.getText() == "") {
				Clients.showNotification("Debe registrar Clave");
				return false;
			}*/
		
			
			if(combo_perfil.getSelectedIndex() == -1) {
				Clients.showNotification("Debe seleccionar Perfil");
				return false;
			}
			
			if(valida.validarDeCedula(txt_cedula.getText()) == false) {
				Clients.showNotification("Cédula no valida");
				return false;
			}
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
}

