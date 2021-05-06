package com.vssyii.vsaudio.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.dataload.PlaylistLoader;
import com.vssyii.vsaudio.models.Playlist;
import com.vssyii.vsaudio.util.PlaylistsUtil;

import java.util.ArrayList;
import java.util.List;

public class DeletePlaylistDialog extends DialogFragment {

    @NonNull
    public static DeletePlaylistDialog create(Playlist playlist) {
        DeletePlaylistDialog dialog = new DeletePlaylistDialog();
        Bundle args = new Bundle();
        args.putInt("playlistID", playlist.id);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int playlistID = getArguments().getInt("playlistID");
        final Playlist playlist = PlaylistLoader.getPlaylist(getContext(), playlistID);
        int title;
        CharSequence content;
        title = R.string.delete_playlist_title;
        content = Html.fromHtml(getString(R.string.delete_playlist_x, playlist.name));

        return new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(content)
                .positiveText("Delete")
                .negativeText("Cancel")
                .onPositive((dialog, which) -> {
                    if (getActivity() == null)
                        return;
                    PlaylistsUtil.deletePlaylists(getActivity(), playlist);
                })
                .build();
    }
}
