package Captcha;
 

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;

import controlador.Menu;
public class FormViewModel extends UserForm {
  	
	Menu e = new Menu();
	Email c = new Email();
	
	
	UserForm f = new UserForm();
	
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
    	 c.setContrase�a("dotlsjdrgllfdcgq");
    	 c.setUsuarioCorreo("floresdark59@gmail.com");
    	 c.setAsunto("Cambio de Clave");
    	 c.setMensaje("El empleado con C�dula: " + e.Glob_Cedula.toString() + " Solicita recuperaci�n de contrase�a");
    	 c.setDestino("flores_dark10@outlook.es");
    	 
    	 ControladorCorreo co = new ControladorCorreo();
    	 if(co.enviarcorreo(c)) {
    		 Clients.showNotification("Correo Enviado");
    		 
               /*  		
             	*/
    	
    		 //f.regenerateCaptcha();
    		 
    	 }else {
    		 Clients.showNotification("Correo no enviado");
				
    	 }
     }
   
 
     
     /*****************************/
     
     
   
}