package it.sti.landsidemonitor.bo;

import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortException;

public class Serial {

static SerialPort serialPort=null;

public static SerialPort getConnection (String port ,int frameRate) {
   
         serialPort = new SerialPort(port);
        try {
            serialPort.openPort();

            serialPort.setParams(frameRate,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);

 

        } catch (jssc.SerialPortException e) 
        {
            System.out.println("There are an error on writing string to port: " + e);
        }
		return serialPort;
    }

	public static ArrayList<String> read() throws SerialPortException
	{
		ArrayList<String> lista = new ArrayList<>();
		
		 byte[] buffer = serialPort.readBytes(1024);
	     String s=new String(buffer);
	     String[] parts= s.split("\\r\\n");
		 for (int i = 0; i < parts.length; i++) 
		 {
			 lista.add(parts[i]);		
		 }
		
		return lista;
	}
	
	public static void write(String string) throws SerialPortException 
	{
		serialPort.writeString(string);
	}

 }



