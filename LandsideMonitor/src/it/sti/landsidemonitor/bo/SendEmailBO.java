package it.sti.landsidemonitor.bo;



import java.io.File;

import javax.mail.Authenticator;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import it.sti.landsidemonitor.dto.SensorDTO;


 

public class SendEmailBO {
	public static void sendEmailAlarm(String mailTo,SensorDTO sensor,String posX,String posY,String posZ) throws Exception {
				

		  
		  
 	}

	public static void sendEmailAlarm(String destinatario, String idSonda, int tipoAllarme, double pos_X, double pos_Y,double pos_Z) throws EmailException {
		  // Create the email message
		  HtmlEmail email = new HtmlEmail();
		  email.setHostName(Costanti.HOST_NAME_MAIL);
		 //email.setDebug(true);
		  email.setAuthentication(Costanti.USERNAME_MAIL,Costanti.PASSWORD_MAIL);

		  String labelAlarm=getLabel(tipoAllarme);

	        email.getMailSession().getProperties().put("mail.smtp.auth",Costanti.SMTP_AUTH);
	        email.getMailSession().getProperties().put("mail.debug", "true");
	        email.getMailSession().getProperties().put("mail.smtp.port", Costanti.PORT_MAIL);
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", Costanti.PORT_MAIL);
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.fallback", "false");
	        email.getMailSession().getProperties().put("mail.smtp.ssl.enable", Costanti.SSL);

		  

		  email.addTo(destinatario);
		  email.setFrom("system@alarm.com", "Segnalazione Allarme");
		  email.setSubject("Segnalazione "+ labelAlarm+" sonda "+idSonda);
		  

		  email.setHtmlMsg("<html><h3>Segnalazione "+labelAlarm+" sonda "+idSonda+" <br>"
		  					   + "ACC X:"+pos_X+" <br>"
		  					   + "ACC Y:"+pos_Y+" <br>"
		  					   + "ACC Z:"+pos_Z+"</h3></html>");


		  // set the alternative message
		  email.setTextMsg("Segnalazione allarme");

		  // add the attachment
		  
		  // send the email
		  email.send();
		  
		
	}

	private static String getLabel(int tipoAllarme) {
		
		if(tipoAllarme==1) 
		{
			return "PRE ALLARME 1";
		}
		if(tipoAllarme==2) 
		{
			return "PRE ALLARME 2";
		}
		if(tipoAllarme==3) 
		{
			return "PRE ALLARME 3";
		}
		if(tipoAllarme==4) 
		{
			return "ALLARME";
		}
		return null;
	}
}
