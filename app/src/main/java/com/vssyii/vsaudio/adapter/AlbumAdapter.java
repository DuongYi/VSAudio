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
import com.vssyii.vsaudio.models.Album;

import java.util.List;

import static com.vssyii.vsaudio.adapter.SongAdapter.getImage;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AH> {
   private List<Album> albumList;
   private Activity context;

    public AlbumAdapter(Activity context, List<Album> albumList) {
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
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.drawable.hp1).resetViewBeforeLoading(true).build());
        }
    }

    @Override
    public int getItemCount() {
        return albumList!=null?albumList.size():0;
    }

    public class AH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ShapeableImageView albumImg;
        private TextView albumName;
        private TextView albumArtist;

        public AH(@NonNull View itemView) {
            super(itemView);

            albumImg = itemView.findViewById(R.id.albumImage);
            albumName = itemView.findViewById(R.id.albumName);
            albumArtist = itemView.findViewById(R.id.albumArtist);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            long albumId = albumList.get(getAdapterPosition()).id;

            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment;

            transaction.setCustomAnimations(R.anim.layout_fad_in, R.anim.layout_fad_out,
                                            R.anim.layout_fad_in, R.anim.layout_fad_out);

            fragment = albumDetails_fragment.newInstance(albumId);

            transaction.hide(((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.main_container));

            transaction.add(R.id.main_container, fragment);
            transaction.addToBackStack(null).commit();
        }
    }
}
