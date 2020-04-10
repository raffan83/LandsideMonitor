package it.sti.landsidemonitor.scheduler;

import java.util.ArrayList;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dto.SensorDTO;

public class JobSchedulerAtTime implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		ArrayList<SensorDTO> listaSonde=PortReader.getListaSonde();
		
		double startTime=System.currentTimeMillis();
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
							message.split(",").length==4) 
						{
							PortReader.puntiAttiviB.put(sensorDTO.getIdentifier(), sensorDTO);
						//	System.out.println("Add: "+message);
						}
						
						if(message.startsWith("<HT-"+sensorDTO.getIdentifier())) 
						{
							PortReader.puntiAttiviB.remove(sensorDTO.getIdentifier());
					//		System.out.println("Remove: "+message);
						}
					}
				}else 
				{
					break;
				}
			}
		
	}

}
