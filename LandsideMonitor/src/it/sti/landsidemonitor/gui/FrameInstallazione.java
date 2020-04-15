package it.sti.landsidemonitor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dto.SensorDTO;
import it.sti.landsidemonitor.scheduler.JobCalibration;

public class FrameInstallazione extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static PanelInstallazione mainPanel;
	ArrayList<SensorDTO> listaSonde=null;
	JTable tabellaSonde;
	PortReader pr;
	JPanel pannello;
	
	public FrameInstallazione(ArrayList<SensorDTO> listaSensori) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException 
	{
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	int x = (dim.width - 1000) / 2;
	int y = (dim.height - 600) / 2;
	setLocation(x, y);
	setTitle("Controllo Sonde");
	
	//setResizable(false);
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	//setSize(width,height+20);

	setSize(1000, 600);
	
	mainPanel=new PanelInstallazione(listaSensori);
	JScrollPane scrollTop= new JScrollPane(mainPanel);
	getContentPane().add(scrollTop);
	
	addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent we) {
		    
			try {
				mainPanel.stopScheduler();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
	});
	
	}
	
class PanelInstallazione extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		ArrayList<SensorDTO> listaSensori;
		Scheduler scheduler;

		public PanelInstallazione(ArrayList<SensorDTO> _listaSensori) 
		{
			super(true); //crea un JPanel con doubleBuffered true
			listaSensori=_listaSensori;
			setBackground(Color.WHITE);
			try {
				startScheduler();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
		
		    public void startScheduler() throws SchedulerException {
	    	
	    	JobDetail job = JobBuilder.newJob(JobCalibration.class).withIdentity("write", "group1").build();

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
						int y_pos=(i+1)*50;
						g.setFont(new Font("Arial", Font.BOLD, 16)); 
						g.setColor(Color.BLACK);
				    	g.drawString("SENSOR "+listaSensori.get(i).getIdentifier(),10,y_pos);
				    	
				    	g.setFont(new Font("Arial", Font.PLAIN, 15));
				    	g.setColor(Color.BLACK);
				    	g.drawString("Bering: ",130,y_pos);
				    	
				    	g.setFont(new Font("Arial", Font.BOLD, 15));
				    	g.setColor(Color.RED);
				    	g.drawString(listaSensori.get(i).getBering(),180,y_pos);
				    	
				    	g.setFont(new Font("Arial", Font.PLAIN, 15));
				    	g.setColor(Color.BLACK);
				    	g.drawString("Pitch: ",250,y_pos);
				    	
				    	g.setFont(new Font("Arial", Font.BOLD, 15));
				    	g.setColor(Color.RED);
				    	g.drawString(listaSensori.get(i).getPitch(),295,y_pos);
				    	
				    	g.setFont(new Font("Arial", Font.PLAIN, 15));
				    	g.setColor(Color.BLACK);
				    	g.drawString("Roll: ",360,y_pos);
				    	
				    	g.setFont(new Font("Arial", Font.BOLD, 15));
				    	g.setColor(Color.RED);
				    	g.drawString(listaSensori.get(i).getRoll(),395,y_pos);
				    	
				    	g.setColor(Color.BLACK);
				    	int pos_line=y_pos+10;
				    	g.drawLine(10, pos_line, 780, pos_line);
				    	
				    	
				    	g.setFont(new Font("Arial", Font.PLAIN, 15));
				    	g.setColor(Color.BLACK);
				    	g.drawString("Lev. Batt: ",450,y_pos);
				    	
				    	
				    	if(listaSensori.get(i).getBattLevel()!="N/D") 
				    	{
				    		double lev_bat=Double.parseDouble(listaSensori.get(i).getBattLevel());
				    		
				    		String imgLocation ="";
							
				    		if(lev_bat<=3.7) 
				    		{
				    			imgLocation = "/image/bat0.png";
				    		}
				    		if(lev_bat>3.7 && lev_bat<=3.83) 
				    		{
				    			imgLocation = "/image/bat1.png";
				    		}
				    		if(lev_bat>3.83 && lev_bat<=3.96) 
				    		{
				    			imgLocation = "/image/bat2.png";
				    		}
				    		if(lev_bat>3.96 && lev_bat<=4.1) 
				    		{
				    			imgLocation = "/image/bat3.png";
				    		}
				    		if(lev_bat>4.1) 
				    		{
				    			imgLocation = "/image/bat4.png";
				    		}
				    		
				    		URL imageURL = MainFrame.class.getResource(imgLocation);
							BufferedImage img =(ImageIO.read(imageURL));
				    		g.drawImage(img, 520, y_pos-20, 60, 30,this);
				    	}else 
				    	{
				    		g.setFont(new Font("Arial", Font.BOLD, 15));
					    	g.setColor(Color.RED);
					    	g.drawString(listaSensori.get(i).getBattLevel(),520,y_pos);
				    	}
				    	
				    	g.setFont(new Font("Arial", Font.PLAIN, 15));
				    	g.setColor(Color.BLACK);
				    	g.drawString("Signal: ",600,y_pos);
				    	
				    	
				    	if(listaSensori.get(i).getSignal()!="N/D") 
				    	{
				    		String imgLocation ="";
				    		
				    		double lev_sig=Double.parseDouble(listaSensori.get(i).getSignal());

				    		if(lev_sig<=-120) 
				    		{
				    			imgLocation = "/image/sign_0.png";
				    		}
				    		if(lev_sig>-120 && lev_sig<=-100) 
				    		{
				    			imgLocation = "/image/sign_1.png";
				    		}
				    		if(lev_sig>-100 && lev_sig<=-80) 
				    		{
				    			imgLocation = "/image/sign_2.png";
				    		}
				    		if(lev_sig>-80 && lev_sig<=-60) 
				    		{
				    			imgLocation = "/image/sign_3.png";
				    		}
				    		if(lev_sig>-60 && lev_sig<=-40) 
				    		{
				    			imgLocation = "/image/sign_4.png";
				    		}
				    		if(lev_sig>-40 && lev_sig<=-20) 
				    		{
				    			imgLocation = "/image/sign_5.png";
				    		}
				    		if(lev_sig > -20) 
				    		{
				    			imgLocation = "/image/sign_6.png";
				    		}
				    		
							URL imageURL = MainFrame.class.getResource(imgLocation);
							BufferedImage img =(ImageIO.read(imageURL));
				    		g.drawImage(img, 650, y_pos-25, 60, 30,this);
				    		
				    		g.setFont(new Font("Arial", Font.BOLD, 13));
					    	g.setColor(Color.RED);
				    		g.drawString("("+listaSensori.get(i).getSignal()+" db)", 720, y_pos);
				    	}else 
				    	{
				    		g.setFont(new Font("Arial", Font.BOLD, 15));
					    	g.setColor(Color.RED);
					    	g.drawString(listaSensori.get(i).getSignal(),650,y_pos);
				    	}
				  
				    	
				    	
				    	
				    	
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
}
