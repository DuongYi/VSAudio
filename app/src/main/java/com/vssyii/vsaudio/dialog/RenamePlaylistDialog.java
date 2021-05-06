package com.vssyii.vsaudio.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.util.PlaylistsUtil;

public class RenamePlaylistDialog extends DialogFragment {
    @NonNull
    public static RenamePlaylistDialog create(int playlistId) {
        RenamePlaylistDialog dialog = new RenamePlaylistDialog();
        Bundle args = new Bundle();
        args.putInt("playlistID", playlistId);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int playlistId = getArguments().getInt("playlistID");
        return new MaterialDialog.Builder(getActivity())
                .title("Rename playlist")
                .positiveText("Rename")
                .negativeText("Cancel")
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .input(getString(R.string.playlist_name_empty), PlaylistsUtil.getNameForPlaylist(getActivity(), playlistId), false,
                        (materialDialog, charSequence) -> {
                            final String name = charSequence.toString().trim();
                            if (!name.isEmpty()) {
                                int playlistId1 = getArguments().getInt("playlistID");
                                PlaylistsUtil.renamePlaylist(getActivity(), playlistId1, name);
                            }
                        })
                .build();
    }
}
