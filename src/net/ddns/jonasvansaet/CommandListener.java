package net.ddns.jonasvansaet;

import net.ddns.jonasvansaet.utils.IgnoredPeople;
import net.ddns.jonasvansaet.utils.Logger;
import net.ddns.jonasvansaet.utils.WelcomeServers;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by jonas on 12/14/2016.
 */
public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        String serverId = event.getGuild().getId();
        String prefix = "!";

        if(IgnoredPeople.ignoredIds.contains(event.getAuthor().getId())){
            Logger.logEvent(event);
            Logger.logLine("Command from user " + event.getAuthor().getName() + " with ID " + event.getAuthor().getId() + " ignored");
            return;
        }

        if(WelcomeServers.prefixServers.containsKey(serverId)) {
            prefix = WelcomeServers.prefixServers.get(serverId).getPrefix();
        }

        if (!event.getAuthor().isBot() && event.getMessage().getContentRaw().startsWith(prefix)){
            Main.handleCommand(Main.parser.parse(event.getMessage().getContentRaw().toLowerCase(), event));
        }

        if (!event.getAuthor().isBot() && event.getMessage().getContentRaw().startsWith("\" ")){
            Main.handleCommand(Main.parser.parse("!getquote", event));
        }

        if (!event.getAuthor().isBot() && event.getMessage().getContentRaw().startsWith(("' "))){
            Main.handleCommand(Main.parser.parse("!insertquote", event));
        }

        if (event.getMessage().getMentionedUsers().contains(event.getJDA().getSelfUser()) && event.getMessage().getContentRaw().contains("help")){
            Main.handleCommand(Main.parser.parse("!help", event));
        }

    }
}
