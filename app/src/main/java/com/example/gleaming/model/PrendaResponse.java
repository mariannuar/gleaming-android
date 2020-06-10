package com.example.gleaming.model;

import java.util.ArrayList;

public class PrendaResponse {

    private ArrayList<Prenda> prendas;

    public PrendaResponse(ArrayList<Prenda> results) {
        this.prendas = results;
    }

    public PrendaResponse() {
    }

    public ArrayList<Prenda> getResults() {
        return prendas;
    }

    public void setResults(ArrayList<Prenda> results) {
        this.prendas = results;
    }

    @Override
    public String toString() {
        return "PrendaResponse{" +
                "results=" + prendas +
                '}';
    }
}
