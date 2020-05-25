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
public class EightBallCommand implements Command {
    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        Random random = new Random();
        String[] answers = {"yes", "most likely", "no", "no u", "maybe", "ask again", "same tbh", "why would you even ask that", "nah", "the answer is in your heart", "ok bye", "ye"};
        int answer = random.nextInt(answers.length);
        ParameterParser parameterParser = new ParameterParser();
        String question = parameterParser.unparsedParameter(event);


        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/e/eb/Magic_eight_ball.png");
        if (question != ""){
            embedBuilder.setDescription(question);
        }
            embedBuilder.addField("answer", answers[answer], true);


        embedBuilder.setColor(Color.green);
        MessageEmbed messageEmbed = embedBuilder.build();
        event.getChannel().sendMessage(messageEmbed).queue();
    }

    @Override
    public String help() {
        return "answers your questions";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
