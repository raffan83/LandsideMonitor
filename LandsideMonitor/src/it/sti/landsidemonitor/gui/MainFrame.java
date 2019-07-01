package it.sti.landsidemonitor.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.Serial;
import it.sti.landsidemonitor.dto.SensorDTO;
import jssc.SerialPort;
import jssc.SerialPortException;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame g;
	public RasterPanel mainPanel=null;
	static ArrayList<SensorDTO> listaSensori=null;
	JMenuBar menuBar;
	static PortReader pr=null;
	JMenu menu;
	JMenuItem it1;
	JMenuItem it2;
	JMenuItem it3;
	
	
	public MainFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SerialPortException, SQLException 
	{
	g=this;
	
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	int w=(int)dim.getWidth();
	int h=(int)dim.getHeight();
	
	Costanti.SCREEN_X=w-40;
	Costanti.SCREEN_Y=h-70;

	setSize(w,h);

	//itMis.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
	  	
	//setUndecorated(true);
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

	listaSensori=Core.getListaSensori();
	
	jssc.SerialPort serialPort =Serial.getConnection(Costanti.PORT, Costanti.FRAMERATE);
	  
	 pr = new PortReader(serialPort);

	 try 
	 {
			serialPort.addEventListener(pr, SerialPort.MASK_RXCHAR);
			
			costruisciFrame(listaSensori);
			
			Thread thread = new Thread(new PointSensor());
			thread.start();
			
	} catch (jssc.SerialPortException e) {
		
		JOptionPane.showMessageDialog(null, "Porta "+Costanti.PORT+ " non trovata, configurare correttamente nome porta dal pannello Impostazioni","Porta non trovata",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(MainFrame.class.getResource("/image/error.png")));
	//	listaSensori=new ArrayList<SensorDTO>();
		costruisciFrame( listaSensori);
		
	}
	 
	
	
//	Core.setRatio();
	 

	
	}

	private void costruisciFrame(ArrayList<SensorDTO> listaSensori) {
		

		try 
		{
		 JMenuBar menuBar = new JMenuBar();
		    it1= new JMenuItem("Parametri");
		  	it2= new JMenuItem("Sonde");
		  	it3= new JMenuItem("Console");
		  	
		  
		  	
		  	menu = new JMenu("Impostazioni");
		  	menuBar.add(menu);
			
		  	menu.add(it1);
		  	menu.add(it2);
		  	menu.add(it3);
		  	
		  	setJMenuBar(menuBar);
		  	
		
			String imgLocation = "/image/landside.jpg";
			URL imageURL = MainFrame.class.getResource(imgLocation);

			mainPanel = new RasterPanel(imageURL, g,listaSensori);

			JScrollPane scrollTop= new JScrollPane(mainPanel);
			this.getContentPane().add(scrollTop);
		
		it1.addActionListener(new ActionListener() {
				
				
				@Override
				public void actionPerformed(ActionEvent e) {
				
					try 
					{
					//JFrame f=new FrameParametri();
	            	
					JFrame f=new Password(g);	
					f.setDefaultCloseOperation(1);
	      	        f.setVisible(true);
					}catch (Exception ex2) {
						ex2.printStackTrace();
					}
				}
			});
		
			
			
			it2.addActionListener(new ActionListener() {
				
				
				@Override
				public void actionPerformed(ActionEvent e) {
				
					try 
					{
					JFrame f=new FrameSonde(mainPanel);
	            	f.setDefaultCloseOperation(1);
	      	        f.setVisible(true);
					}catch (Exception ex2) {
						ex2.printStackTrace();
					}
				}
			});
	
			
			it3.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					JFrame f=new FrameConsole(pr);
	            	f.setDefaultCloseOperation(1);
	      	        f.setVisible(true);
				}
			});
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	
	
	class PointSensor implements Runnable {



		@Override
		public void run() 
		{
			try
			{

						for (SensorDTO sensor : listaSensori) 
						{
							MainSensor d =	 new MainSensor(mainPanel,pr,sensor);
							d.execute();
						}
						
						
					}
				

			
		
			catch(Exception ex)
			{
				
				ex.printStackTrace();
			}
		}
	}
}
