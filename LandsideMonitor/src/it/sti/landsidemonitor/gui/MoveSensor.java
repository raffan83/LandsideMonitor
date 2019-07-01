package it.sti.landsidemonitor.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.dto.SensorDTO;
import net.miginfocom.swing.MigLayout;
import javax.swing.ImageIcon;
import java.awt.Font;

public class MoveSensor extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	SensorDTO sonda;
	RasterPanel mainP;

	public MoveSensor(SensorDTO _sonda, RasterPanel _mainP) 
	{
		mainP=_mainP;
		sonda=_sonda;
		setResizable(false);

		setTitle("Posizione Sonda "+sonda.getIdentifier());
		setSize(400, 400);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - 500) / 2;
		int y = (dim.height - 500) / 2;

		setLocation(x, y);


		getContentPane().setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[][22px][][100px][][]"));

		JButton btnSinistra = new JButton("");
		btnSinistra.setIcon(new ImageIcon(MoveSensor.class.getResource("/image/arrow_left.png")));
		JButton btnSopra = new JButton("");
		btnSopra.setIcon(new ImageIcon(MoveSensor.class.getResource("/image/arrow_up.png")));

		JLabel lblXPos = new JLabel("X POS:");
		lblXPos.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblXPos, "cell 0 0,alignx right");


		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		textField.setEditable(false);
		textField.setEnabled(false);
		getContentPane().add(textField, "cell 1 0,width :75:,alignx left");
		textField.setColumns(10);

		textField.setText(""+(int)sonda.getPoint().getX());


		JLabel lblYPos = new JLabel("Y POS:");
		lblYPos.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(lblYPos, "cell 3 0,alignx right");

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		textField_1.setEnabled(false);
		textField_1.setEditable(false);
		getContentPane().add(textField_1, "cell 4 0,width :75:,alignx left");
		textField_1.setColumns(10);

		textField_1.setText(""+(int)sonda.getPoint().getY());

		JButton btnSopra_db = new JButton("");
		btnSopra_db.setIcon(new ImageIcon(MoveSensor.class.getResource("/image/db_arrow_up.png")));
		getContentPane().add(btnSopra_db, "cell 2 1,alignx center,aligny center");
		getContentPane().add(btnSopra, "cell 2 2,alignx center,aligny center");

		JButton btnSinistra_db = new JButton("");
		btnSinistra_db.setIcon(new ImageIcon(MoveSensor.class.getResource("/image/db_arrow_left.png")));
		getContentPane().add(btnSinistra_db, "cell 0 3,alignx center,aligny center");

		JButton btnDestra = new JButton("");
		btnDestra.setIcon(new ImageIcon(MoveSensor.class.getResource("/image/arrow_right.png")));

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MoveSensor.class.getResource("/image/sensor.png")));
		getContentPane().add(label, "cell 2 3");

		JButton btnDestra_db = new JButton("");
		btnDestra_db.setIcon(new ImageIcon(MoveSensor.class.getResource("/image/db_arrow_right.png")));
		getContentPane().add(btnDestra_db, "cell 4 3,alignx center,aligny center");

		JButton btnSotto = new JButton("");
		btnSotto.setIcon(new ImageIcon(MoveSensor.class.getResource("/image/arrow_down.png")));

		JButton btnSotto_db = new JButton("");
		btnSotto_db.setIcon(new ImageIcon(MoveSensor.class.getResource("/image/db_arrow_down.png")));

		/*BOTTONE SOPRA*/

		btnSopra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int y=Integer.parseInt(textField_1.getText())-1;

				if(y>=0) 
				{
					textField_1.setText(""+y);
					mainP.translate(""+sonda.getId(), 0, -1);

					try {
						Core.updatePosition(sonda,textField.getText(),textField_1.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}	
			}
		});

		btnSopra_db.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				int y=Integer.parseInt(textField_1.getText())-10;
				if(y>=0) 
				{
					textField_1.setText(""+y);

					mainP.translate(""+sonda.getId(), 0, -10);

					try {
						Core.updatePosition(sonda,textField.getText(),textField_1.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});



		/*BOTTONE SOTTO */

		btnSotto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int y=Integer.parseInt(textField_1.getText())+1;

				if(y<=Costanti.SCREEN_Y) {
					textField_1.setText(""+y);	
					mainP.translate(""+sonda.getId(), 0, 1);

					try {
						Core.updatePosition(sonda,textField.getText(),textField_1.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		btnSotto_db.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int y=Integer.parseInt(textField_1.getText())+10;

				if(y<=Costanti.SCREEN_Y) {
					textField_1.setText(""+y);

					mainP.translate(""+sonda.getId(), 0, 10);

					try {
						Core.updatePosition(sonda,textField.getText(),textField_1.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		btnSinistra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int x=Integer.parseInt(textField.getText())-1;

				if(x>=0) 
				{
					textField.setText(""+x);

					mainP.translate(""+sonda.getId(), -1, 0);

					try {
						Core.updatePosition(sonda,textField.getText(),textField_1.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		btnSinistra_db.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int x=Integer.parseInt(textField.getText())-10;

				if(x>=0) 
				{
					textField.setText(""+x);
					mainP.translate(""+sonda.getId(), -10, 0);

					try {
						Core.updatePosition(sonda,textField.getText(),textField_1.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		btnDestra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int x=Integer.parseInt(textField.getText())+1;

				if(x<=Costanti.SCREEN_X) 
				{
					textField.setText(""+x);

					mainP.translate(""+sonda.getId(), 1, 0);

					try {
						Core.updatePosition(sonda,textField.getText(),textField_1.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});

		btnDestra_db.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int x=Integer.parseInt(textField.getText())+10;

				if(x<=Costanti.SCREEN_X) 
				{
					textField.setText(""+x);

					mainP.translate(""+sonda.getId(), 10, 0);

					try {
						Core.updatePosition(sonda,textField.getText(),textField_1.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		getContentPane().add(btnSotto, "cell 2 4,alignx center,aligny center");
		getContentPane().add(btnSinistra, "cell 1 3,alignx center,aligny center");
		getContentPane().add(btnDestra, "cell 3 3,alignx center,aligny center");
		getContentPane().add(btnSotto_db, "cell 2 5,alignx center,aligny center");
	}
}
