package controlador;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import model.SisCategoriapago;
import model.SisCategoriapagoDAO;





//LISTADO DE LAS EMBARCACIONES QUE SOLICITARON EL SERVICIO DE DESEMBARQUE DE PESCA

@SuppressWarnings({ "serial", "rawtypes" })
public class Adm_ListaCategoriaPago extends SelectorComposer{

	@Wire 
	Textbox txtBuscar;

	@Wire 
	private Listbox LstTipoActividades;

	//Lamar venta 
	@Wire
	Window winListaEmpleados;


	// Instancia el objeto para acceso a datos.
	private SisCategoriapagoDAO SisEmbTipodao = new SisCategoriapagoDAO();


	private List<SisCategoriapago> Sistipoactividad;


	//aqui para seleccionar el objeto  a añadir o editar
	private SisCategoriapago TipoactividadesSelecionada;






	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

		listaIngreso();
	}


	//lista las embarcaciones a descargar

	@Listen("onClick=#btnBuscar;onOK=#txtBuscar")
	public void listaIngreso(){
		if (Sistipoactividad != null) {
			Sistipoactividad = null; 
		}
		Sistipoactividad = SisEmbTipodao.getListaCategorias(txtBuscar.getText());
		LstTipoActividades.setModel(new ListModelList(Sistipoactividad));
		
		//Limpiar
		TipoactividadesSelecionada = null;
	}

	
	
	//*******************************************
	//Escucha evento
	@Listen("onClick=#btnNuevo")
	public void nuevo(){

		//Enviar por parametros - para editar persona 
		Map<String, Object> params = new HashMap<String, Object>(); //Objeto Paramn tipo de objeto hasMap
		params.put("SisCategoriapago", null);
		params.put("VentanaPadre", this);


		Window ventanaCargar = (Window)Executions.createComponents("/A_Administracion/Adm_CategoriaPago.zul", winListaEmpleados,params);
		ventanaCargar.doModal();
	}


	//*******************************************
	//Escucha evento
	@Listen("onClick=#btnEditar")
	public void editar(){

		if(TipoactividadesSelecionada == null){
			Clients.showNotification("Debe seleccionar un Registro");
			return;
		}
		//Actualiza la instancia antes de editar
		SisEmbTipodao.getEntityManager().refresh(TipoactividadesSelecionada);//berifica el ultimo valor en la BD

		//Enviar por parametros - para editar persona 
		Map<String, Object> params = new HashMap<String, Object>(); //Objeto Paramn tipo de objeto hasMap
		params.put("SisCategoriapago", TipoactividadesSelecionada);
		params.put("VentanaPadre", this);

		Window ventanaCargar = (Window)Executions.createComponents("/A_Administracion/Adm_CategoriaPago.zul", winListaEmpleados,params);
		ventanaCargar.doModal();
	}
	//*********************ELIMINAR*************************
	/**
	 * Escucha el evento "onClick" del objeto "btnEliminar"
	 * Elimina logicamente una persona.
	 */
	@Listen("onClick=#btnEliminar")
	public void eliminar(){
		if (TipoactividadesSelecionada == null) {
			Clients.showNotification(" Debe seleccionar un Registro");
			return;
		}
		Messagebox.show("Desea Eliminar el Registro Seleccionado","Mensaje", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						//personaSeleccionada.setEstado("X");
						TipoactividadesSelecionada.setEstadoPago("I");;
						SisEmbTipodao.getEntityManager().getTransaction().begin();
						SisEmbTipodao.getEntityManager().persist(TipoactividadesSelecionada);
						SisEmbTipodao.getEntityManager().getTransaction().commit();;
						Clients.showNotification("Registro Eliminado");
						listaIngreso();
					} catch (Exception e) {
						e.printStackTrace();
						SisEmbTipodao.getEntityManager().getTransaction().rollback();
					}
				}


			}
		});

	}


	public List<SisCategoriapago> getSistipoactividad() {
		return Sistipoactividad;
	}


	public void setSistipoactividad(List<SisCategoriapago> sistipoactividad) {
		Sistipoactividad = sistipoactividad;
	}


	public SisCategoriapago getTipoactividadesSelecionada() {
		return TipoactividadesSelecionada;
	}


	public void setTipoactividadesSelecionada(SisCategoriapago tipoactividadesSelecionada) {
		TipoactividadesSelecionada = tipoactividadesSelecionada;
	}

	
	//Getters and Setters



}       

