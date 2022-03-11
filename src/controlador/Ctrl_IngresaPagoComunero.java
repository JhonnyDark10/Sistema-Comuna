package controlador;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import model.SegPeriodoEncargo;
import model.SegPeriodoEncargoDAO;
import model.SegUsuario;
import model.SegUsuarioDAO;
import model.SegUsuariogenero;
import model.SisCategoriapago;
import model.SisCategoriapagoDAO;
import model.SisControlpago;
import model.SisControlpagoDAO;
import model.SisFactura;
import model.SisFacturaDAO;
import model.SisFormapago;
import model.SisFormapagoDAO;
import util.PrintReport;



@SuppressWarnings({ "serial", "rawtypes" })
public class Ctrl_IngresaPagoComunero extends SelectorComposer {

	
	Date date = new Date();
	@Wire
	Textbox horaactual,fechaactual,totalfac,cambiofac,efectivofac,cedula,nombre,celular,id_monto;
	
	@Wire Combobox comboPago,comboCategoria;
	@Wire
	Window winPago;
	private Ctrl_ListaPagosComuneros listaP;
	
	private SisControlpago persona;
	
	List<SegUsuario> reporteListaC;
	
	SegUsuarioDAO usuariodao = new SegUsuarioDAO();
	SisCategoriapagoDAO categoriadao = new SisCategoriapagoDAO();
	SisFormapagoDAO formadao = new SisFormapagoDAO();
	
	SisControlpagoDAO controldao = new SisControlpagoDAO();
	
	private SisFacturaDAO facturaDao = new SisFacturaDAO() ;
	private SisFactura controlingresofactura;
	
	SisCategoriapago categoriaSeleccionado;
	SisFormapago formaSeleccionado;
	
	private SegPeriodoEncargoDAO periodoDao = new SegPeriodoEncargoDAO();

