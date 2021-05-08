package com.vssyii.vsaudio.models;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    public int id;
    public String name;
    public List<Long> songs_id = new ArrayList<>();

    public Playlist() {
        id = -1;
        name = "VS Music";
        songs_id = null;
    }

    public Playlist(int id, String name, List<Long> songs_id) {
        this.id = id;
        this.name = name;
        this.songs_id = songs_id;
    }

}
