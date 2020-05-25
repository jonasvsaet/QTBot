package net.ddns.jonasvansaet.Commands.Lookup;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.ddns.jonasvansaet.utils.ReadUrl;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;

//import org.jsoup.Jsoup;

/**
 * Created by jonas on 17/02/2017.
 */
public class MangaCommand implements Command {
    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        ParameterParser parameterParser = new ParameterParser();
        String anime = parameterParser.unparsedParameter(event).replace(" ", "+");
        String json = ReadUrl.readUrl("https://api.jikan.moe/v3/search/manga?q=" + anime.replace(" ", "%20") + "$page=1");
        EmbedBuilder embedBuilder = new EmbedBuilder();

        JSONObject object = new JSONObject(json);
        JSONArray list = object.getJSONArray("results");

        if(list.length() > 0){
            JSONObject first = list.getJSONObject(0);

            String name = first.getString("title");
            String image = first.getString("image_url");
            String synopsis = first.getString("synopsis");
            if (synopsis.length() >= 1200){
                synopsis = synopsis.substring(0, 1200) + " ...";
            }
            String url = first.getString("url");

            embedBuilder.setTitle(name);
            embedBuilder.addField("Description", synopsis, false);
            embedBuilder.addField("Link", url, false);
            embedBuilder.setThumbnail(image);
            embedBuilder.setColor(Color.GREEN);
        }else {
            embedBuilder.setDescription("Manga not found");
            embedBuilder.setColor(Color.RED);
        }

        MessageEmbed messageEmbed = embedBuilder.build();
        event.getChannel().sendMessage(messageEmbed).queue();
    }

    @Override
    public String help() {
        return "Search for manga on My anime list";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
