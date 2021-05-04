package com.vssyii.vsaudio.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.adapter.AlbumAdapter;
import com.vssyii.vsaudio.adapter.GridSpacingItemDecoration;
import com.vssyii.vsaudio.adapter.PlaylistAdapter;
import com.vssyii.vsaudio.dataload.AlbumLoader;
import com.vssyii.vsaudio.dataload.PlaylistLoader;
import com.vssyii.vsaudio.models.Playlist;

import java.util.List;

public class playList_fragment extends Fragment {

    private RecyclerView recyclerView;
    private PlaylistAdapter playlistAdapter;
    int spanCount = 2;
    int spacing = 30;
    boolean includeEdge = false;
    private List<Playlist> playlists;

    public playList_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);

        recyclerView = view.findViewById(R.id.playlistRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));

        new loadData().execute("");

        return view;
    }

    public class loadData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            if (getActivity() != null) {
                playlists = new PlaylistLoader().getAllPlaylistsAudio(getActivity());
                playlistAdapter = new PlaylistAdapter(getActivity(), playlists);
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String s) {
            recyclerView.setAdapter(playlistAdapter);
            if (getActivity() != null) {
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}