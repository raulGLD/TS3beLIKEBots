/*
 * You will need to get the TS3 JAVA API from here: https://bit.ly/TS3JavaAPI
 * If you don't want to do this and compile it yourself, feel free to write us and we will do everything for you!
 */

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.*;

public class Main {

    public static void main(String[] args) {

        final TS3Config config = new TS3Config();
        
        // your server IP
        config.setHost("127.0.0.1");    
        
        config.setEnableCommunicationsLogging(true);

        final TS3Query query = new TS3Query(config);
        query.connect();

        final TS3Api api = query.getApi();
        
        // your server admin and password for the bot to join
        api.login("serveradmin", "serverpassword");   
        
        // always 1 unless you know you have multiple virtual servers
        api.selectVirtualServerById(1); 
        
        // name of your bot
        api.setNickname("beLIKE bot");  

        // getting our own clientID
        final int clientId = api.whoAmI().getId();

        api.registerEvent(TS3EventType.SERVER);

        api.addTS3Listeners(new TS3EventAdapter() {
            @Override
            public void onClientJoin(ClientJoinEvent e) {
            
                // testing that we don't send a message to the server query
                if (e.getClientId() != clientId) {
                    api.sendPrivateMessage(e.getClientId(), "Your custom message");
                }
            }
        });
    }
}
