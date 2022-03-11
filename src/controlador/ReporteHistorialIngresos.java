package controlador;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Radio;

import model.SegPerfilDAO;
import model.SegPeriodoEncargo;
import model.SegPeriodoEncargoDAO;
import model.SegUsuario;
import model.SegUsuarioDAO;
import util.PrintReport;

@SuppressWarnings({ "serial", "rawtypes" })
public class ReporteHistorialIngresos extends SelectorComposer{

	private SegPerfilDAO IngresoDao = new SegPerfilDAO();

	private SegUsuario usu = new SegUsuario();
	private SegUsuarioDAO usudao = new SegUsuarioDAO();

	private SegPeriodoEncargoDAO predao = new SegPeriodoEncargoDAO();
	List<SegUsuario> listausu;

	private SegUsuario TipocomunerosSelecionada;
	
	private SegPeriodoEncargo Periodoseleccionado;

	@Wire Radio radio_historial,radio_comuneros,radio_fechas,radio_historial2;
	@Wire Combobox combocomuneros,combopresidentes;
	@Wire Datebox fecha_inicioB,fecha_finB,ver1,ver2;
	@Wire Label fi,ff,ver0;
	// Instancia el objeto para acceso a datos.
	private SegPeriodoEncargoDAO usuarioDao = new SegPeriodoEncargoDAO();



