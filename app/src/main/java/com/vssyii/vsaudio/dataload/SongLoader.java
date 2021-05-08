package com.vssyii.vsaudio.dataload;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vssyii.vsaudio.models.Playlist;
import com.vssyii.vsaudio.models.Song;

import java.util.ArrayList;
import java.util.List;

public class SongLoader {
    public static List<Song> getAllSongs(Context context) {
        String selection = "is_music=1";
        return getSong(makeSongByCursor(context, selection, null));
    }

    public static List<Song> getAllSongAudio(Context context) {
        List<Song> songList = new ArrayList<>();
        songList.addAll(getAllSongs(context));
        return songList;
    }

    public static Song getSongByID(Context context, long songID) {
        return  song(makeSongCursor(context, MediaStore.Audio.Media._ID + "=?",
                new String[]{String.valueOf(songID)}));
    }

    public static Song song(Cursor cursor) {
        Song song = new Song();
        if (cursor.moveToFirst() && cursor != null) {
            song = getSongFromCursor(cursor);
        }
        if (cursor != null) {
            cursor.close();
        }
        return song;
    }

    public static List<Song> getSong(Cursor cursor) {
        List<Song> songList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                songList.add(getSongFromCursor(cursor));
            }
            while (cursor.moveToNext());

            if (cursor != null) {
                cursor.close();
            }
        }
        return songList;
    }

    public static Song getSongFromCursor(Cursor cursor) {
        long id = cursor.getLong(0);
        String title = cursor.getString(1);
        long album_id = cursor.getLong(2);
        String album = cursor.getString(3);
        long artist_id = cursor.getLong(4);
        String artist = cursor.getString(5);
        int duration = cursor.getInt(6);
        int track = cursor.getInt(7);
        String path = cursor.getString(8);

        return new Song(id, title, album_id, album, artist_id, artist,duration, track, path);
    }

    public static Cursor makeSongCursor(Context context, String selection, String[] selectionValues) {
        return makeSongByCursor(context, selection, selectionValues);
    }

    public static Cursor makeSongByCursor(Context context, String selection, String[] selectionArg) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[] {
                MediaStore.Audio.Media._ID,//0
                MediaStore.Audio.Media.TITLE,//1
                MediaStore.Audio.Media.ALBUM_ID,//2
                MediaStore.Audio.Media.ALBUM,//3
                MediaStore.Audio.Media.ARTIST_ID,//4
                MediaStore.Audio.Media.ARTIST,//5
                MediaStore.Audio.Media.DURATION,//6
                MediaStore.Audio.Media.TRACK,//7
                MediaStore.Audio.Media.DATA//8
        };
        String sortOrder = MediaStore.Audio.Media.DISPLAY_NAME;
        Cursor cursor = context.getContentResolver().query(uri, projection,
                selection, null, sortOrder);
        return cursor;
    }
}
