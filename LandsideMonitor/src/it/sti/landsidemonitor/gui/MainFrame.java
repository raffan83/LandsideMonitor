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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

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
	public static jssc.SerialPort serialPort;
	public static ArrayList<SensorDTO> listaSensori=null;
	JMenuBar menuBar;
	public static PortReader pr=null;
	JMenu menu;
	JMenuItem it1,it2,it3,it4,it5;
	
	final static Logger logger = Logger.getLogger(MainFrame.class);
	
	public MainFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SerialPortException, SQLException 
	{
	g=this;
	
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	int w=(int)dim.getWidth();
	int h=(int)dim.getHeight();
	
	Costanti.SCREEN_X=w-40;
	Costanti.SCREEN_Y=h-70;

	setSize(w,h);

	try 
	 
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	
		listaSensori=Core.getListaSensori();
		
		serialPort =Serial.getConnection(Costanti.PORT, Costanti.FRAMERATE);
		
		costruisciFrame(listaSensori);
		
		pr = new PortReader(mainPanel,serialPort,listaSensori);

		serialPort.addEventListener(pr, SerialPort.MASK_RXCHAR);
		
		PortReader.startSchedulers();
		
	} catch (jssc.SerialPortException e) {
		
		JOptionPane.showMessageDialog(null, "Porta "+Costanti.PORT+ " non trovata, configurare correttamente nome porta dal pannello Impostazioni","Porta non trovata",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(MainFrame.class.getResource("/image/error.png")));
		InitSplash.close();
		costruisciFrame( listaSensori);
		
	 }
	 catch (Exception e) 
	{
		 logger.error("Errore Main", e);
		 costruisciFrame( listaSensori);
		 InitSplash.close();
	}
	
	}

	private void costruisciFrame(ArrayList<SensorDTO> listaSensori) {
		

		try 
		{
		 JMenuBar menuBar = new JMenuBar();
		    it1= new JMenuItem("Parametri");
		  	it2= new JMenuItem("Sonde");
		  	it3= new JMenuItem("Console");		  	
		  	it4= new JMenuItem("Controllo Sonde");
		  	it5= new JMenuItem("Allarmi");
		  	
		  
		  	
		  	menu = new JMenu("Impostazioni");
		  	menuBar.add(menu);
			
		  	menu.add(it1);
		  	menu.add(it2);
		  	menu.add(it3);
		  	menu.add(it4);
		  	menu.add(it5);
		  	
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
					JFrame f=new Password(g,1,mainPanel,pr,listaSensori);	
					f.setDefaultCloseOperation(1);
	      	        f.setVisible(true);
	      	      Password.setFocus();
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
					JFrame f=new Password(g,2,mainPanel,pr,listaSensori);
	            	f.setDefaultCloseOperation(1);
	      	        f.setVisible(true);
	      	      Password.setFocus();
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
			
			it4.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				
					try 
					{
						JFrame f=new Password(g,3,mainPanel,pr,PortReader.getListaSonde());
		            	f.setDefaultCloseOperation(1);
		      	        f.setVisible(true);
		      	        Password.setFocus();
				//	JFrame f=new FrameInstallazione(listaSensori);
	           // 	f.setDefaultCloseOperation(1);
	      	   //     f.setVisible(true);
	      	        
					}catch (Exception ex2) {
						ex2.printStackTrace();
					}
				}
			});
	
			it5.addActionListener(new ActionListener() {
				
				
				@Override
				public void actionPerformed(ActionEvent e) {
				
					try 
					{
					JFrame f=new PasswordAllarme(g);
	            	f.setDefaultCloseOperation(1);
	      	        f.setVisible(true);
					}catch (Exception ex2) {
						ex2.printStackTrace();
					}
				}
			});
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
