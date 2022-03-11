package controlador;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import model.SegPerfil;
import model.SegPerfilDAO;
import model.SegPeriodoEncargo;
import model.SegPeriodoEncargoDAO;
import model.SegUsuario;
import model.SegUsuarioDAO;
import model.SegUsuariogenero;
import model.SegUsuariogeneroDAO;
import model.SisControlpago;
import model.SisDetallefacturaservicio;
import model.SisDetallefacturaservicioDAO;
import model.SisFactura;
import model.SisFacturaDAO;
import model.SisFormapago;
import model.SisFormapagoDAO;
import model.SisServicio;
import model.SisServicioDAO;
import util.PrintReport;


@SuppressWarnings({ "serial", "rawtypes" })
public class Ctrl_IngresoPagoServcios extends SelectorComposer {

	Date date = new Date();
	@Wire
	Textbox horaactual,fechaactual,totalfac,cambiofac,nacionalidad,efectivofac,cedula,nombre,celular,apellido1,apellido2,cantidad;

	@Wire Combobox cbo_perfil,cbo_genero,comboPago,cbo_servicios;

	@Wire Radio radioComuneros;
	@Wire Radio radioClientes;
	@Wire
	Window winEmpleados;
	
	@Wire Label Lab_nac,Lab_per,Lab_gen;

	@Wire Listbox listaServicios;

	private SegPeriodoEncargoDAO periodoDao = new SegPeriodoEncargoDAO();

	private List<SegPeriodoEncargo> PeriodosE;
	SisDetallefacturaservicio serviciosSelecionada;

	int cont = 0;
	int verificaU = 0;

	int idfacturaBuscar = 0;


	List<SegUsuario> reporteListaC;
	List<SegUsuario> reporteListaS;

	SegUsuarioDAO usuariodao = new SegUsuarioDAO();

	SegUsuario personaUsuario;

	private Ctrl_ListaPagosServicios listaP;
	SegUsuariogenero generoSelecionado;
	private SisDetallefacturaservicio persona;
	SegPerfilDAO perfildao = new SegPerfilDAO();
	SegPerfil perfilSelecionado;
	SegUsuariogeneroDAO generodao = new SegUsuariogeneroDAO();
	SisServicioDAO servicidao = new SisServicioDAO();
	SisServicio tipoServicioSelecionado;

	private SisFacturaDAO facturaDao = new SisFacturaDAO() ;
	private SisFactura controlingresofactura;

	SisFormapago formaSeleccionado;
	SisFormapagoDAO formadao = new SisFormapagoDAO();

	ListModelList modeloLista;

	SisDetallefacturaservicioDAO detalledao = new SisDetallefacturaservicioDAO();

	SisDetallefacturaservicio detallenuevo;

	List<SisDetallefacturaservicio> ListaFac;

