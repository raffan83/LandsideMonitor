package it.sti.landsidemonitor.bo;

public class Utility {

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
		return "";
	}

}
