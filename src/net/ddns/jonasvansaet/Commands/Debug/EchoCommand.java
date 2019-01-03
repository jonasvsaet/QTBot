package net.ddns.jonasvansaet.Commands.Debug;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.utils.CommandParser;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by jonas on 12/17/2016.
 */
public class EchoCommand implements Command{


    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        String textToEcho = ParameterParser.unparsedParameter(event);
        if(textToEcho != ""){
            event.getChannel().sendMessage(textToEcho).queue();
        }
    }

    @Override
    public String help() {
        return "echos whatever you want. usage: !echo [text to echo]";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
