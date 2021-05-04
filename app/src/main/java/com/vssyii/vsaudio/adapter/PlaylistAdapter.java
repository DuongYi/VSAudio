package com.vssyii.vsaudio.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.fragments.albumDetails_fragment;
import com.vssyii.vsaudio.models.Playlist;

import java.util.List;

import static com.vssyii.vsaudio.adapter.SongAdapter.getImage;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PLVH> {
    private Activity context;
    private List<Playlist> playlists;

    public PlaylistAdapter(Activity context, List<Playlist> playlists) {
        this.context = context;
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PLVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PLVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PLVH holder, int position) {
        Playlist playlist = playlists.get(position);
        if (playlist != null) {
            holder.playlistTitle.setText(playlist.name);
            holder.playlistTotalSong.setText(String.valueOf(playlist.id));
            ImageLoader.getInstance().displayImage(getImage(playlist.id).toString(), holder.playlistImage,
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.drawable.unknownplaylist).resetViewBeforeLoading(true).build());
        }
    }

    @Override
    public int getItemCount() {
        return playlists!=null?playlists.size():0;
    }

    public class PLVH extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ShapeableImageView playlistImage;
        private TextView playlistTitle, playlistTotalSong;

        public PLVH(@NonNull View itemView) {
            super(itemView);

            playlistImage = itemView.findViewById(R.id.playlistImage);
            playlistTitle = itemView.findViewById(R.id.playlistTitle);
            playlistTotalSong = itemView.findViewById(R.id.playlistSongs);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int playlistId = playlists.get(getAdapterPosition()).id;

            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment;

            transaction.setCustomAnimations(R.anim.layout_fad_in, R.anim.layout_fad_out,
                    R.anim.layout_fad_in, R.anim.layout_fad_out);

            fragment = albumDetails_fragment.newInstance(playlistId);

            transaction.hide(((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.main_container));

            transaction.add(R.id.main_container, fragment);
            transaction.addToBackStack(null).commit();
        }
    }
}
