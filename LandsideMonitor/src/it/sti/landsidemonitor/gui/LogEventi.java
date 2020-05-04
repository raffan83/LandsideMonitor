package it.sti.landsidemonitor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import org.apache.log4j.Logger;


import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.PortReader;
import it.sti.landsidemonitor.bo.Utility;
import it.sti.landsidemonitor.dto.SensorDTO;
import net.miginfocom.swing.MigLayout;

public class LogEventi extends JFrame{

	JTable tabellaEventi;
	ModelTabella model;
	JFrame frm;
	
	final static Logger logger = Logger.getLogger(LogEventi.class);
	
	public LogEventi(SensorDTO sensor, RasterPanel mainP) throws SQLException {
	
		setSize(1200,600);
		setTitle("Log eventi sonda "+sensor.getIdentifier());
		frm=this;
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - 1200) / 2;
		int y = (dim.height - 600) / 2;
		
		setLocation(x, y);
		
		model = new ModelTabella();
		
		ArrayList<String> listaEventi=Core.getListaEventi(sensor.getIdentifier());
		
		for (int i=0;i<listaEventi.size();i++) 
		{
			
			String[]evento=listaEventi.get(i).split(";");
			
			model.addRow(new Object[0]);
			model.setValueAt(evento[0],i,0);
			model.setValueAt(Utility.getLabelStato(Integer.parseInt(evento[1])),i,1);
			model.setValueAt(evento[2],i,2);
			model.setValueAt(evento[3],i,3);
			model.setValueAt(evento[4],i,4);
			model.setValueAt(evento[5],i,5);
			model.setValueAt(evento[6],i,6);
		}
		
		tabellaEventi= new JTable(model);
		tabellaEventi.setDefaultRenderer(Object.class, new MyCellRenderer());
		
		
		tabellaEventi.setFont(new Font("Arial", Font.BOLD, 14));
		tabellaEventi.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
		tabellaEventi.setRowHeight(35);
		tabellaEventi.getColumnModel().getColumn(0).setPreferredWidth(200);
		getContentPane().setLayout(new MigLayout("", "grow", "[47.00][561px]"));
		
		JButton btnCancellaLog = new JButton("Cancella Log");
		btnCancellaLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Core.cancellaLog(sensor.getIdentifier());
					dispose();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnCancellaLog.setIcon(new ImageIcon(LogEventi.class.getResource("/image/error.png")));
		btnCancellaLog.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(btnCancellaLog, "flowx,cell 0 0");
		
		JScrollPane scroll = new JScrollPane(tabellaEventi);
		
		getContentPane().add(scroll, "cell 0 1,grow");
		
		JButton btnScaricaCvs = new JButton("Export CVS");
		btnScaricaCvs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			

				try {

						JFileChooser jfc = new JFileChooser();
						jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						jfc.showSaveDialog(frm);

						File f= jfc.getSelectedFile();
						if(f!=null)
						{
							FileOutputStream fos = new FileOutputStream(new File(f.getAbsoluteFile()+"\\"+"Datalog_sensor_"+sensor.getIdentifier()+".csv"));
							PrintStream ps = new PrintStream(fos);
							
							ps.println("Data;Stato;codice;Descrizione;Asse X;Asse Y;Asse Z");
							
							for (String evt : listaEventi) {
								String[]evento=evt.split(";");
								
								String evtLog="";

								evtLog=evento[0]+";";
								evtLog=evtLog+Utility.getLabelStato(Integer.parseInt(evento[1]))+";";
								evtLog=evtLog+evento[2]+";";
								evtLog=evtLog+evento[3]+";";
								evtLog=evtLog+evento[4]+";";
								evtLog=evtLog+evento[5]+";";
								evtLog=evtLog+evento[6];
								
								ps.println(evtLog);
							}
							ps.close();
							fos.close();
						}

					
				} catch (Exception ec) {

					logger.error(ec);
				}
				
			}
		});
		btnScaricaCvs.setIcon(new ImageIcon(LogEventi.class.getResource("/image/load.png")));
		btnScaricaCvs.setFont(new Font("Arial", Font.BOLD, 14));
		getContentPane().add(btnScaricaCvs, "cell 0 0");
	}

	class ModelTabella extends DefaultTableModel {

		ArrayList<SensorDTO> listaSnd=null;

		public ModelTabella() {
			
			addColumn("Data");
			addColumn("Stato");
			addColumn("Codice");
			addColumn("Descrizione");
			addColumn("Asse X");
			addColumn("Asse Y");
			addColumn("Asse Z");
			

		}
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			case 4:
				return String.class;
			case 5:
				return String.class;
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
	}
	
	public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
		
	

        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        	final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row % 2 == 0) 
            {
                cellComponent.setForeground(Color.black);
                cellComponent.setBackground(Color.white);

            }
            else
            {
            	cellComponent.setForeground(Color.black);
                cellComponent.setBackground(new Color(224,224,224));
                
            }
            
            int code=Integer.parseInt(table.getValueAt(row, 2).toString());
            
            if(code>=1 && code <=4 ) 
            {
         	   cellComponent.setForeground(Color.black);
                cellComponent.setBackground(Color.ORANGE);
            }
            
           if(code==5) 
           {
        	   cellComponent.setForeground(Color.black);
               cellComponent.setBackground(Color.RED);
           }
   
        return cellComponent;

        }

    }
}
