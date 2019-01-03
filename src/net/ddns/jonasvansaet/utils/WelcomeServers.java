package net.ddns.jonasvansaet.utils;

import net.ddns.jonasvansaet.DA.DAPrefix;
import net.ddns.jonasvansaet.DA.DAServersettings;
import net.ddns.jonasvansaet.Objects.Prefix;
import net.ddns.jonasvansaet.Objects.Serversettings;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jonas on 6/02/2017.
 */
public class WelcomeServers {
    public static HashMap<String, Serversettings> welcomeServers = new HashMap<>();
    public static HashMap<String, Prefix> prefixServers = new HashMap<>();

    public void updateWelcomeMessageServers () {
        DAServersettings daServersettings = new DAServersettings();
        HashMap<String, Serversettings> enabledServers = new HashMap<>();

        ArrayList<Serversettings> allServerSettings = daServersettings.getAllServerSettings();
        enabledServers.clear();

        for (Serversettings serversettings:allServerSettings){
            if (serversettings.isWelcomeEnabled()) {
                enabledServers.put(serversettings.getServerId(), serversettings);
            }
        }

        this.welcomeServers = enabledServers;
    }

    public void updatePrefixServers () {
        DAPrefix daPrefix = new DAPrefix();
        HashMap<String, Prefix> result = new HashMap<>();

        ArrayList<Prefix> allPrefixes = daPrefix.getAllPrefixes();
        prefixServers.clear();

        for(Prefix prefix : allPrefixes){
            if(prefix.isEnabled()){
                result.put(prefix.getId(), prefix);
            }
        }

        this.prefixServers = result;

    }


}
