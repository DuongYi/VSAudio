package com.vssyii.vsaudio.MusicAction;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.dialog.AddToPlaylistDialog;
import com.vssyii.vsaudio.models.Song;
import com.vssyii.vsaudio.util.MusicUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class musicAction {
    View view;
    Context context;

    public musicAction(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void createAction(List<Song> songList, int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_musicaction, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.addOnPlaylist:
                            AddToPlaylistDialog.create(songList.get(position)).show(((AppCompatActivity)context).getSupportFragmentManager(), "ADD_PLAYLIST");
                            break;
                        case R.id.delete:
                            deleteFile(songList, position, v);
                            break;
                        case R.id.share:
                            context.startActivity(Intent.createChooser(MusicUtil.createShareSongFileIntent(songList.get(position), context), null).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            break;
                    }
                    return true;
                });
            }
        });
    }

    private void deleteFile(List<Song> songList, int position, View  view) {
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                songList.get(position).id);
        File file = new File(songList.get(position).path);
        boolean deleted = file.delete();
        if (deleted) {
            context.getContentResolver().delete(contentUri, null,null);
            songList.remove(position);
            Snackbar.make(view, "Deleted: ", Snackbar.LENGTH_LONG).show();
        }
        else {
            Snackbar.make(view, "Can't be deleted", Snackbar.LENGTH_LONG).show();
        }
    }
}
