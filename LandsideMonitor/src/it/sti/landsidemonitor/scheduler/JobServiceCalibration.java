package it.sti.landsidemonitor.scheduler;

import java.util.ArrayList;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.SendEmailBO;
import it.sti.landsidemonitor.dto.SensorDTO;
import it.sti.landsidemonitor.gui.MainFrame;
import jssc.SerialPortException;

public class JobServiceCalibration  implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	
		ArrayList<SensorDTO> listaSensori=MainFrame.listaSensori;
		
		System.out.println("Inizio calibrazione periodica");
		for (int i=0;i<listaSensori.size();i++) 
		{	
			System.out.println("Chiamata periodica sonda: "+listaSensori.get(i).getIdentifier());
			
			String idSonda = ""+listaSensori.get(i).getId();

          	try {
          		
				PortReader.write("C"+listaSensori.get(i).getIdentifier());
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
           	
           	double tempoStart=System.currentTimeMillis();
			
    		String msgCalibration="";
    		
           	while(true)
			{
				double tempoTrascorso=System.currentTimeMillis()-tempoStart;
				
				String message=PortReader.getMessage();
				
				if(message.startsWith("<CL-"+listaSensori.get(i).getIdentifier()))
					{
					System.out.println("Risposta periodica sonda: "+listaSensori.get(i).getIdentifier());
						msgCalibration=message;
						break;
					}
				
				if(tempoTrascorso>1500) 
				{
					if(msgCalibration.equals(""))
					{
						System.out.println("Mancata risposta: "+listaSensori.get(i).getIdentifier()+" invio mail");
						try {
							Core.cambiaStato(Integer.parseInt(idSonda), 5);
						//	SendEmailBO.
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				}
			}	
				
			}
		System.out.println("Fine calibrazione periodica");
		
	}

}