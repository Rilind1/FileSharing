package com.example.filesharing_up.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Afatet implements Serializable {
    private String id;
    private String title;
    private ArrayList<Material> materials;

    public Afatet() {
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

    public ArrayList<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<Material> materials) {
        this.materials = materials;
    }
}
