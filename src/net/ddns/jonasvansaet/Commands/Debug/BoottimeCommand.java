package net.ddns.jonasvansaet.Commands.Debug;

import net.ddns.jonasvansaet.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jonas on 12/17/2016.
 */
public class BoottimeCommand implements Command {
    LocalDateTime bootTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");

    public BoottimeCommand(LocalDateTime bootTime) {
        this.bootTime = bootTime;
    }

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        //embedBuilder.setThumbnail("");
        embedBuilder.addField("Bot boottime", this.bootTime.format(formatter), true);
        embedBuilder.setColor(Color.green);
        MessageEmbed messageEmbed = embedBuilder.build();
        event.getChannel().sendMessage(messageEmbed).queue();

    }

    @Override
    public String help() {
        return "returns when the bot booted";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
