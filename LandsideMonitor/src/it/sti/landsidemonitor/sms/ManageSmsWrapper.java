package it.sti.landsidemonitor.sms;


import icontact.sms.ws.tutorial.managesms.client.ManageSms;
import icontact.sms.ws.tutorial.managesms.client.ManageSmsService;
import icontact.sms.ws.tutorial.managesms.client.Msisdn;
import icontact.sms.ws.tutorial.managesms.client.SendPayLoad;
import icontact.sms.ws.tutorial.managesms.client.SendResponse;
import icontact.sms.ws.tutorial.managesms.client.SmsDatePayLoad;
import icontact.sms.ws.tutorial.managesms.client.SmsIdPayLoad;
import icontact.sms.ws.tutorial.managesms.client.SmsInfoResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Utility for call ManageSms web service methods
 *
 */
public class ManageSmsWrapper {
    
    private ManageSms port;
    private String username;
    private String password;
    
    public ManageSmsWrapper(String username,String password) {
        ManageSmsService service = new ManageSmsService();
        port = service.getManageSmsPort();
        this.username = username;
        this.password = password;
    }
    
    /**
     * call send method
     *
     */
    public SendResponse send(String from,String to,String text,String customerId,String transactionId){
        SendPayLoad sendPayLoad = new SendPayLoad();
        sendPayLoad.setUsername(username);
        sendPayLoad.setPassword(password);
        sendPayLoad.setFrom(from);
        sendPayLoad.setText(text);
        sendPayLoad.setTransactionId(transactionId);
        
        Msisdn msisdn = new Msisdn();
        msisdn.setNumber(to);
        msisdn.setCustomerId(customerId);
        
        sendPayLoad.getNumbers().add(msisdn);
        return port.send(sendPayLoad);
        
    }
    
    /**
     * call getSmsById method
     *
     */
    public SmsInfoResponse getSmsById(Integer[] internalId,String transactionId){
        SmsIdPayLoad smsIdPayLoad = new SmsIdPayLoad();
        smsIdPayLoad.setUsername(username);
        smsIdPayLoad.setPassword(password);
        smsIdPayLoad.setTransactionId(transactionId);
        
        if(internalId!=null){
            for(Integer id:internalId)
                smsIdPayLoad.getInternalId().add(id);
        }
        return port.getSmsById(smsIdPayLoad);
        
    }
    
    /**
     * call getSmsById method
     *
     */
    public SmsInfoResponse getSmsByDate(String from,String to){
        SmsDatePayLoad smsDatePayLoad = new SmsDatePayLoad();
        smsDatePayLoad.setUsername(username);
        smsDatePayLoad.setPassword(password);
        smsDatePayLoad.setFromDate(from);
        smsDatePayLoad.setToDate(to);
        return port.getSmsByDate(smsDatePayLoad);
        
    }
    
}
