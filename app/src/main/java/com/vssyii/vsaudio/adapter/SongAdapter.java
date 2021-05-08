package com.vssyii.vsaudio.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vssyii.vsaudio.MusicAction.musicAction;
import com.vssyii.vsaudio.PlayerActivity;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.models.Song;

import java.io.File;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.VH> {

    private List<Song> songList;
    private Context context;

    public SongAdapter(Context context, List<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Song song = songList.get(position);

        if (song != null) {
            holder.titl.setText(song.title);
            holder.art.setText(song.artistName);
            ImageLoader.getInstance().displayImage(getImage(song.albumId).toString(), holder.imageView,
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.drawable.jbl).resetViewBeforeLoading(true).build());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        new musicAction(holder.menu_musicAction, context).createAction(songList, position);
    }

    public static Uri getImage(long albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }



    @Override
    public int getItemCount() {
        return songList != null ? songList.size() : 0;
    }

    public class VH extends RecyclerView.ViewHolder {

        private ImageView imageView, menu_musicAction;
        private TextView titl, art;

        public VH(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.album_art);
            titl = itemView.findViewById(R.id.song_name);
            art = itemView.findViewById(R.id.artist_name);
            menu_musicAction = itemView.findViewById(R.id.music_action);
        }
    }
}
