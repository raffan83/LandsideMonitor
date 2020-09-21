package it.sti.landsidemonitor.bo;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import it.sti.landsidemonitor.dto.SensorDTO;

public class SendEmailBO implements Runnable {

	private String idSonda="";
	private int tipoAllarme;
	private int tipoMail;

	public SendEmailBO(String _idSonda,int _tipoAllarme,int _tipoMail) 
	{
		idSonda=_idSonda;
		tipoAllarme=_tipoAllarme;
		tipoMail=_tipoMail;
	}

	private static String getLabel(int tipoAllarme) {

		if(tipoAllarme==1) 
		{
			return "<h3 style=\"color: red\">ALLARME</h3>";
		}
		if(tipoAllarme==2) 
		{
			return "<h3 style=\"color: orange\">ALLERTA</h3>";
		}
		return null;
	}

	public static void sendEmailReport() throws EmailException {

		String[] destinatari=Costanti.DEST_MAIL_MAN.split(";");
		for (String dest : destinatari) 
		{
			
		System.out.println("INVIO EMAIL AVVIO SITEMA A:"+dest);
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



		email.addTo(dest);
		email.setFrom("system@alarm.com", "Report manutenzione");
		email.setSubject("Report avvio sitema monitoraggio");

		String html="<html><style type=\"text/css\">\n" + 
				".tg  {border-collapse:collapse;border-spacing:0;}\n" + 
				".tg td{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;\n" + 
				"  overflow:hidden;padding:10px 5px;word-break:normal;}\n" + 
				".tg th{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;\n" + 
				"  font-weight:normal;overflow:hidden;padding:10px 5px;word-break:normal;}\n" + 
				".tg .tg-d97j{background-color:#fe0000;border-color:#000000;color:#000000;font-size:15px;text-align:left;vertical-align:top}\n" + 
				".tg .tg-6haw{font-size:15px;font-style:italic;font-weight:bold;text-align:left;vertical-align:top}\n" + 
				".tg .tg-cbs6{font-size:15px;text-align:left;vertical-align:top}\n" + 
				".tg .tg-6t3r{font-style:italic;font-weight:bold;text-align:left;vertical-align:top}\n" +
				".tg .tg-0lax{text-align:left;vertical-align:top}"+
				"</style>\n" + 
				" <h3>Report avvio sistema monitoraggio frane</h3><br>" +
				"<table class=\"tg\" style=\"undefined;table-layout: fixed; width: 776px\">\n" + 
				"<colgroup>\n" + 
				"<col style=\"width: 104px\">\n" + 
				"<col style=\"width: 104px\">\n" + 
				"<col style=\"width: 104px\">\n" + 
				"<col style=\"width: 104px\">\n" + 
				"<col style=\"width: 103px\">\n" + 
				"<col style=\"width: 103px\">\n" + 
				"<col style=\"width: 154px\">\n" + 
				"</colgroup>\n" + 
				"  <tr>\n" + 
				"    <th class=\"tg-cbs6\"><span style=\"font-weight:bold;font-style:italic\">Sensor</span></th>\n" + 
				"    <th class=\"tg-6haw\">Bering</th>\n" + 
				"    <th class=\"tg-6t3r\">Pitch</th>\n" + 
				"    <th class=\"tg-cbs6\"><span style=\"font-weight:bold;font-style:italic\">Roll</span></th>\n" + 
				"    <th class=\"tg-cbs6\"><span style=\"font-weight:bold;font-style:italic\">Batt Lev</span></th>\n" + 
				"    <th class=\"tg-6haw\">Signal</th>\n" + 
				"    <th class=\"tg-6haw\">Note</th>\n" + 
				"  </tr>\n";

		String tr="";
		for (SensorDTO sensor : PortReader.getListaSonde()) 
		{
			if(sensor.getBering().equals("N/D")) 
			{
				tr="<tr>\n" + 
						"    <td class=\"tg-0lax\">"+sensor.getIdentifier()+"</td>\n" + 
						"    <td class=\"tg-0lax\" colspan=\"6\">Sonda non raggiungibile</td>\n" + 
						"  </tr>\n";
				html=html+tr;
			}
			else 
			{
				double signal=0;
				
				if(Utility.isDouble(sensor.getSignal())) 
				{
					signal=Double.parseDouble(sensor.getSignal());
				}
				
				if(Double.parseDouble(sensor.getBattLevel())<3.83 || signal<-100 )
				{
					tr="<tr>\n"+
							"<td class=\"tg-d97j\">"+sensor.getIdentifier()+"</td>\n"+
							"<td class=\"tg-d97j\">"+sensor.getBering()+"</td>\n" +
							"<td class=\"tg-d97j\">"+sensor.getPitch()+"</td>\n"+
							"<td class=\"tg-d97j\">"+sensor.getRoll()+"</td>\n"+
							"<td class=\"tg-d97j\">"+sensor.getBattLevel()+"</td>\n"+
							"<td class=\"tg-d97j\">"+sensor.getSignal()+"</td>\n"+
							"<td class=\"tg-d97j\">Livello batteria o segnale troppo bassi</td>\n"+
							"</tr>\n";
					html=html+tr;
				}
				else

				{
					tr="<tr>\n"+
							"<td class=\"tg-0lax\">"+sensor.getIdentifier()+"</td>\n"+
							"<td class=\"tg-0lax\">"+sensor.getBering()+"</td>\n" +
							"<td class=\"tg-0lax\">"+sensor.getPitch()+"</td>\n"+
							"<td class=\"tg-0lax\">"+sensor.getRoll()+"</td>\n"+
							"<td class=\"tg-0lax\">"+sensor.getBattLevel()+"</td>\n"+
							"<td class=\"tg-0lax\">"+sensor.getSignal()+"</td>\n"+
							"<td class=\"tg-0lax\"></td>\n"+
							"</tr>\n";
					html=html+tr;
				}
			}


		}

		html=html+"</table></html>";

		email.setHtmlMsg(html);


		// set the alternative message
		email.setTextMsg("Segnalazione Avvio Sistema");

		// add the attachment

		// send the email
		email.send();
		}
	}

