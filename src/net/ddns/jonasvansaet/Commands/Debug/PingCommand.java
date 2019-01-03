package net.ddns.jonasvansaet.Commands.Debug;

import net.ddns.jonasvansaet.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by jonas on 12/14/2016.
 */
public class PingCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Pong").queue();
    }

    @Override
    public String help() {
        return "returns pong";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
