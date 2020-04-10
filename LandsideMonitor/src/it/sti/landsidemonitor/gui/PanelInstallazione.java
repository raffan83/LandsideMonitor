package it.sti.landsidemonitor.gui;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.Write;
import it.sti.landsidemonitor.dto.SensorDTO;


public class PanelInstallazione extends JPanel {
	
	RasterPanel mainPanel;
	PortReader pr;
	ArrayList<SensorDTO> listaSensori;
	Scheduler scheduler;

	boolean exit;
	
	
	public PanelInstallazione(ArrayList<SensorDTO> _listaSensori) 
	{
		super(true); //crea un JPanel con doubleBuffered true
		listaSensori=_listaSensori;
		
		JButton b = new JButton("Test") ;
		add(b);
		
		try {
			startScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	    public void startScheduler() throws SchedulerException {
    	
    	JobDetail job = JobBuilder.newJob(Write.class).withIdentity("write", "group1").build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("write", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                .build();

        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }



	  public void paintComponent(Graphics g){
		    
		  super.paintComponent(g);

		  for (int i=0;i< listaSensori.size();i++) {
			    try {
					int y_pos=(i+1)*20;
			    	g.drawString("SENSOR "+listaSensori.get(i).getIdentifier()+" Bering: "+listaSensori.get(i).getBering(),10,y_pos);
			    	
			    	g.drawString(" Pitch: "+listaSensori.get(i).getPitch(),150,y_pos);
			    	
			    	g.drawString(" Roll : "+listaSensori.get(i).getRoll(),210,y_pos);
			    	
			    	g.drawString(" BattLev : "+listaSensori.get(i).getBattLevel(),310,y_pos);
			    	
			    	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	}
		    repaint();
		  }

	public void stopScheduler() throws SchedulerException {
	
		scheduler.shutdown();
		
	}
		
}