	private List<SegPeriodoEncargo> PeriodosE;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

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
		id_monto.setValue("00.00");
		
		
		listaP = (Ctrl_ListaPagosComuneros)Executions.getCurrent().getArg().get("VentanaPadre");

		
		if(Executions.getCurrent().getArg().get("Vistalistadopagoscomunero")!=null){
			//Persona Nueva
			persona = new SisControlpago();
			controlingresofactura = new SisFactura();
		}else {
			persona = new SisControlpago();
			controlingresofactura = new SisFactura();
		}
	}
	
	
	//evento enter
		@Listen("onOK=#efectivofac")
		public void onQuery() {
			// do something
			//System.out.println("jhonny flores ");
			float codEfectivo = (float) (Float.parseFloat(efectivofac.getValue()));
			float codCambio	= (float)	(Float.parseFloat(id_monto.getValue()));
			float valorResp = codEfectivo - codCambio;
			cambiofac.setValue("" + valorResp );

			DateFormat hourFormat = new SimpleDateFormat("HH:mm");
			horaactual.setValue(hourFormat.format(date));

		}
		
		
		
		
		@Listen("onOK=#cedula")
		public void recuperarComuneros(){
			
			reporteListaC = usuariodao.getRecuperaSoloComuneros(cedula.getValue());

			if (reporteListaC.size() > 0)
			{
				for(SegUsuario pago : reporteListaC) {
					
					
					nombre.setValue(pago.getNombre());
					celular.setValue(pago.getCelular());		
				}
			}else{

				totalfac.setValue("00.00");
				cambiofac.setValue("00.00");
				efectivofac.setValue("00.00");
				nombre.setValue("");
				celular.setValue("");
				id_monto.setValue("00.00");
				
				Clients.showNotification("No existen Comuneros con ese registro");
				return;
			}
		}	
		
		

		@Listen("onClick=#salir")
		public void salir(){
			winPago.detach();
			
		}

		
	
		
		@Listen("onClick=#grabar")
		public void grabar(){
			
			try {
				
				Time hora =  new  Time (date.getTime());
				//valida si existe usuario 
				reporteListaC = usuariodao.getRecuperaSoloComuneros(cedula.getValue());
				if (reporteListaC.size() > 0)
				{
					
					if(validarDatos() == true){

						
						for(SegUsuario pago : reporteListaC) {
							
							controlingresofactura.setSegUsuario(pago);
									
						}
						
						
						controlingresofactura.setDescripcion("Ninguna");
						controlingresofactura.setEstadoFactura("A");
						controlingresofactura.setFecha(date);
						controlingresofactura.setHora(hora);
						
						controlingresofactura.setSisFormapago(formaSeleccionado);
						controlingresofactura.setSubtotalFactura(Float.parseFloat(id_monto.getText()));
						controlingresofactura.setTotalFactura(Float.parseFloat(id_monto.getText()));

						//factura	
						facturaDao.getEntityManager().getTransaction().begin();
						facturaDao.getEntityManager().persist(controlingresofactura);
						facturaDao.getEntityManager().getTransaction().commit(); 
						
						
						persona.setEstadoServ("A");
						persona.setSisCategoriapago(categoriaSeleccionado);
						persona.setSisFactura(controlingresofactura);
						persona.setValorTotal(Float.parseFloat(id_monto.getText()));
						
						//CONTROL
						
						
						controldao.getEntityManager().getTransaction().begin();
						controldao.getEntityManager().persist(persona);
						controldao.getEntityManager().getTransaction().commit(); 
						
						
						Clients.showNotification("Registro Almacenado");

						
						//SECCION PARA IMPRIRMIR UN COMPROBANTE S TODO ESTA CORRECTO
						
						//aqui imprimo el reporte en pdf 
						//imprimir reporte   
						PrintReport obj = new PrintReport();
						Map<String,Object> parametros = new HashMap<String,Object>();
						parametros.put("idfactura",controlingresofactura.getId_Factura());	
						obj.ejecutaReporte(controldao,"/A_ModulosPagos/ReportePagos/PagoComuneros.jasper", parametros, "PDF", "Comprobante");				
						
						
						
						
						//
						//actualizar el valor de ingreso del presidente 
						
						
						PeriodosE =  periodoDao.getListaPerfiles("");
						
					
						for(SegPeriodoEncargo det : PeriodosE) {
							
							float acum = det.getValorIngreso();
							
							det.setValorIngreso(acum + Float.parseFloat(id_monto.getText()));
							
							periodoDao.getEntityManager().getTransaction().begin();
							periodoDao.getEntityManager().persist(det);
							periodoDao.getEntityManager().getTransaction().commit();
						}

						
						
						
						salir();
						listaP.buscar();
						
						
					}else{
						return;
					}
					
					
					
				}else {
					
					Clients.showNotification("No existen Comuneros con ese registro");
					return;
					
				}
				
				
				
				
				
			} catch (Exception e) {
				facturaDao.getEntityManager().getTransaction().rollback();
			}
			
			
		}
		
		
		
		
		
		
	public List<SisCategoriapago> getListaCategorias(){

		return categoriadao.getListaCategorias("");

	}
	
	
	
	
	public List<SisFormapago> getListaPagos(){

		return formadao.getListaFormas("");

	}


	public SisControlpago getPersona() {
		return persona;
	}


	public void setPersona(SisControlpago persona) {
		this.persona = persona;
	}


	public SisCategoriapago getCategoriaSeleccionado() {
		return categoriaSeleccionado;
	}


	public void setCategoriaSeleccionado(SisCategoriapago categoriaSeleccionado) {
		this.categoriaSeleccionado = categoriaSeleccionado;
	}


	public SisFormapago getFormaSeleccionado() {
		return formaSeleccionado;
	}


	public void setFormaSeleccionado(SisFormapago formaSeleccionado) {
		this.formaSeleccionado = formaSeleccionado;
	}

	private boolean validarDatos() {
		try {

			if(id_monto.getText() == "") {
				Clients.showNotification("Debe registrar monto");
				return false;
			}
			
			if(comboPago.getSelectedIndex() == -1) {
				Clients.showNotification("Debe seleccionar Forma de pago");
				return false;
			}
			
			if(comboCategoria.getSelectedIndex() == -1) {
				Clients.showNotification("Debe seleccionar Categoria");
				return false;
			}
		
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
}
