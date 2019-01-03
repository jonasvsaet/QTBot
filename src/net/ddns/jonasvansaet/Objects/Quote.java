package net.ddns.jonasvansaet.Objects;

/**
 * Created by jonas on 12/18/2016.
 */
public class Quote {
    private int id;
    private String name;
    private String content;

    public Quote() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
