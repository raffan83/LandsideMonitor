package it.sti.landsidemonitor.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.Utility;
import it.sti.landsidemonitor.dto.SensorDTO;

public class JobSchedulerAtTime implements Job {

	final static Logger logger = Logger.getLogger(JobSchedulerAtTime.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		ArrayList<SensorDTO> listaSonde=PortReader.getListaSonde();
		
		double startTime=System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss.SSS");
		while(true) 
		{
				double tempoTrascorso =System.currentTimeMillis()-startTime;
				if(tempoTrascorso<900) 
				{
					for (SensorDTO sensorDTO : listaSonde) 
					{
						String message=PortReader.getMessage();
					
						if((message.startsWith("<S-B"+sensorDTO.getIdentifier())||
							message.startsWith("<"+sensorDTO.getIdentifier())) &&
							message.split(",").length==5 && sensorDTO.getType().equals("B")) 
						{
							/*Implementare esclusione per calibrazione (creare metrica)*/
							
							double acc_X=0.0;
							double acc_Y=0.0;
							double acc_Z=0.0;

							String[] data=message.split(",");
							
							if(message.split(",")[2].length()>0) 
							{
								acc_X=Math.abs(Double.parseDouble(data[2]));
							}

							if(message.split(",")[3].length()>0)
							{
								acc_Y=Math.abs(Double.parseDouble(data[3]));
							}

							if(message.split(",")[4].length()>0) 
							{ 
								acc_Z=Math.abs(Double.parseDouble(data[4].substring(0,data[4].length()-1)));
							}
							
							
							
							if(Utility.isAutomatiCalibration(acc_X,acc_Y,acc_Z)==false)
							{
								if(!PortReader.puntiAttiviB.containsKey(sensorDTO))
								{
									PortReader.puntiAttiviB.put(sensorDTO,System.currentTimeMillis());
									logger.warn("Add: "+message +"at time "+sdf.format(new Date()));
								}
							}
						}
						
						if(message.startsWith("<HT-"+sensorDTO.getIdentifier())) 
						{
							
							PortReader.puntiAttiviB.remove(sensorDTO);
							logger.warn("Remove: "+message +"at time "+sdf.format(new Date()));
						}
					}
				}else 
				{
					break;
				}
			}
	}

}
