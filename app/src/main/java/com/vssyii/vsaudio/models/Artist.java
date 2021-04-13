package com.vssyii.vsaudio.models;

public class Artist {
    public final long id;
    public final String artName;
    public final int albumCount;
    public final int songCount;

    public Artist() {
        id = -1;
        artName = "";
        albumCount = -1;
        songCount = -1;
    }

    public Artist(long id, String artName, int albumCount, int songCount) {
        this.id = id;
        this.artName = artName;
        this.albumCount = albumCount;
        this.songCount = songCount;
    }
}
