package net.ddns.jonasvansaet.Commands.Debug;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemindMeCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        final Pattern daysPattern = Pattern.compile("([0-9]+d)");
        final Pattern hoursPattern = Pattern.compile("([0-9]+h)");
        final Pattern minutesPattern = Pattern.compile("([0-9]+m)");
        final Pattern secondsPattern = Pattern.compile("([0-9]+s)");

        String parameters = ParameterParser.unparsedParameter(event);

        Matcher daysMatcher = daysPattern.matcher(parameters);
        Matcher hoursMatcher = hoursPattern.matcher(parameters);
        Matcher minutesMatcher = minutesPattern.matcher(parameters);
        Matcher secondsMatcher = secondsPattern.matcher(parameters);

        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        String found;
        String returnString = "";

        if(daysMatcher.find()){
            found = daysMatcher.group();
            days = Integer.parseInt(found.replace("d", ""));
            parameters = parameters.replace(found, "");
        }

        if(hoursMatcher.find()){
            found = hoursMatcher.group();
            hours = Integer.parseInt(found.replace("h", ""));
            parameters = parameters.replace(found, "");
        }

        if(minutesMatcher.find()){
            found = minutesMatcher.group();
            minutes = Integer.parseInt(found.replace("m", ""));
            parameters = parameters.replace(found, "");
        }

        if(secondsMatcher.find()){
            found = secondsMatcher.group();
            seconds = Integer.parseInt(found.replace("s", ""));
            parameters = parameters.replace(found, "");
        }

        final String message = parameters.trim();
        int waitMillis = days * 86400000 + hours * 3600000 + minutes * 60000 + seconds * 1000;

        event.getChannel().sendMessage("Reminding " + event.getAuthor().getAsMention() + " in " + days + "d " + hours + "h " + minutes + "m " + seconds + "s: " + message).queue();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " " +  message).queue();
                    }
                },
                waitMillis
        );

    }

    @Override
    public String help() {
        return "Reminds you in a given time with a given message";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
