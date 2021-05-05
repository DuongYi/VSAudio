package com.vssyii.vsaudio.models;

public class Playlist {
    public int id;
    public String name;

    public Playlist() {
        id = -1;
        name = "VS Music";
    }

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
