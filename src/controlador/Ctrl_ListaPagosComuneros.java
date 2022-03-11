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

import model.SegPeriodoEncargo;
import model.SegPeriodoEncargoDAO;
import model.SegUsuario;
import model.SegUsuarioDAO;
import model.Vistalistadopagoscomunero;
import model.VistalistadopagoscomunerosDAO;



//LISTADO DE LAS EMBARCACIONES QUE SOLICITARON EL SERVICIO DE DESEMBARQUE DE PESCA

@SuppressWarnings({ "serial", "rawtypes" })
public class Ctrl_ListaPagosComuneros extends SelectorComposer{

	@Wire 
	Textbox txtBuscar;

	@Wire 
	private Listbox LstPagosComuneros;

	//Lamar venta 
	@Wire
	Window winListaPagosComuneros;

	private SegPeriodoEncargoDAO periodoDao = new SegPeriodoEncargoDAO();

	private List<SegPeriodoEncargo> PeriodosE;
	
	// Instancia el objeto para acceso a datos.
	private VistalistadopagoscomunerosDAO usuarioDao = new VistalistadopagoscomunerosDAO();

	private List<Vistalistadopagoscomunero> Usuarios;


	//aqui para seleccionar el objeto  a añadir o editar
	private Vistalistadopagoscomunero pagosComuneroSelecionada;






	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

		buscar();
	}


	//lista las embarcaciones a descargar

	@Listen("onClick=#btnBuscar;onOK=#txtBuscar")
	public void buscar(){
		//System.out.println("INGRESO");

		if (Usuarios != null) {
			Usuarios = null; 
		}

		Usuarios = usuarioDao.getListaPagosComuneros(txtBuscar.getText());

		LstPagosComuneros.setModel(new ListModelList(Usuarios));

		//Limpiar
		pagosComuneroSelecionada = null;
	}

	//*******************************************
	//Escucha evento
	@Listen("onClick=#btnNuevo")
	public void nuevo(){

		//Enviar por parametros - para editar persona 
		Map<String, Object> params = new HashMap<String, Object>(); //Objeto Paramn tipo de objeto hasMap
		params.put("Vistalistadopagoscomunero", null);
		params.put("VentanaPadre", this);


		Window ventanaCargar = (Window)Executions.createComponents("/A_ModulosPagos/IngresoPagosComuneros.zul", winListaPagosComuneros,params);
		ventanaCargar.doModal();
	}


	
	//*********************ELIMINAR*************************
	/**
	 * Escucha el evento "onClick" del objeto "btnEliminar"
	 * Elimina logicamente una persona.
	 */
	@Listen("onClick=#btnEliminar")
	public void eliminar(){
		if (pagosComuneroSelecionada == null) {
			Clients.showNotification("Debe seleccionar un Registro");
			return;
		}
		Messagebox.show("Desea Eliminar el Registro Seleccionado", "Mensaje", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						//personaSeleccionada.setEstado("X");
						
						
						
						pagosComuneroSelecionada.setEstadoFactura("I");
						
						
						usuarioDao.getEntityManager().getTransaction().begin();
						usuarioDao.getEntityManager().persist(pagosComuneroSelecionada);
						usuarioDao.getEntityManager().getTransaction().commit();;
						Clients.showNotification("Registro Eliminado");
						
						///RESTA ESE VALOR DEL USUARIO
						PeriodosE =  periodoDao.getListaPerfiles("");


						for(SegPeriodoEncargo det : PeriodosE) {

							float acum = det.getValorIngreso();

							det.setValorIngreso(acum - pagosComuneroSelecionada.getTotal());

							periodoDao.getEntityManager().getTransaction().begin();
							periodoDao.getEntityManager().persist(det);
							periodoDao.getEntityManager().getTransaction().commit();
						}
						
						
						
						buscar();
					} catch (Exception e) {
						e.printStackTrace();
						usuarioDao.getEntityManager().getTransaction().rollback();
					}
				}


			}
		});

	}


	public List<Vistalistadopagoscomunero> getUsuarios() {
		return Usuarios;
	}


	public void setUsuarios(List<Vistalistadopagoscomunero> usuarios) {
		Usuarios = usuarios;
	}


	public Vistalistadopagoscomunero getPagosComuneroSelecionada() {
		return pagosComuneroSelecionada;
	}


	public void setPagosComuneroSelecionada(Vistalistadopagoscomunero pagosComuneroSelecionada) {
		this.pagosComuneroSelecionada = pagosComuneroSelecionada;
	}


	

}       

