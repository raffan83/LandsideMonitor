package it.sti.landsidemonitor.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.sti.landsidemonitor.bo.PortReader;
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
							if(!PortReader.puntiAttiviB.containsKey(sensorDTO))
							{
								PortReader.puntiAttiviB.put(sensorDTO,System.currentTimeMillis());
								logger.warn("Add: "+message +"at time "+sdf.format(new Date()));
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