	private List<SegPeriodoEncargo> Usuarios;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);	
		combopresidentes.setVisible(false);
		combocomuneros.setVisible(false);
		fecha_inicioB.setVisible(false);
		fecha_finB.setVisible(false);
		ff.setVisible(false);
		fi.setVisible(false);
		ver0.setVisible(false);
		ver1.setVisible(false);
		ver2.setVisible(false);
		ver1.setDisabled(true);
		ver2.setDisabled(true);
	}


	@Listen("onClick=#radio_historial")
	public void radio_emple(){
		if(radio_historial.isChecked() == true){
			radio_comuneros.setChecked(false);
			combocomuneros.setVisible(false);
			radio_historial2.setChecked(false);
			combopresidentes.setVisible(false);
			fecha_inicioB.setVisible(false);
			fecha_finB.setVisible(false);
			radio_fechas.setChecked(false);
			ff.setVisible(false);
			fi.setVisible(false);
			ver0.setVisible(false);
			ver1.setVisible(false);
			ver2.setVisible(false);
		}		
	}


	@Listen("onClick=#radio_comuneros")
	public void radio_emplea(){
		if(radio_comuneros.isChecked() == true){
			radio_historial.setChecked(false);
			combocomuneros.setVisible(true);
			combopresidentes.setVisible(false);
			radio_historial2.setChecked(false);
			fecha_inicioB.setVisible(false);
			fecha_finB.setVisible(false);
			radio_fechas.setChecked(false);
			ff.setVisible(false);
			fi.setVisible(false);
			ver0.setVisible(false);
			ver1.setVisible(false);
			ver2.setVisible(false);
		}		
	}


	@Listen("onClick=#radio_fechas")
	public void radio_fechasa(){
		if(radio_fechas.isChecked() == true){
			radio_historial.setChecked(false);
			combocomuneros.setVisible(false);
			radio_historial2.setChecked(false);
			combopresidentes.setVisible(false);
			radio_comuneros.setChecked(false);
			fecha_inicioB.setVisible(true);
			fecha_finB.setVisible(true);
			ff.setVisible(true);
			ver0.setVisible(false);
			ver1.setVisible(false);
			ver2.setVisible(false);
			fi.setVisible(true);
		}		
	}


	
	@Listen("onClick=#radio_historial2")
	public void radio_fecha2(){
		if(radio_historial2.isChecked() == true){
			radio_historial.setChecked(false);
			combocomuneros.setVisible(false);
			combopresidentes.setVisible(true);
			radio_comuneros.setChecked(false);
			
			fecha_inicioB.setVisible(false);
			fecha_finB.setVisible(false);
			radio_fechas.setChecked(false);
			ff.setVisible(false);
			fi.setVisible(false);
			ver0.setVisible(true);
			ver1.setVisible(true);
			ver2.setVisible(true);
		}		
	}

	
	
	@Listen("onSelect=#combopresidentes")
	public void comboaccion(){
	
		

		Usuarios = usuarioDao.getListaSeleccionado(Periodoseleccionado.getIdPeriodo());

		if(Usuarios.size() > 0) {
			for (SegPeriodoEncargo det:Usuarios) {
				ver1.setValue(det.getPeriodoDesde());
				ver2.setValue(det.getPeriodoHasta());
			}
		}

		
	}

	@Listen("onClick=#imprimir")
	public void imprimir(){

		Date	fechainicio = null;
		Date fechafinal = null ;

		Usuarios = usuarioDao.getListaPerfiles("");

		if(Usuarios.size() > 0) {
			for (SegPeriodoEncargo det:Usuarios) {
				fechainicio = det.getPeriodoDesde() ;
				fechafinal  = det.getPeriodoHasta() ;
			}
		}else {
			Clients.showNotification("No tiene presidentes activos");
			return;
		}


		if(radio_historial.isChecked() == true){

			
			//aqui imprimo el reporte en pdf 
			//imprimir reporte   
			PrintReport objA = new PrintReport();
			Map<String,Object> parametrosA = new HashMap<String,Object>();
			parametrosA.put("fechaini",fechainicio);
			parametrosA.put("fechafin",fechafinal);
			objA.ejecutaReporte(IngresoDao,"/A_Reporte/Historial.jasper", parametrosA, "PDF", "Reporte");				
			Clients.showNotification("Reporte Generado");
			return;

		}




		if(radio_comuneros.isChecked() == true){

			
			
			
			//aqui imprimo el reporte en pdf 
			//imprimir reporte   
			PrintReport objA = new PrintReport();
			Map<String,Object> parametrosA = new HashMap<String,Object>();
			parametrosA.put("comuneroId",TipocomunerosSelecionada.getIdUsuario());
			
			objA.ejecutaReporte(IngresoDao,"/A_Reporte/Consulta_Pagos_EspecificoComuneros/ConPagosComuneros.jasper", parametrosA, "PDF", "Reporte");				
			Clients.showNotification("Reporte Generado");
			return;

		}



			if(radio_fechas.isChecked() == true){

	
			//aqui imprimo el reporte en pdf 
			//imprimir reporte   
			PrintReport objA = new PrintReport();
			Map<String,Object> parametrosA = new HashMap<String,Object>();
			parametrosA.put("fechaini",fecha_inicioB);
			parametrosA.put("fechafin",fecha_finB);
			objA.ejecutaReporte(IngresoDao,"/A_Reporte/Consulta_Ingresos_Fechas_desde_hasta/ConIngresosFechas.jasper", parametrosA, "PDF", "Reporte");				
			Clients.showNotification("Reporte Generado");
			return;

		}

			
			

			if(radio_historial2.isChecked() == true){

				Date fechainicio1 = null;
				Date fechafinal1 = null ;

				Usuarios = usuarioDao.getListaSeleccionado(Periodoseleccionado.getIdPeriodo());

				if(Usuarios.size() > 0) {
					for (SegPeriodoEncargo det:Usuarios) {
						fechainicio1 = det.getPeriodoDesde() ;
						fechafinal1  = det.getPeriodoHasta() ;
					}
				}

	
			//aqui imprimo el reporte en pdf 
			//imprimir reporte   
			PrintReport objA = new PrintReport();
			Map<String,Object> parametrosA = new HashMap<String,Object>();
			parametrosA.put("fechaini",fechainicio1);
			parametrosA.put("fechafin",fechafinal1);
			parametrosA.put("periodoId",Periodoseleccionado.getIdPeriodo());
			objA.ejecutaReporte(IngresoDao,"/A_Reporte/Presenta_Ingresos_Por_Periodos/PresidentesPrincipal.jasper", parametrosA, "PDF", "Reporte");				
			Clients.showNotification("Reporte Generado");
			return;

		}

	}


	public List<SegPeriodoEncargo> getUsuarios() {
		return Usuarios;
	}


	public void setUsuarios(List<SegPeriodoEncargo> usuarios) {
		Usuarios = usuarios;
	}



	public List<SegUsuario> getSisCtrlusuario() {
		return usudao.getComunerosBuscaHistorial("");
	}

	
	public List<SegPeriodoEncargo> getSisCtrlpresidentes() {
		return predao.getListaTodosPresidentes();
	}

	
	public SegUsuario getTipocomunerosSelecionada() {
		return TipocomunerosSelecionada;
	}


	public void setTipocomunerosSelecionada(SegUsuario tipocomunerosSelecionada) {
		TipocomunerosSelecionada = tipocomunerosSelecionada;
	}


	public SegPeriodoEncargo getPeriodoseleccionado() {
		return Periodoseleccionado;
	}


	public void setPeriodoseleccionado(SegPeriodoEncargo periodoseleccionado) {
		Periodoseleccionado = periodoseleccionado;
	}



}
