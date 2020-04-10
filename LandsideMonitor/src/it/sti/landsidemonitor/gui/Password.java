package it.sti.landsidemonitor.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

public class Password extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String OK = "ok";

	private JPasswordField passwordField;

	public Password(JFrame f) {
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
							            {	
							            	JFrame f=new FrameParametri();
							            	f.setDefaultCloseOperation(1);
							      	        f.setVisible(true);
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

	}
	void resetFocus() {
		passwordField.requestFocusInWindow();
	}

	/**
	 * Checks the passed-in array against the correct password.
	 * After this method returns, you should invoke eraseArray
	 * on the passed-in array.
	 */
	private static boolean isPasswordCorrect(char[] input) {
		boolean isCorrect = true;
		char[] correctPassword = { '1', '2', '3', '4' };

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
