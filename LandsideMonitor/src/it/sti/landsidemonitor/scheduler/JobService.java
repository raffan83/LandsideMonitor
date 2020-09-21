package it.sti.landsidemonitor.scheduler;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.SendEmailBO;
import it.sti.landsidemonitor.dto.SensorDTO;
import it.sti.landsidemonitor.gui.RasterPanel;

public class JobService implements Job{
	
	final static Logger logger = Logger.getLogger(JobService.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try 
		{
			System.out.println("Job dest");
			
			for (SensorDTO sensor : PortReader.getListaSonde()) {

				sensor.setBering("N/D");
				sensor.setPitch("N/D");
				sensor.setRoll("N/D");
				sensor.setBattLevel("N/D");
				sensor.setSignal("N/D");
			}
			
//	double tempoAttuale=System.currentTimeMillis();
//			
//			for (SensorDTO sensorDTO : PortReader.getListaSonde()) 
//			{
//				
//				while(true) 
//				{
//					if(System.currentTimeMillis()-tempoAttuale>250) 
//					{
//						System.out.println("Call sensor: "+sensorDTO.getIdentifier());
//			//			logger.warn("Call sensor: "+sensorDTO.getIdentifier());
//						PortReader.write("C"+sensorDTO.getIdentifier());
//						tempoAttuale=System.currentTimeMillis();
//						break;
//						
//					}
//				}
//			
//			}
			 
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			System.out.println("click button "+sdf.format(new Date()));
				RasterPanel.reset.doClick();
			
				System.out.println("Sand mail "+sdf.format(new Date()));
				SendEmailBO report =new SendEmailBO("", 0, 2);
				Thread tReport = new Thread(report);
				tReport.start();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		
	}

}
