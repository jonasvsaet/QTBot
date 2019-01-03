package net.ddns.jonasvansaet.utils;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by jonas on 12/14/2016.
 */
public class CommandParser {
    public CommandContainer parse (String rw, MessageReceivedEvent e){
        String prefix = "!";

        if(WelcomeServers.prefixServers.containsKey(e.getGuild().getId())) {
            prefix = WelcomeServers.prefixServers.get(e.getGuild().getId()).getPrefix();
        }

        ArrayList<String> split = new ArrayList<>();
        String raw = rw;
        String beheaded = raw.replaceFirst(Pattern.quote(prefix), "");
        String[] splitBeheaded = beheaded.split(" ");
        for(String s : splitBeheaded) {
            split.add(s);
        }
        String invoke = split.get(0);
        String[] args = new String[split.size() -1];
        split.subList(1, split.size()).toArray(args);

        return new CommandContainer(raw, beheaded, splitBeheaded, invoke, args, e);
    }

    public class CommandContainer {
        public final String raw;
        public final String beheaded;
        public final String[] splitbeheaded;
        public final String invoke;
        public final String[] args;
        public final MessageReceivedEvent event;

        public CommandContainer(String raw, String beheaded, String[] splitbeheaded, String invoke, String[] args, MessageReceivedEvent event) {
            this.raw = raw;
            this.beheaded = beheaded;
            this.splitbeheaded = splitbeheaded;
            this.invoke = invoke;
            this.args = args;
            this.event = event;
        }

        public String getRaw() {
            return raw;
        }

        public String getBeheaded() {
            return beheaded;
        }

        public String[] getSplitbeheaded() {
            return splitbeheaded;
        }

        public String getInvoke() {
            return invoke;
        }

        public String[] getArgs() {
            return args;
        }

        public MessageReceivedEvent getEvent() {
            return event;
        }
    }
}
