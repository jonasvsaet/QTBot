package net.ddns.jonasvansaet.Commands.Debug;

import net.ddns.jonasvansaet.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by jonas on 12/17/2016.
 */
public class UptimeCommand implements Command {
    LocalDateTime bootTime;

    public UptimeCommand(LocalDateTime bootTime) {
        this.bootTime = bootTime;
    }

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        LocalDateTime nowTime = LocalDateTime.now();
        Duration difference = Duration.between(bootTime, nowTime);
        long s = difference.getSeconds();
        String runningFor = "Bot has been running for " + String.format("%d days %d hours %02d minutes %02d seconds", s/86400, (s%86400)/3600, ((s%86400)%3600)/60, (s%60));

        EmbedBuilder embedBuilder = new EmbedBuilder();
        //embedBuilder.setThumbnail("");
        embedBuilder.addField("Bot uptime", runningFor, true);
        embedBuilder.setColor(Color.green);
        MessageEmbed messageEmbed = embedBuilder.build();
        event.getChannel().sendMessage(messageEmbed).queue();
    }

    @Override
    public String help() {
        return "Tells you how long the bot has been running";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
