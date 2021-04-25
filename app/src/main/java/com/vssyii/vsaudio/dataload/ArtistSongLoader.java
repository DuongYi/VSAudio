package com.vssyii.vsaudio.dataload;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.vssyii.vsaudio.models.Song;

import java.util.ArrayList;
import java.util.List;

public class ArtistSongLoader {
    public static List<Song> getAllArtistSongs(Context context, long artist_id) {
        List<Song> artistSongList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[] {
                MediaStore.Audio.Media._ID,//0
                MediaStore.Audio.Media.TITLE,//1
                MediaStore.Audio.Media.ALBUM,//2
                MediaStore.Audio.Media.ARTIST_ID,//3
                MediaStore.Audio.Media.ARTIST,//4
                MediaStore.Audio.Media.DURATION,//5
                MediaStore.Audio.Media.TRACK,//6
                MediaStore.Audio.Media.DATA//6
        };
        String sortOrder = MediaStore.Audio.Media.DISPLAY_NAME;
        String selection = "is_music=1 and title != '' and artist_id = "+ artist_id;
        Cursor cursor = context.getContentResolver().query(uri, projection,
                selection, null, sortOrder);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int trackNumber = cursor.getInt(6);
                while (trackNumber >= 1000) {
                    trackNumber -= 1000;
                }
                artistSongList.add(new Song(cursor.getLong(0), cursor.getString(1),artist_id, cursor.getString(2),
                        cursor.getLong(3),cursor.getString(4), cursor.getInt(5),trackNumber, cursor.getString(6)));
            }
            while (cursor.moveToNext());

            if (cursor != null) {
                cursor.close();
            }
        }
        return artistSongList;
    }
}
