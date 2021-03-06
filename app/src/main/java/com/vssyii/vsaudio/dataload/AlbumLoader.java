package com.vssyii.vsaudio.dataload;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.vssyii.vsaudio.models.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumLoader {

    public List<Album> getAlbums(Cursor cursor) {
        List<Album> list = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                list.add(new Album(cursor.getLong(0), cursor.getString(1), cursor.getLong(2),
                        cursor.getString(3), cursor.getInt(4), cursor.getInt(5)));
            }
            while (cursor.moveToNext());
            if (cursor != null) {
                cursor.close();
            }
        }

        return list;
    }

    public Album getAlbum(Context context, long id ) {

        return  album(makeCursor(context, "_id=?", new String[]{String.valueOf(id)}));
    }

    private Album album(Cursor cursor) {
        Album album = new Album();
        if (cursor.moveToFirst() && cursor != null) {
            album = (new Album(cursor.getLong(0), cursor.getString(1), cursor.getLong(2),
                    cursor.getString(3), cursor.getInt(4), cursor.getInt(5)));
        }
        if(cursor != null) {
            cursor.close();
        }
        return album;
    }

    public List<Album> albumList(Context context) {
        return getAlbums(makeCursor(context, null, null));
    }

    public Cursor makeCursor(Context context, String selection, String[] selectionArg) {

        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String projection[] = new String[] {
                "_id",                //0
                "album",              //1
                "artist_id",          //2
                "artist",             //3
                "numsongs",           //4
                "minyear"             //5
        };
        String sortOrder = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER;
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArg, sortOrder);
        return cursor;
    }
}
