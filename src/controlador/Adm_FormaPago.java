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

import model.SisFormapago;
import model.SisFormapagoDAO;





@SuppressWarnings({ "serial", "rawtypes" })
public class Adm_FormaPago extends SelectorComposer{

	private SisFormapagoDAO SisEmbTipodao = new SisFormapagoDAO();
	
	
	private SisFormapago controlingresotipo;
	
	
	@Wire
	private Textbox txt_nombre;
	
	private Adm_ListaFormaPago personaLista;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		//text_valor.setValue("");
		//listaIngreso();
		//Reecupera la Ventana Padre
				personaLista = (Adm_ListaFormaPago)Executions.getCurrent().getArg().get("VentanaPadre");

				controlingresotipo=null;
				if(Executions.getCurrent().getArg().get("SisFormapago")!=null){
					//Recupera Persona selecionada 
					controlingresotipo = (SisFormapago)Executions.getCurrent().getArg().get("SisFormapago");
				}else{
					//Persona Nueva
					controlingresotipo = new SisFormapago();
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
							if(controlingresotipo.getId_FormaPago() == 0){
								//Si es nuevo "persist"
								List<SisFormapago> personaListar = SisEmbTipodao.getListaFormas(txt_nombre.getText());
								if (personaListar.size() > 0){
									Clients.showNotification("Nombre ya registrado");
									return;
								}
								
								controlingresotipo.setEstadoForma("A");
								SisEmbTipodao.getEntityManager().persist(controlingresotipo);
							}else{
								controlingresotipo = (SisFormapago) SisEmbTipodao.getEntityManager().merge(controlingresotipo);
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
	


	



	public SisFormapago getControlingresotipo() {
		return controlingresotipo;
	}



	public void setControlingresotipo(SisFormapago controlingresotipo) {
		this.controlingresotipo = controlingresotipo;
	}



	private boolean validarDatos() {
		try {

			if(txt_nombre.getText() == "") {
				Clients.showNotification("Debe registrar nombre");
				return false;
			}
			
			
		
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
}
