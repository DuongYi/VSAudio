package com.vssyii.vsaudio.models;

public class Playlist {
    public final int id;
    public final String name;

    public Playlist()  {
        id = -1;
        name = "";
    }

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
