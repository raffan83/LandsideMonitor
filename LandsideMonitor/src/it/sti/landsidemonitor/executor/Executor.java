package it.sti.landsidemonitor.executor;





import javax.swing.SwingUtilities;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.gui.MainFrame;


public class Executor {

	public static void main(String[] args) {
		try 
		{

	     
	        /*Inizzializzazione Parametri*/
			
			Core.inizialize();
	        
	        SwingUtilities.invokeLater(new Runnable(){
	            public void run() 
	            {
	            	try
	            	{
	            		
	            	MainFrame g1 = new MainFrame();
	            	g1.setResizable(false);
	            	g1.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
	            	g1.setDefaultCloseOperation(3);
	      	        g1.setVisible(true);

	      	        }
	            	catch(Exception ex)
	      	        {
					//	GeneralGUI.printException(ex);
	      	        	ex.printStackTrace();
	      	        }
	            }

	        });
	        
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}


