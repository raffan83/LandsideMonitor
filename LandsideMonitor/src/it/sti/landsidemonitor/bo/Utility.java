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
			return "PRE ALLARME";
		}
		else if (stato==3)
		{
			return "NON ATTIVA";
		}else 
		{
			return "";
		}
	}

}
