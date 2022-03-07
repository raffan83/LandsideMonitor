package it.sti.landsidemonitor.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.SendEmailBO;
import it.sti.landsidemonitor.gui.MainFrame;
import it.sti.landsidemonitor.gui.RasterPanel;
public class JobService implements Job{
	
	final static Logger logger = Logger.getLogger(JobService.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try 
		{
			logger.warn("System exit");
			System.exit(0);
			/*
			System.out.println("Report Manutenzione giornaliero");

			MainFrame.pr.checkHealthSensor(PortReader.getListaSonde());
			
			SendEmailBO report =new SendEmailBO("", 0, 2);
			Thread tReport = new Thread(report);
			tReport.start();
				*/		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		
	}

}
