package net.ddns.jonasvansaet.Commands.Admin;

import net.ddns.jonasvansaet.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * Created by jonas on 6/04/2017.
 */
public class UnMuteCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        if(event.getGuild().getMember(event.getAuthor()).hasPermission(Permission.MESSAGE_MANAGE)){
            List<User> users = event.getMessage().getMentionedUsers();

            for (User user: users){
                if(event.getGuild().getMember(user).getRoles().contains(event.getGuild().getRolesByName("QTBotMute", true).get(0))){
                    event.getGuild().getController().removeRolesFromMember(event.getGuild().getMember(user), event.getGuild().getRolesByName("QTBotMute", true).get(0)).queue();
                    event.getChannel().sendMessage(user.getAsMention() + " unmuted").queue();
                }
            }
        } else {
            event.getChannel().sendMessage("Wrong permissions: Manage Messages Required").queue();
        }


    }

    @Override
    public String help() {
        return "Unmutes a muted user. Usage: !unmute [mention user]";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
