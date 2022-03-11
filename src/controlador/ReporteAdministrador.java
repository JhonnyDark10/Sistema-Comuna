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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;

import org.zkoss.zul.Radio;

import model.SegPerfilDAO;
import model.SegPeriodoEncargo;
import model.SegPeriodoEncargoDAO;
import model.SegUsuario;
import model.SegUsuarioDAO;
import util.PrintReport;

@SuppressWarnings({ "serial", "rawtypes" })
public class ReporteAdministrador extends SelectorComposer{

	private SegPerfilDAO IngresoDao = new SegPerfilDAO();

	private SegUsuario usu = new SegUsuario();
	private SegUsuarioDAO usudao = new SegUsuarioDAO();

	List<SegUsuario> listausu;

	private SegUsuario TipocomunerosSelecionada;

	@Wire Radio radio_historial,radio_fechas;
	@Wire Datebox fecha_inicioB,fecha_finB;
	@Wire Label fi,ff;
	
	// Instancia el objeto para acceso a datos.
	private SegPeriodoEncargoDAO usuarioDao = new SegPeriodoEncargoDAO();

	private List<SegPeriodoEncargo> Usuarios;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);	
		fecha_inicioB.setVisible(false);
		fecha_finB.setVisible(false);
		ff.setVisible(false);
		fi.setVisible(false);
	}


	@Listen("onClick=#radio_historial")
	public void radio_emple(){
		if(radio_historial.isChecked() == true){
			fecha_inicioB.setVisible(false);
			fecha_finB.setVisible(false);
			ff.setVisible(false);
			fi.setVisible(false);
			radio_fechas.setChecked(false);
		}		
	}


	@Listen("onClick=#radio_fechas")
	public void radio_fech(){
		if(radio_fechas.isChecked() == true){
			fecha_inicioB.setVisible(true);
			fecha_finB.setVisible(true);
			ff.setVisible(true);
			fi.setVisible(true);
			radio_historial.setChecked(false);
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
			
			objA.ejecutaReporte(IngresoDao,"/A_Reporte/Consulta_TomaDesiciones/Principal.jasper", parametrosA, "PDF", "Reporte");				
			Clients.showNotification("Reporte Generado");
			return;

		}

		
		
		if(radio_fechas.isChecked() == true){

			
			//aqui imprimo el reporte en pdf 
			//imprimir reporte   
			PrintReport objA = new PrintReport();
			Map<String,Object> parametrosA = new HashMap<String,Object>();
			parametrosA.put("fechaini",fecha_inicioB.getValue());
			parametrosA.put("fechafin",fecha_finB.getValue());
			objA.ejecutaReporte(IngresoDao,"/A_Reporte/Consulta_TomaDesiciones/ControlPagoAnual.jasper", parametrosA, "PDF", "Reporte");				
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


	public SegUsuario getTipocomunerosSelecionada() {
		return TipocomunerosSelecionada;
	}


	public void setTipocomunerosSelecionada(SegUsuario tipocomunerosSelecionada) {
		TipocomunerosSelecionada = tipocomunerosSelecionada;
	}



}
