package net.ddns.jonasvansaet.Commands.Debug;

import net.ddns.jonasvansaet.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * Created by jonas on 12/02/2017.
 */
public class ServerinfoCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
        Guild guild = event.getGuild();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.green);
        embedBuilder.addField("name", guild.getName(), true);
        embedBuilder.addField("id", guild.getId(), true);
        embedBuilder.addField("Created at", guild.getTimeCreated().format(formatter), true);
        embedBuilder.addField("owner", guild.getOwner().getEffectiveName(), true);
        embedBuilder.addField("number of members", Integer.toString(guild.getMembers().size()), true);
        embedBuilder.setThumbnail(guild.getIconUrl());
        MessageEmbed messageEmbed = embedBuilder.build();

        event.getChannel().sendMessage(messageEmbed).queue();
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
