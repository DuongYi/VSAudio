package com.vssyii.vsaudio.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vssyii.vsaudio.MusicAction.musicAction;
import com.vssyii.vsaudio.PlayerActivity;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.models.Song;

import java.util.List;

import static com.vssyii.vsaudio.fragments.albumDetails_fragment.btAlbumPlayShuffle;
import static com.vssyii.vsaudio.fragments.songs_fragment.songList;

public class AlbumSongAdapter extends RecyclerView.Adapter<AlbumSongAdapter.ASVH> {

    public static List<Song> albumSongList;
    private Activity context;

    public AlbumSongAdapter(Activity context, List<Song> albumSongList) {
        this.context = context;
        this.albumSongList = albumSongList;
    }

    @NonNull
    @Override
    public ASVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ASVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.albumsongs_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ASVH holder, int position) {
        Song song = albumSongList.get(position);
        if (song != null) {
            holder.stt.setText(String.valueOf(song.trackNumber));
            holder.albumSongTitle.setText(song.title);
            holder.albumSongArt.setText(song.artistName);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("sender", "AlbumSongAdapter");
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
        btAlbumPlayShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("sender", "AlbumSongAdapter");
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        new musicAction(holder.albumSongAction, context).createAction(albumSongList, position);
    }

    @Override
    public int getItemCount() {
        return albumSongList!=null?albumSongList.size():0;
    }

    public class ASVH extends RecyclerView.ViewHolder {

        private TextView stt, albumSongTitle, albumSongArt;
        private ImageView albumSongAction;

        public ASVH(@NonNull View itemView) {
            super(itemView);

            stt = itemView.findViewById(R.id.stt);
            albumSongTitle = itemView.findViewById(R.id.albumSongTitle);
            albumSongArt = itemView.findViewById(R.id.albumSongArt);
            albumSongAction = itemView.findViewById(R.id.albumSongSetting);
        }
    }
}
