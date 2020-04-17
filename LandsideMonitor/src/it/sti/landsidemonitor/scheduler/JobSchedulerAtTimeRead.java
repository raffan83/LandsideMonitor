package it.sti.landsidemonitor.scheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dao.MainDAO;
import it.sti.landsidemonitor.dto.SensorDTO;

public class JobSchedulerAtTimeRead implements Job{

	final static Logger logger = Logger.getLogger(JobSchedulerAtTimeRead.class);
	
	@Override
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	
		
		final int PUNTI_5_SEC=4;
		final int PUNTI_9_SEC=3;
		final int PUNTI_12_SEC=2;
		final int PUNTI_15_SEC=1;
	
	try {
	
		if(PortReader.puntiAttiviB.size()>=PUNTI_5_SEC) 
		{
			ArrayList<SensorDTO> lisSen = new ArrayList<>();
			
			 Iterator it = PortReader.puntiAttiviB.entrySet().iterator();
			 while(it.hasNext()){
			       Map.Entry me = (Map.Entry)it.next();
			      
			       long time =(Long)me.getValue();
			       
			       logger.warn("[5 SEC] " +(System.currentTimeMillis()-time));
			       if(System.currentTimeMillis()-time>=5000) 
			       {
			    	   lisSen.add((SensorDTO)me.getKey());
			       }
			 }
			 
			 if(lisSen.size()>=PUNTI_5_SEC) 
			 {
				 for (SensorDTO sensorDTO : lisSen) 
				 {
					 PortReader.cambiaStato(sensorDTO, 1);
				     logger.warn("Cambio Stato ALLARME sonda: "+sensorDTO.getIdentifier());
				     Core.registraEvento(sensorDTO.getIdentifier(),"006",2,0,0,0);
				}
			 }
			   
		}
		

		if(PortReader.puntiAttiviB.size()==PUNTI_9_SEC) 
		{
			ArrayList<SensorDTO> lisSen = new ArrayList<>();
			Iterator it = PortReader.puntiAttiviB.entrySet().iterator();
			
			while(it.hasNext()){
			       Map.Entry me = (Map.Entry)it.next();
			      
			       long time =(Long)me.getValue();
			       
			       System.out.println("[9 SEC] " +(System.currentTimeMillis()-time));
			       if(System.currentTimeMillis()-time>=9000) 
			       {
			    	   lisSen.add((SensorDTO)me.getKey());
			       }
			 }
			 
			 if(lisSen.size()>=PUNTI_9_SEC) 
			 {
				 for (SensorDTO sensorDTO : lisSen) 
				 {
					 PortReader.cambiaStato(sensorDTO, 2);
					 logger.warn("Cambio Stato ALLARME sonda: "+sensorDTO.getIdentifier());
				     Core.registraEvento(sensorDTO.getIdentifier(),"007",2,0,0,0);
				 }
			 }
			 
		}
		

		if(PortReader.puntiAttiviB.size()==PUNTI_12_SEC) 
		{
			ArrayList<SensorDTO> lisSen = new ArrayList<>();
			Iterator it = PortReader.puntiAttiviB.entrySet().iterator();
			
			while(it.hasNext()){
			       Map.Entry me = (Map.Entry)it.next();
			      
			       long time =(Long)me.getValue();
			       
			     System.out.println("[12 SEC] " +(System.currentTimeMillis()-time));
			       if(System.currentTimeMillis()-time>=12000) 
			       {
			    	   lisSen.add((SensorDTO)me.getKey());
			       }
			 }
			 
			 if(lisSen.size()>=PUNTI_12_SEC) 
			 {
				 for (SensorDTO sensorDTO : lisSen) 
				 {
					 PortReader.cambiaStato(sensorDTO, 2);
					 logger.warn("Cambio Stato ALLARME sonda: "+sensorDTO.getIdentifier());
				     Core.registraEvento(sensorDTO.getIdentifier(),"008",2,0,0,0);
				 }
			 }
		
		}
		

		if(PortReader.puntiAttiviB.size()>=PUNTI_15_SEC) 
		{
			ArrayList<SensorDTO> lisSen = new ArrayList<>();
			Iterator it = PortReader.puntiAttiviB.entrySet().iterator();
			
			while(it.hasNext()){
			       Map.Entry me = (Map.Entry)it.next();
			      
			       long time =(Long)me.getValue();
			       
			     System.out.println("[15 SEC] " +(System.currentTimeMillis()-time));
			       if(System.currentTimeMillis()-time>=15000) 
			       {
			    	   lisSen.add((SensorDTO)me.getKey());
			       }
			 }
			 
			 if(lisSen.size()>=PUNTI_15_SEC) 
			 {
				 for (SensorDTO sensorDTO : lisSen) 
				 {
					 PortReader.cambiaStato(sensorDTO, 2);
				     logger.warn("Cambio Stato ALLARME sonda: "+sensorDTO.getIdentifier());
				     Core.registraEvento(sensorDTO.getIdentifier(),"009",2,0,0,0);
				 }
			 }
		
		
		}
			
	}catch (Exception e) 
	{
		logger.error(e);
		
	}
	}

}
