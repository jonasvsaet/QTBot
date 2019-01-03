package net.ddns.jonasvansaet.utils;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

/**
 * Created by jonas on 12/17/2016.
 */
public class ParameterParser {

    public static String unparsedParameter(MessageReceivedEvent e){
        String raw = e.getMessage().getContentRaw();
        String result;
        if (raw.contains(" ")){
            int firstSpaceIndex = raw.indexOf(' ');
            return raw.substring(firstSpaceIndex + 1);
        } else return "";
    }

    public static String[] parseParameters(MessageReceivedEvent e){
        ArrayList<String> split = new ArrayList<>();
        String unparsed = unparsedParameter(e);
        String[] splitUnparsed = unparsed.split(" ");

        for (String s : splitUnparsed) {
            if (!s.isEmpty()){
                split.add(s);
            }
        }
        String[] result = split.toArray(new String[split.size()]);
        return result;
    }

}
