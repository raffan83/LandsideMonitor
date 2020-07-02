package it.sti.landsidemonitor.sms;


import java.util.Calendar;

import icontact.sms.ws.tutorial.managesms.client.Result;
import icontact.sms.ws.tutorial.managesms.client.SendResponse;
import icontact.sms.ws.tutorial.managesms.client.SmsInfoResponse;
import icontact.sms.ws.tutorial.manageuser.client.UserInfoResponse;
import it.sti.landsidemonitor.bo.Costanti;

/**
 *
 * @author Main Class
 */
public class SendSMS implements Runnable{

	
	private static String idSonda="";
	private static int tipoAllarme;
	
	
	public SendSMS(String _idSonda,int _tipoAllarme) 
	{
		idSonda=_idSonda;
		tipoAllarme=_tipoAllarme;
	}
 
	
	public void run() {
    	
       

        String username ="ncsnetowk";
        String password = "Ncsnetwork2020";
        
        String[] numeri=Costanti.NUMBER_SMS.split(";");
        
        
        if(numeri!=null && numeri.length>0 && Costanti.FLAG_SMS==1) 
        {
        
        for (String numero : numeri) {
			
			
        System.out.println("\n\n--> SEND SMS\n");
        try {
            ManageSmsWrapper smsWrapper = new ManageSmsWrapper(username, password);

            //Send SMS
            String segnalazione="";
            if(tipoAllarme==1) 
            {
            	segnalazione="ALLARME";
            }
            else 
            {
            	segnalazione="ALLERTA";
            }
            SendResponse sendResult = smsWrapper.send("Monitoraggio sito",numero,"Segnalazione "+segnalazione+" sonda "+idSonda + System.currentTimeMillis(),null,null);


            Integer internalId = null;

            String opRes = Utility.checkOperarationResult(sendResult);
            if (Utility.OPERATION_SUCCESS.equals(opRes)) {
                System.out.println(Utility.toStringSendResponse(sendResult));
                Result smsResult = sendResult.getResults().get(0);
                internalId = new Integer(smsResult.getInternalId());

            } else {
                System.out.println(opRes);
                internalId = 4939254;//used for demo
            }

            //Load SMS by Id
            System.out.println("\n\n--> GET SMS\n");
            SmsInfoResponse getSmsResult = smsWrapper.getSmsById(new Integer[]{internalId}, null);

            opRes = Utility.checkOperarationResult(getSmsResult);
            if (Utility.OPERATION_SUCCESS.equals(opRes)) {
                System.out.println(Utility.toStringSmsInfoResponse(getSmsResult));
            } else {
                System.out.println(opRes);
            }

            //Load SMS by Date
            System.out.println("\n\n--> GET SMS BY DATE\n");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            SmsInfoResponse getSmsDateResult = smsWrapper.getSmsByDate(Utility.formatDate(calendar.getTime()), null);

            opRes = Utility.checkOperarationResult(getSmsDateResult);
            if (Utility.OPERATION_SUCCESS.equals(opRes)) {
                System.out.println(Utility.toStringSmsInfoResponse(getSmsDateResult));
            } else {
                System.out.println(opRes);
            }

        } catch (Exception e) {
            e.printStackTrace();
            
        }

        }
        
        System.out.println("\n\n--> USER INFO\n");
        try {

            ManageUserWrapper userWrpapper = new ManageUserWrapper(username, password);
            UserInfoResponse response = userWrpapper.getUserInfo();


            if (Utility.OPERATION_SUCCESS.equals(response.getOperationResult())) {
                String name = response.getUserInfo().getName();
                double credit = response.getUserInfo().getCredit();
                System.out.println("User " + name + ": " + credit + " Eur");
            } else {
                System.out.println(response.getOperationResult());
            }


        } catch (Exception e) {
            e.printStackTrace();
          
        }
        }

    }

}
