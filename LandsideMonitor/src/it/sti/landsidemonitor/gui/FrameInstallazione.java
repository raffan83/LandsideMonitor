package it.sti.landsidemonitor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import jssc.SerialPortException;

public class FrameInstallazione extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static PanelInstallazione mainPanel;
	private double startH=0;
	private Scheduler scheduler;
	JobDetail job;
	
	JTable tabellaSonde;
	PortReader pr;
	JFrame frm;

	public FrameInstallazione(ArrayList<SensorDTO> listaSensori) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException 
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - 1000) / 2;
		int y = (dim.height - 600) / 2;
		setLocation(x, y);
		setTitle("Controllo Sonde");

		
		
		frm=this;
		//setResizable(false);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//setSize(width,height+20);

		setSize(1000, 600);

		startH=System.currentTimeMillis();

		mainPanel=new PanelInstallazione(listaSensori);
		int altezzaPannello=(50*listaSensori.size())+90;

		mainPanel.setPreferredSize(new Dimension(950,altezzaPannello));

		JScrollPane scrollTop= new JScrollPane(mainPanel);
		getContentPane().add(scrollTop);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {

				try {
					scheduler.deleteJob(job.getKey());
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
		

		public PanelInstallazione(ArrayList<SensorDTO> _listaSensori) 
		{
			super(true);

			try {

				listaSensori=_listaSensori;
				costruiscipannello();
				setBackground(Color.WHITE);

				startScheduler();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		private void costruiscipannello() {

			setLayout(null);

			JLabel lab= new JLabel("Impostazione livello potenza sonda");

			lab.setFont(new Font("Arial",Font.BOLD,15));
			lab.setBounds(10, 10, 280, 30);
		//	add(lab);

			JLabel sonda = new JLabel("Sonda");
			sonda.setFont(new Font("Arial",Font.BOLD,14));
			sonda.setBounds(20, 50, 75, 30);
		//	add(sonda);

			JComboBox<String> comboSonde = new JComboBox<String>();

			for (SensorDTO sensorDTO : listaSensori) {

				comboSonde.addItem(sensorDTO.getIdentifier());
			}

			comboSonde.setBounds(80, 50, 40, 25);
			comboSonde.setFont(new Font("Arial",Font.BOLD,14));
		//	add(comboSonde);

			JLabel lab_sign= new JLabel("Potenza segnale");

			lab_sign.setFont(new Font("Arial",Font.BOLD,15));
			lab_sign.setBounds(150, 50, 120, 25);
		//	add(lab_sign);

			String[] data = new String[]{"10","11","12","13","14","15","16","17","18","19","20","21","22","23"};

			JComboBox<String> comboSign = new JComboBox<String>(data);

			comboSign.setBounds(280, 50, 50, 25);
			comboSign.setFont(new Font("Arial",Font.BOLD,14));
		//	add(comboSign);

			JButton button = new JButton("Invia");
			button.setIcon(new ImageIcon(FrameParametri.class.getResource("/image/continue.png")));
			button.setFont(new Font("Arial", Font.BOLD, 14));
			button.setBounds(360,44 , 120, 37);
		//	add(button);

			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String sonda =comboSonde.getSelectedItem().toString();
					String sign=comboSign.getSelectedItem().toString();

					try {
						PortReader.write("P"+sonda+sign);
					} catch (SerialPortException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			JButton buttonAgg = new JButton("Aggiorna");
			buttonAgg.setIcon(new ImageIcon(FrameParametri.class.getResource("/image/update.png")));
			buttonAgg.setFont(new Font("Arial", Font.BOLD, 14));
			buttonAgg.setBounds(10,44 , 140, 37);
			add(buttonAgg);

			buttonAgg.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					azzeraValoriSonde();

				}
			});
		}


		public void startScheduler() throws SchedulerException {

			azzeraValoriSonde();

			 job = JobBuilder.newJob(JobCalibration.class).withIdentity("calibration", "group1").build();
			
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("calibration", "group1")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
					.build();

			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.scheduleJob(job, trigger);
			scheduler.start();
		}



		private void azzeraValoriSonde() {

			for (SensorDTO sensor : listaSensori) {

				sensor.setBering("N/D");
				sensor.setPitch("N/D");
				sensor.setRoll("N/D");
				sensor.setBattLevel("N/D");
				sensor.setSignal("N/D");
			}

		}

		public void paintComponent(Graphics g){

			super.paintComponent(g);

			double actualTime=System.currentTimeMillis()-startH;
			try {

			
				if(actualTime>300000) 
				{
					frm.dispose();
					scheduler.deleteJob(job.getKey());
				}

				int start_pos=75;
				for (int i=0;i< listaSensori.size();i++) {

					int y_pos=((i+1)*50)+start_pos;
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
						g.drawString(""+lev_bat, 570, y_pos-20);
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

				} 

			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}

	}

	public static void confirmPower(String value) {

		JOptionPane.showMessageDialog(null,"Potenza sonda "+value.subSequence(3, 4)+" settata correttmente a "+value.subSequence(4, 6)+" db","Conferma variazione potenza",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(FrameInstallazione.class.getResource("/image/confirm.png")));

	}
}
