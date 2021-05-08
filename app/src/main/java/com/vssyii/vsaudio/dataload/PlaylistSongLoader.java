package com.vssyii.vsaudio.dataload;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.vssyii.vsaudio.models.Song;

import java.util.ArrayList;
import java.util.List;

public class PlaylistSongLoader {
    public static List<Song> getAllPlaylistSongs(Context context, int playlistId, List<Integer> trackPlaylist_ID) {
        List<Song> playlistSongList = new ArrayList<>();

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
                MediaStore.Audio.Media.DATA,//8
        };
        String sortOrder = MediaStore.Audio.Media.DISPLAY_NAME;

        if (trackPlaylist_ID != null) {
            for (int i = 0; i < trackPlaylist_ID.size(); i++) {
                String selection = "is_music=1 and title != '' and track= " + trackPlaylist_ID.get(i);
                Cursor cursor = context.getContentResolver().query(uri, projection,
                        selection, null, sortOrder);

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        int trackIdNumber = trackPlaylist_ID.get(i);
                        playlistSongList.add(new Song(cursor.getLong(0), cursor.getString(1), cursor.getLong(2), cursor.getString(3),
                                cursor.getLong(4),cursor.getString(5), cursor.getInt(6), trackIdNumber, cursor.getString(8)));
                    }
                    while (cursor.moveToNext());

                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }
        return playlistSongList;
    }
}
