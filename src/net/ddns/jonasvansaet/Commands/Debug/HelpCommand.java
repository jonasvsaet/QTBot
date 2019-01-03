package net.ddns.jonasvansaet.Commands.Debug;

import com.sun.xml.internal.bind.v2.util.QNameMap;
import net.ddns.jonasvansaet.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonas on 12/18/2016.
 */
public class HelpCommand implements Command{
    private HashMap<String, Command> commands;

    public HelpCommand(HashMap<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        //String result = "https://discord.gg/ZZp86F6\n\nif you can't find the answer join the server for further help\n \nadd QT Bot to your server: \nhttps://goo.gl/7tBuiQ  \n```";
        String commandHelpString = "";
        for (Map.Entry<String, Command> entry : commands.entrySet()){

            Command command = entry.getValue();
            commandHelpString += "!" + entry.getKey() + ": " + command.help() + "\n";
        }


        event.getChannel().sendMessage("Check your DMs").queue();
        try {
            event.getAuthor().openPrivateChannel().queue();
        } catch (Exception e){
            System.out.println(e);
        }

        //event.getAuthor().getPrivateChannel().sendMessage(result).queue();

        EmbedBuilder embedBuilder =  new EmbedBuilder();
        //embedBuilder.setThumbnail("");
        embedBuilder.setColor(Color.green);
        //embedBuilder.addField("Commands", commandHelpString, false);
        embedBuilder.addField("Add quotebot to your server", "https://goo.gl/7tBuiQ", false);
        embedBuilder.addField("Additional help","https://discord.gg/ZZp86F6", false );
        MessageEmbed messageEmbed = embedBuilder.build();

        final String helpString = commandHelpString;

        event.getAuthor().openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(messageEmbed).queue();
            channel.sendMessage("```" + helpString + "```").queue();
        });

    }

    @Override
    public String help() {
        return "Returns this";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
