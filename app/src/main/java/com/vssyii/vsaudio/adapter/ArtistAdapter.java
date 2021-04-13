package com.vssyii.vsaudio.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vssyii.vsaudio.R;
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

            ImageLoader.getInstance().displayImage(getImage(artist.id).toString(), holder.artistImage,
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.drawable.unknown).resetViewBeforeLoading(true).build());
        }
    }

    @Override
    public int getItemCount() {
        return artistList!=null?artistList.size():0;
    }

    public class ARV extends RecyclerView.ViewHolder {

        private ImageView artistImage;
        private TextView artistName;
        private TextView totalSongs;

        public ARV(@NonNull View itemView) {
            super(itemView);

            artistImage = itemView.findViewById(R.id.artistImage);
            artistName = itemView.findViewById(R.id.artistName);
            totalSongs = itemView.findViewById(R.id.totalSong);
        }
    }
}
