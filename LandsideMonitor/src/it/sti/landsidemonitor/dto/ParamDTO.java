package it.sti.landsidemonitor.dto;

public class ParamDTO {
	
	private  String PORT;
	private  int FRAMERATE;
	private  String DEBUG;

	private   double LIMITE_MIN_P1 = 0;
	private   double LIMITE_MAX_P1 = 0;
	private  int ITERAZIONI_P1=0;
	
	private   double LIMITE_MIN_P2 = 0;
	private   double LIMITE_MAX_P2 = 0;
	private  int ITERAZIONI_P2=0;
	
	private   double LIMITE_MIN_P3 = 0;
	private   double LIMITE_MAX_P3 = 0;
	private  int ITERAZIONI_P3=0;
	
	
	private  String HOST_NAME_MAIL;
	private  String USERNAME_MAIL;
	private  String PASSWORD_MAIL;
	private  String SMTP_AUTH;
	private  String PORT_MAIL;
	private  String SSL;
	private  String DEST_MAIL_PRE;
	private  String DEST_MAIL_ALARM;
	
	
	public String getDEST_MAIL_PRE() {
		return DEST_MAIL_PRE;
	}
	public void setDEST_MAIL_PRE(String dEST_MAIL_PRE) {
		DEST_MAIL_PRE = dEST_MAIL_PRE;
	}
	public String getDEST_MAIL_ALARM() {
		return DEST_MAIL_ALARM;
	}
	public void setDEST_MAIL_ALARM(String dEST_MAIL_ALARM) {
		DEST_MAIL_ALARM = dEST_MAIL_ALARM;
	}
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
	
	
	public String getDEBUG() {
		return DEBUG;
	}
	public void setDEBUG(String dEBUG) {
		DEBUG = dEBUG;
	}

	public double getLIMITE_MIN_P1() {
		return LIMITE_MIN_P1;
	}
	public void setLIMITE_MIN_P1(double lIMITE_MIN_P1) {
		LIMITE_MIN_P1 = lIMITE_MIN_P1;
	}
	public double getLIMITE_MAX_P1() {
		return LIMITE_MAX_P1;
	}
	public void setLIMITE_MAX_P1(double lIMITE_MAX_P1) {
		LIMITE_MAX_P1 = lIMITE_MAX_P1;
	}
	public int getITERAZIONI_P1() {
		return ITERAZIONI_P1;
	}
	public void setITERAZIONI_P1(int iTERAZIONI_P1) {
		ITERAZIONI_P1 = iTERAZIONI_P1;
	}
	public double getLIMITE_MIN_P2() {
		return LIMITE_MIN_P2;
	}
	public void setLIMITE_MIN_P2(double lIMITE_MIN_P2) {
		LIMITE_MIN_P2 = lIMITE_MIN_P2;
	}
	public double getLIMITE_MAX_P2() {
		return LIMITE_MAX_P2;
	}
	public void setLIMITE_MAX_P2(double lIMITE_MAX_P2) {
		LIMITE_MAX_P2 = lIMITE_MAX_P2;
	}
	public int getITERAZIONI_P2() {
		return ITERAZIONI_P2;
	}
	public void setITERAZIONI_P2(int iTERAZIONI_P2) {
		ITERAZIONI_P2 = iTERAZIONI_P2;
	}
	public double getLIMITE_MIN_P3() {
		return LIMITE_MIN_P3;
	}
	public void setLIMITE_MIN_P3(double lIMITE_MIN_P3) {
		LIMITE_MIN_P3 = lIMITE_MIN_P3;
	}
	public double getLIMITE_MAX_P3() {
		return LIMITE_MAX_P3;
	}
	public void setLIMITE_MAX_P3(double lIMITE_MAX_P3) {
		LIMITE_MAX_P3 = lIMITE_MAX_P3;
	}
	public int getITERAZIONI_P3() {
		return ITERAZIONI_P3;
	}
	public void setITERAZIONI_P3(int iTERAZIONI_P3) {
		ITERAZIONI_P3 = iTERAZIONI_P3;
	}
	public String getHOST_NAME_MAIL() {
		return HOST_NAME_MAIL;
	}
	public void setHOST_NAME_MAIL(String hOST_NAME_MAIL) {
		HOST_NAME_MAIL = hOST_NAME_MAIL;
	}
	public String getUSERNAME_MAIL() {
		return USERNAME_MAIL;
	}
	public void setUSERNAME_MAIL(String uSERNAME_MAIL) {
		USERNAME_MAIL = uSERNAME_MAIL;
	}
	public String getPASSWORD_MAIL() {
		return PASSWORD_MAIL;
	}
	public void setPASSWORD_MAIL(String pASSWORD_MAIL) {
		PASSWORD_MAIL = pASSWORD_MAIL;
	}
	public String getSMTP_AUTH() {
		return SMTP_AUTH;
	}
	public void setSMTP_AUTH(String sMTP_AUTH) {
		SMTP_AUTH = sMTP_AUTH;
	}
	public String getPORT_MAIL() {
		return PORT_MAIL;
	}
	public void setPORT_MAIL(String pORT_MAIL) {
		PORT_MAIL = pORT_MAIL;
	}
	public String getSSL() {
		return SSL;
	}
	public void setSSL(String sSL) {
		SSL = sSL;
	}
	
	
	

}
