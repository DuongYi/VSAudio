package com.vssyii.vsaudio.fragments;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.vssyii.vsaudio.R;

import static com.vssyii.vsaudio.MainActivity.ARTIST_TO_FRAG;
import static com.vssyii.vsaudio.MainActivity.PATH_TO_FRAG;
import static com.vssyii.vsaudio.MainActivity.SHOW_MINI_PLAYER;
import static com.vssyii.vsaudio.MainActivity.SONG_NAME_TO_FRAG;

public class nowPlayingBottom_fragment extends Fragment {

    ShapeableImageView playerBottom_albumImage;
    TextView playerBottom_Title, playerBottom_artist;
    ImageView playerBottom_playPause, playerBottom_btNext;

    public nowPlayingBottom_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing_bottom, container, false);
        playerBottom_albumImage = view.findViewById(R.id.playerBottom_albumImage);
        playerBottom_Title = view.findViewById(R.id.playerBottom_Title);
        playerBottom_artist = view.findViewById(R.id.playerBottom_artistName);
        playerBottom_btNext = view.findViewById(R.id.playerBottom_btNext);
        playerBottom_playPause = view.findViewById(R.id.playerBottom_playPause);

        playerBottom_playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "PlayPause", Toast.LENGTH_SHORT).show();
            }
        });

        playerBottom_btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Next", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SHOW_MINI_PLAYER) {
            if (PATH_TO_FRAG != null) {
                byte[] art = getAlbumArt(PATH_TO_FRAG);
                Glide.with(getContext())
                        .load(art)
                        .error(R.drawable.unknown)
                        .into(playerBottom_albumImage);
            }
            playerBottom_Title.setText(SONG_NAME_TO_FRAG);
            playerBottom_artist.setText(ARTIST_TO_FRAG);
        }

    }

    private byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        return art;
    }
}