package net.ddns.jonasvansaet.Commands.Lookup;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.ddns.jonasvansaet.utils.ReadUrl;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;



import org.json.JSONObject;

import java.awt.*;


/**
 * Created by jonas on 31/01/2017.
 */
public class UrbanDictionaryCommand implements Command{

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        ParameterParser parameterParser = new ParameterParser();
        String parameter = parameterParser.unparsedParameter(event);

        String json = ReadUrl.readUrl("http://api.urbandictionary.com/v0/define?term=" + parameter.replace(" ", "%20"));
        JSONObject obj = new JSONObject(json);
        String resultType = obj.getString("result_type");
        EmbedBuilder embedBuilder = new EmbedBuilder();

        if(!obj.getString("result_type").equals("no_results")){
            JSONObject first = obj.getJSONArray("list").getJSONObject(0);
            String defenition = first.getString("definition");
            String name = first.getString("word");
            String example = first.getString("example");
            String permalink = first.getString("permalink");

            embedBuilder.setThumbnail("https://lh5.googleusercontent.com/-rY97dP0iEo0/AAAAAAAAAAI/AAAAAAAAAGA/xm1HYqJXdMw/s0-c-k-no-ns/photo.jpg");
            embedBuilder.setColor(Color.green);
            if (defenition.length() > 1000){
                defenition = defenition.substring(0, 1000) + " ...";
            }
            if (example.length() > 1000){
                example = example.substring(0, 1000) + " ...";
            }
            embedBuilder.addField(name, defenition, false);
            embedBuilder.addField("example", example, false);
            embedBuilder.addField("permalink", permalink, false);


        } else {
            embedBuilder.setColor(Color.red);
            embedBuilder.setDescription("no results found");

        }

        MessageEmbed embed = embedBuilder.build();
        event.getChannel().sendMessage(embed).queue();

    }

    @Override
    public String help() {
        return "search for a definition on urbandictionary";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
