package it.sti.landsidemonitor.sms;


import icontact.sms.ws.tutorial.managesms.client.CommonResponse;
import icontact.sms.ws.tutorial.managesms.client.Result;
import icontact.sms.ws.tutorial.managesms.client.SendResponse;
import icontact.sms.ws.tutorial.managesms.client.SmsInfo;
import icontact.sms.ws.tutorial.managesms.client.SmsInfoResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Utility class for Tutorial
 *
 */
public class Utility {
    
    public static final String OPERATION_SUCCESS = "SUCCESS";
    public static final String OPERATION_FAILED = "FAILED";
    
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmZ"); 
   
    public static String checkOperarationResult(CommonResponse response){
        if(response == null || !OPERATION_SUCCESS.equals(response.getOperationResult())){
            return OPERATION_FAILED +"-"+response.getOperationDetail();
        }else
            return OPERATION_SUCCESS;       
    }
    
    public static String formatDate(Date date) throws Exception{
        return formatter.format(date);
    }
    
    public static String toStringSendResponse(SendResponse sendResponse){
        StringBuffer toReturn =  new StringBuffer("SendResponse:\n");
        List<Result> smsResultList = sendResponse.getResults();
        
        if(smsResultList != null && smsResultList.size()>0){
            for(Result result : smsResultList){
                toReturn.append("--> id "+result.getInternalId()+"\n");
                toReturn.append("    status "+result.getStatus()+"\n");
                toReturn.append("    statusDetail "+result.getStatusDetail()+"\n");
            }
        }else{
            toReturn.append("NO-MESSAGE");
        }
        
        return toReturn.toString();
    }
    
    public static String toStringSmsInfoResponse(SmsInfoResponse smsInfoResponse){
        StringBuffer toReturn =  new StringBuffer("SmsInfoResponse:\n");
        List<SmsInfo> smsInfoList = smsInfoResponse.getSmsInfo();
        
        if(smsInfoList != null && smsInfoList.size()>0){
            for(SmsInfo info : smsInfoList){
                toReturn.append("--> id "+info.getId()+"\n");
                toReturn.append("    text "+info.getText()+"\n");
                toReturn.append("    status "+info.getStatus()+"\n");
                toReturn.append("    date "+info.getInsertDate()+"\n");
            }
        }else{
            toReturn.append("NO-MESSAGE");
        }
        
        return toReturn.toString();
    }
    
    
    
    
}
