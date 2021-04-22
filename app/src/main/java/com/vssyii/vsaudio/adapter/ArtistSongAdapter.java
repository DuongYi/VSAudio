package com.vssyii.vsaudio.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.models.Song;

import java.util.List;

public class ArtistSongAdapter extends RecyclerView.Adapter<ArtistSongAdapter.ATSVH> {

    private List<Song> artistSongList;
    private Activity context;

    public ArtistSongAdapter(Activity context, List<Song> artistSongList) {
        this.context = context;
        this.artistSongList = artistSongList;
    }

    @NonNull
    @Override
    public ATSVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ATSVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artistsongs_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ATSVH holder, int position) {
        Song song = artistSongList.get(position);
        if (song != null) {
            holder.trackN.setText(String.valueOf(song.trackNumber));
            holder.artistSongTitle.setText(song.title);
            holder.artistSongArtN.setText(song.artistName);
        }
    }

    @Override
    public int getItemCount() {
        return artistSongList!=null?artistSongList.size():0;
    }

    public class ATSVH extends RecyclerView.ViewHolder {

        private TextView trackN, artistSongTitle, artistSongArtN;

        public ATSVH(@NonNull View itemView) {
            super(itemView);

            trackN = itemView.findViewById(R.id.trackN);
            artistSongTitle = itemView.findViewById(R.id.artistSongTitle);
            artistSongArtN = itemView.findViewById(R.id.artistSongArtN);
        }
    }
}