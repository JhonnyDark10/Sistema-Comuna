package controlador;

import java.util.List;

import org.zkoss.bind.ValidationContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Captcha;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;

import Captcha.ControladorCorreo;
import Captcha.Email;
import Captcha.RandomStringGenerator;
import model.SegUsuario;
import model.SegUsuarioDAO;
import util.Validar;

@SuppressWarnings({ "serial", "rawtypes" })
public class RecuperaCorreo extends SelectorComposer{

	
	public static String Glob_fecha = "";
	public static String Glob_hora = "";
	public static String Glob_asunto = "";
	public static String Glob_lugar = "";
	public static String Glob_descripcion = "";

	
	
	
	
	private List<SegUsuario> UsuarioR;
	
	private String captcha;
	Validar valida = new Validar();
	SegUsuarioDAO personaDao = new SegUsuarioDAO();	
	Email c = new Email();
	
	@Wire Textbox hora,lugar,descripcion,asunto,correo,fecha;
	
	@Wire Label errorcedula,errorusuario,errorvalidacion,mensaje;
	@Wire Captcha codigo;
	@Wire Button actualizar,enviar;
	@Wire Panel panel;
	@Wire Checkbox uno,muchos;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		
	}
	
	
		
	//*****************************
	//ENVIAR EL CORREO 
	//*****************************
		
	@Listen("onClick=#enviar;")
	public void enviar(){
		
	
   		mensaje.setValue("");
   		
      
		Glob_fecha = fecha.getText();
		Glob_hora =  hora.getText();
		Glob_asunto = asunto.getText();
		Glob_lugar = lugar.getText();
		Glob_descripcion = descripcion.getText();
		
		
		enviarCorreo();
		   
	   
	}
	
	
    //**********************************************
	//*** ENVIO DE CORREO ELECTRONICO
	//**********************************************
    public void enviarCorreo(){
    	
    	if(uno.isChecked() == true) {
    		
    		//*************
    		
    	   	 c.setContraseña("rvvfzpxqsfqkrbit");
    	   	 c.setUsuarioCorreo("floresdark59@gmail.com");
    	   	 c.setAsunto("" + asunto.getText());
    	   	 c.setMensaje(
    		
    	   			 ""
    	   	         + "\n" + "\nFecha : " + "  " + fecha.getText()
    	   	         + "\n" + "\nHora  : " + "  " + hora.getText()
    	   	         + "\n" + "\nAsunto: " + "  " + asunto.getText()
    	   	         + "\n" + "\nLugar : " + "  " + lugar.getText()
    	   	         + "\n" + ""
    	   			 + "\n" + "\nEstimado/s:\n" 
    	   			 + "\n" +""
    	   	         + "\n" +  descripcion.getText()
    	   	         + "\n"  + "" 
    	   	         + "\n"  + "" 
    	   	         + "\n"  + "" 
    	   	         + "\n"  + "" 
    	   	         + "\n"  + "                                 Atentamente" 
    	   	         + "\n"  + "                          Presidente/a de la Comuna" );
    	   	        
    	   	  
    	   	 
    	   	 
    	   	 c.setDestino("" + correo.getText());
    	   	 
    	   	 ControladorCorreo co = new ControladorCorreo();
    	   	 if(co.enviarcorreo(c)) {

    	   		mensaje.setValue("Petición Realizada!");
    	   		
    	   		
    	   		fecha.setText("");
    	   		hora.setText("");
    	   		asunto.setText("");
    	   		lugar.setText("");
    	   		descripcion.setText("");
    	   	
    	   		correo.setValue("");
    	   		
    	   		Clients.showNotification("Correo Enviado");
    	   		
    			
    	   	 }else {
    	   		 Clients.showNotification("Correo no enviado");
    	   		

    	   	 }
    		
    		//*************
    		
    	}
    	
    	
    	
    	
    	
    	
    	if(muchos.isChecked() == true) {
    		
    		UsuarioR = personaDao.getListaUbicaciones();
    		
    		for(SegUsuario det : UsuarioR) {
    			
    			//*************
        		

       	   	 c.setContraseña("rvvfzpxqsfqkrbit");
       	   	 c.setUsuarioCorreo("floresdark59@gmail.com");
       	   	 c.setAsunto("" + asunto.getText());
       	   	 c.setMensaje("______________________________________________________"
       	   			 + "\n" + "_________________________________________________________"
       	   			 + "\n" +""
       	   			 + "\n" +"  ***************** Comuna Montañita *****************" 
       	   			 + "\n" +""
       	   	         + "\n" + "Fecha : " + fecha.getText()
       	   	         + "\n" + "Hora  : " + hora.getText()
       	   	         + "\n" + "Asunto: " + asunto.getText()
       	   	         + "\n" + "Lugar : " + lugar.getText()
       	   	         + "\n" +"_________________________________________________________"
       	   	         + "\n" +"_________________________________________________________"
       	   	         + "\n" +""
       	   			 + "\n" + "Descripción:" 
       	   	         + "\n" +  descripcion.getText());
       	   	 
       	   	 
       	   	 
       	   	 c.setDestino("" + det.getEmail());
       	   	 
       	   	 ControladorCorreo co = new ControladorCorreo();
       	   	 if(co.enviarcorreo(c)) {

       	   		mensaje.setValue("Petición Realizada!");
       	   		
       	   		
       	   		Clients.showNotification("Correo Enviado");
       	   		
       			
       	   	 }else {
       	   		 Clients.showNotification("Correo no enviado");
       	   		
       	   	 }
       		
    			
    			
    		}
    		
    		fecha.setText("");
   	   		hora.setText("");
   	   		asunto.setText("");
   	   		lugar.setText("");
   	   		descripcion.setText("");
   	   		
   	   		correo.setValue("");
    	}
    	
    }
  
   

		
	@Listen("onClick=#uno;")
	public void uno(){
		if(uno.isChecked() == true) {
			muchos.setDisabled(true);
			muchos.setChecked(false);
			correo.setVisible(true);
		}else {
			muchos.setDisabled(false);
			muchos.setChecked(false);
		}
	}
	
	@Listen("onClick=#muchos;")
	public void muchos(){
		if(muchos.isChecked() == true) {
			uno.setDisabled(true);
			uno.setChecked(false);
			correo.setVisible(false);
		}else {
			uno.setDisabled(false);
			uno.setChecked(false);
			correo.setVisible(true);
		}
	}
	
	
}
