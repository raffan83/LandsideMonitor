package it.sti.landsidemonitor.executor;





import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.gui.InitSplash;
import it.sti.landsidemonitor.gui.MainFrame;


public class Executor {
	
	final static Logger logger = Logger.getLogger(Executor.class);

	public static void main(String[] args) {
		try 
		{

	     
	        /*Inizzializzazione Parametri*/
			
			
	        
	        SwingUtilities.invokeLater(new Runnable(){
	            public void run() 
	            {
	            	try
	            	{
	            	
	            	Core.inizialize();
	            	
	            	final InitSplash fr= new InitSplash();
	           	        
	           	    fr.setVisible(true);
	           	        
	           	    InitSplash.setMessage("Inizializzazione applicazione...", 0);
	           	    Thread.sleep(50);
	           	        
	            	MainFrame g1 = new MainFrame();
	            	g1.setResizable(false);
	            	g1.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
	            	g1.setDefaultCloseOperation(3);
	      	        g1.setVisible(true);

	      	        Thread t = new Thread(new Runnable() {
						
						@Override
						public void run() {
						
							 try {
							//	SendEmailBO.sendEmailAlarm("raffan83@gmail.com");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
	      	   //    t.start();
	      	        
	      	        }
	            	catch (SQLException exe) 
	            	{
	            		JOptionPane.showMessageDialog(null, "Errore accesso db ","Errore DB",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(MainFrame.class.getResource("/image/error.png")));
	            		logger.error(exe);
					}
	            	catch(Exception ex)
	      	        {
					//	GeneralGUI.printException(ex);
	            		logger.error(ex);
	      	        }
	            }

	        });
	        
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}


