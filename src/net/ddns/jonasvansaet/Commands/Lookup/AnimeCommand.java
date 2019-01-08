package net.ddns.jonasvansaet.Commands.Lookup;

import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.utils.Config;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.ddns.jonasvansaet.utils.ReadUrl;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
//import org.jsoup.Jsoup;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.ByteArrayInputStream;

/**
 * Created by jonas on 17/02/2017.
 */
public class AnimeCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        ParameterParser parameterParser = new ParameterParser();
        String anime = parameterParser.unparsedParameter(event).replace(" ", "+");
        String json = ReadUrl.readUrl("https://api.jikan.moe/v3/search/anime?q=" + anime.replace(" ", "%20") + "$page=1");
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
            embedBuilder.setDescription("Anime not found");
            embedBuilder.setColor(Color.RED);
        }

        MessageEmbed messageEmbed = embedBuilder.build();
        event.getChannel().sendMessage(messageEmbed).queue();
    }

    @Override
    public String help() {
        return "Search for anime on My anime list";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
