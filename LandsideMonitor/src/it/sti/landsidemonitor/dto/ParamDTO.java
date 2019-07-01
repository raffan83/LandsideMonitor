package it.sti.landsidemonitor.dto;

public class ParamDTO {
	
	private  String PORT;
	private  int FRAMERATE;
	private  double LIMIT_GRAPH_X_AXIS;
	private  double LIMIT_GRAPH_Y_AXIS;
	private  double LIMIT_GRAPH_Z_AXIS;
	private  int FRAMERATE_READ_GRAPH;
	private  String DEBUG;
	private  int VALORE_MANCATA_RICEZIONE_SONDA;
	private  double LIMITE_ALLARME;
	private  double LIMITE_PREALLARME;
	
	
	public String getPORT() {
		return PORT;
	}
	public void setPORT(String pORT) {
		PORT = pORT;
	}
	public int getFRAMERATE() {
		return FRAMERATE;
	}
	public void setFRAMERATE(int fRAMERATE) {
		FRAMERATE = fRAMERATE;
	}
	public double getLIMIT_GRAPH_X_AXIS() {
		return LIMIT_GRAPH_X_AXIS;
	}
	public void setLIMIT_GRAPH_X_AXIS(double lIMIT_GRAPH_X_AXIS) {
		LIMIT_GRAPH_X_AXIS = lIMIT_GRAPH_X_AXIS;
	}
	public double getLIMIT_GRAPH_Y_AXIS() {
		return LIMIT_GRAPH_Y_AXIS;
	}
	public void setLIMIT_GRAPH_Y_AXIS(double lIMIT_GRAPH_Y_AXIS) {
		LIMIT_GRAPH_Y_AXIS = lIMIT_GRAPH_Y_AXIS;
	}
	public double getLIMIT_GRAPH_Z_AXIS() {
		return LIMIT_GRAPH_Z_AXIS;
	}
	public void setLIMIT_GRAPH_Z_AXIS(double lIMIT_GRAPH_Z_AXIS) {
		LIMIT_GRAPH_Z_AXIS = lIMIT_GRAPH_Z_AXIS;
	}
	public int getFRAMERATE_READ_GRAPH() {
		return FRAMERATE_READ_GRAPH;
	}
	public void setFRAMERATE_READ_GRAPH(int fRAMERATE_READ_GRAPH) {
		FRAMERATE_READ_GRAPH = fRAMERATE_READ_GRAPH;
	}

	
	public String getDEBUG() {
		return DEBUG;
	}
	public void setDEBUG(String dEBUG) {
		DEBUG = dEBUG;
	}
	
	public int getVALORE_MANCATA_RICEZIONE_SONDA() {
		return VALORE_MANCATA_RICEZIONE_SONDA;
	}
	public void setVALORE_MANCATA_RICEZIONE_SONDA(int vALORE_MANCATA_RICEZIONE_SONDA) {
		VALORE_MANCATA_RICEZIONE_SONDA = vALORE_MANCATA_RICEZIONE_SONDA;
	}
	public double getLIMITE_ALLARME() {
		return LIMITE_ALLARME;
	}
	public void setLIMITE_ALLARME(double lIMITE_ALLARME) {
		LIMITE_ALLARME = lIMITE_ALLARME;
	}
	public double getLIMITE_PREALLARME() {
		return LIMITE_PREALLARME;
	}
	public void setLIMITE_PREALLARME(double lIMITE_PREALLARME) {
		LIMITE_PREALLARME = lIMITE_PREALLARME;
	}
	
	

}
