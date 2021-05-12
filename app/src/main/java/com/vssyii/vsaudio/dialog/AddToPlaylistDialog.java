package com.vssyii.vsaudio.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.dataload.PlaylistLoader;
import com.vssyii.vsaudio.dataload.SongLoader;
import com.vssyii.vsaudio.models.Playlist;
import com.vssyii.vsaudio.models.Song;
import com.vssyii.vsaudio.util.PlaylistsUtil;

import java.util.ArrayList;
import java.util.List;

import static com.vssyii.vsaudio.fragments.songs_fragment.songList;

public class AddToPlaylistDialog extends DialogFragment {

    @NonNull
    public static AddToPlaylistDialog create(Song song) {
        AddToPlaylistDialog dialog = new AddToPlaylistDialog();
        Bundle args = new Bundle();
        args.putLong("songID", song.id);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        long songID = getArguments().getLong("songID");

        final ArrayList<Playlist> playlists = PlaylistLoader.getAllPlaylists(getActivity());
        CharSequence[] playlistNames = new CharSequence[playlists.size()];
        for (int i = 0;  i < playlistNames.length; i++) {
            playlistNames[i] = playlists.get(i).name;
        }
        return new MaterialDialog.Builder(getActivity())
                .title("Choose Playlist to add")
                .items(playlistNames)
                .itemsColor(Color.BLUE)
                .itemsCallback((materialDialog, view, i, charSequence) -> {
                    materialDialog.dismiss();
                    Log.e("AAAAAAAAAAAAAAAAAAAAAAAPLAYLIST", "name: " + playlists.get(i).name + " - list: " +playlists.get(i).songs_id);
                    PlaylistsUtil.addToPlaylist(getActivity(), songID, playlists.get(i).id);
                })
                .build();
    }
}
