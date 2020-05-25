package net.ddns.jonasvansaet.Commands.Games;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Random;

/**
 * Created by jonas on 3/02/2017.
 */
public class RollCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        ParameterParser parser = new ParameterParser();
        String[] parameters = parser.parseParameters(event);
        int number;
        int result;
        Random random = new Random();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Two_red_dice_01.svg/2000px-Two_red_dice_01.svg.png");
        embedBuilder.setColor(Color.green);

        try{
            number = Integer.parseInt(parameters[0]);

        } catch (Exception e) {
            embedBuilder.setDescription("rolling for 6");
            number = 6;
        }

        result = random.nextInt(number)+1;

        embedBuilder.addField("result", Integer.toString(result), false);
        MessageEmbed messageEmbed = embedBuilder.build();

        event.getChannel().sendMessage(messageEmbed).queue();


    }

    @Override
    public String help() {
        return "rolls for a random number between 1 and your number (default is 6)";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
