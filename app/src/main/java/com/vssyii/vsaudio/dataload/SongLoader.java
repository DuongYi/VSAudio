package com.vssyii.vsaudio.dataload;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.vssyii.vsaudio.models.Song;

import java.util.ArrayList;
import java.util.List;

public class SongLoader {
    public List<Song> getAllSongs(Context context) {
        List<Song> songList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[] {
                MediaStore.Audio.Media._ID,//0
                MediaStore.Audio.Media.TITLE,//1
                MediaStore.Audio.Media.ALBUM_ID,//6
                MediaStore.Audio.Media.ALBUM,//2
                MediaStore.Audio.Media.ARTIST_ID,//3
                MediaStore.Audio.Media.ARTIST,//4
                MediaStore.Audio.Media.DURATION,//5
                MediaStore.Audio.Media.TRACK,//6
                MediaStore.Audio.Media.DATA//7
        };
        String sortOrder = MediaStore.Audio.Media.DISPLAY_NAME;
        String selection = "is_music=1";
        Cursor cursor = context.getContentResolver().query(uri, projection,
                selection, null, sortOrder);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                songList.add(new Song(cursor.getLong(0),
                                    cursor.getString(1),
                                    cursor.getLong(2),
                                    cursor.getString(3),
                                    cursor.getLong(4),
                                    cursor.getString(5),
                                    cursor.getInt(6),
                                    cursor.getInt(7),
                                    cursor.getString(8)));
            }
            while (cursor.moveToNext());

            if (cursor != null) {
                cursor.close();
            }
        }
        return songList;
    }
}
