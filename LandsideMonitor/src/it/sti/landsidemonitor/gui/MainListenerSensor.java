package it.sti.landsidemonitor.gui;

import it.sti.landsidemonitor.dto.SensorDTO;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class MainListenerSensor implements SerialPortEventListener {
	
	SerialPort serialPort=null;
	SensorDTO sensor=null;
	String msg;
	
	public MainListenerSensor(RasterPanel mainPanel, SerialPort _serialPort, SensorDTO _sensor) 
	{
		serialPort=_serialPort;
		sensor=_sensor;
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {
		
		String str="";
		byte[] by= new byte[1];                       
		while(true){
			try {             
				by = serialPort.readBytes(1);
				if(by[0]!=10 && by[0]!=13)
				{                         
						str +=(char)by[0];//accumola nella stringa                       
				}else{                       
					if(by[0]==13) {                                  
						try{
							msg=str;
							System.out.println(msg);

							String pivot=msg.split(",")[0];/*Recupero l'ID del sensore dalla stringa di lettura*/
							
							if(pivot.equals(sensor.getIdentifier())) 
							{
								
							
						//	mainP.cambiaStato(sensor.getId(), sensor.getStatoOriginale());	
							double acc_X=0.0;
							double acc_Y=0.0;
							double acc_Z=0.0;
							
							
							
							if(msg.split(",")[1].length()>0) 
							{
								 acc_X=Double.parseDouble(msg.split(",")[1]);
							}
							
							if(msg.split(",")[2].length()>0)
							{
								 acc_Y=Double.parseDouble(msg.split(",")[2]);
							}
							
							if(msg.split(",")[3].length()>0) 
							{ 
								 acc_Z=Double.parseDouble(msg.split(",")[3]);
							}
							
							System.out.println(sensor.getIdentifier()+" POS X"+acc_X+" POS Y"+acc_Y+" POS Z"+acc_Z);
						}
						}
							catch(NumberFormatException ex){
							//System.out.println(ex);
						}                                            
					}
				}               
			}catch (SerialPortException ex) {
				System.out.println(ex);
			}                           
		}
		
	}

}
