package com.example.filesharing_up.models;

public class User {
    private String name;
    private String username;
    private String email;
    private String Id;
    private String fakultetId;
    private String drejtimId;

    public User(String name, String username, String email, String id, String fakultetId, String drejtimId) {
        this.name = name;
        this.username = username;
        this.email = email;
        Id = id;
        this.fakultetId = fakultetId;
        this.drejtimId = drejtimId;
    }

    public String getFakultetId() {
        return fakultetId;
    }

    public void setFakultetId(String fakultetId) {
        this.fakultetId = fakultetId;
    }

    public String getDrejtimId() {
        return drejtimId;
    }

    public void setDrejtimId(String drejtimId) {
        this.drejtimId = drejtimId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
