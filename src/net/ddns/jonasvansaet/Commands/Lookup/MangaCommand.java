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
public class MangaCommand implements Command {
    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        ParameterParser parameterParser = new ParameterParser();
        String anime = parameterParser.unparsedParameter(event).replace(" ", "+");
        String xml = ReadUrl.readUrlWithAuth("https://myanimelist.net/api/manga/search.xml?q=" + anime, Config.malUser, Config.malPassword);
        EmbedBuilder embedBuilder = new EmbedBuilder();

        if (!xml.equals("")){
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document doc = documentBuilder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));

                NodeList nodeList = doc.getElementsByTagName("entry");
                Node entry = nodeList.item(0);
                Element element = (Element) entry;

                String name = element.getElementsByTagName("title").item(0).getTextContent();
                String englishName = element.getElementsByTagName("english").item(0).getTextContent();
                String image = element.getElementsByTagName("image").item(0).getTextContent();
                String synopsis = element.getElementsByTagName("synopsis").item(0).getTextContent();
                synopsis = StringEscapeUtils.unescapeXml(synopsis);
                synopsis = synopsis.replace("<br />", "");
                if (synopsis.length() >= 1200){
                    synopsis = synopsis.substring(0, 1200) + " ...";
                }
                String id = element.getElementsByTagName("id").item(0).getTextContent();

                embedBuilder.addField(name, englishName, true);
                embedBuilder.addField("Description", synopsis, true);
                embedBuilder.addField("Link", "https://myanimelist.net/manga/" + id, true);
                embedBuilder.setThumbnail(image);
                embedBuilder.setColor(Color.GREEN);

            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
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
