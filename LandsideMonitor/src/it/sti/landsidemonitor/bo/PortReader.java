package it.sti.landsidemonitor.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import it.sti.landsidemonitor.dto.SensorDTO;
import it.sti.landsidemonitor.gui.FrameInstallazione;
import it.sti.landsidemonitor.gui.InitSplash;
import it.sti.landsidemonitor.gui.RasterPanel;
import it.sti.landsidemonitor.scheduler.JobSchedulerAtTime;
import it.sti.landsidemonitor.scheduler.JobSchedulerAtTimeRead;
import it.sti.landsidemonitor.scheduler.JobService;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class PortReader implements SerialPortEventListener {

	static SerialPort serialPort;
	String canale;
	public static  String msg;
	public static  ArrayList<SensorDTO> listaSensori;
	private static RasterPanel mainP;
	public static HashMap<String, SensorDTO> sogliaAllerta;
	public static HashMap<SensorDTO, Long> puntiAttiviB;
	public static String  prevState;

	SimpleDateFormat sdf = new SimpleDateFormat("ss.SSS");
	
	public static Scheduler scheduler;
	
	final static Logger logger = Logger.getLogger(PortReader.class);
	
	public PortReader(RasterPanel _mainP,SerialPort serialPort, ArrayList<SensorDTO> _listaSensori) throws Exception 
	{
		this.serialPort = serialPort;
		listaSensori=_listaSensori;
		msg = "";
		sogliaAllerta=new HashMap<String,SensorDTO>();
		puntiAttiviB=new HashMap<SensorDTO,Long>();
		mainP=_mainP;
	
		for (int i=0;i<_listaSensori.size();i++)
		{
			int pr=(100/_listaSensori.size())*(i+1);
			InitSplash.setMessage("Calibrazione sonda "+_listaSensori.get(i).getIdentifier(), pr);
			checkHealthSensor(_listaSensori.get(i));
		}
		InitSplash.setMessage("Avvio monitoraggio", 100);
		InitSplash.close();
	//	startSchedulers();
		
	}

	public static  void startSchedulers() throws SchedulerException {
		
		JobDetail job = JobBuilder.newJob(JobSchedulerAtTime.class).withIdentity("atTime", "group1").build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("atTime", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();

        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
        logger.warn("Start scheduler write state");
        
        JobDetail job1 = JobBuilder.newJob(JobSchedulerAtTimeRead.class).withIdentity("atTimeRead", "group2").build();

        Trigger trigger1 = TriggerBuilder
                .newTrigger()
                .withIdentity("atTimeRead", "group2")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();

        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job1, trigger1);
        logger.warn("Start scheduler read state");
        

        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
       
        JobDetail job2 = JobBuilder.newJob(JobService.class).build();
        Trigger trigger2 = TriggerBuilder.newTrigger()
                                        .startNow()
                                        .withSchedule(
                                             CronScheduleBuilder.cronSchedule("0 59 11 1/1 * ? *"))
                                        .build();
        scheduler.scheduleJob(job2, trigger2);       
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        logger.warn("Start scheduler service");
        
	}

	public static void checkHealthSensor(SensorDTO sensorDTO) throws SerialPortException {
		
		logger.warn("Check sensor: "+sensorDTO.getIdentifier());
		write("C"+sensorDTO.getIdentifier());
		
		String playload="";
		boolean read=false;
		byte[] by= new byte[1];
		ArrayList<String> listaMessaggi= new ArrayList<>();
		
		double tempoStart=System.currentTimeMillis();

		while(true){
			try {             
				by = serialPort.readBytes(1);
				
				
				double tempoTrascorso=System.currentTimeMillis()-tempoStart;
						
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
							listaMessaggi.add(msg);
						}
				}
				
				if(tempoTrascorso>1250) 
				{
					boolean alive=false;
					for (String msg : listaMessaggi) 
					{
						if(msg.startsWith("<CL-"+sensorDTO.getIdentifier()) && sensorDTO.getStato()!=1)
						{

							logger.warn("CALIBRATION ["+sensorDTO.getIdentifier()+"]");
							alive=true;
							
							String levBatt=msg.split(",")[1];
							
							String bering=msg.split(",")[2];
							
							String pitch=msg.split(",")[3];
							
							String roll=msg.split(",")[4].substring(0,msg.split(",")[4].length()-1);				
							
							
							sensorDTO.setBattLevel(levBatt);
							
							sensorDTO.setBering(bering);
							
							sensorDTO.setPitch(pitch);
							
							sensorDTO.setRoll(roll);
							
							if(sensorDTO.getStato()!=1 || sensorDTO.getStato()!=2) 
							{
								mainP.cambiaStato(sensorDTO.getId(), 0);
								sensorDTO.setStato(0);
							}
						}
						if(msg.startsWith("<RSSI"+sensorDTO.getIdentifier()))
						{
							
							sensorDTO.setSignal(msg.split(":")[1].substring(0,msg.split(":")[1].length()-1));
						}
						
					}
					if(alive==false) 
					{
						mainP.cambiaStato(sensorDTO.getId(), 5);
						sensorDTO.setStato(5);
					}
					break;
				}
				
			}catch (Exception ex) {
				logger.error("Errore calibrazione", ex);
			}                           
		}
	}

	public static String getMessage() 
	{
		return msg;
	}
	
	@Override
	public void serialEvent(SerialPortEvent event) { 
		System.out.println("start serial event");
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
							
						//	System.out.println("Lettura: "+ msg);
							valutaSegnale(msg);
							
						}
				}
			}catch (Exception ex) {
				logger.error(ex);
			}                           
		}

	}
	
	private void valutaSegnale(String value) throws Exception {
		
		for (SensorDTO sensor : listaSensori) {
			
			
			if(value.startsWith("<CL-"+sensor.getIdentifier()))
			{
				
				String levBatt=value.split(",")[1];
				
				String bering=value.split(",")[2];
				
				String pitch=value.split(",")[3];
				
				String roll=value.split(",")[4].substring(0,value.split(",")[4].length()-1);				
				
				
				logger.warn("CALIBRATION  "+sensor.getIdentifier()+" ["+levBatt+"] ["+bering+"]["+pitch+"]["+roll+"]");
				
				sensor.setBattLevel(levBatt);
				
				sensor.setBering(bering);
				
				sensor.setPitch(pitch);
				
				sensor.setRoll(roll);
				
				
				
			}
			
			if(value.startsWith("<RSSI"+sensor.getIdentifier()))
			{
				logger.warn("SIGNAL  "+sensor.getIdentifier()+" "+value.split(":")[1].substring(0,value.split(":")[1].length()-1));
				
				sensor.setSignal(value.split(":")[1].substring(0,value.split(":")[1].length()-1));
			}
			
			if(value.startsWith("<OK"+sensor.getIdentifier()))
			{
				logger.warn("SIGNAL POWER "+value);
				FrameInstallazione.confirmPower(value);
			}
			
			if(value.startsWith("<HT-"+sensor.getIdentifier()) && sensor.getStato()!=1)
			{
			
				logger.warn("HEALTH ["+sensor.getIdentifier()+"] "+value);
				sensor.setTempo_periodo_5_sec(0);
				sensor.setTempo_periodo_3_sec(0);
				sensor.setTempo_periodo_2_sec(0);
		
			}
			
			
			if((value.startsWith("<S-A"+sensor.getIdentifier())||value.startsWith("<S-B"+sensor.getIdentifier())||value.startsWith("<"+sensor.getIdentifier()))&& !value.startsWith("<:") && !value.startsWith("<CL") && !value.startsWith("<HT"))
			{
				String[] data=value.split(",");
				
				double acc_X=0.0;
				double acc_Y=0.0;
				double acc_Z=0.0;


				
				if(value.split(",")[2].length()>0) 
				{
					acc_X=Math.abs(Double.parseDouble(data[2]));
				}

				if(value.split(",")[3].length()>0)
				{
					acc_Y=Math.abs(Double.parseDouble(data[3]));
				}

				if(value.split(",")[4].length()>0) 
				{ 
					acc_Z=Math.abs(Double.parseDouble(data[4].substring(0,data[4].length()-1)));
				}
						
				/*Dati test accelerazione */
			//	acc_X=1.25;
			//	acc_Y=1.25;
			//	acc_Z=1.25;
				
				/*SONDE APPARTENENTE AL GRUPPO A*/
				if(sensor.getType().equals("A") && sensor.getStato()!=1 )
				{

					if(sensor.getStato()!=1 && sensor.getStato()!=2) 
					{
						sensor.setStato(3);
						mainP.cambiaStato(sensor.getId(), 3);
						
					}
					
	//				System.out.println("Movimento sonda: "+sensor.getIdentifier()+" Evento:["+data[0]+" - "+data[1]+ "] [X]:"+acc_X+" [Y]:"+acc_Y+" [Z]:"+acc_Z + " Time: "+sdf.format(new Date()));
				
					/*ALLERTA*/
					if(statoAllarme(acc_X,acc_Y,acc_Z)==1) 
					{
						Core.registraEvento(sensor.getIdentifier(),"001",2,acc_X,acc_Y,acc_Z);
						logger.warn("ALLERTA ISTANTANEA > "+Costanti.LIMITE_MAX_P3+" m/s SONDA: "+ sensor.getIdentifier());
						sogliaAllerta.put(sensor.getIdentifier(), sensor);
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
							sogliaAllerta.put(sensor.getIdentifier(),sensor);
							//serialPort.writeString("X");							
							Core.registraEvento(sensor.getIdentifier(),"002",2,acc_X,acc_Y,acc_Z);
							logger.warn("ALLERTA 5 SEC ["+sensor.getIdentifier()+"]");
							
							
						}

					}
					
					/*Pre-Allerta 3 sec*/
					if(statoAllarme(acc_X,acc_Y,acc_Z)==3 ) 
					{
						if(valutaTempo(sensor,3,System.currentTimeMillis())) 
						{
							mainP.cambiaStato(sensor.getId(), 2);
							sensor.setStato(2);
							sogliaAllerta.put(sensor.getIdentifier(), sensor);
//							serialPort.writeString("X");
							
							Core.registraEvento(sensor.getIdentifier(),"003",2,acc_X,acc_Y,acc_Z);
							logger.warn("ALLERTA 3 SEC ["+sensor.getIdentifier()+"]");
						}

					}
					/*Pre-Allerta 2 sec*/
					if(statoAllarme(acc_X,acc_Y,acc_Z)==4) 
					{
						if(valutaTempo(sensor,4,System.currentTimeMillis())) 
						{
							mainP.cambiaStato(sensor.getId(), 2);
							sensor.setStato(2);
							sogliaAllerta.put(sensor.getIdentifier(), sensor);
//							serialPort.writeString("X");
							
							Core.registraEvento(sensor.getIdentifier(),"004",2,acc_X,acc_Y,acc_Z);
							logger.warn("ALLERTA 2 SEC ["+sensor.getIdentifier()+"]");
						}

					}
					
					/*Due o più sonde in Allerta --> Allarme*/
					
					if(sogliaAllerta.size()>=2 )
					{
						logger.warn("ALLARME 2 o PIU' SONDE IN ALLERTA");
						
						Iterator it = sogliaAllerta.entrySet().iterator();
						 while(it.hasNext()){
						       Map.Entry me = (Map.Entry)it.next();
						      
						      
						       logger.warn("\t SONDA: "+me.getKey());
						      
						       SensorDTO s=(SensorDTO)me.getValue();
						       mainP.cambiaStato(s.getId(), 1);
							   s.setStato(1);
							   Core.registraEvento(s.getIdentifier(),"005",1,acc_X,acc_Y,acc_Z);
						 }

						sogliaAllerta= new HashMap<String,SensorDTO>();
					//	serialPort.writeString("Y");
					}
				}
				
				/*SONDE APPARTENENTE AL GRUPPO B*/
				if(sensor.getType().equals("B") && sensor.getStato()!=1 )
				{

					if(sensor.getStato()!=1 && sensor.getStato()!=2) 
					{
						sensor.setStato(3);
						mainP.cambiaStato(sensor.getId(), 3);
						
					}
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
			
			//System.out.println("Tempo Trascorso (5 sec) "+sensor.getIdentifier()+" "+tempoTrascorso);
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
			
		//	System.out.println("Tempo Trascorso (3 sec)"+sensor.getIdentifier()+" "+tempoTrascorso);
			
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
			
	//		System.out.println("Tempo Trascorso (2 sec)"+sensor.getIdentifier()+" "+tempoTrascorso);
			
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
	public static void cambiaStato(SensorDTO s, int idStato) 
	{
		  mainP.cambiaStato(s.getId(), idStato);
		  s.setStato(idStato);
	}
	
	public static  void write(String string) throws SerialPortException 
	{
		byte[] b = new byte[string.getBytes().length+1];
		
		byte[] original= string.getBytes();
		
		for (int i = 0; i < original.length; i++) 
		{
			b[i]=original[i];
		}
		b[b.length-1]= 0x0A;
		
		serialPort.writeBytes(b);
		
	}

	public static ArrayList<SensorDTO> getListaSonde() {
		
		return listaSensori;
	}
	
}

