package com.example.gleaming.model;

public class Prenda {

    private int id;
    private String description;
    private String img;
    private double latitude;
    private double longitude;
    private String garment;

    public Prenda() {
    }

    public Prenda(int id, String description, String img, double latitude, double longitude, String garment) {
        this.id = id;
        this.description = description;
        this.img = img;
        this.latitude = latitude;
        this.longitude = longitude;
        this.garment  = garment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGarment() {
        return garment;
    }

    public void setGarment(String garment) {
        this.garment = garment;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Prenda{" +
                "id=" + id +
                ", description='" + description + '\''+
                ", img='" + img +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", garment=" + garment +
                '}';
    }
}
