package it.sti.landsidemonitor.gui;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JProgressBar;

import javafx.scene.control.ProgressBar;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.border.LineBorder;


public class InitSplash extends JFrame{
	static JProgressBar pb;
	static JLabel lblPb;
	static JLabel lblMessPb;
	public static JFrame g;
	private JLabel lblNewLabel;
	
	public InitSplash() {
		setUndecorated(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(InitSplash.class.getResource("/image/covic.png")));
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		g=this;
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.RED, 2, true));
		panel.setBackground(Color.WHITE);
		setSize(480, 280);
		panel.setSize(480,280);
		panel.setLayout(new MigLayout("", "[grow]", "[grow][30px][]"));
	
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - panel.getWidth()) / 2;
		int y = (dim.height - panel.getHeight()) / 2;
		setLocation(x, y);
		
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		 
		 lblNewLabel = new JLabel("");
		 lblNewLabel.setIcon(new ImageIcon(InitSplash.class.getResource("/image/covic.png")));
		 panel.add(lblNewLabel, "cell 0 0");
		
		
		
		pb = new JProgressBar();
		panel.add(pb, "flowx,cell 0 1,grow");
		
		lblPb = new JLabel("0%");
		lblPb.setFont(new Font("Arial", Font.BOLD, 12));
		panel.add(lblPb, "cell 0 1,width :30:");
		
		lblMessPb = new JLabel("-");
		lblMessPb.setFont(new Font("Arial", Font.PLAIN, 12));
		panel.add(lblMessPb, "flowx,cell 0 2,growx");
		
		getContentPane().add("cell 0 0 ,grow", panel);
	}
	
	public static void setMessage(final String mess, final int progress)
	{
	
		
		Runnable updateGUI = new Runnable() {  
			public void run() {  
				pb.setValue(progress);
				lblPb.setText(progress+"%");
				lblMessPb.setText(mess);
				g.update(g.getGraphics());
			}  
		};  
		try{
				Thread t = new Thread(updateGUI);  
				t.start();
				
				Thread.sleep(10);
		}catch 
		(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void close() {
		
		g.dispose();
	}
}
