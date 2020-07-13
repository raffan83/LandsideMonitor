package it.sti.landsidemonitor.scheduler;

import java.util.ArrayList;

import org.apache.commons.mail.EmailException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.SendEmailBO;
import it.sti.landsidemonitor.dto.SensorDTO;
import it.sti.landsidemonitor.gui.MainFrame;
import it.sti.landsidemonitor.gui.RasterPanel;
import jssc.SerialPortException;

public class JobServiceCalibration  implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		if(PortReader.firstSendMailCalibration==true) 
		{	
			System.out.println("Inizio calibrazione periodica");
			try 
			{

				ArrayList<SensorDTO> listaSensori=MainFrame.listaSensori;

				ArrayList<SensorDTO> listaSensoriAssenti=new ArrayList<SensorDTO>();

				System.out.println("Inizio calibrazione periodica");
				
				for (int i=0;i<listaSensori.size();i++) 
				{	
					System.out.println("Chiamata periodica sonda: "+listaSensori.get(i).getIdentifier());

					PortReader.write("C"+listaSensori.get(i).getIdentifier());

					double tempoStart=System.currentTimeMillis();

					String msgCalibration="";

					while(true)
					{
						double tempoTrascorso=System.currentTimeMillis()-tempoStart;

						String message=PortReader.getMessage();

						System.out.println(message);
						if(message.startsWith("<CL-"+listaSensori.get(i).getIdentifier()))
						{
							System.out.println("Risposta periodica sonda: "+listaSensori.get(i).getIdentifier());
							msgCalibration=message;

						}

						if(tempoTrascorso>15000) 
						{
							if(msgCalibration.equals(""))
							{
								System.out.println("Mancata risposta: "+listaSensori.get(i).getIdentifier()+" invio mail");

								listaSensoriAssenti.add(listaSensori.get(i));
								PortReader.cambiaStato(listaSensori.get(i), 5);

							}
							break;
						}
					}	

				}

				if(listaSensoriAssenti.size()>0) 
				{

//					SendEmailBO mail = new SendEmailBO("",0,2,listaSensoriAssenti);
//					new Thread(mail).start();
//
//
//
//					System.out.println("Fine calibrazione periodica");

				}
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			//	System.out.println("Abilitazione controllo");
			PortReader.firstSendMailCalibration=true;
		}

	}
}