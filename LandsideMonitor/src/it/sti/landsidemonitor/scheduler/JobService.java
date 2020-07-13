package it.sti.landsidemonitor.scheduler;



import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.SendEmailBO;

public class JobService implements Job{
	
	final static Logger logger = Logger.getLogger(JobService.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try 
		{
			System.out.println("Job dest");
			String[] destinatari=Costanti.DEST_MAIL_MAN.split(";");
			for (String dest : destinatari) 
			{
				System.out.println("Send man: "+dest);
				SendEmailBO.sendEmailReport();
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		
	}

}
