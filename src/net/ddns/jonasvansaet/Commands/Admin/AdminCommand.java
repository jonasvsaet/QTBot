package net.ddns.jonasvansaet.Commands.Admin;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.Main;
import net.ddns.jonasvansaet.utils.Config;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
                    event.getJDA().getPresence().setActivity(Activity.playing(parameterParser.unparsedParameter(event).replace("setgame", "")));
                    break;

                case "createMuteRole":
                    event.getChannel().sendMessage("Mute Role doesn't exist yet, creating role ...").queue();

                    List<TextChannel> channels = event.getGuild().getTextChannels();

                    event.getGuild().createRole().queue((Role role) -> {
                        role.getManager().revokePermissions(Permission.values()).queue();
                        role.getManager().setName("QTBotMute").queue();

                        for(TextChannel channel : channels){
                            channel.createPermissionOverride(role).queue(permissionOverride -> permissionOverride.getManager().deny(Permission.MESSAGE_WRITE).queue());
                        }
                    });

                    event.getChannel().sendMessage("Mute role created").queue();
                    break;

                case "randomping":
                    Random random = new Random();
                    Member randomMember = event.getGuild().getMembers().get(random.nextInt(event.getGuild().getMembers().size()));
                    event.getChannel().sendMessage(randomMember.getAsMention()).queue();
                    break;
                default:
                    event.getChannel().sendMessage("Wrong usage, use listservers, getlog, countservers or setgame").queue();
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
