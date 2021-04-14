package com.vssyii.vsaudio.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.adapter.ArtistSongAdapter;
import com.vssyii.vsaudio.dataload.ArtistLoader;
import com.vssyii.vsaudio.dataload.ArtistSongLoader;
import com.vssyii.vsaudio.models.Artist;
import com.vssyii.vsaudio.models.Song;

import java.util.ArrayList;
import java.util.List;

import static com.vssyii.vsaudio.adapter.SongAdapter.getImage;


public class artistDetails_fragment extends Fragment {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private long artist_id;

    private List<Song> songList = new ArrayList<>();
    private Artist artist;
    private ImageView collapsingArtistBg;
    private RecyclerView recyclerView;
    private ArtistSongAdapter artistSongAdapter;
    private Toolbar toolbar;

    public static artistDetails_fragment newInstance(long id) {

        Bundle args = new Bundle();
        args.putLong("_ID", id);
        artistDetails_fragment fragment = new artistDetails_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        artist_id = getArguments().getLong("_ID");

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.artistdetails_fragment, container, false);

        collapsingArtistBg = rootView.findViewById(R.id.collapsingArtistBg);
        collapsingToolbarLayout = rootView.findViewById(R.id.collapsingArtistLayout);
        toolbar = rootView.findViewById(R.id.toolbar);
        recyclerView = rootView.findViewById(R.id.collapsingArtistRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        artist = new ArtistLoader().getArtist(getActivity(), artist_id);
        setDetail();
        setArtistList();
        initToolbar();
        return rootView;
    }

    private void setArtistList() {
        songList = ArtistSongLoader.getAllArtistSongs(getActivity(), artist_id);
        artistSongAdapter = new ArtistSongAdapter(getActivity(), songList);
        recyclerView.setAdapter(artistSongAdapter);
    }

    private void setDetail() {
        String totalSong ="";
        if(artist.songCount > 1) {
            totalSong = artist.songCount + " songs";
        }
        else totalSong = artist.songCount + " song";
        collapsingToolbarLayout.setTitle(artist.artName + " - " + totalSong);
        ImageLoader.getInstance().displayImage(getImage(artist.id).toString(), collapsingArtistBg,
                new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.drawable.unknowart).resetViewBeforeLoading(true).build());
    }

    private void initToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This is back button", Toast.LENGTH_SHORT).show();;
            }
        });
    }
}