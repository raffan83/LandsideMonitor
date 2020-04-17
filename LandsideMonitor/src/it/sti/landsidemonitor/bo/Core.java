package it.sti.landsidemonitor.bo;

import java.sql.SQLException;
import java.util.ArrayList;

import it.sti.landsidemonitor.dao.MainDAO;
import it.sti.landsidemonitor.dto.ParamDTO;
import it.sti.landsidemonitor.dto.SensorDTO;

public class Core {



	/*Metodo delegato al recupero delle impostazioni dei sensori sul DB*/
	
	public static ArrayList<SensorDTO> getListaSensori() throws SQLException {
		
		ArrayList<SensorDTO> lista =MainDAO.getListaSonde();
		
		return lista;
	}

	public static void removeSensor(String idSonda) throws SQLException {
		
	MainDAO.removeSensor(idSonda);
		
	}

	public static int aggiungiSensore(String id, int posX, int posY, String type) throws Exception {
		
		return MainDAO.inserisciSonda(id,posX,posY,type);
		
	}

	public static void updatePosition(SensorDTO sonda, String x, String y) throws SQLException {
		
		MainDAO.updatePosition(sonda,x,y);
		
	}

	public synchronized static void registraEvento(String identifier,String codice, int i, double acc_X, double acc_Y, double acc_Z) throws SQLException {
		
		String descrizione =Utility.getDescrizioneEvento(codice);
		
		synchronized (identifier) 
		{
			MainDAO.registraEvento(identifier,i,codice,descrizione,acc_X,acc_Y,acc_Z);
		}
		
	}

	public static ArrayList<String> getListaEventi(String identifier) throws SQLException {
		
		return MainDAO.getListaEventi(identifier);
	}

	public static void cambiaStato(int idSonda, int stato) throws Exception {
		
		if(stato==1) 
		{
			PortReader.write("Y");
		}
		if(stato==2) 
		{
			PortReader.write("X");
		}
		if(stato==0) 
		{
			PortReader.write("Z");
		}
		
		MainDAO.cambiaStatoDAO(idSonda,stato);
		
	}
	
	public static ParamDTO getParam() throws SQLException 
	{
		return MainDAO.getParam();
	}

	public static void inizialize() throws SQLException {
		
		ParamDTO parametri =getParam();
		
		Costanti.PORT=parametri.getPORT();
		Costanti.FRAMERATE=parametri.getFRAMERATE();
				
		if(parametri.getDEBUG().equals("0"))
		{
			Costanti.DEBUG=false;
		}
		else 
		{
			Costanti.DEBUG=true;
		}
		
		
		Costanti.LIMITE_MIN_P1=parametri.getLIMITE_MIN_P1();
		Costanti.LIMITE_MAX_P1=parametri.getLIMITE_MAX_P1();
		Costanti.TEMPO_ALLERTA_1=parametri.getITERAZIONI_P1()*1000;
		
		Costanti.LIMITE_MIN_P2=parametri.getLIMITE_MIN_P2();
		Costanti.LIMITE_MAX_P2=parametri.getLIMITE_MAX_P2();
		Costanti.TEMPO_ALLERTA_2=parametri.getITERAZIONI_P2()*1000;
		
		Costanti.LIMITE_MIN_P3=parametri.getLIMITE_MIN_P3();
		Costanti.LIMITE_MAX_P3=parametri.getLIMITE_MAX_P3();
		Costanti.TEMPO_ALLERTA_3=parametri.getITERAZIONI_P3()*1000;
		
		
		Costanti.HOST_NAME_MAIL=parametri.getHOST_NAME_MAIL();
		Costanti.USERNAME_MAIL=parametri.getUSERNAME_MAIL();
		Costanti.PASSWORD_MAIL=parametri.getPASSWORD_MAIL();
		Costanti.SMTP_AUTH=parametri.getSMTP_AUTH();
		Costanti.PORT_MAIL=parametri.getPORT_MAIL();
		Costanti.SSL=parametri.getSSL();
		Costanti.DEST_MAIL_PRE=parametri.getDEST_MAIL_PRE();
		Costanti.DEST_MAIL_ALARM=parametri.getDEST_MAIL_ALARM();
		
		
	}

	public static void saveParam(ParamDTO param) throws SQLException {
		
		MainDAO.saveParam(param);
		
	}

	public static void cancellaLog(String identifier) throws SQLException {
		
		MainDAO.cancellaLog(identifier);
		
	}
	
	
}
