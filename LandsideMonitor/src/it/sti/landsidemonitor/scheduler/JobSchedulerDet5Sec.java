package it.sti.landsidemonitor.scheduler;

import java.util.Iterator;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dao.MainDAO;
import it.sti.landsidemonitor.dto.SensorDTO;

public class JobSchedulerDet5Sec implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	
		boolean alarm=false;
		System.out.println("Check 5 second");
		
		if(PortReader.puntiAttiviB.size()==4) 
		{
			alarm=true;
		}
		 Iterator it = PortReader.puntiAttiviB.entrySet().iterator();
		 while(it.hasNext()){
		       Map.Entry me = (Map.Entry)it.next();
		       System.out.println("\t Move sensor: "+me.getKey());
		       if(alarm) 
		       {
			       SensorDTO s=(SensorDTO)me.getValue();
			       PortReader.cambiaStato(s, 1);
			       System.out.println("Cambio Stato ALLARME sonda: "+s.getIdentifier());
		       }
		 }
		
		
	}

}
