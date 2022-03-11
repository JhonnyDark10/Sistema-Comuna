package Captcha;
 

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import controlador.Menu;
import model.SegUsuario;
import model.SegUsuarioDAO;
import util.Validar;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.Property;


public class FormValidator extends AbstractValidator {
     
	UserForm dat = new UserForm();
	Validar valida = new Validar();

	private List<SegUsuario> UsuarioR;
	SegUsuarioDAO personaDao = new SegUsuarioDAO();
    Menu m = new Menu();
	
    public void validate(ValidationContext ctx) {
       
    	
    	Map<String,Property> beanProps = ctx.getProperties(ctx.getProperty().getBase());
         
    	m.Glob_Cedula = (String)beanProps.get("cedula").getValue();
        m.Glob_Correo = (String)beanProps.get("email").getValue();
        
        
        
    	validateCedula(ctx, (String)beanProps.get("cedula").getValue());
    	validateEmail(ctx, (String)beanProps.get("email").getValue());
        validateCaptcha(ctx, (String)ctx.getValidatorArg("captcha"), (String)ctx.getValidatorArg("captchaInput"));
  
    }
     
    
    
    private void validateEmail(ValidationContext ctx, String email) {
       /* if(email == null || !email.matches(".+@.+\\.[a-z]+")) {
            this.addInvalidMessage(ctx, "email", "Direccion no valida!");            
        }*/
        
    	UsuarioR = personaDao.getTodos(m.Glob_Cedula);
		
    	for(SegUsuario det : UsuarioR) {

    		if (det.getUsuario().equals(m.Glob_Correo )) {
    			  return;  			
    		}
    		else
    		{
    		   this.addInvalidMessage(ctx, "email", "Usuario no encontrado!");
    		}
			
		}       
        
    }
  
     
    private void validateCedula(ValidationContext ctx, String cedula) {
       
    	if(valida.validarDeCedula(cedula) == false) {
    		
    		    		
    		this.addInvalidMessage(ctx, "cedula", "Cédula Incorrecta!");
    		
		} 	
    
    }
    
  
    
    
    private void validateCaptcha(ValidationContext ctx, String captcha, String captchaInput) {
        if(captchaInput == null || !captcha.equals(captchaInput)) {
            this.addInvalidMessage(ctx, "captcha", "El captcha no coincide!");
        }
    }
     
}