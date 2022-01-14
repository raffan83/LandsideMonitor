package it.sti.landsidemonitor.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.dto.SensorDTO;
import net.miginfocom.swing.MigLayout;

public class Password extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String OK = "ok";

	private static JPasswordField passwordField;

	public Password(JFrame f, int i, RasterPanel mainPanel, PortReader pr, ArrayList<SensorDTO> listaSensori) {
		//Use the default FlowLayout.
	
		
		setTitle("Password");
		setSize(400,110);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - 400) / 2;
		int y = (dim.height - 110) / 2;
		setLocation(x, y);
		
		getContentPane().setLayout(new MigLayout("", "[378px][][][][][]", "[31.00px]"));
		//Create everything.
		passwordField = new JPasswordField(17);
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordField.setActionCommand(OK);

		passwordField.requestFocusInWindow();
	

		getContentPane().add(passwordField, "cell 0 0");
				
						JLabel label = new JLabel("Password: ");
						getContentPane().add(label, "cell 1 0,alignx center,aligny center");
						label.setFont(new Font("Arial", Font.BOLD, 14));
						label.setLabelFor(passwordField);
						
						JButton btnNewButton = new JButton("");
						btnNewButton.setIcon(new ImageIcon(Password.class.getResource("/image/arrow_right.png")));
						getContentPane().add(btnNewButton, "cell 2 0");
						
						btnNewButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								char[] input = passwordField.getPassword();
								if (isPasswordCorrect(input)) 
								{
									SwingUtilities.invokeLater(new Runnable() {
							            public void run() 
							            
							            { 
							            try 
							            {	if(i==1) {
							            	JFrame f=new FrameParametri();
							            	f.setDefaultCloseOperation(1);
							      	        f.setVisible(true);
							      	        }else if(i==2) 
							      	        {
							      	      	JFrame f=new FrameSonde(mainPanel, pr);
							            	f.setDefaultCloseOperation(1);
							      	        f.setVisible(true);
							      	        }else if(i==3) 
							      	        {
							      	        	JFrame f=new FrameInstallazione(listaSensori);
							      	        	f.setDefaultCloseOperation(1);
							     	      	    f.setVisible(true);
							      	        }
							            }catch (Exception e) 
							            {
											e.printStackTrace();
										}    
							            }
							        });
									dispose();
								
								
								} else {
									JOptionPane.showMessageDialog(null,
											"Password Errata",
											"Error Message",
											JOptionPane.INFORMATION_MESSAGE,new ImageIcon(Password.class.getResource("/image/error.png")));
								}

								//Zero out the possible password, for security.
								Arrays.fill(input, '0');

								passwordField.selectAll();
								resetFocus();

							}
						});

						 passwordField.addKeyListener(new KeyAdapter() {
						      public void keyReleased(KeyEvent e) {
						   //     JTextField textField = (JTextField) e.getSource();
						    //    String text = textField.getText();
						     //   textField.setText(text.toUpperCase());
						       
						    	     if(e.getKeyChar() == KeyEvent.VK_ENTER){

					                      btnNewButton.doClick();
					                       
					                    }
						    	     if(e.getKeyChar() == KeyEvent.VK_ESCAPE){

					                      dispose();
					                       
					                    }
						      }

						      public void keyTyped(KeyEvent e) {
						      }

						      public void keyPressed(KeyEvent e) {
						      }
						    });
						  
	}
	void resetFocus() {
		passwordField.requestFocusInWindow();
	}

	public static  void setFocus() 
	{
		passwordField.requestFocusInWindow();
	}
	/**
	 * Checks the passed-in array against the correct password.
	 * After this method returns, you should invoke eraseArray
	 * on the passed-in array.
	 */
	private static boolean isPasswordCorrect(char[] input) {
		boolean isCorrect = true;
		char[] correctPassword = { 'l', 'm', 's', '2', '0', '2', '1', '!' };

		if (input.length != correctPassword.length) {
			isCorrect = false;
		} else {
			isCorrect = Arrays.equals (input, correctPassword);
		}

		//Zero out the password.
		Arrays.fill(correctPassword,'0');

		return isCorrect;
	}


}
