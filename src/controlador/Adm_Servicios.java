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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import model.SisServicio;
import model.SisServicioDAO;
import model.SisTiposervicio;
import model.SisTiposervicioDAO;



@SuppressWarnings({ "serial", "rawtypes" })
public class Adm_Servicios extends SelectorComposer{

	private SisServicioDAO SisEmbTipodao = new SisServicioDAO();
	
	private SisTiposervicioDAO SisCtrldepartamentodao = new SisTiposervicioDAO();
	
	private SisServicio controlingresotipo;
	
	
	@Wire
	private Textbox txt_nombre,txt_descripcion;
	
	private Adm_ListaServicios personaLista;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		//text_valor.setValue("");
		//listaIngreso();
		//Reecupera la Ventana Padre
				personaLista = (Adm_ListaServicios)Executions.getCurrent().getArg().get("VentanaPadre");

				controlingresotipo=null;
				if(Executions.getCurrent().getArg().get("SisServicio")!=null){
					//Recupera Persona selecionada 
					controlingresotipo = (SisServicio)Executions.getCurrent().getArg().get("SisServicio");
				}else{
					//Persona Nueva
					controlingresotipo = new SisServicio();
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
							if(controlingresotipo.getId_Servicios() == 0){
								//Si es nuevo "persist"
								List<SisServicio> personaListar = SisEmbTipodao.getListaServicios(txt_nombre.getText());
								if (personaListar.size() > 0){
									Clients.showNotification("Nombre ya registrado");
									return;
								}
								
								controlingresotipo.setEstadoServ("A");
								SisEmbTipodao.getEntityManager().persist(controlingresotipo);
							}else{
								controlingresotipo = (SisServicio) SisEmbTipodao.getEntityManager().merge(controlingresotipo);
							}
						}else{
							return;
						}
						//cierra la transaccion
						SisEmbTipodao.getEntityManager().getTransaction().commit();
						Clients.showNotification("Registro Almacenado");
						salir();
						personaLista.listaIngreso();
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
	
	


	public SisServicio getControlingresotipo() {
		return controlingresotipo;
	}



	public void setControlingresotipo(SisServicio controlingresotipo) {
		this.controlingresotipo = controlingresotipo;
	}



	private boolean validarDatos() {
		try {

			if(txt_nombre.getText() == "") {
				Clients.showNotification("Debe registrar nombre");
				return false;
			}
			if(txt_descripcion.getText() == "") {
				Clients.showNotification("Debe registrar descripción");
				return false;
			}	
		
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	
	//aqui modifique 
	public List<SisTiposervicio> getSisCtrldepartamentodao() {
		return SisCtrldepartamentodao.getListatiposervicios("");
	}

	
}
