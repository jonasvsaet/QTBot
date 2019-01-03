package net.ddns.jonasvansaet.Commands.Lookup;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import net.ddns.jonasvansaet.Command;
import net.ddns.jonasvansaet.utils.Config;
import net.ddns.jonasvansaet.utils.ParameterParser;
import net.ddns.jonasvansaet.utils.ReadUrl;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by jonas on 12/20/2016.
 */
public class ImgCommand implements Command {

    @Override
    public boolean called(MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(MessageReceivedEvent event) {
        String key = Config.googleKey;
        String query;
        Random random = new Random();
        if (ParameterParser.unparsedParameter(event) == ""){
            query = "Cheese Pizza";
        } else {
            query = ParameterParser.unparsedParameter(event);
        }


        try {
            Customsearch customsearch = new Customsearch.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("Quotebot Search").build();

            Customsearch.Cse.List list = customsearch.cse().list(query);
            list.setCx(Config.googleCx);
            list.setNum((long)10);
            list.setKey(key);
            list.setSafe("medium");
            list.setSearchType("image");

            Search results = list.execute();
            List<Result> items = results.getItems();
            Result result = items.get(random.nextInt(items.size()));
            event.getChannel().sendMessage(result.getLink()).queue();


        } catch (Exception e){
            System.out.println(e);

            HashMap<String, String> httpParameters = new HashMap<>();
            httpParameters.put("Authorization", "Client-ID " + Config.googleClientId);
            String json =  ReadUrl.readUrlWithParameters("https://api.imgur.com/3/gallery/search/top/?q=" + query.replace(" ", "%20"), httpParameters);

            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("data");

            if (jsonArray.length() <= 0){
                event.getChannel().sendMessage("nothing found").queue();
            } else {
                JSONObject nonAlbum = findNonAlbum(jsonArray);
                event.getChannel().sendMessage(nonAlbum.getString("link")).queue();
            }
        }
    }

    @Override
    public String help() {
        return "Gives back the first google image. Usage: !img [query]";
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    private JSONObject findNonAlbum(JSONArray array){
        Random random = new Random();
        int randomIndex = random.nextInt(array.length());

        JSONObject selectedObject = array.getJSONObject(randomIndex);

        if(selectedObject.getBoolean("is_album")){
            return findNonAlbum(array);
        } else {
            return selectedObject;
        }
    }
}


