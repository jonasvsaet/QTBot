package net.ddns.jonasvansaet.Objects;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Reminders {
    private long id;
    private String userId;
    private String reminder;
    private Timestamp time;
    private String channelId;

    public Reminders(){}

    public Reminders(long id, String userId, String reminder, Timestamp time, String channelId) {
        this.id = id;
        this.userId = userId;
        this.reminder = reminder;
        this.time = time;
        this.channelId = channelId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
