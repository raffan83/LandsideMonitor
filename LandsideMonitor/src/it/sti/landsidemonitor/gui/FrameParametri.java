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
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.dto.ParamDTO;
import net.miginfocom.swing.MigLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

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
	private JTextField textField_min_p1;
	private JTextField textField_hostname;
	private JTextField textField_username;
	private JTextField textField_password;
	private JTextField textField_smtp_port;
	private JTextField textField_destinatari_pre;
	private JTextField textField_max_p1;
	private JTextField textField_iter_p1;
	private JTextField textField_min_p2;
	private JTextField textField_min_p3;
	private JTextField textField_max_p2;
	private JTextField textField_max_p3;
	private JTextField textField_iter_p2;
	private JTextField textField_iter_p3;
	private JTextField textField_destinatari_alarm;

	public FrameParametri() throws SQLException 
	{
		setTitle("Impostazioni Sistema");
		setSize(650, 600);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - 650) / 2;
		int y = (dim.height - 600) / 2;
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel panelMainParam = new JPanel();
		
		JPanel panelMail = new JPanel();
		
		tabbedPane.addTab("Parametri generali",panelMainParam);
		
		tabbedPane.addTab("Parametri Mail",panelMail);
		
		setLocation(x, y);
		panelMainParam.setLayout(new MigLayout("", "[pref!,grow][pref!][grow][grow]", "[][9.00][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][]"));
		
		panelMail.setLayout(new MigLayout("", "[pref!,grow][pref!,grow][grow]", "[][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][]"));
		
		JLabel lblHostMail = new JLabel("HOST MAIL");
		lblHostMail.setFont(new Font("Arial", Font.BOLD, 14));
		panelMail.add(lblHostMail, "cell 0 1,alignx trailing");
		
		textField_hostname = new JTextField();
		textField_hostname.setText("");
		textField_hostname.setColumns(10);
		panelMail.add(textField_hostname, "cell 1 1,width :200:");
		
		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
		panelMail.add(lblUsername, "cell 0 2,alignx trailing");
		
		textField_username = new JTextField();
		textField_username.setText("");
		textField_username.setColumns(10);
		panelMail.add(textField_username, "cell 1 2,growx");
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
		panelMail.add(lblPassword, "cell 0 3,alignx trailing");
		
		textField_password = new JTextField();
		textField_password.setText("");
		textField_password.setColumns(10);
		panelMail.add(textField_password, "cell 1 3,growx");
		
		JLabel lblSmtpAuth = new JLabel("SMTP AUTH");
		lblSmtpAuth.setFont(new Font("Arial", Font.BOLD, 14));
		panelMail.add(lblSmtpAuth, "cell 0 4,alignx trailing");
		
		JComboBox comboBox_smtp_auth = new JComboBox();
		comboBox_smtp_auth.setModel(new DefaultComboBoxModel(new String[] {"true", "false"}));
		panelMail.add(comboBox_smtp_auth, "cell 1 4,width :75:");
		
		JLabel lblSmtpPort = new JLabel("SMTP PORT");
		lblSmtpPort.setFont(new Font("Arial", Font.BOLD, 14));
		panelMail.add(lblSmtpPort, "cell 0 5,alignx trailing");
		
		textField_smtp_port = new JTextField();
		textField_smtp_port.setText("");
		textField_smtp_port.setColumns(10);
		panelMail.add(textField_smtp_port, "cell 1 5,width :75:");
		
		JLabel lblSsl = new JLabel("SSL");
		lblSsl.setFont(new Font("Arial", Font.BOLD, 14));
		panelMail.add(lblSsl, "cell 0 6,alignx trailing");
		
		JComboBox comboBox_ssl = new JComboBox();
		comboBox_ssl.setModel(new DefaultComboBoxModel(new String[] {"true", "false"}));
		panelMail.add(comboBox_ssl, "cell 1 6,width :75:");
		
		JLabel lblDestinatari = new JLabel("DESTINATARI PREALLARME");
		lblDestinatari.setFont(new Font("Arial", Font.BOLD, 14));
		panelMail.add(lblDestinatari, "cell 0 7,alignx trailing");
		
		textField_destinatari_pre = new JTextField();
		textField_destinatari_pre.setText("");
		textField_destinatari_pre.setColumns(10);
		panelMail.add(textField_destinatari_pre, "cell 1 7 2 1,growx");
		
		JLabel lblParametriSistema = new JLabel("PARAMETRI SISTEMA");
		lblParametriSistema.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
		panelMainParam.add(lblParametriSistema, "cell 0 0 3 1,alignx center,aligny center");
		
		JLabel lblPort = new JLabel("PORT *");
		lblPort.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblPort, "cell 0 2,alignx trailing");
		
		textField = new JTextField();
		panelMainParam.add(textField, "cell 1 2,alignx left");
		textField.setColumns(10);
		
		JLabel lblFramerate = new JLabel("FRAMERATE *");
		lblFramerate.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblFramerate, "cell 0 3,alignx trailing");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		panelMainParam.add(textField_1, "cell 1 3,alignx left");
		
		JLabel lblLimiteRumeroAsse = new JLabel("LIMITE RUMORE ASSE X");
		lblLimiteRumeroAsse.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblLimiteRumeroAsse, "cell 0 4,alignx trailing");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		panelMainParam.add(textField_2, "flowx,cell 1 4,alignx left");
		
		JLabel lblLimiteRumoreAsse = new JLabel("LIMITE RUMORE ASSE Y");
		lblLimiteRumoreAsse.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblLimiteRumoreAsse, "cell 0 5,alignx trailing");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		panelMainParam.add(textField_3, "flowx,cell 1 5,alignx left");
		
		JLabel lblLimiteRumoreAsse_1 = new JLabel("LIMITE RUMORE ASSE Z");
		lblLimiteRumoreAsse_1.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblLimiteRumoreAsse_1, "cell 0 6,alignx trailing");
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		panelMainParam.add(textField_4, "flowx,cell 1 6,alignx left");
		
		JLabel lblVelocitaLetturaSonde = new JLabel("VELOCITA' LETTURA GRAFICO*");
		lblVelocitaLetturaSonde.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblVelocitaLetturaSonde, "cell 0 7,alignx trailing");
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		panelMainParam.add(textField_5, "cell 1 7,alignx left");
		
		JLabel lblDebug = new JLabel("DEBUG");
		lblDebug.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblDebug, "cell 0 8,alignx trailing");
		
		JComboBox comboBox = new JComboBox(new String[] {"SI","NO"});
		panelMainParam.add(comboBox, "cell 1 8,alignx left");
		
		JLabel lblValoreMancataRicezione = new JLabel("SENSIBILITA' MANCATA RICEZIONE");
		lblValoreMancataRicezione.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblValoreMancataRicezione, "cell 0 9,alignx trailing");
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		panelMainParam.add(textField_6, "cell 1 9,alignx left");
		
		JLabel lblLimiteAllarme = new JLabel("RANGE ALLERTA 1*");
		lblLimiteAllarme.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblLimiteAllarme, "cell 0 10,alignx trailing");
		
		JLabel lblMin = new JLabel("MIN");
		lblMin.setFont(new Font("Arial", Font.BOLD, 12));
		panelMainParam.add(lblMin, "flowx,cell 1 10,alignx trailing");
		
		textField_min_p1 = new JTextField();
		textField_min_p1.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_min_p1.setColumns(10);
		panelMainParam.add(textField_min_p1, "cell 1 10,width 75:75:75,alignx left");
		
		JLabel lblRangePreallarme = new JLabel("RANGE ALLERTA 2*");
		lblRangePreallarme.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblRangePreallarme, "cell 0 11,alignx trailing");
		
		JLabel label = new JLabel("MIN");
		label.setFont(new Font("Arial", Font.BOLD, 12));
		panelMainParam.add(label, "flowx,cell 1 11,alignx trailing");
		
		JLabel label_2 = new JLabel("MAX");
		label_2.setFont(new Font("Arial", Font.BOLD, 12));
		panelMainParam.add(label_2, "flowx,cell 2 11,alignx trailing");
		
		JLabel lblSec = new JLabel("SEC");
		lblSec.setFont(new Font("Arial", Font.BOLD, 12));
		panelMainParam.add(lblSec, "flowx,cell 3 11,alignx trailing");
		
		JLabel lblRangePreallarme_1 = new JLabel("RANGE ALLERTA 3*");
		lblRangePreallarme_1.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblRangePreallarme_1, "cell 0 12,alignx trailing");
		
		JLabel label_1 = new JLabel("MIN");
		label_1.setFont(new Font("Arial", Font.BOLD, 12));
		panelMainParam.add(label_1, "flowx,cell 1 12,alignx trailing");
		
		JLabel label_3 = new JLabel("MAX");
		label_3.setFont(new Font("Arial", Font.BOLD, 12));
		panelMainParam.add(label_3, "flowx,cell 2 12,alignx trailing");
		
		JLabel lblSec_1 = new JLabel("SEC\r\n");
		lblSec_1.setFont(new Font("Arial", Font.BOLD, 12));
		panelMainParam.add(lblSec_1, "flowx,cell 3 12,alignx trailing");
		
		JLabel lblIParametri = new JLabel("* I parametri verranno applicati solo al prossimo riavvio");
		lblIParametri.setFont(new Font("Arial", Font.BOLD, 12));
		panelMainParam.add(lblIParametri, "flowx,cell 0 13 3 1");
		
		JButton btnSalva = new JButton("SALVA");
		btnSalva.setIcon(new ImageIcon(FrameParametri.class.getResource("/image/save.png")));
		btnSalva.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(btnSalva, "cell 0 14 4 1,alignx center");
	
		JLabel lblDestinatariAllarme = new JLabel("DESTINATARI ALLARME");
		lblDestinatariAllarme.setFont(new Font("Arial", Font.BOLD, 14));
		panelMail.add(lblDestinatariAllarme, "cell 0 8,alignx trailing");
		
		textField_destinatari_alarm = new JTextField();
		textField_destinatari_alarm.setText("");
		textField_destinatari_alarm.setColumns(10);
		panelMail.add(textField_destinatari_alarm, "cell 1 8 2 1,growx");
		
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
		
		JLabel lblMax = new JLabel("MAX");
		lblMax.setFont(new Font("Arial", Font.BOLD, 12));
		panelMainParam.add(lblMax, "flowx,cell 2 10,alignx trailing");
		
		textField_max_p1 = new JTextField();
		textField_max_p1.setHorizontalAlignment(SwingConstants.RIGHT);
		panelMainParam.add(textField_max_p1, "cell 2 10,width 75:75:75");
		textField_max_p1.setColumns(10);
		
		JLabel lblSensibilita = new JLabel("SEC");
		lblSensibilita.setFont(new Font("Arial", Font.BOLD, 12));
		panelMainParam.add(lblSensibilita, "flowx,cell 3 10,alignx trailing");
		
		textField_iter_p1 = new JTextField();
		textField_iter_p1.setHorizontalAlignment(SwingConstants.RIGHT);
		panelMainParam.add(textField_iter_p1, "cell 3 10,width 75:75:75");
		textField_iter_p1.setColumns(10);
		
		textField_min_p2 = new JTextField();
		textField_min_p2.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_min_p2.setColumns(10);
		panelMainParam.add(textField_min_p2, "cell 1 11,width 75:75:75,alignx left");
		
		textField_min_p3 = new JTextField();
		textField_min_p3.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_min_p3.setColumns(10);
		panelMainParam.add(textField_min_p3, "cell 1 12,width 75:75:75,alignx left");
		
		textField_max_p2 = new JTextField();
		textField_max_p2.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_max_p2.setColumns(10);
		panelMainParam.add(textField_max_p2, "cell 2 11,width 75:75:75,alignx left");
		
		textField_max_p3 = new JTextField();
		textField_max_p3.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_max_p3.setColumns(10);
		panelMainParam.add(textField_max_p3, "cell 2 12,width 75:75:75,alignx left");
		
		textField_iter_p2 = new JTextField();
		textField_iter_p2.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_iter_p2.setColumns(10);
		panelMainParam.add(textField_iter_p2, "cell 3 11,width 75:75:75,alignx left");
		
		textField_iter_p3 = new JTextField();
		textField_iter_p3.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_iter_p3.setColumns(10);
		panelMainParam.add(textField_iter_p3, "cell 3 12,width 75:75:75,alignx left");
		
		textField_min_p1.setText(""+Costanti.LIMITE_MIN_P1);
		textField_max_p1.setText(""+Costanti.LIMITE_MAX_P1);
		textField_iter_p1.setText(""+Costanti.TEMPO_ALLERTA_1/1000);
		
		textField_min_p2.setText(""+Costanti.LIMITE_MIN_P2);
		textField_max_p2.setText(""+Costanti.LIMITE_MAX_P2);
		textField_iter_p2.setText(""+Costanti.TEMPO_ALLERTA_2/1000);
		
		textField_min_p3.setText(""+Costanti.LIMITE_MIN_P3);
		textField_max_p3.setText(""+Costanti.LIMITE_MAX_P3);
		textField_iter_p3.setText(""+Costanti.TEMPO_ALLERTA_3/1000);
		
		JButton btnAnnulla = new JButton("ANNULLA");
		btnAnnulla.setIcon(new ImageIcon(FrameParametri.class.getResource("/image/abort.png")));
		btnAnnulla.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(btnAnnulla, "cell 0 14 4 1");
		
		JLabel label_6 = new JLabel("m/s\u00B2");
		label_6.setFont(new Font("Arial", Font.PLAIN, 11));
		panelMainParam.add(label_6, "cell 1 4");
		
		JLabel label_7 = new JLabel("m/s\u00B2");
		label_7.setFont(new Font("Arial", Font.PLAIN, 11));
		panelMainParam.add(label_7, "cell 1 5");
		
		JLabel label_8 = new JLabel("m/s\u00B2");
		label_8.setFont(new Font("Arial", Font.PLAIN, 11));
		panelMainParam.add(label_8, "cell 1 6");
		
		JLabel label_10 = new JLabel("m/s\u00B2");
		label_10.setFont(new Font("Arial", Font.PLAIN, 11));
		panelMainParam.add(label_10, "cell 1 11");
		
		JLabel label_11 = new JLabel("m/s\u00B2");
		label_11.setFont(new Font("Arial", Font.PLAIN, 11));
		panelMainParam.add(label_11, "cell 1 12");
		
		JLabel label_12 = new JLabel("m/s\u00B2");
		label_12.setFont(new Font("Arial", Font.PLAIN, 11));
		panelMainParam.add(label_12, "cell 2 10");
		
		JLabel label_13 = new JLabel("m/s\u00B2");
		label_13.setFont(new Font("Arial", Font.PLAIN, 11));
		panelMainParam.add(label_13, "cell 2 11");
		
		JLabel label_14 = new JLabel("m/s\u00B2");
		label_14.setFont(new Font("Arial", Font.PLAIN, 11));
		panelMainParam.add(label_14, "cell 2 12");
		
		JLabel label_9 = new JLabel("m/s\u00B2");
		label_9.setFont(new Font("Arial", Font.PLAIN, 11));
		panelMainParam.add(label_9, "cell 1 10");
		btnAnnulla.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
			}
		});
		
		
		textField_hostname.setText(Costanti.HOST_NAME_MAIL);
		textField_username.setText(Costanti.USERNAME_MAIL);
		textField_password.setText(Costanti.PASSWORD_MAIL);

		if(Costanti.SMTP_AUTH.equals("true"))
		{
			comboBox_smtp_auth.setSelectedIndex(0);
		}else 
		{
			comboBox_smtp_auth.setSelectedIndex(1);
		}
		
		textField_smtp_port.setText(Costanti.PORT_MAIL);
		
		if(Costanti.SSL.equals("true"))
		{
			comboBox_ssl.setSelectedIndex(0);
		}else 
		{
			comboBox_ssl.setSelectedIndex(1);
		}
		
		textField_destinatari_pre.setText(Costanti.DEST_MAIL_PRE);
		textField_destinatari_alarm.setText(Costanti.DEST_MAIL_ALARM);
		
		
		
		getContentPane().add(tabbedPane);
		
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
				
				
				/*Soglia pre allarme 1*/
				if(textField_min_p1.getText().length()==0 || controllaNumero(textField_min_p1.getText())==false) 
				{
					textField_min_p1.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_min_p1.setBackground(Color.white);
				}
				
				if(textField_min_p1.getText().length()==0 || controllaNumero(textField_min_p1.getText())==false) 
				{
					textField_min_p1.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_min_p1.setBackground(Color.white);
				}
				
				if(textField_max_p1.getText().length()==0 || controllaNumero(textField_max_p1.getText())==false) 
				{
					textField_max_p1.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_max_p1.setBackground(Color.white);
				}
				
				if(textField_iter_p1.getText().length()==0 || controllaNumero(textField_iter_p1.getText())==false) 
				{
					textField_iter_p1.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_iter_p1.setBackground(Color.white);
				}
				
				
				/*
				 * Soglia Preallarme 2
				 */
				if(textField_min_p2.getText().length()==0 || controllaNumero(textField_min_p2.getText())==false) 
				{
					textField_min_p2.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_min_p2.setBackground(Color.white);
				}
				
				if(textField_min_p2.getText().length()==0 || controllaNumero(textField_min_p2.getText())==false) 
				{
					textField_min_p2.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_min_p2.setBackground(Color.white);
				}
				
				if(textField_max_p2.getText().length()==0 || controllaNumero(textField_max_p2.getText())==false) 
				{
					textField_max_p2.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_max_p2.setBackground(Color.white);
				}
				
				if(textField_iter_p2.getText().length()==0 || controllaNumero(textField_iter_p2.getText())==false) 
				{
					textField_iter_p2.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_iter_p2.setBackground(Color.white);
				}
				
				/*
				 * Soglia Preallarme 3
				 */
				if(textField_min_p3.getText().length()==0 || controllaNumero(textField_min_p3.getText())==false) 
				{
					textField_min_p3.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_min_p3.setBackground(Color.white);
				}
				
				if(textField_min_p3.getText().length()==0 || controllaNumero(textField_min_p3.getText())==false) 
				{
					textField_min_p3.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_min_p3.setBackground(Color.white);
				}
				
				if(textField_max_p3.getText().length()==0 || controllaNumero(textField_max_p3.getText())==false) 
				{
					textField_max_p3.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_max_p3.setBackground(Color.white);
				}
				
				if(textField_iter_p3.getText().length()==0 || controllaNumero(textField_iter_p2.getText())==false) 
				{
					textField_iter_p3.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_iter_p3.setBackground(Color.white);
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
					
					param.setLIMITE_MIN_P1(Double.parseDouble(textField_min_p1.getText()));
					param.setLIMITE_MAX_P1(Double.parseDouble(textField_max_p1.getText()));
					param.setITERAZIONI_P1(Integer.parseInt(textField_iter_p1.getText()));
					
					param.setLIMITE_MIN_P2(Double.parseDouble(textField_min_p2.getText()));
					param.setLIMITE_MAX_P2(Double.parseDouble(textField_max_p2.getText()));
					param.setITERAZIONI_P2(Integer.parseInt(textField_iter_p2.getText()));
					
					param.setLIMITE_MIN_P3(Double.parseDouble(textField_min_p3.getText()));
					param.setLIMITE_MAX_P3(Double.parseDouble(textField_max_p3.getText()));
					param.setITERAZIONI_P3(Integer.parseInt(textField_iter_p3.getText()));
					
					param.setHOST_NAME_MAIL(textField_hostname.getText());
					param.setUSERNAME_MAIL(textField_username.getText());
					param.setPASSWORD_MAIL(textField_password.getText());
					param.setSMTP_AUTH(comboBox_smtp_auth.getSelectedItem().toString());
					param.setPORT_MAIL(textField_smtp_port.getText());
					param.setSSL(comboBox_ssl.getSelectedItem().toString());
					param.setDEST_MAIL_PRE(textField_destinatari_pre.getText());
					param.setDEST_MAIL_ALARM(textField_destinatari_alarm.getText());
					
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
					
					Costanti.LIMITE_MIN_P1=param.getLIMITE_MIN_P1();
					Costanti.LIMITE_MAX_P1=param.getLIMITE_MAX_P1();
					Costanti.TEMPO_ALLERTA_1=param.getITERAZIONI_P1();
					
					Costanti.LIMITE_MIN_P2=param.getLIMITE_MIN_P2();
					Costanti.LIMITE_MAX_P2=param.getLIMITE_MAX_P2();
					Costanti.TEMPO_ALLERTA_2=param.getITERAZIONI_P2();
					
					Costanti.LIMITE_MIN_P3=param.getLIMITE_MIN_P3();
					Costanti.LIMITE_MAX_P3=param.getLIMITE_MAX_P3();
					Costanti.TEMPO_ALLERTA_3=param.getITERAZIONI_P3();
					
					Costanti.HOST_NAME_MAIL=param.getHOST_NAME_MAIL();
					Costanti.USERNAME_MAIL=param.getUSERNAME_MAIL();
					Costanti.PASSWORD_MAIL=param.getPASSWORD_MAIL();
					Costanti.SMTP_AUTH=param.getSMTP_AUTH();
					Costanti.PORT_MAIL=param.getPORT_MAIL();
					Costanti.SSL=param.getSSL();
					Costanti.DEST_MAIL_PRE=param.getDEST_MAIL_PRE();
					Costanti.DEST_MAIL_ALARM=param.getDEST_MAIL_ALARM();
					
					
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
