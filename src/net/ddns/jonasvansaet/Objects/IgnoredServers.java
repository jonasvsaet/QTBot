package net.ddns.jonasvansaet.Objects;

public class IgnoredServers {
    String serverId;
    boolean enabled; //true means server gets ignored

    public IgnoredServers(){
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
