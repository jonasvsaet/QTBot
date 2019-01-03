package net.ddns.jonasvansaet.utils;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.io.BufferedWriter;
import java.time.LocalDateTime;

/**
 * Created by jonas on 13/01/2017.
 */
public class Logger {

    public Logger() {
    }

    public static void logLine (String log){
        LocalDateTime now = LocalDateTime.now();
        String logString = now.toString() + "[ProgramLog]: " + log;

        try{
            FileWriter fw = new FileWriter("quotebot.log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(logString);
            System.out.println(logString);
            bw.close();
        } catch (Exception e){

        }
    }

    public static void logEvent (MessageReceivedEvent event){
        LocalDateTime now = LocalDateTime.now();
        String logString = now.toString() + " [guild] " + event.getGuild().getName() + " [Channel] " + event.getChannel().getName() + " [user] " + event.getAuthor().getName() + ": " + event.getMessage().getContentRaw();
        try{
            FileWriter fw = new FileWriter("quotebot.log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(logString);
            System.out.println(logString);
            bw.close();
        } catch (Exception e){

        }
    }
}
