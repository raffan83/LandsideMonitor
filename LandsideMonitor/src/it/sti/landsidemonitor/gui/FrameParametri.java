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

public class FrameParametri extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
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
	private JTextField textField_det_5_sec;
	private JTextField textField_det_9_sec;
	private JTextField textField_det_12_sec;
	private JTextField textField_det_15_sec;
	private JTextField textField_destinatari_manutenzione;
	private JTextField textField_lista_sms;

	public FrameParametri() throws SQLException 
	{
		getContentPane().setBackground(Color.WHITE);
		setTitle("Impostazioni Sistema");
		setSize(650, 600);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - 650) / 2;
		int y = (dim.height - 600) / 2;
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel panelMainParam = new JPanel();
		panelMainParam.setBackground(Color.WHITE);
		
		JPanel panelMail = new JPanel();
		panelMail.setBackground(Color.WHITE);
		
		JPanel panelSignal = new JPanel();
		panelSignal.setBackground(Color.WHITE);
		
		tabbedPane.addTab("Parametri generali",panelMainParam);
		
		tabbedPane.addTab("Parametri Mail & SMS\r\n",panelMail);
		
		tabbedPane.addTab("Potenza segnale",panelSignal);
		
		setLocation(x, y);
		panelMainParam.setLayout(new MigLayout("", "[pref!,grow][pref!,grow][grow][grow]", "[][9.00][30px:30px][:30px:30px][:30px:30px][grow][:20px:20px][grow]"));
		
		panelMail.setLayout(new MigLayout("", "[pref!,grow][pref!,grow][grow]", "[][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][]"));
		
		panelSignal.setLayout(null);
		
		JLabel lab= new JLabel("Impostazione livello potenza sonda");

		lab.setFont(new Font("Arial",Font.BOLD,15));
		lab.setBounds(10, 10, 280, 30);
		panelSignal.add(lab);

		JLabel sonda = new JLabel("Sonda");
		sonda.setFont(new Font("Arial",Font.BOLD,14));
		sonda.setBounds(20, 50, 75, 30);
		panelSignal.add(sonda);

		JComboBox<String> comboSonde = new JComboBox<String>();

		for (SensorDTO sensorDTO : PortReader.listaSensori) {

			comboSonde.addItem(sensorDTO.getIdentifier());
		}

		comboSonde.setBounds(80, 50, 40, 25);
		comboSonde.setFont(new Font("Arial",Font.BOLD,14));
		panelSignal.add(comboSonde);

		JLabel lab_sign= new JLabel("Potenza segnale");

		lab_sign.setFont(new Font("Arial",Font.BOLD,15));
		lab_sign.setBounds(150, 50, 120, 25);
		panelSignal.add(lab_sign);

		String[] data = new String[]{"10","11","12","13","14","15","16","17","18","19","20","21","22","23"};

		JComboBox<String> comboSign = new JComboBox<String>(data);

		comboSign.setBounds(280, 50, 50, 25);
		comboSign.setFont(new Font("Arial",Font.BOLD,14));
		panelSignal.add(comboSign);

		JButton button = new JButton("Invia");
		button.setIcon(new ImageIcon(FrameParametri.class.getResource("/image/continue.png")));
		button.setFont(new Font("Arial", Font.BOLD, 14));
		button.setBounds(360,44 , 120, 37);
		panelSignal.add(button);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String sonda =comboSonde.getSelectedItem().toString();
				String sign=comboSign.getSelectedItem().toString();

				try {
					PortReader.write("P"+sonda+sign);
				} catch (SerialPortException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		
		
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
		
		JLabel lblDestinatari = new JLabel("DESTINATARI ALLERTA");
		lblDestinatari.setFont(new Font("Arial", Font.BOLD, 14));
		panelMail.add(lblDestinatari, "cell 0 7,alignx left");
		
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
		
		JPanel panel_rocc_a = new JPanel();
		panel_rocc_a.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Parametri sonde tipo roccioso (Gruppo A)", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel_rocc_a.setBackground(Color.WHITE);
		panelMainParam.add(panel_rocc_a, "cell 0 5 4 1,grow");
		panel_rocc_a.setLayout(new MigLayout("", "[grow][grow][grow][grow]", "[grow][grow][grow]"));
		
		JLabel lblLimiteAllarme = new JLabel("RANGE ALLERTA 1*");
		panel_rocc_a.add(lblLimiteAllarme, "cell 0 0");
		lblLimiteAllarme.setFont(new Font("Arial", Font.BOLD, 14));
		
		JLabel lblMin = new JLabel("MIN");
		panel_rocc_a.add(lblMin, "flowx,cell 1 0");
		lblMin.setFont(new Font("Arial", Font.BOLD, 12));
		
		textField_min_p1 = new JTextField();
		panel_rocc_a.add(textField_min_p1, "cell 1 0");
		textField_min_p1.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_min_p1.setColumns(10);
		
		textField_min_p1.setText(""+Costanti.LIMITE_MIN_P1);
		
		JLabel label_9 = new JLabel("m/s\u00B2");
		panel_rocc_a.add(label_9, "cell 1 0");
		label_9.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel lblMax = new JLabel("MAX");
		panel_rocc_a.add(lblMax, "flowx,cell 2 0");
		lblMax.setFont(new Font("Arial", Font.BOLD, 12));
		
		textField_max_p1 = new JTextField();
		panel_rocc_a.add(textField_max_p1, "cell 2 0");
		textField_max_p1.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_max_p1.setColumns(10);
		textField_max_p1.setText(""+Costanti.LIMITE_MAX_P1);
		
		JLabel label_12 = new JLabel("m/s\u00B2");
		panel_rocc_a.add(label_12, "cell 2 0");
		label_12.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel lblSensibilita = new JLabel("SEC");
		panel_rocc_a.add(lblSensibilita, "flowx,cell 3 0");
		lblSensibilita.setFont(new Font("Arial", Font.BOLD, 12));
		
		textField_iter_p1 = new JTextField();
		panel_rocc_a.add(textField_iter_p1, "cell 3 0");
		textField_iter_p1.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_iter_p1.setColumns(10);
		textField_iter_p1.setText(""+Costanti.TEMPO_ALLERTA_1/1000);
		
		JLabel lblRangePreallarme = new JLabel("RANGE ALLERTA 2*");
		panel_rocc_a.add(lblRangePreallarme, "cell 0 1");
		lblRangePreallarme.setFont(new Font("Arial", Font.BOLD, 14));
		
		JLabel label = new JLabel("MIN");
		panel_rocc_a.add(label, "flowx,cell 1 1");
		label.setFont(new Font("Arial", Font.BOLD, 12));
		
		textField_min_p2 = new JTextField();
		panel_rocc_a.add(textField_min_p2, "cell 1 1");
		textField_min_p2.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_min_p2.setColumns(10);
		
		textField_min_p2.setText(""+Costanti.LIMITE_MIN_P2);
		
		JLabel label_10 = new JLabel("m/s\u00B2");
		panel_rocc_a.add(label_10, "cell 1 1");
		label_10.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel label_2 = new JLabel("MAX");
		panel_rocc_a.add(label_2, "flowx,cell 2 1");
		label_2.setFont(new Font("Arial", Font.BOLD, 12));
		
		textField_max_p2 = new JTextField();
		panel_rocc_a.add(textField_max_p2, "cell 2 1");
		textField_max_p2.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_max_p2.setColumns(10);
		textField_max_p2.setText(""+Costanti.LIMITE_MAX_P2);
		
		JLabel label_13 = new JLabel("m/s\u00B2");
		panel_rocc_a.add(label_13, "cell 2 1");
		label_13.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel lblSec = new JLabel("SEC");
		panel_rocc_a.add(lblSec, "flowx,cell 3 1");
		lblSec.setFont(new Font("Arial", Font.BOLD, 12));
		
		textField_iter_p2 = new JTextField();
		panel_rocc_a.add(textField_iter_p2, "cell 3 1");
		textField_iter_p2.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_iter_p2.setColumns(10);
		textField_iter_p2.setText(""+Costanti.TEMPO_ALLERTA_2/1000);
		
		JLabel lblRangePreallarme_1 = new JLabel("RANGE ALLERTA 3*");
		panel_rocc_a.add(lblRangePreallarme_1, "cell 0 2");
		lblRangePreallarme_1.setFont(new Font("Arial", Font.BOLD, 14));
		
		JLabel label_1 = new JLabel("MIN");
		panel_rocc_a.add(label_1, "flowx,cell 1 2");
		label_1.setFont(new Font("Arial", Font.BOLD, 12));
		
		textField_min_p3 = new JTextField();
		panel_rocc_a.add(textField_min_p3, "cell 1 2");
		textField_min_p3.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_min_p3.setColumns(10);
		
		textField_min_p3.setText(""+Costanti.LIMITE_MIN_P3);
		
		JLabel label_11 = new JLabel("m/s\u00B2");
		panel_rocc_a.add(label_11, "cell 1 2");
		label_11.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel label_3 = new JLabel("MAX");
		panel_rocc_a.add(label_3, "flowx,cell 2 2");
		label_3.setFont(new Font("Arial", Font.BOLD, 12));
		
		textField_max_p3 = new JTextField();
		panel_rocc_a.add(textField_max_p3, "cell 2 2");
		textField_max_p3.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_max_p3.setColumns(10);
		textField_max_p3.setText(""+Costanti.LIMITE_MAX_P3);
		
		JLabel label_14 = new JLabel("m/s\u00B2");
		panel_rocc_a.add(label_14, "cell 2 2");
		label_14.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel lblSec_1 = new JLabel("SEC\r\n");
		panel_rocc_a.add(lblSec_1, "flowx,cell 3 2");
		lblSec_1.setFont(new Font("Arial", Font.BOLD, 12));
		
		textField_iter_p3 = new JTextField();
		panel_rocc_a.add(textField_iter_p3, "cell 3 2");
		textField_iter_p3.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_iter_p3.setColumns(10);
		textField_iter_p3.setText(""+Costanti.TEMPO_ALLERTA_3/1000);
		
		JLabel lblDebug = new JLabel("DEBUG");
		lblDebug.setFont(new Font("Arial", Font.BOLD, 14));
		panelMainParam.add(lblDebug, "cell 0 4,alignx trailing");
		
		JComboBox comboBox = new JComboBox(new String[] {"SI","NO"});
		panelMainParam.add(comboBox, "cell 1 4,alignx left");
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Parametri sonde detritiche (Gruppo B)", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panel.setBackground(new Color(255, 255, 255));
		panelMainParam.add(panel, "cell 0 7 4 1,grow");
		panel.setLayout(new MigLayout("", "[131px][][][][]", "[17px,grow][grow][grow][grow]"));
		
		JLabel lblNPuntiAllarme = new JLabel("N\u00B0 PUNTI ALLARME MOVIMENTO 5 SECONDI");
		lblNPuntiAllarme.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblNPuntiAllarme, "cell 0 0");
		
		textField_det_5_sec = new JTextField();
		textField_det_5_sec.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_det_5_sec.setColumns(10);
		textField_det_5_sec.setText(""+Costanti.PUNTI_DET_5_SEC);
		panel.add(textField_det_5_sec, "cell 2 0,width 30:30:30");
		
		JLabel lblAllarme = new JLabel("ALLARME");
		lblAllarme.setForeground(Color.RED);
		lblAllarme.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblAllarme, "cell 4 0");
		
		JLabel lblNPuntiAllarme_1 = new JLabel("N\u00B0 PUNTI ALLARME MOVIMENTO 9 SECONDI");
		lblNPuntiAllarme_1.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblNPuntiAllarme_1, "cell 0 1");
		
		textField_det_9_sec = new JTextField();
		textField_det_9_sec.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_det_9_sec.setColumns(10);
		textField_det_9_sec.setText(""+Costanti.PUNTI_DET_9_SEC);
		panel.add(textField_det_9_sec, "cell 2 1,width 30:30:30");
		
		JLabel lblAllerta = new JLabel("ALLERTA\r\n");
		lblAllerta.setForeground(Color.ORANGE);
		lblAllerta.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblAllerta, "cell 4 1");
		
		JLabel lblNPuntiAllarme_2 = new JLabel("N\u00B0 PUNTI ALLARME MOVIMENTO 12 SECONDI");
		lblNPuntiAllarme_2.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblNPuntiAllarme_2, "cell 0 2,alignx trailing");
		
		textField_det_12_sec = new JTextField();
		textField_det_12_sec.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_det_12_sec.setColumns(10);
		textField_det_12_sec.setText(""+Costanti.PUNTI_DET_12_SEC);
		panel.add(textField_det_12_sec, "cell 2 2,width 30:30:30");
		
		JLabel label_4 = new JLabel("ALLERTA\r\n");
		label_4.setForeground(Color.ORANGE);
		label_4.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(label_4, "cell 4 2");
		
		JLabel lblNPuntiAllarme_3 = new JLabel("N\u00B0 PUNTI ALLARME MOVIMENTO 15 SECONDI");
		lblNPuntiAllarme_3.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(lblNPuntiAllarme_3, "cell 0 3,alignx trailing");
		
		textField_det_15_sec = new JTextField();
		textField_det_15_sec.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_det_15_sec.setColumns(10);
		textField_det_15_sec.setText(""+Costanti.PUNTI_DET_15_SEC);
		panel.add(textField_det_15_sec, "cell 2 3,width 30:30:30");
		
		JLabel label_5 = new JLabel("ALLERTA\r\n");
		label_5.setForeground(Color.ORANGE);
		label_5.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(label_5, "cell 4 3");
	
		JLabel lblDestinatariAllarme = new JLabel("DESTINATARI ALLARME");
		lblDestinatariAllarme.setFont(new Font("Arial", Font.BOLD, 14));
		panelMail.add(lblDestinatariAllarme, "cell 0 8,alignx left");
		
		textField_destinatari_alarm = new JTextField();
		textField_destinatari_alarm.setText("");
		textField_destinatari_alarm.setColumns(10);
		panelMail.add(textField_destinatari_alarm, "cell 1 8 2 1,growx");
		
		textField.setText(Costanti.PORT);
		textField_1.setText(""+Costanti.FRAMERATE);
		
		if(Costanti.DEBUG==true) 
		{
			comboBox.setSelectedIndex(0);
		}else 
		{
			comboBox.setSelectedIndex(1);
		}
		
		
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
		JLabel lblDestinatariManutenzione = new JLabel("DESTINATARI MANUTENZIONE");
		lblDestinatariManutenzione.setFont(new Font("Arial", Font.BOLD, 14));
		panelMail.add(lblDestinatariManutenzione, "cell 0 9,alignx trailing");
		
		textField_destinatari_manutenzione = new JTextField();
		textField_destinatari_manutenzione.setText("");
		textField_destinatari_manutenzione.setColumns(10);
		panelMail.add(textField_destinatari_manutenzione, "cell 1 9 2 1,growx");
		
		
		textField_destinatari_pre.setText(Costanti.DEST_MAIL_PRE);
		textField_destinatari_alarm.setText(Costanti.DEST_MAIL_ALARM);
		textField_destinatari_manutenzione.setText(Costanti.DEST_MAIL_MAN);
		
		JLabel lblSeparareI = new JLabel("* separare i nuemri con \";\" ed anteporre al numero 39");
		lblSeparareI.setFont(new Font("Arial", Font.BOLD, 12));
		panelMail.add(lblSeparareI, "cell 1 11 2 1,aligny bottom");
		
		JCheckBox chckbxInvioSms = new JCheckBox("INVIO SMS");
		chckbxInvioSms.setFont(new Font("Arial", Font.BOLD, 12));
		chckbxInvioSms.setBackground(Color.WHITE);
		panelMail.add(chckbxInvioSms, "cell 0 12,alignx right");
	
		if(Costanti.FLAG_SMS==1) 
		{
			chckbxInvioSms.setSelected(true);
		}
		else 
		{
			chckbxInvioSms.setSelected(false);
		}
		textField_lista_sms = new JTextField();
		textField_lista_sms.setText("");
		textField_lista_sms.setColumns(10);
		panelMail.add(textField_lista_sms, "cell 1 12 2 1,growx");
	
		if(Costanti.NUMBER_SMS!=null)
		{
			textField_lista_sms.setText(Costanti.NUMBER_SMS);
		}
		getContentPane().setLayout(new MigLayout("", "[634px,grow]", "[561px,grow][15px:n][60px:n]"));
		
		getContentPane().add(tabbedPane, "cell 0 0,grow");
		
		JLabel lblIParametri = new JLabel("* I parametri verranno applicati solo al prossimo riavvio");
		getContentPane().add(lblIParametri, "cell 0 1");
		lblIParametri.setFont(new Font("Arial", Font.BOLD, 12));
		
		JButton btnSalva = new JButton("SALVA");
		getContentPane().add(btnSalva, "flowx,cell 0 2,alignx center");
		btnSalva.setIcon(new ImageIcon(FrameParametri.class.getResource("/image/save.png")));
		btnSalva.setFont(new Font("Arial", Font.BOLD, 14));
		
		JButton btnAnnulla = new JButton("ANNULLA");
		getContentPane().add(btnAnnulla, "cell 0 2");
		btnAnnulla.setIcon(new ImageIcon(FrameParametri.class.getResource("/image/abort.png")));
		btnAnnulla.setFont(new Font("Arial", Font.BOLD, 14));
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
				
				/*Punti detritici 5-9-12-15*/
				
				if(textField_det_5_sec.getText().length()==0 || controllaNumero(textField_det_5_sec.getText())==false) 
				{
					textField_det_5_sec.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_det_5_sec.setBackground(Color.white);
				}
				
				if(textField_det_9_sec.getText().length()==0 || controllaNumero(textField_det_9_sec.getText())==false) 
				{
					textField_det_9_sec.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_det_9_sec.setBackground(Color.white);
				}
				
				if(textField_det_12_sec.getText().length()==0 || controllaNumero(textField_det_12_sec.getText())==false) 
				{
					textField_det_12_sec.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_det_12_sec.setBackground(Color.white);
				}
				
				if(textField_det_15_sec.getText().length()==0 || controllaNumero(textField_det_15_sec.getText())==false) 
				{
					textField_det_15_sec.setBackground(Color.red);
					save =false;
				}
				else 
				{
					textField_det_15_sec.setBackground(Color.white);
				}
				
				if(save) 
				{
					ParamDTO param = new ParamDTO();
					param.setPORT(textField.getText());
					param.setFRAMERATE(Integer.parseInt(textField_1.getText()));
					
					if(comboBox.getSelectedIndex()==0) 
					{
						param.setDEBUG("1");
					}else 
					{
						param.setDEBUG("0");
					}
					
					param.setLIMITE_MIN_P1(Double.parseDouble(textField_min_p1.getText()));
					param.setLIMITE_MAX_P1(Double.parseDouble(textField_max_p1.getText()));
					param.setITERAZIONI_P1(Integer.parseInt(textField_iter_p1.getText()));
					
					param.setLIMITE_MIN_P2(Double.parseDouble(textField_min_p2.getText()));
					param.setLIMITE_MAX_P2(Double.parseDouble(textField_max_p2.getText()));
					param.setITERAZIONI_P2(Integer.parseInt(textField_iter_p2.getText()));
					
					param.setLIMITE_MIN_P3(Double.parseDouble(textField_min_p3.getText()));
					param.setLIMITE_MAX_P3(Double.parseDouble(textField_max_p3.getText()));
					param.setITERAZIONI_P3(Integer.parseInt(textField_iter_p3.getText()));
					
					param.setPUNTI_DET_5_SEC(Integer.parseInt(textField_det_5_sec.getText()));
					param.setPUNTI_DET_9_SEC(Integer.parseInt(textField_det_9_sec.getText()));
					param.setPUNTI_DET_12_SEC(Integer.parseInt(textField_det_12_sec.getText()));
					param.setPUNTI_DET_15_SEC(Integer.parseInt(textField_det_15_sec.getText()));
					
					param.setHOST_NAME_MAIL(textField_hostname.getText());
					param.setUSERNAME_MAIL(textField_username.getText());
					param.setPASSWORD_MAIL(textField_password.getText());
					param.setSMTP_AUTH(comboBox_smtp_auth.getSelectedItem().toString());
					param.setPORT_MAIL(textField_smtp_port.getText());
					param.setSSL(comboBox_ssl.getSelectedItem().toString());
					param.setDEST_MAIL_PRE(textField_destinatari_pre.getText());
					param.setDEST_MAIL_ALARM(textField_destinatari_alarm.getText());
					param.setDEST_MAIL_MAN(textField_destinatari_manutenzione.getText());
					
					if(chckbxInvioSms.isSelected()) 
					{
						param.setFLAG_SMS(1);
						Costanti.FLAG_SMS=1;
					}else 
					{
						param.setFLAG_SMS(0);
						Costanti.FLAG_SMS=0;
					}
					
					param.setNUMBER_SMS(textField_lista_sms.getText());
					
					Costanti.PORT=param.getPORT();
					Costanti.FRAMERATE=param.getFRAMERATE();
					if(param.getDEBUG().equals("1")) 
					{
						Costanti.DEBUG=true;
					}
					else 
					{
						Costanti.DEBUG=false;
					}
	
					
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
					Costanti.DEST_MAIL_MAN=param.getDEST_MAIL_MAN();
					Costanti.NUMBER_SMS=param.getNUMBER_SMS();
					
					
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
