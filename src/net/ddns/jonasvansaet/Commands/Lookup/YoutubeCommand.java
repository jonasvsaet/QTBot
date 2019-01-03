package net.ddns.jonasvansaet.Commands.Lookup;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.utils.Config;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.List;

/**
 * Created by jonas on 12/20/2016.
 */
public class YoutubeCommand implements Command{

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        YouTube youtube;
        YouTube.Search.List query;

        final String KEY = Config.googleKey;

        String queryTerm;

        if (ParameterParser.unparsedParameter(event) == ""){
            queryTerm = "remove kebab loop";
        } else {
            queryTerm = ParameterParser.unparsedParameter(event);
        }

        try{
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("Quotebot Search").build();

            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(KEY);
            search.setQ(queryTerm);
            search.setType("video");
            search.setMaxResults((long)1);
            //search.setSafeSearch("strict");

            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null){
                for (SearchResult result:searchResultList) {
                    event.getChannel().sendMessage("http://www.youtube.com/watch?v=" +  result.getId().getVideoId()).queue();
                }
            } else {
                event.getChannel().sendMessage("no video found").queue();
            }

        } catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public String help() {
        return "Gives the first youtube video result. Usage: !yt [query]";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
