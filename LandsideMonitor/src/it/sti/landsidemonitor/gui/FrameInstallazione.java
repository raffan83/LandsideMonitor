package it.sti.landsidemonitor.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.quartz.SchedulerException;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.Utility;
import it.sti.landsidemonitor.dto.SensorDTO;
import net.miginfocom.swing.MigLayout;

public class FrameInstallazione extends JFrame {
	
	static PanelInstallazione mainPanel;
	ArrayList<SensorDTO> listaSonde=null;
	JTable tabellaSonde;
	PortReader pr;
	JPanel pannello;
	
	public FrameInstallazione(RasterPanel mainPan,PortReader _pr, ArrayList<SensorDTO> listaSensori) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException 
	{
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	int x = (dim.width - 1000) / 2;
	int y = (dim.height - 600) / 2;
	setLocation(x, y);
	setTitle("Sonde");
	
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
