package com.vssyii.vsaudio.models;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    public int id;
    public String name;
    public List<Long> songs_id;

    public Playlist() {
        id = -1;
        name = "VS Music";
        songs_id = new ArrayList<>();
    }

    public Playlist(int id, String name, List<Long> songs_id) {
        this.id = id;
        this.name = name;
        if (songs_id != null)
            this.songs_id = songs_id;
        else
            this.songs_id = new ArrayList<>();
    }

    public void addSong(Long id) {
        this.songs_id.add(id);
    }

    public List<Long> getSongs_id() {
        return this.songs_id;
    }

}
