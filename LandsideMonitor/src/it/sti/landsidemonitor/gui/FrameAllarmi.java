package it.sti.landsidemonitor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dto.ParamDTO;
import it.sti.landsidemonitor.dto.SensorDTO;
import jssc.SerialPortException;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.JCheckBox;

public class FrameAllarmi extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public FrameAllarmi() throws SQLException 
	{
		getContentPane().setBackground(Color.WHITE);
		setTitle("Gestione Allarmi");
		setSize(650, 600);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - 650) / 2;
		int y = (dim.height - 600) / 2;
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		
		JPanel panelAllarmi = new JPanel();
	
		
		tabbedPane.addTab("Allarmi",panelAllarmi);
		
		panelAllarmi.setLayout(null);
		
		JButton allarmeRosso= new JButton("ALLARME");
		allarmeRosso.setFont(new Font("Arial", Font.BOLD, 14));
		allarmeRosso.setBounds(10,10,100,35);
		
		allarmeRosso.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			try {
				PortReader.write("Y");
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
			}
		});

		JButton allarmeGiallo= new JButton("ALLERTA");
		allarmeGiallo.setFont(new Font("Arial", Font.BOLD, 14));
		allarmeGiallo.setBounds(10,55,100,35);
		
		allarmeGiallo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			try {
				PortReader.write("X");
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
			}
		});
		
		JButton stop= new JButton("STOP");
		stop.setFont(new Font("Arial", Font.BOLD, 14));
		stop.setBounds(10,100,100,35);
		
		stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			try {
				PortReader.write("Z");
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
			}
		});
		panelAllarmi.add(allarmeRosso);
		panelAllarmi.add(allarmeGiallo);
		panelAllarmi.add(stop);
		
		setLocation(x, y);
	
		getContentPane().setLayout(new MigLayout("", "[634px,grow]", "[561px,grow][15px:n][60px:n]"));
		
		getContentPane().add(tabbedPane, "cell 0 0,grow");
		

	}

}
