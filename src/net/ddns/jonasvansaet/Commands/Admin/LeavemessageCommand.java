package net.ddns.jonasvansaet.Commands.Admin;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.DA.DAByeSettings;
import net.ddns.jonasvansaet.DA.DAServersettings;
import net.ddns.jonasvansaet.JoinListener;
import net.ddns.jonasvansaet.utils.ByeServers;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.ddns.jonasvansaet.utils.WelcomeServers;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by jonas on 7/02/2017.
 */

public class LeavemessageCommand implements Command {
    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        ParameterParser parameterParser = new ParameterParser();
        User user = event.getAuthor();
        Member member = event.getGuild().getMember(user);
        Guild guild = event.getGuild();
        String[] parameters = parameterParser.parseParameters(event);
        DAByeSettings daByeSettings = new DAByeSettings();
        DAByeSettings daServerChecker = new DAByeSettings();


        if(member.hasPermission(Permission.ADMINISTRATOR)){
            if(!daServerChecker.serverExists(guild.getId())){
                daServerChecker.createServer(guild.getId(), false, "%user% left the server", event.getChannel().getId());
                event.getChannel().sendMessage("Server not yet found, Creating").queue();
            }

            switch (parameters[0].toLowerCase()){
                case "enable":
                    //enable listening on the server
                    daByeSettings.updateByeMessageEnabled(guild.getId(), true, event.getChannel().getId());
                    event.getChannel().sendMessage("Enabling Leavemessages on server").queue();
                    break;
                case "disable":
                    //disable listening on the server
                    daByeSettings.updateByeMessageEnabled(guild.getId(), false, event.getChannel().getId());
                    event.getChannel().sendMessage("Disabling Leavemessages on server").queue();
                    break;
                case "setmessage":
                    //set the message on the server
                    String command = event.getMessage().getRawContent().toLowerCase();
                    int index = command.indexOf("setmessage ") + 11;
                    String parameter = event.getMessage().getRawContent().substring(index);
                    daByeSettings.updateByeMessage(guild.getId(), parameter, event.getChannel().getId());
                    event.getChannel().sendMessage("Message set to \"" + parameter + "\"").queue();
                    break;
                default:
                    event.getChannel().sendMessage("Wrong usage of the command, use \n!leavemessage [enable|disable|setmessage] <leavemessage> (use %user% for the name of the person that joined)").queue();
            }
            new ByeServers().updateByeMessageServers();
        } else {
            event.getChannel().sendMessage("You need the administration permission to use this command").queue();
        }
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}


