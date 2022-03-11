package Captcha;
 



import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import controlador.Menu;

 
public class Soporte extends UserForm {
  	

	Email c = new Email();
	
	


	@Wire Textbox cedula_f;
	@Wire Textbox email;
	@Wire Button boton;
	
	@Wire Groupbox grupo;
	@Wire Window ventana;
	
    @Command
    @NotifyChange("captcha")
    public void regenerate() {
        this.regenerateCaptcha();
    }
 
    @Command
    public void submit() {   
    	
    	enviarCorreo();
    	
    }
    

   
    
     public void enviarCorreo(){
    	 c.setContraseña("dotlsjdrgllfdcgq");
    	 c.setUsuarioCorreo("floresdark59@gmail.com");
    	 c.setAsunto("Soporte o Citación");
    	 c.setMensaje("" + cedula_f.getText() + "");
    	 c.setDestino("" + email.getText() );
    	 
    	 ControladorCorreo co = new ControladorCorreo();
    	 
    	 if(co.enviarcorreo(c)) {
    		 Clients.showNotification("Correo Enviado");
    
    		 
    	 }else {
    		 Clients.showNotification("Correo no enviado");
				
    	 }
     }
   
 

     
   
}