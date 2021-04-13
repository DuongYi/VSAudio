package com.vssyii.vsaudio.dataload;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.vssyii.vsaudio.models.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistLoader {
    public List<Artist> getArtists(Cursor cursor) {
        List<Artist> list = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                list.add(new Artist(cursor.getLong(0), cursor.getString(1),
                        cursor.getInt(2), cursor.getInt(3)));
            }
            while (cursor.moveToNext());
            if (cursor != null) {
                cursor.close();
            }
        }

        return list;
    }

    public Artist getArtist(Context context, long id ) {

        return  artist(makeCursor(context, "id=?", new String[]{String.valueOf(id)}));
    }

    private Artist artist(Cursor cursor) {
        Artist artist = new Artist();
        if (cursor.moveToFirst() && cursor != null) {
            artist = new Artist(cursor.getLong(0), cursor.getString(1),
                    cursor.getInt(2), cursor.getInt(3));
        }
        if(cursor != null) {
            cursor.close();
        }
        return artist;
    }

    public List<Artist> artistList(Context context) {
        return getArtists(makeCursor(context, null, null));
    }

    public Cursor makeCursor(Context context, String selection, String[] selectionArg) {

        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        String projection[] = new String[] {
                "_id",                        //0
                "artist",                     //1
                "number_of_albums",           //2
                "number_of_tracks"            //3
        };
        String sortOrder = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER;
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArg, sortOrder);
        return cursor;
    }
}
