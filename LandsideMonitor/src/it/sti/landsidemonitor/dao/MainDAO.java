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
		        
			pst=con.prepareStatement("INSERT INTO tbl_eventi(id_sonda,date,stato,asse_x,asse_y,asse_z,abilitato) VALUES(?,?,?,?,?,?,?)");
			pst.setString(1, identifier);
			pst.setString(2,DateToStr);
			pst.setInt(3,stato);
			pst.setDouble(4, acc_X);
			pst.setDouble(5, acc_Y);
			pst.setDouble(6, acc_Z);
			pst.setInt(7,1 );
			
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
			pst=con.prepareStatement("SELECT * FROM tbl_eventi WHERE id_sonda=? AND abilitato=1 ORDER BY id ASC");
			
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
				
				param.setLIMITE_MIN_P1(rs.getDouble("LIMITE_MIN_P1"));
				param.setLIMITE_MAX_P1(rs.getDouble("LIMITE_MAX_P1"));
				param.setITERAZIONI_P1(rs.getInt("ITERAZIONI_P1"));
				
				param.setLIMITE_MIN_P2(rs.getDouble("LIMITE_MIN_P2"));
				param.setLIMITE_MAX_P2(rs.getDouble("LIMITE_MAX_P2"));
				param.setITERAZIONI_P2(rs.getInt("ITERAZIONI_P2"));
				
				param.setLIMITE_MIN_P3(rs.getDouble("LIMITE_MIN_P3"));
				param.setLIMITE_MAX_P3(rs.getDouble("LIMITE_MAX_P3"));
				param.setITERAZIONI_P3(rs.getInt("ITERAZIONI_P3"));
				
				param.setHOST_NAME_MAIL(rs.getString("HOST_NAME_MAIL"));
				param.setUSERNAME_MAIL(rs.getString("USER_NAME_MAIL"));
				param.setPASSWORD_MAIL(rs.getString("PASSWORD_MAIL"));
				param.setSMTP_AUTH(rs.getString("SMTP_AUTH"));
				param.setPORT_MAIL(rs.getString("PORT_MAIL"));
				param.setSSL(rs.getString("SSL"));
				param.setDEST_MAIL_PRE(rs.getString("DEST_MAIL_PRE"));
				param.setDEST_MAIL_ALARM(rs.getString("DEST_MAIL_ALARM"));
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
					+ "LIMIT_GRAPH_Z_AXIS=?,FRAMERATE_READ_GRAPH=?,DEBUG=?,VALORE_MANCATA_RICEZIONE_SONDA=?,"
					+ "LIMITE_MIN_P1=?,LIMITE_MAX_P1=?,ITERAZIONI_P1=?,"
					+ "LIMITE_MIN_P2=?,LIMITE_MAX_P2=?,ITERAZIONI_P2=?,"
					+ "LIMITE_MIN_P3=?,LIMITE_MAX_P3=?,ITERAZIONI_P3=?,"
					+ "HOST_NAME_MAIL=?,USER_NAME_MAIL=?,PASSWORD_MAIL=?,SMTP_AUTH=?,PORT_MAIL=?,SSL=?,DEST_MAIL_PRE=?,DEST_MAIL_ALARM=? WHERE id=1");
			
			pst.setString(1, param.getPORT());
			pst.setInt(2,param.getFRAMERATE());
			pst.setDouble(3, param.getLIMIT_GRAPH_X_AXIS());
			pst.setDouble(4, param.getLIMIT_GRAPH_Y_AXIS());
			pst.setDouble(5, param.getLIMIT_GRAPH_Z_AXIS());
			pst.setInt(6, param.getFRAMERATE_READ_GRAPH());
			pst.setString(7, param.getDEBUG());
			pst.setInt(8, param.getVALORE_MANCATA_RICEZIONE_SONDA());
			
			pst.setDouble(9, param.getLIMITE_MIN_P1());
			pst.setDouble(10, param.getLIMITE_MAX_P1());
			pst.setInt(11, param.getITERAZIONI_P1());
			
			pst.setDouble(12, param.getLIMITE_MIN_P2());
			pst.setDouble(13, param.getLIMITE_MAX_P2());
			pst.setInt(14, param.getITERAZIONI_P2());
			
			pst.setDouble(15, param.getLIMITE_MIN_P3());
			pst.setDouble(16, param.getLIMITE_MAX_P3());
			pst.setInt(17, param.getITERAZIONI_P3());
			
			pst.setString(18, param.getHOST_NAME_MAIL());
			pst.setString(19, param.getUSERNAME_MAIL());
			pst.setString(20, param.getPASSWORD_MAIL());
			pst.setString(21, param.getSMTP_AUTH());
			pst.setString(22, param.getPORT_MAIL());
			pst.setString(23, param.getSSL());
			pst.setString(24, param.getDEST_MAIL_PRE());
			pst.setString(25, param.getDEST_MAIL_ALARM());
			
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

	public static void cancellaLog(String identifier) throws SQLException {
		

		Connection con=null;
		PreparedStatement pst=null;
	
		
		try {
			
			con=getConnection();
			pst=con.prepareStatement("UPDATE tbl_eventi  SET abilitato=0 WHERE id_sonda=? ");
			pst.setString(1, identifier);

			
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
