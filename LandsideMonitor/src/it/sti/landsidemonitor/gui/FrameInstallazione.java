package it.sti.landsidemonitor.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.quartz.SchedulerException;

import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dto.SensorDTO;

public class FrameInstallazione extends JFrame {
	
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
	setTitle("Installazione Sonde");
	
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
	
}
