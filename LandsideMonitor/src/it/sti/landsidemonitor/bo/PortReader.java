package it.sti.landsidemonitor.bo;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class PortReader implements SerialPortEventListener {

	SerialPort serialPort;
	String canale;
	private  String msg;


	public PortReader(SerialPort serialPort) {
		this.serialPort = serialPort;
		msg = "";

	}

	@Override
	public void serialEvent(SerialPortEvent event) { 
		String str="";
		byte[] by= new byte[1];                       
		while(true){
			try {             
				by = serialPort.readBytes(1);
				if(by[0]!=10 && by[0]!=13){                         
					
						str +=(char)by[0];//accumola nella stringa 
					                        
				}else{                       
					if(by[0]==13) {                                  
						try{
							msg=str;
							break;
						}catch(NumberFormatException ex){
							//System.out.println(ex);
						}                                            
					}
				}               
			}catch (SerialPortException ex) {
				System.out.println(ex);
			}                           
		}

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

