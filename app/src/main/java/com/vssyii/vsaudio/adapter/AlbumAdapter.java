package com.vssyii.vsaudio.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
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
import com.vssyii.vsaudio.models.Album;

import java.util.List;

import static com.vssyii.vsaudio.adapter.SongAdapter.getImage;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AH> {
   private Context context;
   private List<Album> albumList;

    public AlbumAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AH(LayoutInflater.from(parent.getContext()).inflate(R.layout.album_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AH holder, int position) {
        Album album = albumList.get(position);

        if (album != null) {
            holder.albumName.setText(album.albumName);
            holder.albumArtist.setText(album.artistName);
            ImageLoader.getInstance().displayImage(getImage(album.id).toString(), holder.albumImg,
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.drawable.hp).resetViewBeforeLoading(true).build());
        }
    }

    @Override
    public int getItemCount() {
        return albumList!=null?albumList.size():0;
    }

    public class AH extends RecyclerView.ViewHolder {
        private ImageView albumImg;
        private TextView albumName;
        private TextView albumArtist;

        public AH(@NonNull View itemView) {
            super(itemView);

            albumImg = itemView.findViewById(R.id.albumImage);
            albumName = itemView.findViewById(R.id.albumName);
            albumArtist = itemView.findViewById(R.id.albumArtist);
        }
    }
}
