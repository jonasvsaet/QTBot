package net.ddns.jonasvansaet.Commands.Admin;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.Main;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.impl.RoleImpl;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.role.RoleCreateEvent;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.managers.RoleManager;
import net.dv8tion.jda.core.managers.RoleManagerUpdatable;
import net.dv8tion.jda.core.requests.Route;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by jonas on 6/04/2017.
 */
public class MuteCommand implements Command {
    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        if(event.getGuild().getMember(event.getAuthor()).hasPermission(Permission.MESSAGE_MANAGE)){
            Timer timer = new Timer();


            List<User> users = event.getMessage().getMentionedUsers();
            String[] parameters = ParameterParser.parseParameters(event);
            List<Role> roles = event.getGuild().getRolesByName("QTBotMute", true);

            //creating role for mute
            if (roles.size() <= 0){
                event.getChannel().sendMessage("Mute Role doesn't exist yet, creating role ...").queue();

                List<TextChannel> channels = event.getGuild().getTextChannels();

                event.getGuild().getController().createRole().queue((Role role) -> {
                    role.getManager().revokePermissions(Permission.values()).queue();
                    role.getManager().setName("QTBotMute").queue();

                    for(TextChannel channel : channels){
                        channel.createPermissionOverride(role).queue(permissionOverride -> permissionOverride.getManager().deny(Permission.MESSAGE_WRITE).queue());
                    }
                });

                event.getChannel().sendMessage("Role created, reuse command to mute someone").queue();
            } else {
                for (User user: users) {
                    if(parameters.length != 1){
                        try {

                            int timeToBan = Integer.parseInt(parameters[1]);
                            event.getChannel().sendMessage("muting " + user.getName() + " for " + timeToBan + " minutes").queue();
                            event.getGuild().getController().addRolesToMember(event.getGuild().getMember(user), roles.get(0)).queue();
                            Role banRole = roles.get(0);

                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {

                                    if (event.getGuild().getMember(user).getRoles().contains(event.getGuild().getRolesByName("QTBotMute", true).get(0))){
                                        event.getChannel().sendMessage("Unmuting " + user.getAsMention()).queue();
                                        event.getGuild().getController().removeRolesFromMember(event.getGuild().getMember(user), banRole).queue();
                                    }
                                }
                            }, timeToBan * 60 * 1000);
                        } catch (Exception e){
                            event.getChannel().sendMessage("Wrong usage of command, use as !mute [user mention] [(optional) minutes to ban]").queue();
                            System.out.println(e);
                        }


                    } else {
                        event.getChannel().sendMessage("muting " + user.getName() + " until unmuted").queue();
                        event.getGuild().getController().addRolesToMember(event.getGuild().getMember(user), roles.get(0)).queue();
                    }

                }
            }

        }else {
            event.getChannel().sendMessage("Wrong permissions: Manage Messages Required").queue();
        }

    }

    @Override
    public String help() {
        return "Makes the mentioned user unable to type in chat. Requires manage messages permission. Usage: !mute [mention user] [(optional) minutes to mute]";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

}
