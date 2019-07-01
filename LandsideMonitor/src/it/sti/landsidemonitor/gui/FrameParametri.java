package it.sti.landsidemonitor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.dto.ParamDTO;
import net.miginfocom.swing.MigLayout;

public class FrameParametri extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;

	public FrameParametri() throws SQLException 
	{
		setTitle("Impostazioni Sistema");
		setSize(600, 600);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - 300) / 2;
		int y = (dim.height - 600) / 2;
		
		setLocation(x, y);
		getContentPane().setLayout(new MigLayout("", "[pref!,grow][pref!][grow]", "[][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][]"));
		
		JLabel lblParametriSistema = new JLabel("PARAMETRI SISTEMA");
		lblParametriSistema.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
		getContentPane().add(lblParametriSistema, "cell 0 0 3 1,alignx center,aligny center");
		
		JLabel lblPort = new JLabel("PORT *");
		lblPort.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblPort, "cell 0 2,alignx trailing");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 1 2,alignx left");
		textField.setColumns(10);
		
		JLabel lblFramerate = new JLabel("FRAMERATE *");
		lblFramerate.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblFramerate, "cell 0 3,alignx trailing");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		getContentPane().add(textField_1, "cell 1 3,alignx left");
		
		JLabel lblLimiteRumeroAsse = new JLabel("LIMITE RUMERO ASSE X");
		lblLimiteRumeroAsse.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblLimiteRumeroAsse, "cell 0 4,alignx trailing");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		getContentPane().add(textField_2, "cell 1 4,alignx left");
		
		JLabel lblLimiteRumoreAsse = new JLabel("LIMITE RUMORE ASSE Y");
		lblLimiteRumoreAsse.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblLimiteRumoreAsse, "cell 0 5,alignx trailing");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		getContentPane().add(textField_3, "cell 1 5,alignx left");
		
		JLabel lblLimiteRumoreAsse_1 = new JLabel("LIMITE RUMORE ASSE Z");
		lblLimiteRumoreAsse_1.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblLimiteRumoreAsse_1, "cell 0 6,alignx trailing");
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		getContentPane().add(textField_4, "cell 1 6,alignx left");
		
		JLabel lblVelocitaLetturaSonde = new JLabel("VELOCITA LETTURA SONDE");
		lblVelocitaLetturaSonde.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblVelocitaLetturaSonde, "cell 0 7,alignx trailing");
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		getContentPane().add(textField_5, "cell 1 7,alignx left");
		
		JLabel lblDebug = new JLabel("DEBUG");
		lblDebug.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblDebug, "cell 0 8,alignx trailing");
		
		JComboBox comboBox = new JComboBox(new String[] {"SI","NO"});
		getContentPane().add(comboBox, "cell 1 8,alignx left");
		
		JLabel lblValoreMancataRicezione = new JLabel("SENSIBILITA' MANCATA RICEZIONE");
		lblValoreMancataRicezione.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblValoreMancataRicezione, "cell 0 9,alignx trailing");
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		getContentPane().add(textField_6, "cell 1 9,alignx left");
		
		JLabel lblLimiteAllarme = new JLabel("LIMITE ALLARME");
		lblLimiteAllarme.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblLimiteAllarme, "cell 0 10,alignx trailing");
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		getContentPane().add(textField_7, "cell 1 10,alignx left");
		
		JLabel lblLimitePreallarme = new JLabel("LIMITE PRE-ALLARME");
		lblLimitePreallarme.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblLimitePreallarme, "cell 0 11,alignx trailing");
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		getContentPane().add(textField_8, "cell 1 11,alignx left");
		
		JLabel lblIParametri = new JLabel("* I parametri verranno applicati solo al prossimo riavvio");
		lblIParametri.setFont(new Font("Arial", Font.BOLD, 12));
		getContentPane().add(lblIParametri, "cell 0 13 3 1");
		
		JButton btnAnnulla = new JButton("ANNULLA");
		btnAnnulla.setIcon(new ImageIcon(FrameParametri.class.getResource("/image/abort.png")));
		btnAnnulla.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(btnAnnulla, "flowx,cell 1 14,alignx center");
		
		JButton btnSalva = new JButton("SALVA");
		btnSalva.setIcon(new ImageIcon(FrameParametri.class.getResource("/image/save.png")));
		btnSalva.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(btnSalva, "cell 0 14,alignx center");
	
	
		
		textField.setText(Costanti.PORT);
		textField_1.setText(""+Costanti.FRAMERATE);
		textField_2.setText(""+Costanti.LIMIT_GRAPH_X_AXIS);
		textField_3.setText(""+Costanti.LIMIT_GRAPH_Y_AXIS);
		textField_4.setText(""+Costanti.LIMIT_GRAPH_Z_AXIS);
		textField_5.setText(""+Costanti.FRAMERATE_READ_GRAPH);
		
		if(Costanti.DEBUG==true) 
		{
			comboBox.setSelectedIndex(0);
		}else 
		{
			comboBox.setSelectedIndex(1);
		}
		textField_6.setText(""+Costanti.VALORE_MANCATA_RICEZIONE_SONDA);
		textField_7.setText(""+Costanti.LIMITE_ALLARME);
		textField_8.setText(""+Costanti.LIMITE_PREALLARME);
		
		
		btnAnnulla.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
			}
		});
		
		btnSalva.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean save =true;
				
				if(textField.getText().length()==0) 
				{
					textField.setBackground(Color.red);
					save =false;
				}else 
				{
					textField.setBackground(Color.white);
				}
				
				if(textField_1.getText().length()==0 || controllaNumero(textField_1.getText())==false) 
				{
					textField_1.setBackground(Color.red);
					save =false;
				}else 
				{
					textField_1.setBackground(Color.white);
				}
				
				if(textField_2.getText().length()==0 || controllaNumero(textField_2.getText())==false) 
				{
					textField_2.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_2.setBackground(Color.white);
				}
				
				if(textField_3.getText().length()==0 || controllaNumero(textField_3.getText())==false) 
				{
					textField_3.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_3.setBackground(Color.white);
				}
				
				if(textField_4.getText().length()==0 || controllaNumero(textField_4.getText())==false) 
				{
					textField_4.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_4.setBackground(Color.white);
				}
				
				if(textField_5.getText().length()==0 || controllaNumero(textField_5.getText())==false) 
				{
					textField_5.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_5.setBackground(Color.white);
				}
				
				if(textField_6.getText().length()==0 || controllaNumero(textField_6.getText())==false) 
				{
					textField_6.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_6.setBackground(Color.white);
				}
				
				if(textField_7.getText().length()==0 || controllaNumero(textField_7.getText())==false) 
				{
					textField_7.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_7.setBackground(Color.white);
				}
				
				if(textField_8.getText().length()==0 || controllaNumero(textField_8.getText())==false) 
				{
					textField_8.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_8.setBackground(Color.white);
				}
				
				if(save) 
				{
					ParamDTO param = new ParamDTO();
					param.setPORT(textField.getText());
					param.setFRAMERATE(Integer.parseInt(textField_1.getText()));
					param.setLIMIT_GRAPH_X_AXIS(Double.parseDouble(textField_2.getText()));
					param.setLIMIT_GRAPH_Y_AXIS(Double.parseDouble(textField_3.getText()));
					param.setLIMIT_GRAPH_Z_AXIS(Double.parseDouble(textField_4.getText()));
					param.setFRAMERATE_READ_GRAPH(Integer.parseInt(textField_5.getText()));
					
					if(comboBox.getSelectedIndex()==0) 
					{
						param.setDEBUG("1");
					}else 
					{
						param.setDEBUG("0");
					}
					param.setVALORE_MANCATA_RICEZIONE_SONDA(Integer.parseInt(textField_6.getText()));
					param.setLIMITE_ALLARME(Double.parseDouble(textField_7.getText()));
					param.setLIMITE_PREALLARME(Double.parseDouble(textField_8.getText()));
					
					Costanti.PORT=param.getPORT();
					Costanti.FRAMERATE=param.getFRAMERATE();
					Costanti.LIMIT_GRAPH_X_AXIS=param.getLIMIT_GRAPH_X_AXIS();
					Costanti.LIMIT_GRAPH_Y_AXIS=param.getLIMIT_GRAPH_Y_AXIS();
					Costanti.LIMIT_GRAPH_Z_AXIS=param.getLIMIT_GRAPH_Z_AXIS();
					if(param.getDEBUG().equals("1")) 
					{
						Costanti.DEBUG=true;
					}
					else 
					{
						Costanti.DEBUG=false;
					}
					Costanti.VALORE_MANCATA_RICEZIONE_SONDA=param.getVALORE_MANCATA_RICEZIONE_SONDA();
					Costanti.LIMITE_ALLARME=param.getLIMITE_ALLARME();
					Costanti.LIMITE_PREALLARME=param.getLIMITE_PREALLARME();
					
					try {
						Core.saveParam(param);
						JOptionPane.showMessageDialog(null,"Salvataggio eseguito con successo","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(FrameParametri.class.getResource("/image/confirm.png")));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}

			
			
			private boolean controllaNumero(String text) {
				
				try 
				{
					Double.parseDouble(text);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		});
	}

}
