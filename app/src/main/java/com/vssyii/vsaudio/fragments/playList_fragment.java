package com.vssyii.vsaudio.fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.adapter.AlbumAdapter;
import com.vssyii.vsaudio.adapter.GridSpacingItemDecoration;
import com.vssyii.vsaudio.adapter.PlaylistAdapter;
import com.vssyii.vsaudio.dataload.AlbumLoader;
import com.vssyii.vsaudio.dataload.PlaylistLoader;
import com.vssyii.vsaudio.dialog.CreatePlaylistDialog;
import com.vssyii.vsaudio.models.Playlist;
import com.vssyii.vsaudio.util.PlaylistsUtil;

import java.util.List;
import java.util.Random;

public class playList_fragment extends Fragment {

    public static final String TAG = "YourFragmentTag";

    private RecyclerView recyclerView;
    private PlaylistAdapter playlistAdapter;
    private FloatingActionButton btCreatePlaylist;
    int spanCount = 2;
    int spacing = 30;
    int refresh;
    boolean includeEdge = false;
    private List<Playlist> playlists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);

        recyclerView = view.findViewById(R.id.playlistRecyclerView);
        btCreatePlaylist = view.findViewById(R.id.btCreatePlaylist);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));

        new loadData().execute("");

        btCreatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePlaylistDialog.create().show(getActivity().getSupportFragmentManager(), "ADD_TO_PLAYLIST");

                if (refresh != new PlaylistLoader().getAllPlaylistsAudio(getActivity()).size() ) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    if (Build.VERSION.SDK_INT >= 26) {
                        ft.setReorderingAllowed(false);
                    }
                    ft.detach(playList_fragment.this).attach(playList_fragment.this).commit();
                }
            }
        });

        Log.e("AAAAAAAAAAA", "" + refresh );
        Log.e("AAAAAAAAAAAT", "" + new PlaylistLoader().getAllPlaylistsAudio(getActivity()).size() );

        //reload



        return view;
    }

    public class loadData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            if (getActivity() != null) {
                playlists = new PlaylistLoader().getAllPlaylistsAudio(getActivity());
                refresh = playlists.size();
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