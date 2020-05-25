package net.ddns.jonasvansaet.Commands.Quote;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.DA.DAQuote;
import net.ddns.jonasvansaet.Objects.Quote;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jonas on 12/18/2016.
 */
public class GetquoteCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {

        DAQuote daQuote = new DAQuote();
        Random random = new Random();

        if (ParameterParser.parseParameters(event).length !=  0){
            ArrayList<Quote> quotes = daQuote.getQuoteByName(ParameterParser.parseParameters(event)[0]);

            if (quotes.size() > 0){
                int randomNumber = random.nextInt(quotes.size());
                event.getChannel().sendMessage(quotes.get(randomNumber).getContent()).queue();
            } else {
                event.getChannel().sendMessage("*no quote found*").queue();
            }
        }


    }

    @Override
    public String help() {
        return "Gives back a quote. Usage: !getquote [quotename] OR \" [quotename] (there's a space between \" and the quotename)";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
