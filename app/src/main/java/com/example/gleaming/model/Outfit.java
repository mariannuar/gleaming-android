package com.example.gleaming.model;

public class Outfit {

    private int id;
    private String description;
    private String img;

    public Outfit() {
    }

    public Outfit(int id, String description, String img) {
        this.id = id;
        this.description = description;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Outfit{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
