package net.ddns.jonasvansaet.Commands.Lookup;

import com.google.api.client.util.Base64;
import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Created by jonas on 20/06/2017.
 */
public class PhiladelphiaCommand implements Command {
    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        String param = ParameterParser.unparsedParameter(event);
        byte[] bytesEncoded = Base64.encodeBase64(param.getBytes());
        event.getChannel().sendMessage("http://alexanderlozada.com/iasip/?" + new String (bytesEncoded)).queue();
    }

    @Override
    public String help() {
        return "Generates a link for a 'it's sunny in philadelphia' title. Usage: !philadelphia [param]";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