	List<SisFactura> ListaRecupera;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);


		modeloLista = new ListModelList();

		listaServicios.setModel(modeloLista);

		//Caso 1: obtener la hora y salida por pantalla con formato:
		DateFormat hourFormat = new SimpleDateFormat("HH:mm");
		horaactual.setValue(hourFormat.format(date));

		//Caso 2: obtener la fecha y salida por pantalla con formato:
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		fechaactual.setValue(dateFormat.format(date));

		totalfac.setValue("00.00");
		cambiofac.setValue("00.00");
		efectivofac.setValue("00.00");
		cedula.setValue("xxxxxxxxxx");
		cantidad.setValue("0");


		listaP = (Ctrl_ListaPagosServicios)Executions.getCurrent().getArg().get("VentanaPadre");

		nombre.setDisabled(true);
		celular.setDisabled(true);
		apellido1.setDisabled(true);
		apellido2.setDisabled(true);
		cbo_perfil.setDisabled(true);
		cbo_genero.setDisabled(true);
		nacionalidad.setDisabled(true);


		if(Executions.getCurrent().getArg().get("Vistalistadoserviciosdistinto")!=null){
			//Persona Nueva
			persona = new SisDetallefacturaservicio();
			controlingresofactura = new SisFactura();
			personaUsuario = new SegUsuario();
			detallenuevo = new SisDetallefacturaservicio();
		}else {
			persona = new SisDetallefacturaservicio();
			controlingresofactura = new SisFactura();
			personaUsuario = new SegUsuario();
			detallenuevo = new SisDetallefacturaservicio();
		}
	}


	public List<SisServicio> getTipoServicios(){

		return servicidao.getListaServicios("");

	}


	public List<SegUsuariogenero> getGeneroPersona(){

		return generodao.getListaGeneros("");

	}


	public SisServicio getTipoServicioSelecionado() {
		return tipoServicioSelecionado;
	}


	public void setTipoServicioSelecionado(SisServicio tipoServicioSelecionado) {
		this.tipoServicioSelecionado = tipoServicioSelecionado;
	}


	public SegUsuariogenero getGeneroSelecionado() {
		return generoSelecionado;
	}


	public void setGeneroSelecionado(SegUsuariogenero generoSelecionado) {
		this.generoSelecionado = generoSelecionado;
	}


	public List<SegPerfil> getListaPerfiles(){

		return perfildao.getListaPerfiles("");

	}


	public SegPerfil getPerfilSelecionado() {
		return perfilSelecionado;
	}


	public void setPerfilSelecionado(SegPerfil perfilSelecionado) {
		this.perfilSelecionado = perfilSelecionado;
	}


	@Listen("onClick=#radioComuneros")
	public void radio_com(){
		if(radioComuneros.isChecked() == true){

			radioClientes.setChecked(false);
			nombre.setDisabled(true);
			celular.setDisabled(true);
			apellido1.setDisabled(true);
			apellido2.setDisabled(true);
			cbo_perfil.setVisible(false);
			cbo_genero.setVisible(false);
			nacionalidad.setVisible(false);
			nombre.setText("");
			celular.setText("");
			apellido1.setText("");
			apellido2.setText("");
			cbo_perfil.setText("");
			cbo_genero.setText("");
			nacionalidad.setText("");

			Lab_nac.setVisible(false);
			Lab_per.setVisible(false);
			Lab_gen.setVisible(false);

		}		
	}

	@Listen("onClick=#radioClientes")
	public void radio_cli(){
		if(radioClientes.isChecked() == true){
			radioComuneros.setChecked(false);


			nombre.setDisabled(true);
			celular.setDisabled(true);
			apellido1.setDisabled(true);
			apellido2.setDisabled(true);
			cbo_perfil.setVisible(false);
			cbo_genero.setVisible(false);
			nacionalidad.setVisible(true);
			nombre.setText("");
			celular.setText("");
			apellido1.setText("");
			apellido2.setText("");
			cbo_perfil.setText("");
			cbo_genero.setText("");
			nacionalidad.setText("");

			Lab_nac.setVisible(true);
			Lab_per.setVisible(true);
			Lab_gen.setVisible(true);
		}		
	}


	@Listen("onClick=#salir")
	public void salir(){
		winEmpleados.detach();

	}



	@Listen("onOK=#efectivofac")
	public void onQuery() {
		// do something
		//System.out.println("jhonny flores ");
		float codEfectivo = (float) (Float.parseFloat(efectivofac.getValue()));
		float codCambio	= (float)	(Float.parseFloat(totalfac.getValue()));
		float valorResp = codEfectivo - codCambio;
		cambiofac.setValue("" + valorResp );

		DateFormat hourFormat = new SimpleDateFormat("HH:mm");
		horaactual.setValue(hourFormat.format(date));

	}



	@Listen("onOK=#cedula")
	public void recuperarDatos(){


		if(radioComuneros.isChecked() == false && radioClientes.isChecked() == false) {

			Clients.showNotification("Seleccionar un tipo de usuario");
			return;
		}


		reporteListaC = usuariodao.getRecuperaSoloComuneros(cedula.getValue());

		if(radioComuneros.isChecked() == true) {

			if (reporteListaC.size() > 0)
			{
				for(SegUsuario pago : reporteListaC) {


					nombre.setValue(pago.getNombre());
					celular.setValue(pago.getCelular());
					apellido1.setValue(pago.getApellidoPaterno());
					apellido2.setValue(pago.getApellidoMaterno());
				}
			}else{

				totalfac.setValue("00.00");
				cambiofac.setValue("00.00");
				efectivofac.setValue("00.00");

				nombre.setValue("");
				celular.setValue("");
				apellido1.setValue("");
				apellido2.setValue("");

				Clients.showNotification("No existen Comuneros con esa cédula");
				return;
			}

		}else {

			if(radioClientes.isChecked() == true) {

				//aqui para buscar x cliente
				reporteListaS = usuariodao.getRecuperaSoloClientes(cedula.getValue());

				if (reporteListaS.size() > 0)
				{
					for(SegUsuario pago : reporteListaS) {


						nombre.setValue(pago.getNombre());
						celular.setValue(pago.getCelular());
						apellido1.setValue(pago.getApellidoPaterno());
						apellido2.setValue(pago.getApellidoMaterno());
						nacionalidad.setValue(pago.getNacionalidad());

						nombre.setDisabled(true);
						celular.setDisabled(true);
						apellido1.setDisabled(true);
						apellido2.setDisabled(true);
						cbo_perfil.setVisible(false);
						cbo_genero.setVisible(false);
						nacionalidad.setVisible(true);
					}
				}else{


					totalfac.setValue("00.00");
					cambiofac.setValue("00.00");
					efectivofac.setValue("00.00");

					nombre.setValue("");
					celular.setValue("");
					apellido1.setValue("");
					apellido2.setValue("");
					nacionalidad.setValue("");
					Clients.showNotification("No existen Clientes con esa cédula");

					nombre.setDisabled(false);
					celular.setDisabled(false);
					apellido1.setDisabled(false);
					apellido2.setDisabled(false);
					cbo_perfil.setVisible(true);
					cbo_perfil.setDisabled(false);
					cbo_genero.setVisible(true);
					cbo_genero.setDisabled(false);
					nacionalidad.setVisible(true);
					nacionalidad.setDisabled(false);

					return;
				}



			}



		}


	}


	public List<SisFormapago> getListaPagos(){

		return formadao.getListaFormas("");

	}


	public SisFormapago getFormaSeleccionado() {
		return formaSeleccionado;
	}


	public void setFormaSeleccionado(SisFormapago formaSeleccionado) {
		this.formaSeleccionado = formaSeleccionado;
	}


	private boolean validarDatos() {
		try {


			if(celular.getText() == "") {
				Clients.showNotification("Debe registrar o recuperar celular");
				return false;
			}


			if(nombre.getText() == "") {
				Clients.showNotification("Debe registrar o recuperar nombre");
				return false;
			}

			if(apellido1.getText() == "") {
				Clients.showNotification("Debe registrar o recuperar apellido");
				return false;
			}

			if(apellido2.getText() == "") {
				Clients.showNotification("Debe registrar o recuperar apellido");
				return false;
			}

			if(cedula.getText() == "") {
				Clients.showNotification("Debe registrar cédula");
				return false;
			}


			if(comboPago.getSelectedIndex() == -1) {
				Clients.showNotification("Debe selecionar Forma de Pago");
				return false;
			}



			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}



	@Listen("onClick=#sumar")
	public void agregar(){

		if(cont == 0) {
			try {

				Time hora =  new  Time (date.getTime());

				if(validarDatos() == true){	


					//alamcena datos ("aqui para verificar usuario y guardar una pre factura .... ");
					reporteListaS = usuariodao.getRecuperaSoloClientes(cedula.getValue());

					if(reporteListaS.size()>0) {
						for(SegUsuario pago : reporteListaS) {

							verificaU = 1;
							//asigno para gurdar facura.seguridad

							controlingresofactura.setSegUsuario(pago);
						}
					}

					reporteListaC = usuariodao.getRecuperaSoloComuneros(cedula.getValue());
					
					if(reporteListaC.size()>0) {
						
						for(SegUsuario pago : reporteListaC) {

							//asigno para gurdar facura.seguridad
							verificaU = 1;
							controlingresofactura.setSegUsuario(pago);
						}
					}

					//si la persona es nuev cliente y no esta en la lista entonces se procede a
					//alamacenar ese dato

					if (verificaU == 0) {

						//almacenar ese dato
						if(radioClientes.isChecked()== true) {


							if(nacionalidad.getText() == "") {
								Clients.showNotification("Debe registrar nacionalidad");
								return;
							}


							if(cbo_genero.getSelectedIndex() == -1) {
								Clients.showNotification("Debe selecionar genero");
								return ;
							}

							if(cbo_perfil.getSelectedIndex() == -1) {
								Clients.showNotification("Debe selecionar perfil cliente");
								return ;
							}


							personaUsuario.setApellidoMaterno(apellido1.getText());
							personaUsuario.setApellidoPaterno(apellido2.getText());
							personaUsuario.setCedula(cedula.getText());
							personaUsuario.setCelular(celular.getText());
							personaUsuario.setEstado("A");
							personaUsuario.setFecha(date);
							personaUsuario.setHora(hora);
							personaUsuario.setNacionalidad(nacionalidad.getText());
							personaUsuario.setNombre(nombre.getText());
							personaUsuario.setSegPerfil(perfilSelecionado);
							personaUsuario.setSegUsuariogenero(generoSelecionado);



							usuariodao.getEntityManager().getTransaction().begin();
							usuariodao.getEntityManager().persist(personaUsuario);
							usuariodao.getEntityManager().getTransaction().commit();

							controlingresofactura.setSegUsuario(personaUsuario);

						}else {
							Clients.showNotification("Debe verificar que este seleccionado radio cliente ");
							return;
						}
					}	
					
					//

				}else {
					Clients.showNotification("Debe verificar los datos del cliente ");
					return;
				}


				//hazta aqui comienzao otro proceso
				//*****************************
				if(comboPago.getSelectedIndex() == -1) {
					Clients.showNotification("Debe selecionar forma de pago");
					return ;
				}

				//guardar pre factura
				controlingresofactura.setDescripcion("Ninguna");
				controlingresofactura.setEstadoFactura("C");
				controlingresofactura.setFecha(date);
				controlingresofactura.setHora(hora);

				controlingresofactura.setSisFormapago(formaSeleccionado);
				controlingresofactura.setSubtotalFactura(0);
				controlingresofactura.setTotalFactura(0);

				//factura	
				facturaDao.getEntityManager().getTransaction().begin();
				facturaDao.getEntityManager().persist(controlingresofactura);
				facturaDao.getEntityManager().getTransaction().commit(); 

				cont = 1;


				idfacturaBuscar = controlingresofactura.getId_Factura();

				if(cantidad.getText().equals("0")) {
					Clients.showNotification("Debe registrar cantidad");
					return;
				}


				if(cbo_servicios.getSelectedIndex() == -1) {
					Clients.showNotification("Debe selecionar servicio");
					return ;
				}

				detallenuevo.setCantidad(Integer.parseInt(cantidad.getText()));
				detallenuevo.setEstadoServ("A");
				detallenuevo.setSisFactura(controlingresofactura);
				detallenuevo.setSisServicio(tipoServicioSelecionado);

				//	
				detalledao.getEntityManager().getTransaction().begin();
				detalledao.getEntityManager().persist(detallenuevo);
				detalledao.getEntityManager().getTransaction().commit(); 

				detallenuevo = new SisDetallefacturaservicio();
				listarServicios();

				cantidad.setText("0");
				cbo_servicios.setText("");
			}catch (Exception e) {
				// TODO: handle exception
			}
		}else {

			//aqui ahora solo guardo los servicios  que va a utilizar para pagar 

			if(cantidad.getText().equals("0")) {
				Clients.showNotification("Debe registrar cantidad");
				return;
			}


			if(cbo_servicios.getSelectedIndex() == -1) {
				Clients.showNotification("Debe selecionar servicio");
				return ;
			}

			detallenuevo.setCantidad(Integer.parseInt(cantidad.getText()));
			detallenuevo.setEstadoServ("A");
			detallenuevo.setSisFactura(controlingresofactura);
			detallenuevo.setSisServicio(tipoServicioSelecionado);

			//	
			detalledao.getEntityManager().getTransaction().begin();
			detalledao.getEntityManager().persist(detallenuevo);
			detalledao.getEntityManager().getTransaction().commit(); 

			detallenuevo = new SisDetallefacturaservicio();
			cantidad.setText("0");
			cbo_servicios.setText("");
			listarServicios();
		}
	}


	public void listarServicios() {

		if (ListaFac != null) {
			ListaFac = null; 
		}

		float valoru= 0;
		float cantidad = 0;
		float Tprevio = 0;
		float acum = 0;
		ListaFac = detalledao.getListaPorIdFactura(idfacturaBuscar);

		listaServicios.setModel(new ListModelList(ListaFac));

		for(SisDetallefacturaservicio det :ListaFac) {

			cantidad = det.getCantidad();
			valoru = det.getSisServicio().getValorServicios();

			Tprevio = cantidad * valoru;

			acum = acum + Tprevio;
		}

		totalfac.setValue("" + acum);
		//Limpiar
		serviciosSelecionada = null;

	}


	public SisDetallefacturaservicio getServiciosSelecionada() {
		return serviciosSelecionada;
	}


	public void setServiciosSelecionada(SisDetallefacturaservicio serviciosSelecionada) {
		this.serviciosSelecionada = serviciosSelecionada;
	}


	@Listen("onClick=#restar")
	public void restar(){

		if (serviciosSelecionada == null) {
			Clients.showNotification(" Debe seleccionar un Registro de la lista");
			return;
		}
		Messagebox.show("Desea Eliminar el Registro Seleccionado","Mensaje", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						//personaSeleccionada.setEstado("X");
						serviciosSelecionada.setEstadoServ("I");;
						detalledao.getEntityManager().getTransaction().begin();
						detalledao.getEntityManager().persist(serviciosSelecionada);
						detalledao.getEntityManager().getTransaction().commit();;
						Clients.showNotification("Registro Eliminada");



						listarServicios();
						//Limpiar
						serviciosSelecionada = null;
					} catch (Exception e) {
						e.printStackTrace();
						detalledao.getEntityManager().getTransaction().rollback();
					}
				}


			}
		});

	}



	@Listen("onClick=#grabar")
	public void grabar(){

		try {

			if(validarDatos() == true) {

				if (cont == 1) {

					//actualizar el estado y total de la pre facura anterior guardada 



					ListaRecupera = facturaDao.getListaIdFactura(idfacturaBuscar);

					if(ListaRecupera.size() > 0) {

						for(SisFactura fa:ListaRecupera) {
							fa.setEstadoFactura("A");
							//fa.setSisFormapago(formaSeleccionado);
							fa.setTotalFactura(Float.parseFloat(totalfac.getText()));
							fa.setSubtotalFactura(Float.parseFloat(totalfac.getText()));

							facturaDao.getEntityManager().getTransaction().begin();
							facturaDao.getEntityManager().persist(fa);
							facturaDao.getEntityManager().getTransaction().commit(); 

						}


						salir();

						listaP.buscar();
						Clients.showNotification("Registro Almacenado");


						//imprimir
						//SECCION PARA IMPRIRMIR UN COMPROBANTE S TODO ESTA CORRECTO

						//aqui imprimo el reporte en pdf 
						//imprimir reporte   
						PrintReport obj = new PrintReport();
						Map<String,Object> parametros = new HashMap<String,Object>();
						parametros.put("idfactura",idfacturaBuscar);	
						obj.ejecutaReporte(facturaDao,"/A_ModulosPagos/ReportePagos/PagoServicios.jasper", parametros, "PDF", "Comprobante");				




						//


						PeriodosE =  periodoDao.getListaPerfiles("");


						for(SegPeriodoEncargo det : PeriodosE) {

							float acum = det.getValorIngreso();

							det.setValorIngreso(acum + Float.parseFloat(totalfac.getText()));

							periodoDao.getEntityManager().getTransaction().begin();
							periodoDao.getEntityManager().persist(det);
							periodoDao.getEntityManager().getTransaction().commit();
						}



					}else {
						Clients.showNotification("factura no encontrada");
					}

				}else {
					Clients.showNotification("Ingrese un Servicio");
					return;
				}

			}






		}catch (Exception e) {
			// TODO: handle exception
		}
	}


	public List<SisDetallefacturaservicio> getListaFac() {
		return ListaFac;
	}


	public void setListaFac(List<SisDetallefacturaservicio> listaFac) {
		ListaFac = listaFac;
	}





}

