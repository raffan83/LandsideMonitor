package it.sti.landsidemonitor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.sti.landsidemonitor.bo.Core;
import it.sti.landsidemonitor.bo.Utility;
import it.sti.landsidemonitor.dto.SensorDTO;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogEventi extends JFrame{

	JTable tabellaEventi;
	ModelTabella model;
	
	public LogEventi(SensorDTO sensor, RasterPanel mainP) throws SQLException {
	
		setSize(1200,600);
		setTitle("Log eventi sonda "+sensor.getIdentifier());
		
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
		getContentPane().add(btnCancellaLog, "cell 0 0");
		
		JScrollPane scroll = new JScrollPane(tabellaEventi);
		
		getContentPane().add(scroll, "cell 0 1,grow");
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
