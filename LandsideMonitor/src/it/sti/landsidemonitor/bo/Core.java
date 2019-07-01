package it.sti.landsidemonitor.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import it.sti.landsidemonitor.dao.MainDAO;
import it.sti.landsidemonitor.dto.ParamDTO;
import it.sti.landsidemonitor.dto.SensorDTO;

public class Core {

	//Test

	/*Metodo delegato al recupero delle impostazioni dei sensori sul DB*/
	
	public static ArrayList<SensorDTO> getListaSensori() throws SQLException {
		
		ArrayList<SensorDTO> lista =MainDAO.getListaSonde();
		
		return lista;
	}

	public static void removeSensor(String idSonda) throws SQLException {
		
	MainDAO.removeSensor(idSonda);
		
	}

	public static int aggiungiSensore(String id, int posX, int posY) throws Exception {
		
		return MainDAO.inserisciSonda(id,posX,posY);
		
	}

	public static void updatePosition(SensorDTO sonda, String x, String y) throws SQLException {
		
		MainDAO.updatePosition(sonda,x,y);
		
	}

	public synchronized static void registraEvento(String identifier, int i, double acc_X, double acc_Y, double acc_Z) throws SQLException {
		
		synchronized (identifier) 
		{
			MainDAO.registraEvento(identifier,i,acc_X,acc_Y,acc_Z);
		}
		
	}

	public static ArrayList<String> getListaEventi(String identifier) throws SQLException {
		
		return MainDAO.getListaEventi(identifier);
	}

	public static void cambiaStato(int idSonda, int stato) throws SQLException {
		
		MainDAO.cambiaStato(idSonda,stato);
		
	}
	
	public static ParamDTO getParam() throws SQLException 
	{
		return MainDAO.getParam();
	}

	public static void inizialize() throws SQLException {
		
		ParamDTO parametri =getParam();
		
		Costanti.PORT=parametri.getPORT();
		Costanti.FRAMERATE=parametri.getFRAMERATE();
		
		Costanti.LIMIT_GRAPH_X_AXIS=parametri.getLIMIT_GRAPH_X_AXIS();
		Costanti.LIMIT_GRAPH_Y_AXIS=parametri.getLIMIT_GRAPH_Y_AXIS();
		Costanti.LIMIT_GRAPH_Z_AXIS=parametri.getLIMIT_GRAPH_Z_AXIS();
		
		Costanti.FRAMERATE_READ_GRAPH=parametri.getFRAMERATE_READ_GRAPH();
		
		if(parametri.getDEBUG().equals("0"))
		{
			Costanti.DEBUG=false;
		}
		else 
		{
			Costanti.DEBUG=true;
		}
		
		Costanti.VALORE_MANCATA_RICEZIONE_SONDA=parametri.getVALORE_MANCATA_RICEZIONE_SONDA();
		Costanti.LIMITE_ALLARME=parametri.getLIMITE_ALLARME();
		Costanti.LIMITE_PREALLARME=parametri.getLIMITE_PREALLARME();
		

	}

	public static void saveParam(ParamDTO param) throws SQLException {
		
		MainDAO.saveParam(param);
		
	}
}
