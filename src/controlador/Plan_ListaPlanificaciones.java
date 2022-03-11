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
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import model.SisPlanificacion;
import model.SisPlanificacionDAO;



@SuppressWarnings({ "serial", "rawtypes" })
public class Plan_ListaPlanificaciones extends SelectorComposer{

	@Wire 
	Datebox fecha_inicio,fecha_fin;

	@Wire 
	private Listbox LstCuentas;

	//Lamar venta 
	@Wire
	Window winListaCuentas;

	

	// Instancia el objeto para acceso a datos.
	private SisPlanificacionDAO usuarioDao = new SisPlanificacionDAO();



	private List<SisPlanificacion> Usuarios;


	//aqui para seleccionar el objeto  a añadir o editar
	private SisPlanificacion cuentaSelecionada;






	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

		//buscar();
		
		
	}


	//lista las embarcaciones a descargar

	@Listen("onClick=#btnBuscar;")
	public void buscar(){
		//System.out.println("INGRESO");

		if (Usuarios != null) {
			Usuarios = null; 
		}

		Usuarios = usuarioDao.getListaPlanificacione(fecha_inicio.getValue(),fecha_fin.getValue());

		LstCuentas.setModel(new ListModelList(Usuarios));

		//Limpiar
		cuentaSelecionada = null;
	}

	//*******************************************
	//Escucha evento
	@Listen("onClick=#btnNuevo")
	public void nuevo(){

		//Enviar por parametros - para editar persona 
		Map<String, Object> params = new HashMap<String, Object>(); //Objeto Paramn tipo de objeto hasMap
		params.put("SisPlanificacion", null);
		params.put("VentanaPadre", this);


		Window ventanaCargar = (Window)Executions.createComponents("/A_Planificacion/Planificacion.zul", winListaCuentas,params);
		ventanaCargar.doModal();
		
	}


	//*******************************************
	//Escucha evento
	@Listen("onClick=#btnEditar")
	public void editar(){

		if(cuentaSelecionada == null){
			Clients.showNotification("Debe seleccionar un Registro");
			return;
		}
		//Actualiza la instancia antes de editar
		usuarioDao.getEntityManager().refresh(cuentaSelecionada);//berifica el ultimo valor en la BD

		//Enviar por parametros - para editar persona 
		Map<String, Object> params = new HashMap<String, Object>(); //Objeto Paramn tipo de objeto hasMap
		params.put("SisPlanificacion", cuentaSelecionada);
		params.put("VentanaPadre", this);

		Window ventanaCargar = (Window)Executions.createComponents("/A_Planificacion/Planificacion.zul", winListaCuentas,params);
		ventanaCargar.doModal();
	}
	//*********************ELIMINAR*************************
	/**
	 * Escucha el evento "onClick" del objeto "btnEliminar"
	 * Elimina logicamente una persona.
	 */
	@Listen("onClick=#btnEliminar")
	public void eliminar(){
		if (cuentaSelecionada == null) {
			Clients.showNotification("Debe seleccionar un Registro");
			return;
		}
		Messagebox.show("Desea Eliminar el Registro Seleccionado", "Mensaje", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						
						
						cuentaSelecionada.setEstadoPlan("I");;
						usuarioDao.getEntityManager().getTransaction().begin();
						usuarioDao.getEntityManager().persist(cuentaSelecionada);
						usuarioDao.getEntityManager().getTransaction().commit();;
						Clients.showNotification("Registro Eliminado");
						buscar();
						
					} catch (Exception e) {
						e.printStackTrace();
						usuarioDao.getEntityManager().getTransaction().rollback();
					}
				}


			}
		});

	}


	public List<SisPlanificacion> getUsuarios() {
		return Usuarios;
	}


	public void setUsuarios(List<SisPlanificacion> usuarios) {
		Usuarios = usuarios;
	}


	public SisPlanificacion getCuentaSelecionada() {
		return cuentaSelecionada;
	}


	public void setCuentaSelecionada(SisPlanificacion cuentaSelecionada) {
		this.cuentaSelecionada = cuentaSelecionada;
	}






}       

