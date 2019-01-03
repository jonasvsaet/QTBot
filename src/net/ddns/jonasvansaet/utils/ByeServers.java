package net.ddns.jonasvansaet.utils;

import net.ddns.jonasvansaet.DA.DAByeSettings;
import net.ddns.jonasvansaet.DA.DAServersettings;
import net.ddns.jonasvansaet.Objects.ByeSettings;
import net.ddns.jonasvansaet.Objects.Serversettings;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jonas on 7/02/2017.
 */
public class ByeServers {
    public static HashMap<String, ByeSettings> byeservers = new HashMap<>();

    public void updateByeMessageServers () {
        DAByeSettings dabyeSettings = new DAByeSettings();
        HashMap<String, ByeSettings> enabledServers = new HashMap<>();

        ArrayList<ByeSettings> allByeServers = dabyeSettings.getAllByeSettings();
        enabledServers.clear();

        for (ByeSettings byeSettings:allByeServers){
            if (byeSettings.isByeEnabled()) {
                enabledServers.put(byeSettings.getServerId(), byeSettings);
            }
        }

        this.byeservers = enabledServers;
    }
}
