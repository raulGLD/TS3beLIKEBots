/*
 * You will need to get the TS3 JAVA API from here: https://bit.ly/TS3JavaAPI
 * If you don't want to do this and compile it yourself, feel free to write us and we will do everything for you!
 */

import com.github.theholywaffle.teamspeak3.*;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.*;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

            final TS3Config config = new TS3Config();
            config.setHost("127.0.0.1");    // your server IP
            config.setEnableCommunicationsLogging(true);

            final TS3Query query = new TS3Query(config);
            query.connect();

            final TS3Api api = query.getApi();
            api.login("serveradmin", "serverpassword");   // your server admin and password for the bot to join
            api.selectVirtualServerById(1); // always 1 unless you know you have multiple virtual servers
            api.setNickname("beLIKE bot");  // name of your bot
            final int channelToListen = 2;  // channel ID where people enter to write the command
            final int channelToOrder = 5;   // the bot will create the channel after this channel's ID

            final int clientId = api.whoAmI().getId();

            api.moveQuery(channelToListen);

            api.registerEvent(TS3EventType.TEXT_CHANNEL, channelToListen);

            api.addTS3Listeners(new TS3EventAdapter() {
                    @Override
                    public void onTextMessage(TextMessageEvent e) {
                            if ((e.getTargetMode() == TextMessageTargetMode.CHANNEL) && (e.getInvokerId() != clientId)) {
                                    String message = e.getMessage().toLowerCase();

                                    if (message.startsWith("!newchannel")) {    // you can name the command however you want
                                            // the next 3 lines will separate the text into words
                                            String[] words = message.split(" ");
                                            String nameOfNewChannel = words[1];
                                            String pwOfNewChannel = words[2];

                                            // here we create the channels properties
                                            final Map<ChannelProperty, String> properties = new HashMap<>();
                                            properties.put(ChannelProperty.CHANNEL_FLAG_PERMANENT, "1");    // we make it permanent
                                            properties.put(ChannelProperty.CHANNEL_ORDER, String.valueOf(channelToOrder));  // we order it after our chosen channel
                                            properties.put(ChannelProperty.CHANNEL_PASSWORD, pwOfNewChannel);   // we store the user's password
                                            api.createChannel(nameOfNewChannel, properties);    // we create it with the given name and password

                                            api.sendPrivateMessage(e.getInvokerId(), "Successfully created your channel! You can now join it using the given password!");
                                    }
                            }

                            //if something...

                            //if something...

                            //if something...
                    }
            });

            // to stop and disconnect the bot
            // disconnect
            //query.exit();

    }
}
