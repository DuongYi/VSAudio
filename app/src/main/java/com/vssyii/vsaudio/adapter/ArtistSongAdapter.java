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

import com.vssyii.vsaudio.PlayerActivity;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.models.Song;

import java.util.List;

import static com.vssyii.vsaudio.fragments.artistDetails_fragment.artistPlayButtonShuffle;

public class ArtistSongAdapter extends RecyclerView.Adapter<ArtistSongAdapter.ATSVH> {

    public static List<Song> artistSongList;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("sender", "ArtistSongAdapter");
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
        artistPlayButtonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("sender", "ArtistSongAdapter");
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        holder.artistSongAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_musicaction, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.addOnPlaylist:
                            Toast.makeText(context, "Add successed on My Music playlist", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.delete:
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.share:
                            Toast.makeText(context, "Shared", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistSongList!=null?artistSongList.size():0;
    }

    public class ATSVH extends RecyclerView.ViewHolder {

        private TextView trackN, artistSongTitle, artistSongArtN;
        private ImageView artistSongAction;

        public ATSVH(@NonNull View itemView) {
            super(itemView);

            trackN = itemView.findViewById(R.id.trackN);
            artistSongTitle = itemView.findViewById(R.id.artistSongTitle);
            artistSongArtN = itemView.findViewById(R.id.artistSongArtN);
            artistSongAction = itemView.findViewById(R.id.artistSongSetting);
        }
    }
}
