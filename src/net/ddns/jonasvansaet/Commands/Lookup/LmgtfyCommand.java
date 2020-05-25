package net.ddns.jonasvansaet.Commands.Lookup;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Created by jonas on 12/20/2016.
 */
public class LmgtfyCommand implements Command {
    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        String query;

        if(ParameterParser.unparsedParameter(event) == ""){
            query = "how to use commands";
        } else {
            query = ParameterParser.unparsedParameter(event);
        }

        event.getChannel().sendMessage("http://lmgtfy.com/?q=" + query.replace(" ", "%20")).queue();
    }

    @Override
    public String help() {
        return "Gives back a lmgtfy link. Usage: !lmgtfy [query]";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
