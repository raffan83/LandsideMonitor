package it.sti.landsidemonitor.dao;

import java.awt.Point;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.sti.landsidemonitor.dto.ParamDTO;
import it.sti.landsidemonitor.dto.SensorDTO;

public class MainDAO {

	
	private static  Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
		
		Connection con=DriverManager.getConnection("jdbc:sqlite:persistence.db");
		
		return con;
	}
	
	public static ArrayList<SensorDTO> getListaSonde() throws SQLException {
		
		ArrayList<SensorDTO> listaSensori=new ArrayList<SensorDTO>();
		
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs= null;
		
		try {
			
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM tbl_sonde ORDER BY id ASC");
			
			rs=pst.executeQuery();
			SensorDTO sensor =null;
			
			while(rs.next()) 
			{
				int stato=rs.getInt("stato");
				sensor= new SensorDTO();
				sensor.setId(rs.getInt("id"));
				sensor.setIdentifier(rs.getString("identifier"));
				Point p = new Point(rs.getInt("pos_x"),rs.getInt("pos_y"));
				sensor.setPoint(p);
				sensor.setStato(stato);
				sensor.setStatoOriginale(stato);
				
				listaSensori.add(sensor);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{
			pst.close();
			con.close();
		}
		
		
		
		return listaSensori;
	}

	public static void removeSensor(String idSonda) throws SQLException {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try {
			
			con=getConnection();
			pst=con.prepareStatement("DELETE FROM tbl_sonde WHERE id=? ");
			pst.setInt(1, Integer.parseInt(idSonda));
			
			pst.execute();
		}catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{
			pst.close();
			con.close();
		}
		
		
	}

	public static int inserisciSonda(String id, int posX, int posY) throws Exception {
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tbl_sonde(identifier,pos_X,pos_Y,stato) VALUES(?,?,?,?)",pst.RETURN_GENERATED_KEYS);
			
			pst.setString(1,id);
			pst.setInt(2,posX);
			pst.setInt(3,posY);
			pst.setInt(4, 0);
			pst.execute();
		
		    ResultSet generatedKeys = pst.getGeneratedKeys(); 
		    	
		            if (generatedKeys.next()) {
		               return (int) generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Error insert Misura, no ID obtained.");
		            }
		        
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
	}

	public static void updatePosition(SensorDTO sonda, String x, String y) throws SQLException {
		
		
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try {
			
			con=getConnection();
			pst=con.prepareStatement("UPDATE tbl_sonde SET pos_x=? , pos_y=?  WHERE id=? ");
			pst.setInt(1, Integer.parseInt(x));
			pst.setInt(2, Integer.parseInt(y));
			pst.setInt(3, sonda.getId());
			
			pst.execute();
		}catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{
			pst.close();
			con.close();
		}
		
	}

	public static void registraEvento(String identifier, int stato, double acc_X, double acc_Y, double acc_Z) throws SQLException {
	
		
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try {
			
			con=getConnection();
			con.setAutoCommit(false);
			
		    SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy HH:mm:ss.SSS");
		    String DateToStr = format.format(new Date());
		        
			pst=con.prepareStatement("INSERT INTO tbl_eventi(id_sonda,date,stato,asse_x,asse_y,asse_z) VALUES(?,?,?,?,?,?)");
			pst.setString(1, identifier);
			pst.setString(2,DateToStr);
			pst.setInt(3,stato);
			pst.setDouble(4, acc_X);
			pst.setDouble(5, acc_Y);
			pst.setDouble(6, acc_Z);
			
			pst.execute();
			
			con.commit();
		}catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{
			pst.close();
			con.close();
		}
		
	}

	public static ArrayList<String> getListaEventi(String identifier) throws SQLException {
		
		ArrayList<String> listaEventi = new ArrayList<String>();
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		
		try {
			
			con=getConnection();		        
			pst=con.prepareStatement("SELECT * FROM tbl_eventi WHERE id_sonda=? ORDER BY id ASC");
			
			pst.setString(1, identifier);
			
			rs=pst.executeQuery();
			
			while (rs.next()) {
				
				listaEventi.add(rs.getString("date")+";"+
								rs.getString("stato")+";"+
								rs.getString("asse_x")+";"+
								rs.getString("asse_y")+";"+
								rs.getString("asse_z"));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{
			pst.close();
			con.close();
		}
		return listaEventi;
		
	}

	public static void cambiaStato(int idSonda, int stato) throws SQLException {
	
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try {
			
			con=getConnection();
			pst=con.prepareStatement("UPDATE tbl_sonde SET stato=? WHERE id=? ");
			pst.setInt(1, stato);
			pst.setInt(2, idSonda);
			
			pst.execute();
		}catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{
			pst.close();
			con.close();
		}
		
	}

	public static ParamDTO getParam() throws SQLException {
		
		ParamDTO param = new ParamDTO();
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		
		try {
			
			con=getConnection();		        
			pst=con.prepareStatement("SELECT * FROM tbl_param");
			
			rs=pst.executeQuery();
			
			while (rs.next()) {
				
				param.setPORT(rs.getString("PORT"));
				param.setFRAMERATE(rs.getInt("FRAMERATE"));
				param.setLIMIT_GRAPH_X_AXIS(rs.getDouble("LIMIT_GRAPH_X_AXIS"));
				param.setLIMIT_GRAPH_Y_AXIS(rs.getDouble("LIMIT_GRAPH_Y_AXIS"));
				param.setLIMIT_GRAPH_Z_AXIS(rs.getDouble("LIMIT_GRAPH_Z_AXIS"));
				param.setFRAMERATE_READ_GRAPH(rs.getInt("FRAMERATE_READ_GRAPH"));
				param.setDEBUG(rs.getString("DEBUG"));
				param.setVALORE_MANCATA_RICEZIONE_SONDA(rs.getInt("VALORE_MANCATA_RICEZIONE_SONDA"));
				param.setLIMITE_ALLARME(rs.getDouble("lIMITE_ALLARME"));
				param.setLIMITE_PREALLARME(rs.getDouble("LIMITE_PREALLARME"));
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{
			pst.close();
			con.close();
		}
		return param;
		
	}

	public static void saveParam(ParamDTO param) throws SQLException {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try {
			
			con=getConnection();
			pst=con.prepareStatement("UPDATE tbl_param SET PORT=?,FRAMERATE=?,LIMIT_GRAPH_X_AXIS=?,LIMIT_GRAPH_Y_AXIS=?,"
					+ "LIMIT_GRAPH_Z_AXIS=?,FRAMERATE_READ_GRAPH=?,DEBUG=?,VALORE_MANCATA_RICEZIONE_SONDA=?,LIMITE_ALLARME=?,"
					+ "LIMITE_PREALLARME=? WHERE id=1");
			
			pst.setString(1, param.getPORT());
			pst.setInt(2,param.getFRAMERATE());
			pst.setDouble(3, param.getLIMIT_GRAPH_X_AXIS());
			pst.setDouble(4, param.getLIMIT_GRAPH_Y_AXIS());
			pst.setDouble(5, param.getLIMIT_GRAPH_Z_AXIS());
			pst.setInt(6, param.getFRAMERATE_READ_GRAPH());
			pst.setString(7, param.getDEBUG());
			pst.setInt(8, param.getVALORE_MANCATA_RICEZIONE_SONDA());
			pst.setDouble(9, param.getLIMITE_ALLARME());
			pst.setDouble(10, param.getLIMITE_PREALLARME());
			
			pst.execute();
		}catch (Exception e) {
			e.printStackTrace();
		} 
		finally 
		{
			pst.close();
			con.close();
		}
		
	}

}
