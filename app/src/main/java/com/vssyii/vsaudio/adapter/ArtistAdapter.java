package com.vssyii.vsaudio.adapter;

import android.app.Activity;
import android.content.ContentUris;
import android.net.Uri;
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
import com.vssyii.vsaudio.fragments.artistDetails_fragment;
import com.vssyii.vsaudio.models.Artist;

import java.util.List;

import static com.vssyii.vsaudio.adapter.SongAdapter.getImage;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ARV> {

    private List<Artist> artistList;
    private Activity context;

    public ArtistAdapter(Activity context, List<Artist> artistList) {
        this.context = context;
        this.artistList = artistList;
    }


    @NonNull
    @Override
    public ARV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ARV(LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ARV holder, int position) {
        Artist artist = artistList.get(position);

        if(artist != null) {
            holder.artistName.setText(artist.artName);

            if (artist.songCount > 1) {
                holder.totalSongs.setText(String.valueOf(artist.songCount) + " songs");
            }
            else {
                holder.totalSongs.setText(String.valueOf(artist.songCount) + " song");
            }

            ImageLoader.getInstance().displayImage(getArtImage(artist.id).toString(), holder.artistImage,
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.drawable.unknown).resetViewBeforeLoading(true).build());
        }
    }

    @Override
    public int getItemCount() {
        return artistList!=null?artistList.size():0;
    }

    public static Uri getArtImage(long artId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), artId);
    }

    public class ARV extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ShapeableImageView artistImage;
        private TextView artistName;
        private TextView totalSongs;

        public ARV(@NonNull View itemView) {
            super(itemView);

            artistImage = itemView.findViewById(R.id.artistImage);
            artistName = itemView.findViewById(R.id.artistName);
            totalSongs = itemView.findViewById(R.id.totalSong);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            long artistId = artistList.get(getAdapterPosition()).id;

            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment;

            transaction.setCustomAnimations(R.anim.layout_fad_in, R.anim.layout_fad_out,
                    R.anim.layout_fad_in, R.anim.layout_fad_out);

            fragment = artistDetails_fragment.newInstance(artistId);

            transaction.hide(((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.main_container));

            transaction.add(R.id.main_container, fragment);
            transaction.addToBackStack(null).commit();
        }
    }
}
