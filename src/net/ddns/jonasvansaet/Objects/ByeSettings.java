package net.ddns.jonasvansaet.Objects;

/**
 * Created by jonas on 7/02/2017.
 */
public class ByeSettings {
    private String serverId;
    private boolean byeEnabled;
    private String byeMessage;
    private String byeChannelId;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public boolean isByeEnabled() {
        return byeEnabled;
    }

    public void setByeEnabled(boolean byeEnabled) {
        this.byeEnabled = byeEnabled;
    }

    public String getByeMessage() {
        return byeMessage;
    }

    public void setByeMessage(String byeMessage) {
        this.byeMessage = byeMessage;
    }

    public String getByeChannelId() {
        return byeChannelId;
    }

    public void setByeChannelId(String byeChannelId) {
        this.byeChannelId = byeChannelId;
    }
}
