package fr.fonkio.utils;

public class Doc {

    private String title;
    private String desc;
    private String link;
    private final String id;

    public Doc(String id, String title, String desc, String link) {
        this.title = title;
        this.desc = desc;
        this.link = link;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getLink() {
        return link;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
