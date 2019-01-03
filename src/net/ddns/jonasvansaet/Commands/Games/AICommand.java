package net.ddns.jonasvansaet.Commands.Games;

import net.ddns.jonasvansaet.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AICommand implements Command {
    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
