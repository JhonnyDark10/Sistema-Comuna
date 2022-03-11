package Captcha;

import java.net.URL;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import controlador.RecuperaCorreo;

public class ControladorCorreo {

		
	
	RecuperaCorreo recuperaC = new RecuperaCorreo();
	
	
	public boolean enviarcorreo(Email c) {
		try {
			Properties p= new Properties();
			p.put("mail.smtp.host", "smtp.gmail.com");
			p.setProperty("mail.smtp.starttls.enable", "true");
			p.setProperty("mail.smtp.port", "587");
			p.setProperty("mail.smtp.user", c.getUsuarioCorreo());
			p.setProperty("mail.smtp.auth", "true");
			
			Session s=  Session.getDefaultInstance(p,null);
			
			
			
			MimeMultipart m= new MimeMultipart("related");
			
			/****************
			 * 
			 * 
			 * 
			 */
			//m.addBodyPart(texto);
						
			
			
			
						// first part (the html)
						//BodyPart 
						BodyPart messageBodyPart = new MimeBodyPart();
						String htmlText = "<H3>El que de pequeño respeta la bandera, sabrá defenderla cuando sea mayor.</H3><img src=\"cid:image\">" 
						+ "<p><strong>Fecha: </strong>" + recuperaC.Glob_fecha + "</p>"	
						+ "<p><strong>Hora: </strong>" + recuperaC.Glob_hora + "</p>"
						+ "<p><strong>Asunto: </strong>" + recuperaC.Glob_asunto + "</p>"
						+ "<p><strong>Lugar: </strong>" + recuperaC.Glob_lugar + "</p>"
						 
						
						+ "<p><strong>Estimado(s): </strong>" + "</p>"
						+ "<p>" + recuperaC.Glob_descripcion + "</p>" 			
						
						+ "<p><strong>         Atentamente</strong>" + "</p>"
						+ "<p><strong>     Presidente(a) de la Comuna</strong>" + "</p>";
						
						messageBodyPart.setContent(htmlText, "text/html");
						// add it
						m.addBodyPart(messageBodyPart);
						
						
					
						// second part (the image)
						BodyPart imagenA = new MimeBodyPart();
						DataSource fds = new FileDataSource(
						 "/cabeceraOficio.png");

						imagenA.setDataHandler(new DataHandler(fds));
						imagenA.setHeader("Content-ID", "<image>");
			
						m.addBodyPart(imagenA);
			
					
					
			
			MimeMessage mensaje = new MimeMessage(s);
			mensaje.setFrom(new InternetAddress(c.getUsuarioCorreo()));
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(c.getDestino()));
			mensaje.setSubject(c.getAsunto());
			mensaje.setContent(m);
			
		
	
			
			Transport t=s.getTransport("smtp");
			t.connect(c.getUsuarioCorreo(), c.getContraseña());
			t.sendMessage(mensaje, mensaje.getAllRecipients());
			t.close();
			return true;
			
		}catch(Exception e) {
			System.out.print("Error");
			return false;
		}
	
	}
	
	
	
	
}
