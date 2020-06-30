package it.sti.landsidemonitor.sms;

import icontact.sms.ws.tutorial.manageuser.client.ManageUser;
import icontact.sms.ws.tutorial.manageuser.client.ManageUserService;
import icontact.sms.ws.tutorial.manageuser.client.Account;
import icontact.sms.ws.tutorial.manageuser.client.UserInfoResponse;

/**
 * Utility for call ManageUser web service methods
 *
 */
public class ManageUserWrapper {
    
    private ManageUser port;
    private String username;
    private String password;
    
    public ManageUserWrapper(String username,String password) {
        ManageUserService service = new ManageUserService();
        port = service.getManageUserPort();
        this.username = username;
        this.password = password;
    }
    
    /**
     * call getUserInfo method
     *
     */
    public UserInfoResponse getUserInfo(){
        Account account = new Account();

        account.setUsername(username);
        account.setPassword(password);
        
        return port.getUserInfo(account);
        
    }
    
   
    
}