	@Override
	public void run() {

		try 
		{
			if(tipoMail==1) 
			{
			System.out.println("INVIO MAIL");
			
			String[] des= null;

			if(tipoAllarme==1) 
			{
				des=Costanti.DEST_MAIL_ALARM.split(";");
			}else 
			{
				des=Costanti.DEST_MAIL_PRE.split(";");
			}

			if(des!=null && des.length>0) 
			{

				for (String destinatario : des) {

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


					if(tipoAllarme==1) 
					{
						email.setSubject("Segnalazione ALLARME sonda "+idSonda);
						email.setFrom("genova@stisrl.com", "Segnalazione ALLARME");
					}
					else 
					{
						email.setSubject("Segnalazione ALLERTA sonda "+idSonda);
						email.setFrom("genova@stisrl.com", "Segnalazione ALLERTA");
					}
					SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

					email.setHtmlMsg("<html><h3>In data "+sdf.format(new Date())+" la sonda "+idSonda+" ha segnalato un "+labelAlarm+"</h3></html>");

					// set the alternative message
					email.setTextMsg("Segnalazione allarme");

					// add the attachment

					// send the email
					email.send();
					System.out.println("MAIL INVIATA");
				}
			}
			}else 
			{
				
			sendEmailReport();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private  void sendEmailReportCalibrazione(ArrayList<SensorDTO> listaSensoriAssenti) throws EmailException {
//
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss.SSS");
//
//		String[] des= Costanti.DEST_MAIL_MAN.split(";");
//
//		if(des!=null && des.length>0) 
//		{
//			for (String destinatario : des) {
//
//				// Create the email message
//				HtmlEmail email = new HtmlEmail();
//				email.setHostName(Costanti.HOST_NAME_MAIL);
//				//email.setDebug(true);
//				email.setAuthentication(Costanti.USERNAME_MAIL,Costanti.PASSWORD_MAIL);
//
//
//				email.getMailSession().getProperties().put("mail.smtp.auth",Costanti.SMTP_AUTH);
//				email.getMailSession().getProperties().put("mail.debug", "true");
//				email.getMailSession().getProperties().put("mail.smtp.port", Costanti.PORT_MAIL);
//				email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", Costanti.PORT_MAIL);
//				email.getMailSession().getProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//				email.getMailSession().getProperties().put("mail.smtp.socketFactory.fallback", "false");
//				email.getMailSession().getProperties().put("mail.smtp.ssl.enable", Costanti.SSL);
//
//				email.addTo(destinatario);
//				email.setFrom("system@alarm.com", "Report manutenzione [perdita sonda]");
//				email.setSubject("Report mancato contatto sonda");
//
//				String html="<html><style type=\"text/css\">\n" + 
//						".tg  {border-collapse:collapse;border-spacing:0;}\n" + 
//						".tg td{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;\n" + 
//						"  overflow:hidden;padding:10px 5px;word-break:normal;}\n" + 
//						".tg th{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;\n" + 
//						"  font-weight:normal;overflow:hidden;padding:10px 5px;word-break:normal;}\n" + 
//						".tg .tg-d97j{background-color:#fe0000;border-color:#000000;color:#000000;font-size:15px;text-align:left;vertical-align:top}\n" + 
//						".tg .tg-6haw{font-size:15px;font-style:italic;font-weight:bold;text-align:left;vertical-align:top}\n" + 
//						".tg .tg-cbs6{font-size:15px;text-align:left;vertical-align:top}\n" + 
//						".tg .tg-6t3r{font-style:italic;font-weight:bold;text-align:left;vertical-align:top}\n" +
//						".tg .tg-0lax{text-align:left;vertical-align:top}"+
//						"</style>\n" + 
//						" <h3>Report periodico contatto sonde</h3><br>" +
//						"<table class=\"tg\" style=\"undefined;table-layout: fixed; width: 776px\">\n" + 
//						"<colgroup>\n" + 
//						"<col style=\"width: 104px\">\n" + 
//						"<col style=\"width: 104px\">\n" + 
//						"<col style=\"width: 104px\">\n" + 
//						"<col style=\"width: 104px\">\n" + 
//						"<col style=\"width: 103px\">\n" + 
//						"<col style=\"width: 103px\">\n" + 
//						"<col style=\"width: 154px\">\n" + 
//						"</colgroup>\n" + 
//						"  <tr>\n" + 
//						"    <th class=\"tg-cbs6\"><span style=\"font-weight:bold;font-style:italic\">Sensor</span></th>\n" + 
//						"    <th class=\"tg-6haw\">Bering</th>\n" + 
//						"    <th class=\"tg-6t3r\">Pitch</th>\n" + 
//						"    <th class=\"tg-cbs6\"><span style=\"font-weight:bold;font-style:italic\">Roll</span></th>\n" + 
//						"    <th class=\"tg-cbs6\"><span style=\"font-weight:bold;font-style:italic\">Batt Lev</span></th>\n" + 
//						"    <th class=\"tg-6haw\">Signal</th>\n" + 
//						"    <th class=\"tg-6haw\">Note</th>\n" + 
//						"  </tr>\n";
//
//				String tr="";
//				for (SensorDTO sensor : listaSensoriAssenti) 
//				{
//
//					tr="<tr>\n" + 
//							"    <td class=\"tg-0lax\">"+sensor.getIdentifier()+"</td>\n" + 
//							"    <td class=\"tg-0lax\" colspan=\"6\">Sonda non raggiungibile ["+sdf.format(new Date())+"]</td>\n" + 
//							"  </tr>\n";
//					html=html+tr;
//
//
//
//
//				}
//
//				html=html+"</table></html>";
//
//				email.setHtmlMsg(html);
//
//
//				// set the alternative message
//				email.setTextMsg("Segnalazione manutenzione");
//
//				// add the attachment
//
//				// send the email
//				email.send();
//			}
//		}
//	}
}
