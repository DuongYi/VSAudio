package com.vssyii.vsaudio.dataload;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.util.Log;

import com.vssyii.vsaudio.models.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistLoader {

    public static  ArrayList<Playlist>getAllPlaylists(Context context) {
        return getPlaylist(makePlaylistCursor(context, null, null));
    }

    public ArrayList<Playlist> getAllPlaylistsAudio(Context context) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        playlists.add(new Playlist(1, "My Music"));
        playlists.add(new Playlist(2, "Favorite Music"));

        playlists.addAll(getAllPlaylists(context));
        for (Playlist p : playlists) {
            Log.d("PLAYLISTTT", "id = " + p.id + " - name = " + p.name);
        }
        return playlists;
    }

    public static Playlist getPlaylist(Context context, int playlistId) {
        return  playlist(makePlaylistCursor(context, MediaStore.Audio.Playlists._ID + "=?",
                new String[]{String.valueOf(playlistId)}));
    }

    public Playlist getPlaylist(Context context, String playlistName) {
        return  playlist(makePlaylistCursor(context, MediaStore.Audio.Playlists.NAME + "=?",
                new String[]{playlistName}));
    }

    public static Playlist playlist(Cursor cursor) {
        Playlist playlist = new Playlist();
        if (cursor.moveToFirst() && cursor != null) {
            playlist = getPlaylistFromCursor(cursor);
        }
        if (cursor != null) {
            cursor.close();
        }
        return playlist;
    }

    public static ArrayList<Playlist>  getPlaylist(Cursor cursor) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                playlists.add(getPlaylistFromCursor(cursor));
            }
            while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return playlists;
    }

    public static Playlist getPlaylistFromCursor(Cursor cursor) {
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        return new Playlist(id, name);
    }

    public static Cursor makePlaylistCursor(Context context, String selection, String[] selectionArg) {
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String projection[] = new String[] {
                MediaStore.Audio.Playlists._ID,    //1
                MediaStore.Audio.Playlists.NAME    //2
        };
        String sortOrder = MediaStore.Audio.Playlists.DEFAULT_SORT_ORDER;
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArg, sortOrder);
        return  cursor;
    }
}
