package com.vssyii.vsaudio.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vssyii.vsaudio.PlayerActivity;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.models.Song;

import java.util.List;

import static com.vssyii.vsaudio.fragments.artistDetails_fragment.artistPlayButtonShuffle;
import static com.vssyii.vsaudio.fragments.playlistDetail_fragment.btPlaylistPlayShuffle;

public class PlaylistSongAdapter extends RecyclerView.Adapter<PlaylistSongAdapter.PLSVH> {
    public static List<Song> playlistSongs;
    private Activity context;

    public PlaylistSongAdapter(Activity context, List<Song> playlistSongs) {
        this.context = context;
        this.playlistSongs = playlistSongs;
    }

    @NonNull
    @Override
    public PLSVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PLSVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlistsongs_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PLSVH holder, int position) {
        Song song = playlistSongs.get(position);
        if (song != null) {
            holder.trackID.setText(String.valueOf(song.trackNumber));
            holder.playlistSong_title.setText(song.title);
            holder.playlistSong_art.setText(song.artistName);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("sender", "PlaylistSongAdapter");
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        btPlaylistPlayShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("sender", "PlaylistSongAdapter");
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlistSongs!=null?playlistSongs.size():0;
    }


    public class PLSVH extends RecyclerView.ViewHolder {

        private TextView trackID, playlistSong_title, playlistSong_art;

        public PLSVH(@NonNull View itemView) {
            super(itemView);
            trackID = itemView.findViewById(R.id.trackID);
            playlistSong_title = itemView.findViewById(R.id.playlistSongTitle);
            playlistSong_art = itemView.findViewById(R.id.playlistSongArt);
        }
    }
}
