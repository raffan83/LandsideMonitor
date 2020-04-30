package it.sti.landsidemonitor.scheduler;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dto.SensorDTO;
import jssc.SerialPortException;

public class JobCalibration implements Job{
	
	final static Logger logger = Logger.getLogger(JobCalibration.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			ArrayList<SensorDTO> listaSonde=PortReader.getListaSonde();
			
			for (SensorDTO sensorDTO : listaSonde) 
			{
			//	if(sensorDTO.getStato()!=5) 
			//	{
					System.out.println("Call sensor: "+sensorDTO.getIdentifier());
					logger.warn("Call sensor: "+sensorDTO.getIdentifier());
					PortReader.write("C"+sensorDTO.getIdentifier());
			//	}
			}
			
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
