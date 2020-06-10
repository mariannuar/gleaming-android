package com.example.gleaming.model;

public class Taggies {

    private Tags tags;
    private boolean is_hidden;
    private int slot;

    public Taggies(Tags tags, boolean is_hidden, int slot) {
        this.tags = tags;
        this.is_hidden = is_hidden;
        this.slot = slot;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public boolean isIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(boolean is_hidden) {
        this.is_hidden = is_hidden;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "Taggies{" +
                "tags=" + tags +
                ", is_hidden=" + is_hidden +
                ", slot=" + slot +
                '}';
    }

}
