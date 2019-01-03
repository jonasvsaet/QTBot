package net.ddns.jonasvansaet.Objects;

/**
 * Created by jonas on 1/02/2017.
 */
public class Serversettings {
    private String serverId;
    private boolean welcomeEnabled;
    private String welcomeMessage;
    private String welcomeChannelId;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public boolean isWelcomeEnabled() {
        return welcomeEnabled;
    }

    public void setWelcomeEnabled(boolean welcomeEnabled) {
        this.welcomeEnabled = welcomeEnabled;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public String getWelcomeChannelId() {
        return welcomeChannelId;
    }

    public void setWelcomeChannelId(String welcomeChannelId) {
        this.welcomeChannelId = welcomeChannelId;
    }

}
