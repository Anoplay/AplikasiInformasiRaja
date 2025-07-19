package com.example.aplikasiinformasiraja;

public class Raja {
    private String id;
    private String name;
    private String reign;
    private String description;
    private String imagePath;

    public Raja(String id, String name, String reign, String description, String imagePath) {
        this.id = id;
        this.name = name;
        this.reign = reign;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getReign() { return reign; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
}
