package it.sti.landsidemonitor.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import it.sti.landsidemonitor.dto.SensorDTO;
import it.sti.landsidemonitor.gui.RasterPanel;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class PortReader implements SerialPortEventListener {

	SerialPort serialPort;
	String canale;
	private  String msg;
	private  ArrayList<SensorDTO> listaSensori;
	private RasterPanel mainP;
	HashMap<String, String> sogliaAllerta;

	SimpleDateFormat sdf = new SimpleDateFormat("ss.SSS");
	
	public PortReader(RasterPanel _mainP,SerialPort serialPort, ArrayList<SensorDTO> _listaSensori) 
	{
		this.serialPort = serialPort;
		listaSensori=_listaSensori;
		msg = "";
		sogliaAllerta=new HashMap<String,String>();
		mainP=_mainP;
	

	}

	@Override
	public void serialEvent(SerialPortEvent event) { 
		String playload="";
		boolean read=false;
		byte[] by= new byte[1];                       
		while(true){
			try {             
				by = serialPort.readBytes(1);
				if(by[0]!=0 )
				{                         
						if(by[0]==60)
						{
							read=true;
						}
						if(read) 
						{
							playload+=(char)by[0];
						}
						if(by[0]==62)
						{
							read=false;
							msg=playload;
							playload="";
							
							
							//System.out.println("Lettura: "+ msg+" "+sdf.format(new Date()));
							valutaSegnale(msg);
							
						}
				}
			}catch (SerialPortException ex) {
				System.out.println(ex);
			}                           
		}

	}
	
	private void valutaSegnale(String value) throws SerialPortException {
		
		for (SensorDTO sensor : listaSensori) {
			
			
			if(value.startsWith("<HT-"+sensor.getIdentifier()) && sensor.getStato()!=1)
			{
				System.out.println("HEALTH ["+sensor.getIdentifier()+"]");
				sensor.setTempo_periodo_5_sec(0);
				sensor.setTempo_periodo_3_sec(0);
				sensor.setTempo_periodo_2_sec(0);
		
			}
			
			
			if((value.startsWith("<S-A"+sensor.getIdentifier())||value.startsWith("<S-B"+sensor.getIdentifier())||value.startsWith("<"+sensor.getIdentifier()))&& !value.startsWith("<:") && !value.startsWith("<HT"))
			{
				String[] data=value.split(",");
				
				double acc_X=0.0;
				double acc_Y=0.0;
				double acc_Z=0.0;


				
				if(value.split(",")[1].length()>0) 
				{
					acc_X=Math.abs(Double.parseDouble(data[1]));
				}

				if(value.split(",")[2].length()>0)
				{
					acc_Y=Math.abs(Double.parseDouble(data[2]));
				}

				if(value.split(",")[3].length()>0) 
				{ 
					acc_Z=Math.abs(Double.parseDouble(data[3].substring(0,data[3].length()-1)));
				}
				
				/*Dati test accelerazione */
			//	acc_X=1.25;
			//	acc_Y=1.25;
			//	acc_Z=1.25;
				
				/*SONDE APPARTENENTE AL GRUPPO A*/
				if(sensor.getType().equals("R") && sensor.getStato()!=1 )
				{

					if(sensor.getStato()!=1 && sensor.getStato()!=2) 
					{
						sensor.setStato(3);
						mainP.cambiaStato(sensor.getId(), 3);
						
					}
					
				//	System.out.println("Movimento sonda: "+sensor.getIdentifier()+" Evento:["+data[0].split("_")[1]+ "] [X]:"+acc_X+" [Y]:"+acc_Y+" [Z]:"+acc_Z + " Time: "+sdf.format(new Date()));
				
					/*ALLARME*/
					if(statoAllarme(acc_X,acc_Y,acc_Z)==1) 
					{
						System.out.println("ALLARME ISTANTANEO > 5 m/s");
						sogliaAllerta.put(sensor.getIdentifier(), sensor.getIdentifier());
						mainP.cambiaStato(sensor.getId(), 2);
						sensor.setStato(2);
					//	serialPort.writeString("Y");
					}
					
					/*Pre-Allerta 5 sec*/
					if(statoAllarme(acc_X,acc_Y,acc_Z)==2 ) 
					{
						if(valutaTempo(sensor,2,System.currentTimeMillis())) 
						{
							mainP.cambiaStato(sensor.getId(), 2);
							sensor.setStato(2);
							sogliaAllerta.put(sensor.getIdentifier(), sensor.getIdentifier());
							//serialPort.writeString("X");
							
							System.out.println("ALLERTA 5 SEC");
						}

					}
					
					/*Pre-Allerta 3 sec*/
					if(statoAllarme(acc_X,acc_Y,acc_Z)==3 ) 
					{
						if(valutaTempo(sensor,3,System.currentTimeMillis())) 
						{
							mainP.cambiaStato(sensor.getId(), 2);
							sensor.setStato(2);
							sogliaAllerta.put(sensor.getIdentifier(), sensor.getIdentifier());
//							serialPort.writeString("X");
							
							System.out.println("ALLERTA 3 SEC");
						}

					}
					/*Pre-Allerta 2 sec*/
					if(statoAllarme(acc_X,acc_Y,acc_Z)==4) 
					{
						if(valutaTempo(sensor,4,System.currentTimeMillis())) 
						{
							mainP.cambiaStato(sensor.getId(), 2);
							sensor.setStato(2);
							sogliaAllerta.put(sensor.getIdentifier(), sensor.getIdentifier());
//							serialPort.writeString("X");
							
							System.out.println("ALLERTA 2 SEC");
						}

					}
					
					/*Due o più sonde in Allerta --> Allarme*/
					
					if(sogliaAllerta.size()>=2 )
					{
						System.out.println("ALLARME 2 o PIU' SONDE IN ALLERTA");
						
						 Iterator it = sogliaAllerta.entrySet().iterator();
						 while(it.hasNext()){
						       Map.Entry me = (Map.Entry)it.next();
						       System.out.println("\t SONDA: "+me.getKey());
						 }
						 
					
						for (SensorDTO sens : listaSensori) {
							if(sens.getType().equals("R")) 
							{
								mainP.cambiaStato(sens.getId(), 1);
								sens.setStato(1);
							}
						} 
			
						
						sogliaAllerta= new HashMap<String,String>();
					//	serialPort.writeString("Y");
					}
				}
				
				/*SONDE APPARTENENTI AL GRUPPO B */
				if(sensor.getType().equals("D"))
				{
					
				}
			
			}
		}
		
	}
	
	private boolean valutaTempo(SensorDTO sensor, int tipoAllerta,long millis) {
		
		double tempoTrascorso=0;
		
		if(tipoAllerta==2) 
		{
			
			if (sensor.getTempo_periodo_5_sec()==0) 
			{
				sensor.setTempo_periodo_5_sec(millis);
				tempoTrascorso=0;
			}
			else 
			{
				tempoTrascorso=millis-sensor.getTempo_periodo_5_sec();
			}
			
			System.out.println("Tempo Trascorso (5 sec) "+sensor.getIdentifier()+" "+tempoTrascorso);
			if(tempoTrascorso>Costanti.TEMPO_ALLERTA_1) 
			{
				return true;
			}
			
			
		}
		
		if(tipoAllerta==3) 
		{
			
			if (sensor.getTempo_periodo_3_sec()==0) 
			{
				sensor.setTempo_periodo_3_sec(millis);
				tempoTrascorso=0;
			}
			else 
			{
				tempoTrascorso=millis-sensor.getTempo_periodo_3_sec();
			}
			
			System.out.println("Tempo Trascorso (3 sec)"+sensor.getIdentifier()+" "+tempoTrascorso);
			
			if(tempoTrascorso>Costanti.TEMPO_ALLERTA_2) 
			{
				return true;
			}
			
			
		}
		
		if(tipoAllerta==4) 
		{
			
			if (sensor.getTempo_periodo_2_sec()==0) 
			{
				sensor.setTempo_periodo_2_sec(millis);
				tempoTrascorso=0;
			}
			else 
			{
				tempoTrascorso=millis-sensor.getTempo_periodo_2_sec();
			}
			
			System.out.println("Tempo Trascorso (2 sec)"+sensor.getIdentifier()+" "+tempoTrascorso);
			
			if(tempoTrascorso>Costanti.TEMPO_ALLERTA_3) 
			{
				return true;
			}
			
			
		}
		
		return false;
	}

	private int statoAllarme(double acc_X, double acc_Y, double acc_Z) {
		
		/*
		 * STATO 1 =ALLARME
		 * STATO 2 =DENTRO 1° LIMITE (5 sec)
		 * STATO 3 =DENTRO 2° LIMITE (3 sec)
		 * STATO 4 =DENTRO 3° LIMITE (2 sec)
		 */
		
		if(acc_X>= Costanti.LIMITE_MAX_P3 || acc_Y>= Costanti.LIMITE_MAX_P3 || acc_Z>= Costanti.LIMITE_MAX_P3)
		{
			return 1;
		}
		
		if((acc_X>= Costanti.LIMITE_MIN_P1 && (acc_X<Costanti.LIMITE_MAX_P1)) || 
		   (acc_Y>= Costanti.LIMITE_MIN_P1 && (acc_Y<Costanti.LIMITE_MAX_P1)) ||
		   (acc_Z>= Costanti.LIMITE_MIN_P1 && (acc_Z<Costanti.LIMITE_MAX_P1)))
		{
			return 2;
		}
		
		if((acc_X>= Costanti.LIMITE_MIN_P2 && (acc_X<Costanti.LIMITE_MAX_P2)) || 
		   (acc_Y>= Costanti.LIMITE_MIN_P2 && (acc_Y<Costanti.LIMITE_MAX_P2)) ||
		   (acc_Z>= Costanti.LIMITE_MIN_P2 && (acc_Z<Costanti.LIMITE_MAX_P2)))
		{
			return 3;
		}
		
		if((acc_X>= Costanti.LIMITE_MIN_P3 && (acc_X<Costanti.LIMITE_MAX_P3)) || 
		   (acc_Y>= Costanti.LIMITE_MIN_P3 && (acc_Y<Costanti.LIMITE_MAX_P3)) ||
		   (acc_Z>= Costanti.LIMITE_MIN_P3 && (acc_Z<Costanti.LIMITE_MAX_P3)))
		{
			return 4;
		}
		
		return 0;
	}

	public  String getValue() {
		synchronized (this){

			return msg;
		}
	}
	
	public  void write(String string) throws SerialPortException 
	{
		serialPort.writeString(string);
	}
	
}

