package com.example.gleaming.model;

import java.util.ArrayList;

public class ProductSingleResponse {

    private Prenda prenda;
    private ArrayList<Prenda> prendas;
    private ArrayList<Tags> tags;

    public ProductSingleResponse() {
    }

    public ArrayList<Prenda> getPrendas() {
        return prendas;
    }

    public void setPrendas(ArrayList<Prenda> prendas) {
        this.prendas = prendas;
    }

    public ProductSingleResponse(Prenda prenda, ArrayList<Prenda> prendas, ArrayList<Tags> tags) {
        this.prenda = prenda;
        this.prendas = prendas;
        this.tags = tags;
    }

    public Prenda getPrenda() {
        return prenda;
    }

    public void setPrenda(Prenda prenda) {
        this.prenda = prenda;
    }

    public ArrayList<Tags> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tags> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ProductSingleResponse{" +
                "prenda=" + prenda +
                ", tags=" + tags +
                '}';
    }
}
