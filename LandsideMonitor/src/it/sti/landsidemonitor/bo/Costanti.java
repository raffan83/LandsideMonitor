package it.sti.landsidemonitor.bo;

public class Costanti {
	
	
	
	public static final String COD_EVE_001 = "ALLERTA SUPERAMENTO SOGLIA MASSIMA ";
	public static final String COD_EVE_002 = "ALLERTA PERIODO 1";
	public static final String COD_EVE_003 = "ALLERTA PERIODO 2";
	public static final String COD_EVE_004 = "ALLERTA PERIODO 3";
	public static final String COD_EVE_005 = "ALLARME 2 o PIU' SONDE IN ALLERTA ";
	
	public static final String COD_EVE_006="ALLARME SUPERAMENTO DURATA 4 PUNTI GR. B";
	public static final String COD_EVE_007="ALLARME SUPERAMENTO DURATA 3 PUNTI GR. B";
	public static final String COD_EVE_008="ALLARME SUPERAMENTO DURATA 2 PUNTI GR. B";
	public static final String COD_EVE_009="ALLARME SUPERAMENTO DURATA 1 PUNTI GR. B";
	public static double SOGLIA_BATTERIA = 0;
	
	
	public static String PORT="";
	public static int FRAMERATE=0;
	public static  boolean DEBUG = true;

	
	public static  double LIMITE_MIN_P1 = 0;
	public static  double LIMITE_MAX_P1 = 0;
	public static int TEMPO_ALLERTA_1=0;
	
	public static  double LIMITE_MIN_P2 = 0;
	public static  double LIMITE_MAX_P2 = 0;
	public static int TEMPO_ALLERTA_2=0;
	
	public static  double LIMITE_MIN_P3 = 0;
	public static  double LIMITE_MAX_P3 = 0;
	public static int TEMPO_ALLERTA_3=0;
	
	public static int PUNTI_DET_5_SEC;
	public static int PUNTI_DET_9_SEC;
	public static int PUNTI_DET_12_SEC;
	public static int PUNTI_DET_15_SEC;
	public static  double SENSIBILITA_PREALLARME = 10;
	

	public  static double SCREEN_X=0;
	public  static double SCREEN_Y=0;
	
	
	public static String HOST_NAME_MAIL="smtps.aruba.it";
	public static String USERNAME_MAIL="calver@accpoint.it";
	public static String PASSWORD_MAIL="7LwqE9w4tu";
	public static String SMTP_AUTH="true";
	public static String PORT_MAIL="465";
	public static String SSL="true";
	public static String DEST_MAIL_PRE="";
	public static String DEST_MAIL_ALARM="";
	public static String DEST_MAIL_MAN="";
	public static long TIMER_ITERAZIONI=15;
	
	public static int FLAG_SMS;
	public static String NUMBER_SMS;
	
	
}
