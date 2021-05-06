package com.vssyii.vsaudio.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.models.Song;
import com.vssyii.vsaudio.util.PlaylistsUtil;

import java.util.ArrayList;

public class CreatePlaylistDialog extends DialogFragment {

    @NonNull
    public static CreatePlaylistDialog create() {
        return create((Song) null);
    }

    @NonNull
    public static CreatePlaylistDialog create(@Nullable Song song) {
        ArrayList<Song> list = new ArrayList<>();
        if (song != null)
            list.add(song);
        return create(list);
    }

    @NonNull
    public static CreatePlaylistDialog create(ArrayList<Song> songs) {
        CreatePlaylistDialog dialog = new CreatePlaylistDialog();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialDialog.Builder(getActivity())
                .title("New Playlist")
                .positiveText("Create")
                .negativeText("Cancel")
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .input(R.string.playlist_name_empty, 0, false, (materialDialog, charSequence) -> {
                    if (getActivity() == null)
                        return;
                    String name = charSequence.toString().trim();
                    if (!name.isEmpty()) {
                        if (!PlaylistsUtil.doesPlaylistExist(getActivity(), name)) {
                            int playlistId = PlaylistsUtil.createPlaylist(getActivity(), name);
                        } else {
                            Toast.makeText(getContext(), "Playlist already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .build();
    }
}
