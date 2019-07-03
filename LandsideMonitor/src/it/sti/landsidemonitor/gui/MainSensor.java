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
	
	boolean statoPreallarme_1=true;
	boolean statoPreallarme_2=true;
	boolean statoPreallarme_3=true;
	
	boolean statoAllarme=true;
	
	int iterazioni_preallarme_1=0;
	int iterazioni_preallarme_2=0;
	int iterazioni_preallarme_3=0;
	
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
			Thread.sleep(200);
			
			String value=portReader.getValue();
			
			if(Costanti.DEBUG) {System.out.println("val "+value);}
			 
			
			
			 if(sensor.getStato()==0) 
			 {
				 statoPreallarme_1=true;
				 statoPreallarme_2=true;
				 statoPreallarme_3=true;
				 statoAllarme=true;
			 }
			 
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
				
				acc_X=Math.abs(acc_X);
				acc_Y=Math.abs(acc_Y);
				acc_Z=Math.abs(acc_Z);
				
				/*ASSE X*/
				
				if( (acc_X>=Costanti.LIMITE_MIN_P1 && acc_X<=Costanti.LIMITE_MAX_P1)|| 
					(acc_Y>=Costanti.LIMITE_MIN_P1 && acc_Y<=Costanti.LIMITE_MAX_P1)||
					(acc_Z>=Costanti.LIMITE_MIN_P1 && acc_Z<=Costanti.LIMITE_MAX_P1)
				  ) 
						{
							if(sensor.getStato()!=1) 
							{
									iterazioni_preallarme_1++;
							}
							System.out.println("++ ITERAZIONE P1 "+acc_X+" "+acc_Y+" "+acc_Z +" ["+iterazioni_preallarme_1+"]");
						}
				
				if(     (acc_X>=Costanti.LIMITE_MIN_P2 && acc_X<=Costanti.LIMITE_MAX_P2)|| 
						(acc_Y>=Costanti.LIMITE_MIN_P2 && acc_Y<=Costanti.LIMITE_MAX_P2)||
						(acc_Z>=Costanti.LIMITE_MIN_P2 && acc_Z<=Costanti.LIMITE_MAX_P2)
					  ) 
						{
							if(sensor.getStato()!=1) 
							{
								iterazioni_preallarme_2++;
							}
							
							System.out.println("++ ITERAZIONE P2 "+acc_X+" "+acc_Y+" "+acc_Z +" ["+iterazioni_preallarme_2+"]");
							
						}
				
				if(     (acc_X>=Costanti.LIMITE_MIN_P3 && acc_X<=Costanti.LIMITE_MAX_P3)|| 
						(acc_Y>=Costanti.LIMITE_MIN_P3 && acc_Y<=Costanti.LIMITE_MAX_P3)||
						(acc_Z>=Costanti.LIMITE_MIN_P3 && acc_Z<=Costanti.LIMITE_MAX_P3)
					  ) 
						{
							if(sensor.getStato()!=1) 
							{
								iterazioni_preallarme_3++;
							}
							System.out.println("++ ITERAZIONE P3 "+acc_X+" "+acc_Y+" "+acc_Z +" ["+iterazioni_preallarme_3+"]");
									
						}
				
				
				if(sensor.getStato()!=1) 
				{
					if( acc_X>Costanti.LIMITE_MAX_P3|| acc_Y > Costanti.LIMITE_MAX_P3 || acc_Z > Costanti.LIMITE_MAX_P3) 
					{
						System.out.println("ALLARME "+acc_X+" "+acc_Y+" "+acc_Z );
						if(statoAllarme==true) 
						{
							
							Core.registraEvento(sensor.getIdentifier(),1,acc_X,acc_Y,acc_Z);
							mainP.cambiaStato(sensor.getId(), 1);
							sensor.setStatoOriginale(1);
							Core.cambiaStato(sensor.getId() ,1);
							portReader.write("A");
							statoAllarme=false;
							
							 iterazioni_preallarme_1=0;
							 iterazioni_preallarme_2=0;
							 iterazioni_preallarme_3=0;
							
						}
						
						
					}
					else 
					{
						if(iterazioni_preallarme_1>=Costanti.ITERAZIONI_P1 && iterazioni_preallarme_2<Costanti.ITERAZIONI_P2 && iterazioni_preallarme_3<Costanti.ITERAZIONI_P3) 
						{
							System.out.println("INSIDE P1");
							
						if(statoPreallarme_1==true) 
							{
								Core.registraEvento(sensor.getIdentifier(),2,acc_X,acc_Y,acc_Z);
								mainP.cambiaStato(sensor.getId(), 2);
								sensor.setStatoOriginale(2);
								Core.cambiaStato(sensor.getId() ,2);
							    portReader.write("B");
								statoPreallarme_1=false;
							}
						}
						if(iterazioni_preallarme_2>=Costanti.ITERAZIONI_P2 && iterazioni_preallarme_3<Costanti.ITERAZIONI_P3) 
						{
							System.out.println("INSIDE P2");
							
							if(statoPreallarme_2==true) 
							{
								Core.registraEvento(sensor.getIdentifier(),2,acc_X,acc_Y,acc_Z);
								mainP.cambiaStato(sensor.getId(), 3);
								sensor.setStatoOriginale(3);
								Core.cambiaStato(sensor.getId() ,3);
							    portReader.write("B");
								statoPreallarme_2=false;
							}
						}
						if(iterazioni_preallarme_3>=Costanti.ITERAZIONI_P3) 
						{
							System.out.println("INSIDE P3");
							if(statoPreallarme_3==true) 
							{
								Core.registraEvento(sensor.getIdentifier(),2,acc_X,acc_Y,acc_Z);
								mainP.cambiaStato(sensor.getId(), 4);
								sensor.setStatoOriginale(4);
								Core.cambiaStato(sensor.getId() ,4);
							    portReader.write("B");
								statoPreallarme_3=false;
							}
						}
					}
						 
				}
				
				
				/*
				if( (acc_X>=Costanti.LIMITE_ && acc_X<=Costanti.LIMITE_ALLARME)  || (acc_X>=-Costanti.LIMITE_PREALLARME && acc_X<=-Costanti.LIMITE_ALLARME)	
						
				  ) 
				{
					if(sensor.getStato()!=1) 
					{
						
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
						}
						else
							
							{
				
						
						
						}
						
					}
				}
				
				if(acc_X>=Costanti.LIMITE_ALLARME || acc_X<=-Costanti.LIMITE_ALLARME)
				{
					if(statoAllarme==true) 
					{
						Core.registraEvento(sensor.getIdentifier(),1,acc_X,acc_Y,acc_Z);
						mainP.cambiaStato(sensor.getId(), 1);
						sensor.setStatoOriginale(1);
						Core.cambiaStato(sensor.getId() ,1);
						portReader.write("A");
						statoAllarme=false;
					}
					
				}
		*/		
				

			
			
				}else
				{
					 VALORE_MANCATA_RICEZIONE_SONDA++;
					 
			
					
					 if(VALORE_MANCATA_RICEZIONE_SONDA > Costanti.VALORE_MANCATA_RICEZIONE_SONDA) 
					 {
						 mainP.cambiaStato(sensor.getId(),5);
					 }
				}
			
			   }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	return 0;	
		
	}
}
