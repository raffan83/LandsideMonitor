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
			return "PRE ALLARME 1 ";
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

}
