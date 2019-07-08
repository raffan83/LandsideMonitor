package it.sti.landsidemonitor.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Costanti;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.Utility;
import it.sti.landsidemonitor.dto.SensorDTO;
import jssc.SerialPortException;
import net.miginfocom.swing.MigLayout;

public class FrameSonde extends JFrame implements ActionListener{
	
	
	RasterPanel mainP=null;
	
	static JPanel mainPanel;
	ArrayList<SensorDTO> listaSonde=null;
	JTable tabellaSonde;
	ModelSonde modelSonde;
	JPopupMenu popupMenu;
	JMenuItem jmit;
	PortReader pr;
	
	public FrameSonde(RasterPanel mainPan,PortReader _pr) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException 
	{
	mainP=mainPan;
	pr=_pr;
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	int x = (dim.width - 1000) / 2;
	int y = (dim.height - 600) / 2;
	setLocation(x, y);
	
	
	setTitle("Sonde");
	
	
	//setResizable(false);
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	//setSize(width,height+20);

	setSize(1000, 600);
	
	mainPanel=new JPanel();
	
	
	getContentPane().add(mainPanel);
	mainPanel.setLayout(new MigLayout("", "[grow][grow]", "[][grow]"));
	
	JButton btnAggiungiSonda = new JButton("Aggiungi Sonda");
	btnAggiungiSonda.setIcon(new ImageIcon(FrameSonde.class.getResource("/image/add.png")));
	btnAggiungiSonda.setFont(new Font("Arial",Font.BOLD,14));
	mainPanel.add(btnAggiungiSonda, "flowx,cell 0 0");
	

	btnAggiungiSonda.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			SwingUtilities.invokeLater(new Runnable(){
	            public void run() 
	            {
	            	try
	            	{
	            	JFrame f=new AddSensor();
	            	
	            	f.setDefaultCloseOperation(1);
	      	        f.setVisible(true);
	      	        
	      	        }
	            	catch(Exception ex)
	      	        {
					//	GeneralGUI.printException(ex);
	      	        	ex.printStackTrace();
	      	        }
	            }

	        });
			
		}
	});
	
	JScrollPane scrollPane = new JScrollPane();
	mainPanel.add(scrollPane, "cell 0 1 2 1,grow");
	
	JPanel panel = new JPanel();
	scrollPane.setViewportView(panel);
	
	
	panel.setLayout(new MigLayout("", "[grow]", "[grow]"));
	
	try {
		listaSonde=Core.getListaSensori();
		
		 tabellaSonde = new JTable();
			
		
			modelSonde = new ModelSonde(listaSonde);

			SensorDTO sensor=null;
			for (int i = 0; i <listaSonde.size(); i++) {
				
				sensor= listaSonde.get(i);
				modelSonde.addRow(new Object[0]);
				modelSonde.setValueAt(sensor.getIdentifier(), i, 0);
				modelSonde.setValueAt(Utility.getLabelStato(sensor.getStato()), i, 1);
				modelSonde.setValueAt(new JButton(), i, 2);
				modelSonde.setValueAt(new JButton(), i, 3);
				modelSonde.setValueAt(new JButton(), i, 4);
				modelSonde.setValueAt(new JButton(), i, 5);
				modelSonde.setValueAt(sensor.getId(), i, 6);
				
			}

			tabellaSonde.setModel(modelSonde);
			tabellaSonde.setFont(new Font("Arial", Font.BOLD, 14));
			tabellaSonde.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
			tabellaSonde.setRowHeight(35);
			
			tabellaSonde.addMouseListener(new JTableButtonMouseListener(tabellaSonde));
			TableColumn column = tabellaSonde.getColumnModel().getColumn(tabellaSonde.getColumnModel().getColumnIndex("id"));
			tabellaSonde.removeColumn(column);
		
			column = tabellaSonde.getColumnModel().getColumn(tabellaSonde.getColumnModel().getColumnIndex("Reset"));
			tabellaSonde.removeColumn(column);
			//tabellaSonde.setDefaultRenderer(Object.class, new MyCellRenderer());
			
			TableCellRenderer buttonRenderer = new JTableButtonRenderer(1); 
			tabellaSonde.getColumn("Posizione").setCellRenderer(buttonRenderer);
			
			buttonRenderer = new JTableButtonRenderer(2); 
			tabellaSonde.getColumn("Grafico").setCellRenderer(buttonRenderer);
			
			buttonRenderer = new JTableButtonRenderer(3); 
			tabellaSonde.getColumn("Log Eventi").setCellRenderer(buttonRenderer);
			
		//	buttonRenderer = new JTableButtonRenderer(4); 
		//	tabellaSonde.getColumn("Reset").setCellRenderer(buttonRenderer);
			
			tabellaSonde.addMouseMotionListener(new MouseMotionListener() {
				
				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			popupMenu= new JPopupMenu();
			jmit= new JMenuItem("Elimina Sonda");
			jmit.addActionListener(this);
			popupMenu.add(jmit);
			tabellaSonde.setComponentPopupMenu(popupMenu);
			
			
			JScrollPane scrollTab = new JScrollPane(tabellaSonde);
			panel.add(scrollTab, "grow");
			
			JButton btnNewButton = new JButton("Reset");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
               
			for (int i=0;i<modelSonde.getRowCount();i++) 
			{		
				String idSonda = modelSonde.getValueAt(i, 6).toString();
            	 
               	 mainP.cambiaStato(Integer.parseInt(idSonda), 0);
               	 mainP.cambiaStatoOriginale(Integer.parseInt(idSonda), 0);
               	 
               	 modelSonde.setValueAt("ATTIVA", i, 2);
               	 
               	 tabellaSonde.repaint();
               	 try {
               		SensorDTO sensor=mainP.getSonda(idSonda);
               		sensor.getSensor().setIterazioni_preallarme_1(0);
               		sensor.getSensor().setIterazioni_preallarme_2(0);
               		sensor.getSensor().setIterazioni_preallarme_3(0);
               		Core.cambiaStato(sensor.getId(), 0); 
						pr.write("R");
						
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
               	 
					
				}
		//	dispose();
			}
			});
			btnNewButton.setIcon(new ImageIcon(FrameSonde.class.getResource("/image/update.png")));
			btnNewButton.setFont(new Font("Arial", Font.BOLD, 14));
			mainPanel.add(btnNewButton, "cell 0 0");
			
			
			
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
	}
	
	class ModelSonde extends DefaultTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		ArrayList<SensorDTO> listaSnd=null;

		public ModelSonde(ArrayList<SensorDTO> _listaSnd) {
			
			listaSnd=_listaSnd;
			addColumn("Identificativo");
			addColumn("Stato");
			addColumn("Posizione");
			addColumn("Grafico");
			addColumn("Log Eventi");
			addColumn("Reset");
			addColumn("id");

		}
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return JButton.class;
			case 3:
				return JButton.class;
			case 4:
				return JButton.class;
			case 5:
				return JButton.class;
			case 6:
				return String.class;
			default:
				return String.class;
			}
		}

		public int getColumnCount() {
			return 7;
		}
		
		@Override
		   public boolean isCellEditable(int row, int column) {
    
                return false;
           
        }
	
		 public Object getValueAt(final int rowIndex, final int columnIndex) {
          
			 
			 
         switch (columnIndex) {
             case 0: return listaSnd.get(rowIndex).getIdentifier();         
             
             case 1: return Utility.getLabelStato(listaSnd.get(rowIndex).getStato());
             
             case 2:  final JButton button = new JButton("Posizione");
             button.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent arg0) {
                     
                	 String idSonda = modelSonde.getValueAt(rowIndex, 6).toString();
                	 
                	 SwingUtilities.invokeLater(new Runnable(){
         	            public void run() 
         	            {
         	            	try
         	            	{
         	            		
         	            	SensorDTO sensor=mainP.getSonda(idSonda);
         	            	
         	            	MoveSensor f=new MoveSensor(sensor,mainP);
         	         
         	            	
         	            	f.setDefaultCloseOperation(1);
         	      	        f.setVisible(true);
         	      	        
         	      	        }
         	            	catch(Exception ex)
         	      	        {
         					//	GeneralGUI.printException(ex);
         	      	        	ex.printStackTrace();
         	      	        }
         	            }

         	        });
                 }
             });
             return button;
            /*Adding button and creating click listener*/
             case 3: final JButton button1 = new JButton("Grafico");
                     button1.addActionListener(new ActionListener() {
                         public void actionPerformed(ActionEvent arg0) {
                           
                        	 String idSonda = modelSonde.getValueAt(rowIndex, 6).toString();
                        	 
                        	 SwingUtilities.invokeLater(new Runnable(){
                 	            public void run() 
                 	            {
                 	            	try
                 	            	{
                 	            		
                 	            	SensorDTO sensor=mainP.getSonda(idSonda);
                 	            	
                 	            	GraphSensor f=new GraphSensor(sensor,mainP);

                 	            	f.setDefaultCloseOperation(1);
                 	      	        f.setVisible(true);
                 	      	        
                 	      	        }
                 	            	catch(Exception ex)
                 	      	        {
                 					//	GeneralGUI.printException(ex);
                 	      	        	ex.printStackTrace();
                 	      	        }
                 	            }

                 	        });
                        	 
                         }
                     });
                     return button1;
             case 4: final JButton button2 = new JButton("Log Eventi");
             button2.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent arg0) {
                	 
                   	 String idSonda = modelSonde.getValueAt(rowIndex, 6).toString();
                	 
                	 SwingUtilities.invokeLater(new Runnable(){
         	            public void run() 
         	            {
         	            	try
         	            	{
         	            		
         	            	SensorDTO sensor=mainP.getSonda(idSonda);
         	            	
         	            	LogEventi f=new LogEventi(sensor,mainP);

         	            	f.setDefaultCloseOperation(1);
         	      	        f.setVisible(true);
         	      	        
         	      	        }
         	            	catch(Exception ex)
         	      	        {
         					//	GeneralGUI.printException(ex);
         	      	        	ex.printStackTrace();
         	      	        }
         	            }

         	        });
                 }
             });
             return button2;
             case 5: final JButton button3 = new JButton("Reset Stato");
             button3.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent arg0) {
                    
                	 String idSonda = modelSonde.getValueAt(rowIndex, 6).toString();
                	 
                	 mainP.cambiaStato(Integer.parseInt(idSonda), 0);
                	 mainP.cambiaStatoOriginale(Integer.parseInt(idSonda), 0);
                	 
                	 try {
                		SensorDTO sensor=mainP.getSonda(idSonda);                		 
                		Core.cambiaStato(sensor.getId(), 0); 
 						pr.write("R");
 						
 					} catch (Exception e) {
 						// TODO Auto-generated catch block
 						e.printStackTrace();
 					}
                 	 
                	 
                 }
             });
             return button3;
             
             case 6: return ""+listaSnd.get(rowIndex).getId();
             
                     
             default: return "Error";
         }

		 }
	}
	
	private static class JTableButtonRenderer implements TableCellRenderer {    
		
		int bottonType=0;
        public JTableButtonRenderer(int i) {
			bottonType=i;
		}

		@Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = (JButton)value;
            button.setFont(new Font("Arial",Font.BOLD,13));
            if(bottonType==1) 
            {
            	button.setIcon(new ImageIcon(FrameSonde.class.getResource("/image/as_l_f.png")));
            }
            if(bottonType==2) 
            {
            	button.setIcon(new ImageIcon(FrameSonde.class.getResource("/image/graf1.png")));
            }
            if(bottonType==3) 
            {
            	button.setIcon(new ImageIcon(FrameSonde.class.getResource("/image/check.png")));
            }
            if(bottonType==4) 
            {
            	button.setIcon(new ImageIcon(FrameSonde.class.getResource("/image/update.png")));
            }
            
            return button;  
        }
    }
	
	private static class JTableButtonMouseListener extends MouseAdapter {
        private final JTable table;

        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        public void mouseClicked(MouseEvent e) {
            int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
            int row    = e.getY()/table.getRowHeight(); //get the row of the button

                    /*Checking the row or column is valid or not*/
            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);
                if (value instanceof JButton) {
                    /*perform a click event*/
                    ((JButton)value).doClick();
                }
            }
        }
    }

	@Override
	public void actionPerformed(ActionEvent event) {
	
		JMenuItem menu = (JMenuItem) event.getSource();
		if (menu == jmit) {
            eliminaSonda();
        }
		
	}

	private void eliminaSonda() {
		
		try
		{
			int selectedRow = tabellaSonde.getSelectedRow();
			if(selectedRow!=-1)
			{
				String idSonda=""+modelSonde.getValueAt(selectedRow, 6);
				
				Core.removeSensor(idSonda);
				
				listaSonde.remove(selectedRow);
				modelSonde.removeRow(selectedRow);
				mainP.removePoint(idSonda);
				System.out.println("id Sonda"+ idSonda);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Selezionare correttamente la riga della sonda da eliminare strumento da duplicare","Attenzione",JOptionPane.WARNING_MESSAGE);
			}
		}catch (Exception e) 
		{
		e.printStackTrace();
		}	
	}
	
	public class AddSensor extends JFrame
	{
		public AddSensor() 
		{
			setSize(400,300);
			setTitle("Aggiungi Sonda");

			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (dim.width - 400) / 2;
			int y = (dim.height - 300) / 2;
			
			setLocation(x, y);
			
			JPanel panel = new JPanel();
			
			getContentPane().add(panel);
			
			panel.setSize(400,300);
			
			panel.setLayout(new MigLayout("", "[][grow][grow]", "[30px][][35px][35][35][35][][][grow]"));

			JLabel lblInserireIDati = new JLabel("Inserire i dati primari Sonda:");
			lblInserireIDati.setFont(new Font("Arial", Font.BOLD, 15));
			panel.add(lblInserireIDati, "cell 0 0");
			
			JLabel lblCodiceOperatore = new JLabel("Id Sonda:");
			lblCodiceOperatore.setFont(new Font("Arial", Font.BOLD, 14));
			panel.add(lblCodiceOperatore, "cell 0 1,alignx trailing");
			
			JTextField tf_id_sonda = new JTextField(10);
			tf_id_sonda.setFont(new Font("Arial", Font.PLAIN, 14));
			panel.add(tf_id_sonda, "cell 1 1,wmax 100");
			tf_id_sonda.setColumns(10);
			
			JLabel lblContatore = new JLabel("Posizione X:");
			lblContatore.setFont(new Font("Arial", Font.BOLD, 14));
			panel.add(lblContatore, "cell 0 2,alignx trailing");
			
			JTextField tf_position_x = new JTextField();
			tf_position_x.setFont(new Font("Arial", Font.PLAIN, 14));
			panel.add(tf_position_x, "cell 1 2,wmax 100");
			tf_position_x.setColumns(10);
			
			JLabel lblDasmPort = new JLabel("Posizione Y:");
			lblDasmPort.setFont(new Font("Arial", Font.BOLD, 14));
			panel.add(lblDasmPort, "cell 0 3,alignx trailing");
			
			JTextField tf_position_y = new JTextField();
			tf_position_y.setFont(new Font("Arial", Font.PLAIN, 14));
			panel.add(tf_position_y, "cell 1 3,wmax 100");
			tf_position_y.setColumns(10);
					
			JButton btnInserisci = new JButton("Inserisci");
			btnInserisci.setFont(new Font("Arial", Font.BOLD, 14));
			
			btnInserisci.setIcon(new ImageIcon(FrameSonde.class.getResource("/image/continue.png")));
			
			btnInserisci.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) 
				{
					
					try 
					{
					boolean check=true;
					boolean save=true;
					
					if(tf_id_sonda.getText().length()==0) 
					{
						tf_id_sonda.setBackground(Color.red);
						check=false;
					}else 
					{
						tf_id_sonda.setBackground(Color.WHITE);
					}
					
					if(tf_position_x.getText().length()<=0 || !controllaNumero(tf_position_x.getText())) 
					 {
						 tf_position_x.setBackground(Color.red);
						 check=false;
					 }
					else 
					{
						tf_position_x.setBackground(Color.WHITE);
					}
					
					if(tf_position_y.getText().length()<=0 || !controllaNumero(tf_position_y.getText())) 
					 {
						 tf_position_y.setBackground(Color.red);
						 check=false;
					 }
					else 
					{
						tf_position_y.setBackground(Color.WHITE);
					}
					
						 
						if(check) 
						{ 
								int x=Integer.parseInt(tf_position_x.getText());
								int y=Integer.parseInt(tf_position_y.getText());
								
								
								if(x < 0 || x > Costanti.SCREEN_X)  
								{
									JOptionPane.showMessageDialog(null, "Asse X fuori range [0 - "+Costanti.SCREEN_X+"]","Out of Range",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(MainFrame.class.getResource("/image/error.png")));
									save=false;
								}
								
								if(y<0 || y>Costanti.SCREEN_Y) 
								{
									JOptionPane.showMessageDialog(null, "Asse Y fuori range [0 - "+Costanti.SCREEN_Y+"]","Out of Range",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(MainFrame.class.getResource("/image/error.png")));
									save=false;
								}
								
								
								if (save) 
								{
								SensorDTO s = new SensorDTO();
								
								int id =Core.aggiungiSensore(tf_id_sonda.getText(),x,y);
								
								s.setId(id);
								s.setIdentifier(tf_id_sonda.getText());
								Point p = new Point(x,y);
								s.setPoint(p);
								s.setStato(0);
								
								listaSonde.add(s);
								int i=modelSonde.getRowCount();
								modelSonde.addRow(new Object[0]);
								modelSonde.setValueAt(s.getIdentifier(), i, 0);
								modelSonde.setValueAt(s.getStato(), i, 1);
								modelSonde.setValueAt(new JButton(), i, 2);
								modelSonde.setValueAt(new JButton(), i, 3);
								modelSonde.setValueAt(new JButton(), i, 4);
								modelSonde.setValueAt(new JButton(), i, 5);
								modelSonde.setValueAt(s.getId(), i, 6);
								dispose();
								
		
								mainP.addPoint(s);
						}	
					   }
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
		
			});
			
			panel.add(btnInserisci, "cell 0 7 3 1,alignx center");
		}
		
		private boolean controllaNumero(String text) {
			
			try 
			{
				Integer.parseInt(text);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}
	
	public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {


		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			try
			{


				if(column==1)
				{

					String stato=table.getValueAt(row, 1).toString();
					
					if( stato.equals("ATTIVA"))
					{
						cellComponent.setForeground(Color.black);
					
					}
					if(stato.equals("ALLARME"))
					{
						cellComponent.setForeground(Color.black);
						cellComponent.setBackground(Color.RED);
					}
					if(stato.equals("PRE ALLARME"))
					{
						cellComponent.setForeground(Color.black);
						cellComponent.setBackground(Color.ORANGE);
					}
					if(stato.equals("NON ATTIVA"));
					{
						cellComponent.setForeground(Color.white);
						cellComponent.setBackground(Color.black);

					}

				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return cellComponent;

		}
	}
}
