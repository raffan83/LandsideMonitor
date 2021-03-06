package it.sti.landsidemonitor.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dto.SensorDTO;
import it.sti.landsidemonitor.gui.FrameConsole;
import it.sti.landsidemonitor.gui.RasterPanel;

public class JobSchedulerAtTimeRead implements Job{

	final static Logger logger = Logger.getLogger(JobSchedulerAtTimeRead.class);
	


	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	
		String currentState=PortReader.getMessage();
		
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		/*
		System.out.println(sdf.format(new Date())+ " Valore Precedente: "+PortReader.prevState+" Valore Corrente"+currentState);
		*/
		if(currentState.equals(PortReader.prevState)) 
		{
			RasterPanel.reset.setEnabled(true);
		}
		else 
		{
			RasterPanel.reset.setEnabled(false);
		}
		PortReader.prevState=currentState;
		
		final int PUNTI_5_SEC=Costanti.PUNTI_DET_5_SEC;
		final int PUNTI_9_SEC=Costanti.PUNTI_DET_9_SEC;
		final int PUNTI_12_SEC=Costanti.PUNTI_DET_12_SEC;
		final int PUNTI_15_SEC=Costanti.PUNTI_DET_15_SEC;
	
	try {
		
		/*Controllo Sirene*/
		
		if(PortReader.alarmDuration!=0) 
		{
			System.out.println(sdf.format(new Date())+ " Controllo durata allarme ["+(System.currentTimeMillis()-PortReader.alarmDuration)+"]");
			if(System.currentTimeMillis()-PortReader.alarmDuration>180000)
			{
				PortReader.write("Z");
				PortReader.alarmDuration=0;
			}
		}
		
	
		if(PortReader.puntiAttiviB.size()>=PUNTI_5_SEC) 
		{
			ArrayList<SensorDTO> lisSen = new ArrayList<>();
			
			 Iterator it = PortReader.puntiAttiviB.entrySet().iterator();
			 while(it.hasNext()){
			       Map.Entry me = (Map.Entry)it.next();
			      
			       long time =(Long)me.getValue();
			       
			       logger.warn("[5 SEC] " +(System.currentTimeMillis()-time));
			       FrameConsole.printConsole("[5 SEC] " +(System.currentTimeMillis()-time));
			       if(System.currentTimeMillis()-time>=5000) 
			       {
			    	   lisSen.add((SensorDTO)me.getKey());
			       }
			 }
			 
			 if(lisSen.size()>=PUNTI_5_SEC) 
			 {
				 for (SensorDTO sensorDTO : lisSen) 
				 {
					 if(sensorDTO.getStato()!=1 ) 
						{
						 PortReader.cambiaStato(sensorDTO, 1);
						}
				     logger.warn("Cambio Stato ALLARME sonda: "+sensorDTO.getIdentifier());
				     Core.registraEvento(sensorDTO.getIdentifier(),"006",1,0,0,0);
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
			       FrameConsole.printConsole("[9 SEC] " +(System.currentTimeMillis()-time));
			       if(System.currentTimeMillis()-time>=9000) 
			       {
			    	   lisSen.add((SensorDTO)me.getKey());
			       }
			 }
			 
			 if(lisSen.size()>=PUNTI_9_SEC) 
			 {
				 for (SensorDTO sensorDTO : lisSen) 
				 {
					 if(sensorDTO.getStato()!=1 && sensorDTO.getStato()!=2) 
					 {
						 PortReader.cambiaStato(sensorDTO, 2);
						 logger.warn("Cambio Stato ALLERTA sonda: "+sensorDTO.getIdentifier());
					     Core.registraEvento(sensorDTO.getIdentifier(),"007",2,0,0,0);
					 }
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
			     FrameConsole.printConsole("[12 SEC] " +(System.currentTimeMillis()-time));
			       if(System.currentTimeMillis()-time>=12000) 
			       {
			    	   lisSen.add((SensorDTO)me.getKey());
			       }
			 }
			 
			 if(lisSen.size()>=PUNTI_12_SEC) 
			 {
				 for (SensorDTO sensorDTO : lisSen) 
				 {
					 if(sensorDTO.getStato()!=1 && sensorDTO.getStato()!=2)
					 {
						 PortReader.cambiaStato(sensorDTO, 2);
						 logger.warn("Cambio Stato ALLERTA sonda: "+sensorDTO.getIdentifier());
					     Core.registraEvento(sensorDTO.getIdentifier(),"008",2,0,0,0);
					 }
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
			     
			     FrameConsole.printConsole("[15 SEC] " +(System.currentTimeMillis()-time));  
			     
			     if(System.currentTimeMillis()-time>=15000) 
			       {
			    	   lisSen.add((SensorDTO)me.getKey());
			       }
			 }
			 
			 if(lisSen.size()>=PUNTI_15_SEC) 
			 {
				 for (SensorDTO sensorDTO : lisSen) 
				 {
					 if(sensorDTO.getStato()!=1 && sensorDTO.getStato()!=2) 
					 {
						 PortReader.cambiaStato(sensorDTO, 2);
						 logger.warn("Cambio Stato ALLERTA sonda: "+sensorDTO.getIdentifier());
						 Core.registraEvento(sensorDTO.getIdentifier(),"009",2,0,0,0);
					 }
				 }
			 }
		
		
		}
			
	}catch (Exception e) 
	{
		logger.error(e);
		
	}
	}

}
