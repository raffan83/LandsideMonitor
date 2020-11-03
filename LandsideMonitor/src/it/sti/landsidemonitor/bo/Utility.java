package it.sti.landsidemonitor.bo;

import org.apache.log4j.Logger;

public class Utility {
	
	final static Logger logger = Logger.getLogger(Utility.class);

	public static Object getLabelStato(int stato) {

		if(stato==0) 
		{
			return "ATTIVA";
		}
		else if(stato==1) 
		{
			return "ALLARME";
		}
		else if (stato == 2) 
		{
			return "ALLERTA";
		}
		else if (stato==3)
		{
			return "PRE ALLARME 2";
		}
		else if (stato==4)
		{
			return "PRE ALLARME 3";
		}
		else if (stato==5)
		{
			return "NON ATTIVA";
		}
		
		else 
		{
			return "";
		}
	}

	public static String getDescrizioneEvento(String codice) {
		
		if(codice.equals("001")) 
		{
			return Costanti.COD_EVE_001+""+Costanti.LIMITE_MAX_P3+" m/s";
		}
		if(codice.equals("002")) 
		{
			return Costanti.COD_EVE_002;
		}
		if(codice.equals("003")) 
		{
			return Costanti.COD_EVE_003;
		}
		if(codice.equals("004")) 
		{
			return Costanti.COD_EVE_004;
		}
		if(codice.equals("005")) 
		{
			return Costanti.COD_EVE_005;
		}
		if(codice.equals("006")) 
		{
			return Costanti.COD_EVE_006;
		}
		if(codice.equals("007")) 
		{
			return Costanti.COD_EVE_007;
		}
		if(codice.equals("008")) 
		{
			return Costanti.COD_EVE_008;
		}
		if(codice.equals("009")) 
		{
			return Costanti.COD_EVE_009;
		}
		
		return "";
	}
	public static boolean isDouble(String text) {
		try 
		{
			Double.parseDouble(text);
			return true;
		} catch (Exception e) 
		{
			return false;
		}
	}

	public static boolean isAutomatiCalibration(double acc_X, double acc_Y, double acc_Z) {
		
		
		
		if( (acc_X>=2.4 && acc_X<=2.6) && (acc_Y>=0 && acc_Y<=0.19) && (acc_Z>=0 && acc_Z<=0.19) ) 
		{
			logger.warn("AUTOMATIC CALIBRATION AX["+acc_X+"] AY["+acc_Y+"] AZ["+acc_Z+"]");
			return true;
		}
		
		if( (acc_X>=0 && acc_X<=0.19) && (acc_Y>=2.4 && acc_Y<=2.6) && (acc_Z>=0 && acc_Z<=0.19) ) 
		{
			logger.warn("AUTOMATIC CALIBRATION AX["+acc_X+"] AY["+acc_Y+"] AZ["+acc_Z+"]");
			return true;
		}
		if( (acc_X>=0 && acc_X<=0.19) && (acc_Y>=0 && acc_Y<=0.19) && (acc_Z>=2.4 && acc_Z<=2.6) ) 
		{
			logger.warn("AUTOMATIC CALIBRATION AX["+acc_X+"] AY["+acc_Y+"] AZ["+acc_Z+"]");
			return true;
		}
		return false;
	
	}

}
