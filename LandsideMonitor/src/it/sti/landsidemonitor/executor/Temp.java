package it.sti.landsidemonitor.executor;

import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.Serial;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class Temp implements SerialPortEventListener {

	jssc.SerialPort serialPort=null;
	String msg="";
	public Temp(SerialPort serialPort) 
	{
		try 
		{
			this.serialPort=serialPort;
			byte[] b= {0x48,0x0A};
			serialPort.writeBytes(b);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void serialEvent(SerialPortEvent arg0) {
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
							
							
							System.out.println("Lettura: "+ msg);
							//valutaSegnale(msg);
							
						}
				}
			}catch (Exception ex) {
				System.out.println(ex);
			}                           
		}

	}	
	}


