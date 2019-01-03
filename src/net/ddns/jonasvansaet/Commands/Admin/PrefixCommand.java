package net.ddns.jonasvansaet.Commands.Admin;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.DA.DAPrefix;
import net.ddns.jonasvansaet.DA.DAServersettings;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.ddns.jonasvansaet.utils.WelcomeServers;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PrefixCommand implements Command {
    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        User user = event.getAuthor();
        Member member = event.getGuild().getMember(user);
        Guild guild = event.getGuild();
        ParameterParser parameterParser = new ParameterParser();
        String[] parameters = parameterParser.parseParameters(event);

        if(member.hasPermission(Permission.ADMINISTRATOR)){

            DAPrefix daPrefix = new DAPrefix();
            if(!daPrefix.serverExists(guild.getId())){
                daPrefix.createPrefix(guild.getId(), "!", false);
                event.getChannel().sendMessage("Server not yet found, Creating").queue();
            }

            switch (parameters[0].toLowerCase()){
                case "enable":
                    //enable prefix on the server
                    daPrefix.updatePrefixEnabled(guild.getId(), true);
                    event.getChannel().sendMessage("Prefix enabled").queue();
                    break;
                case "disable":
                    //disable prefix on the server
                    daPrefix.updatePrefixEnabled(guild.getId(), false);
                    event.getChannel().sendMessage("Prefix disabled").queue();
                    break;
                case "set":
                    daPrefix = new DAPrefix();

                    //set the prefix on the server
                    String command = event.getMessage().getContentRaw().toLowerCase();

                    if(!parameterParser.unparsedParameter(event).trim().toLowerCase().equals("set")){
                        int index = command.indexOf("set ") + 4;
                        String parameter = command.substring(index);

                        if(parameter.equals("\"") || parameter.equals("'")){
                            event.getChannel().sendMessage("You can not change your prefix into " + parameter ).queue();
                        }
                        else {
                            daPrefix.updatePrefix(guild.getId(), parameter);
                            event.getChannel().sendMessage("Prefix changed to \"" + parameter + "\"").queue();
                        }
                    }
                    else {
                        event.getChannel().sendMessage("Prefix empty").queue();
                    }

                    break;
                default:
                    event.getChannel().sendMessage("Wrong usage of the command, use \n!prefix [enable|disable|set] <prefix>").queue();
            }
            new WelcomeServers().updatePrefixServers();
        } else {
            event.getChannel().sendMessage("You need the administration permission to use this command").queue();
        }
    }

    @Override
    public String help() {
        return "Change the prefix on this server. Usage: !prefix <enable|disable|set> [prefix]. Only admin can use this command";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
