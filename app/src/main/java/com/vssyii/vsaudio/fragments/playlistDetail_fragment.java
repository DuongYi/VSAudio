package com.vssyii.vsaudio.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.adapter.PlaylistSongAdapter;
import com.vssyii.vsaudio.dataload.PlaylistLoader;
import com.vssyii.vsaudio.dataload.PlaylistSongLoader;
import com.vssyii.vsaudio.models.Playlist;
import com.vssyii.vsaudio.models.Song;

import java.util.ArrayList;
import java.util.List;

import static com.vssyii.vsaudio.adapter.SongAdapter.getImage;

public class playlistDetail_fragment extends Fragment {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private int playlist_id;

    private List<Song> songlists = new ArrayList<>();
    private Playlist playlist;
    private ImageView collapsingPlaylistBg;
    private RecyclerView recyclerView;
    private PlaylistSongAdapter playlistSongAdapter;
    private Toolbar toolbar;
    public static FloatingActionButton btPlaylistPlayShuffle;

    public static playlistDetail_fragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("_trackID", id);
        playlistDetail_fragment fragment = new playlistDetail_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        playlist_id = getArguments().getInt("_trackID");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_playlist_detail, container, false);
        collapsingPlaylistBg = rootView.findViewById(R.id.collapsingPlaylistBg);
        collapsingToolbarLayout = rootView.findViewById(R.id.collapsingPlaylistLayout);
        toolbar = rootView.findViewById(R.id.playlist_toolbar);
        btPlaylistPlayShuffle = rootView.findViewById(R.id.btPlaylistPlay);
        recyclerView = rootView.findViewById(R.id.collapsingPlaylistRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        playlist = new PlaylistLoader().getPlaylist(getActivity(), playlist_id);

        setDetail();
        setPlaylistList();
        initToolbar();

        return rootView;
    }


    private void setPlaylistList() {
        songlists = PlaylistSongLoader.getAllPlaylistSongs(getActivity(), playlist_id);
        playlistSongAdapter = new PlaylistSongAdapter(getActivity(), songlists);
        recyclerView.setAdapter(playlistSongAdapter);
    }

    private void setDetail() {
        collapsingToolbarLayout.setTitle(playlist.getName());
        collapsingPlaylistBg.setImageResource(R.drawable.unknownplaylist);
    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.playlist_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (getActivity()).onBackPressed();
            }
        });
    }

}