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

public class LogEventi extends JFrame{

	JTable tabellaEventi;
	ModelTabella model;
	
	public LogEventi(SensorDTO sensor, RasterPanel mainP) throws SQLException {
	
		setSize(800,600);
		setTitle("Log eventi sonda "+sensor.getIdentifier());
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - 800) / 2;
		int y = (dim.height - 600) / 2;
		
		setLocation(x, y);
		
		model = new ModelTabella();
		
		ArrayList<String> listaEventi=Core.getListaEventi(sensor.getIdentifier());
		
		for (int i=0;i<listaEventi.size();i++) 
		{
			
			String[]eventi=listaEventi.get(i).split(";");
			
			model.addRow(new Object[0]);
			model.setValueAt(eventi[0],i,0);
			model.setValueAt(Utility.getLabelStato(Integer.parseInt(eventi[1])),i,1);
			model.setValueAt(eventi[2],i,2);
			model.setValueAt(eventi[3],i,3);
			model.setValueAt(eventi[4],i,4);
		}
		
		tabellaEventi= new JTable(model);
		tabellaEventi.setDefaultRenderer(Object.class, new MyCellRenderer());
		
		
		tabellaEventi.setFont(new Font("Arial", Font.BOLD, 14));
		tabellaEventi.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
		tabellaEventi.setRowHeight(35);
		tabellaEventi.getColumnModel().getColumn(0).setPreferredWidth(200);
		
		JScrollPane scroll = new JScrollPane(tabellaEventi);
		
		add(scroll);
	}

	class ModelTabella extends DefaultTableModel {

		ArrayList<SensorDTO> listaSnd=null;

		public ModelTabella() {
			
			addColumn("Data");
			addColumn("Stato");
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
				
			default:
				return String.class;
			}
		}

		public int getColumnCount() {
			return 5;
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
   
        return cellComponent;

        }

		private ArrayList<Boolean> getListaControlloRipetibilita(int punti, int ripetizioni) {
			
			boolean flag=false;
			ArrayList<Boolean> lista=new ArrayList<Boolean>();

			for (int i = 1; i <= ripetizioni; i++) {
				
				if(i % 2 == 0)
				{
					flag=false;
				}
				else
				{
					flag=true;
				}
				for (int j = 1; j <= punti; j++) 
				{
					lista.add(flag);
				}
			
			}
			return lista;
		}

    }
}
