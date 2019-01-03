package net.ddns.jonasvansaet;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by jonas on 12/14/2016.
 */
public interface Command {
    public boolean called(MessageReceivedEvent event);
    public void action(MessageReceivedEvent event);
    public String help ();
    public void executed(boolean success, MessageReceivedEvent event);
}
