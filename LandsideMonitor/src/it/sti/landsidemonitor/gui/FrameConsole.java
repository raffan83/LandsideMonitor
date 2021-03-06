package it.sti.landsidemonitor.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.PortReader;
import net.miginfocom.swing.MigLayout;

public class FrameConsole extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean stop=false;
	private static JTextArea area;
	public FrameConsole(PortReader pr) {
		
		
		setTitle("Console");
		setSize(400,200);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JButton btnStop = new JButton("STOP");
		getContentPane().add(btnStop, "flowx,cell 0 0");
		
		JButton btnStart = new JButton("START");
		getContentPane().add(btnStart, "cell 0 0");
		
		area = new JTextArea();
		area.setBackground(Color.BLACK);
		area.setForeground(Color.WHITE);
		
		JScrollPane scrollPane = new JScrollPane(area);
		getContentPane().add(scrollPane, "cell 0 1,grow");
		
		
		Thread thread = new Thread(){
			@Override public void run() {

				String actualValue="";
				while(!stop) {
		
					try {
					Thread.sleep(200);
					if(!actualValue.equals(PortReader.getMessage()))
					{
						actualValue=PortReader.getMessage();
						area.append(PortReader.getMessage()+"\n");
						area.setCaretPosition(area.getText().length() - 1);
					}
					
					} catch(Exception e) {}
				}
		
			}
		};
		thread.start();
		
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				stop=true;
			}
		});
		
	btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
				JFrame f=new FrameConsole(pr);
            	f.setDefaultCloseOperation(1);
      	        f.setVisible(true);
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
			    
				stop=true;
			    }
		});
		
	}

	public static void printConsole(String value) 
	{
		if (area!=null) 
		{
			area.append(value+"\n");
			area.setCaretPosition(area.getText().length() - 1);
		}
	}
}
