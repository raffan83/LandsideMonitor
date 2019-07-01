package it.sti.landsidemonitor.gui;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.SwingWorker;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dto.SensorDTO;

public class MainSensor extends SwingWorker<Integer, Integer>{

	RasterPanel mainP=null;
	PortReader portReader;
	ArrayList<String> msgs=null;
	SensorDTO sensor=null;
	Double oldVal_Y=0.0,oldVal_X=0.0;
	Double newVal_Y=0.0,newVal_X=0.0;
	
	
	int VALORE_MANCATA_RICEZIONE_SONDA;
	int SENSIBILITA_PREALLARME;
	
	public MainSensor(RasterPanel mainPanel, PortReader pr, SensorDTO _sensor) {
		mainP=mainPanel;
		portReader=pr;
	    sensor=_sensor;
	    VALORE_MANCATA_RICEZIONE_SONDA=0;
	    SENSIBILITA_PREALLARME=0;
	}

	@Override
	protected Integer doInBackground() throws Exception {

		try{
			while(true)
			{
			Thread.sleep(Costanti.FRAMERATE_READ_GRAPH);
			
			String value=portReader.getValue();
			
			if(Costanti.DEBUG) {System.out.println(value);}
			
	
				String pivot=value.split(",")[0];/*Recupero l'ID del sensore dalla stringa di lettura*/
			
				if(pivot.equals(sensor.getIdentifier())) 
				{
					VALORE_MANCATA_RICEZIONE_SONDA=0;
					 
				
				mainP.cambiaStato(sensor.getId(), sensor.getStatoOriginale());	
				double acc_X=0.0;
				double acc_Y=0.0;
				double acc_Z=0.0;
				
				
				
				if(value.split(",")[1].length()>0) 
				{
					 acc_X=Double.parseDouble(value.split(",")[1]);
				}
				
				if(value.split(",")[2].length()>0)
				{
					 acc_Y=Double.parseDouble(value.split(",")[2]);
				}
				
				if(value.split(",")[3].length()>0) 
				{ 
					 acc_Z=Double.parseDouble(value.split(",")[3]);
				}
				
				
				/*ASSE X*/
				
				if( (acc_X>=Costanti.LIMITE_PREALLARME && acc_X<=Costanti.LIMITE_ALLARME)  ||
						
						(acc_X>=-Costanti.LIMITE_PREALLARME && acc_X<=-Costanti.LIMITE_ALLARME)	
						
				  ) 
				{
					if(sensor.getStato()!=1) 
					{
					
						
					/*	
					 * DA TESTARE
					 * 
						
						SENSIBILITA_PREALLARME++;
						
						if(SENSIBILITA_PREALLARME>=Costanti.SENSIBILITA_PREALLARME) 
						{
							Core.registraEvento(sensor.getIdentifier(),2,acc_X,acc_Y,acc_Z);
							mainP.cambiaStato(sensor.getId(), 1);
							sensor.setStatoOriginale(1);
							Core.cambiaStato(sensor.getId() ,1);
							portReader.write("A");
							
							SENSIBILITA_PREALLARME=0;
						}else
							{
					*/
						
						Core.registraEvento(sensor.getIdentifier(),2,acc_X,acc_Y,acc_Z);
						mainP.cambiaStato(sensor.getId(), 2);
						sensor.setStatoOriginale(2);
						Core.cambiaStato(sensor.getId() ,2);
						portReader.write("B");
						//}
						
					}
				}
				
				if(acc_X>=Costanti.LIMITE_ALLARME || acc_X<=-Costanti.LIMITE_ALLARME)
				{
					Core.registraEvento(sensor.getIdentifier(),1,acc_X,acc_Y,acc_Z);
					mainP.cambiaStato(sensor.getId(), 1);
					sensor.setStatoOriginale(1);
					Core.cambiaStato(sensor.getId() ,1);
					portReader.write("A");
				}
				
				
				/*ASSE Y*/
				
				if( (acc_Y>=Costanti.LIMITE_PREALLARME && acc_Y<=Costanti.LIMITE_ALLARME)  ||
						
						(acc_Y>=-Costanti.LIMITE_PREALLARME && acc_Y<=-Costanti.LIMITE_ALLARME)	
						
				  ) 
				{
					if(sensor.getStato()!=1) 
					{
						
						/*	
						 * DA TESTARE
						 * 
							
							SENSIBILITA_PREALLARME++;
							
							if(SENSIBILITA_PREALLARME>=Costanti.SENSIBILITA_PREALLARME) 
							{
								Core.registraEvento(sensor.getIdentifier(),2,acc_X,acc_Y,acc_Z);
								mainP.cambiaStato(sensor.getId(), 1);
								sensor.setStatoOriginale(1);
								Core.cambiaStato(sensor.getId() ,1);
								portReader.write("A");
								
								SENSIBILITA_PREALLARME=0;
							}else
							{
						*/
						
						Core.registraEvento(sensor.getIdentifier(),2,acc_X,acc_Y,acc_Z);
						mainP.cambiaStato(sensor.getId(), 2);
						sensor.setStatoOriginale(2);
						Core.cambiaStato(sensor.getId() ,2);
						portReader.write("B");
						//}
					}
				}
				
				if(acc_Y>=Costanti.LIMITE_ALLARME || acc_Y<=-Costanti.LIMITE_ALLARME)
				{
					Core.registraEvento(sensor.getIdentifier(),1,acc_X,acc_Y,acc_Z);
					mainP.cambiaStato(sensor.getId(), 1);
					sensor.setStatoOriginale(1);
					Core.cambiaStato(sensor.getId() ,1);
					portReader.write("A");
				}
				/*ASSE Z*/
				
				if( (acc_Z>=Costanti.LIMITE_PREALLARME && acc_Z<=Costanti.LIMITE_ALLARME)  ||
						
						(acc_Z>=-Costanti.LIMITE_PREALLARME && acc_Z<=-Costanti.LIMITE_ALLARME)	
						
				  ) 
				{
					if(sensor.getStato()!=1) 
					{
						/*	
						 * DA TESTARE
						 * 
							
							SENSIBILITA_PREALLARME++;
							
							if(SENSIBILITA_PREALLARME>=Costanti.SENSIBILITA_PREALLARME) 
							{
								Core.registraEvento(sensor.getIdentifier(),2,acc_X,acc_Y,acc_Z);
								mainP.cambiaStato(sensor.getId(), 1);
								sensor.setStatoOriginale(1);
								Core.cambiaStato(sensor.getId() ,1);
								portReader.write("A");
								
								SENSIBILITA_PREALLARME=0;
							}else
							{
							
							
						*/
						
						Core.registraEvento(sensor.getIdentifier(),2,acc_X,acc_Y,acc_Z);
						mainP.cambiaStato(sensor.getId(), 2);
						sensor.setStatoOriginale(2);
						Core.cambiaStato(sensor.getId() ,2);
						portReader.write("B");
						
					//}
					}
				}
				
				if(acc_Z>=Costanti.LIMITE_ALLARME || acc_Y<=-Costanti.LIMITE_ALLARME)
				{
					Core.registraEvento(sensor.getIdentifier(),1,acc_X,acc_Y,acc_Z);
					mainP.cambiaStato(sensor.getId(), 1);
					sensor.setStatoOriginale(1);
					Core.cambiaStato(sensor.getId() ,1);
					portReader.write("A");
				}				
			
				}else
				{
					 VALORE_MANCATA_RICEZIONE_SONDA++;
					 
				//	 if(Costanti.DEBUG) {System.out.println("[Sonda - "+sensor.getIdentifier()+"]"+VALORE_MANCATA_RICEZIONE_SONDA);}
					
					 if(VALORE_MANCATA_RICEZIONE_SONDA > Costanti.VALORE_MANCATA_RICEZIONE_SONDA) 
					 {
						 mainP.cambiaStato(sensor.getId(),3);
					 }
				}
			
			   }
		//	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	return 0;	
		
	}
}
