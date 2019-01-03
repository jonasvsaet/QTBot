package net.ddns.jonasvansaet.Commands.Lookup;

import net.ddns.jonasvansaet.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by jonas on 11/04/2017.
 */
public class HentaiCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        event.getChannel().sendMessage("https://www.youtube.com/watch?v=m9Q49OBn_TE").queue();
    }

    @Override
    public String help() {
        return "( ͡° ͜ʖ ͡°)";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
