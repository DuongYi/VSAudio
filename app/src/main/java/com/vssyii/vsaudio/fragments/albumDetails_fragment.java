package com.vssyii.vsaudio.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.adapter.AlbumSongAdapter;
import com.vssyii.vsaudio.dataload.AlbumLoader;
import com.vssyii.vsaudio.dataload.AlbumSongLoader;
import com.vssyii.vsaudio.models.Album;
import com.vssyii.vsaudio.models.Song;

import java.util.ArrayList;
import java.util.List;

import static com.vssyii.vsaudio.adapter.SongAdapter.getImage;


public class albumDetails_fragment extends Fragment {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private long album_id;

    private List<Song> songList = new ArrayList<>();
    private Album album;
    private ImageView collapsingAlbumBg;
    private RecyclerView recyclerView;
    private AlbumSongAdapter albumSongAdapter;

    public static albumDetails_fragment newInstance(long id) {

        Bundle args = new Bundle();
        args.putLong("_ID", id);
        albumDetails_fragment fragment = new albumDetails_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        album_id = getArguments().getLong("_ID");

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.albumdetails_fragment, container, false);

        collapsingAlbumBg = rootView.findViewById(R.id.collapsingAlbumBg);
        collapsingToolbarLayout = rootView.findViewById(R.id.collapsingAlbumLayout);
        recyclerView = rootView.findViewById(R.id.collapsingAlbumRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        album = new AlbumLoader().getAlbum(getActivity(), album_id);
        setDetail();
        setAlbumList();
        return rootView;
    }

    private void setAlbumList() {
        songList = AlbumSongLoader.getAllAlbumSongs(getActivity(), album_id);
        albumSongAdapter = new AlbumSongAdapter(getActivity(), songList);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(albumSongAdapter);
    }

    private void setDetail() {
        collapsingToolbarLayout.setTitle(album.albumName);
        ImageLoader.getInstance().displayImage(getImage(album.id).toString(), collapsingAlbumBg,
                new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.drawable.hp1).resetViewBeforeLoading(true).build());
    }
}