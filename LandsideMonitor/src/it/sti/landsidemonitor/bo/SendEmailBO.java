package it.sti.landsidemonitor.bo;



import java.io.File;

import javax.mail.Authenticator;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;

import it.sti.landsidemonitor.dto.SensorDTO;


 

public class SendEmailBO {
	public static void sendEmailAlarm(String mailTo,SensorDTO sensor,String posX,String posY,String posZ) throws Exception {
				
		  // Create the email message
		  HtmlEmail email = new HtmlEmail();
		  email.setHostName(Costanti.HOST_NAME_MAIL);
  		 //email.setDebug(true);
		  email.setAuthentication(Costanti.USERNAME_MAIL,Costanti.PASSWORD_MAIL);



	        email.getMailSession().getProperties().put("mail.smtp.auth",Costanti.SMTP_AUTH);
	        email.getMailSession().getProperties().put("mail.debug", "true");
	        email.getMailSession().getProperties().put("mail.smtp.port", Costanti.PORT_MAIL);
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", Costanti.PORT_MAIL);
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.fallback", "false");
	        email.getMailSession().getProperties().put("mail.smtp.ssl.enable", Costanti.SSL);

		  

		  email.addTo(mailTo);
		  email.setFrom("system@alarm.com", "Segnalazione Allarme");
		  email.setSubject("Segnalazione allarme sonda "+sensor.getIdentifier());
		  

		  email.setHtmlMsg("<html><h3>Segnalazione allarme sonda "+sensor.getIdentifier()+" <br>"
		  					   + "POS X:"+posX+" <br>"
		  					   + "POS Y:"+posY+" <br>"
		  					   + "POS Z:"+posZ+"</h3></html>");


		  // set the alternative message
		  email.setTextMsg("Segnalazione allarme");

		  // add the attachment
		  
		  // send the email
		  email.send();
		  
		  
		  
 	}
}
