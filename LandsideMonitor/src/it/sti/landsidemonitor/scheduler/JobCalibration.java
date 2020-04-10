package it.sti.landsidemonitor.scheduler;

import java.util.ArrayList;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dto.SensorDTO;
import jssc.SerialPortException;

public class JobCalibration implements Job{
	

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			ArrayList<SensorDTO> listaSonde=PortReader.getListaSonde();
			
			for (SensorDTO sensorDTO : listaSonde) 
			{
			//	if(sensorDTO.getStato()!=5) 
			//	{
					System.out.println("chiamata sonda: "+sensorDTO.getIdentifier());
					PortReader.write(sensorDTO.getIdentifier());
			//	}
			}
			
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
