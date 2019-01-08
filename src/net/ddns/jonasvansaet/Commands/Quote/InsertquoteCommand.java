package net.ddns.jonasvansaet.Commands.Quote;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.DA.DAQuote;
import net.ddns.jonasvansaet.Objects.Quote;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by jonas on 12/19/2016.
 */
public class InsertquoteCommand implements Command {
    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        DAQuote daQuote = new DAQuote();
        Quote quote = new Quote();

        String[] rawSplit = event.getMessage().getContentRaw().split(" ");
        String lowercase = rawSplit[0].toLowerCase();

        if (event.getMessage().getMentionedUsers().size() > 0){
            event.getChannel().sendMessage("Do not mention people in the quotes please").queue();
        } else {

            if (rawSplit[0].toLowerCase().equals("!insertquote") || rawSplit[0].equals("'")){

                String[] parameters = ParameterParser.parseParameters(event);
                String name = parameters[0];
                String unparsedParameters = ParameterParser.unparsedParameter(event);
                String content = unparsedParameters.substring(name.length() + 1);

                if (content.equals("") || name.equals("")){

                    event.getChannel().sendMessage("*Please fill up the quote*").queue();

                } else {

                    quote.setName(name);
                    quote.setContent(content);
                    daQuote.insertQuote(quote);
                    event.getChannel().sendMessage("*Quote added*").queue();

                }

            }
        }


    }

    @Override
    public String help() {
        return "Adds a quote to the database. Usage: !insertquote OR ' [quotename] [content of quote]";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
