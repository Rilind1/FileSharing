package com.example.filesharing_up.models;

public class Material {

    private String id;
    private String title;
    private String type;
    private String url;

    public Material() {
    }

    public Material(String id, String title, String type, String url) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
