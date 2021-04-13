package com.vssyii.vsaudio.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.adapter.SongAdapter;
import com.vssyii.vsaudio.dataload.SongLoader;


public class songs_fragment extends Fragment {

    private SongAdapter songAdapter;
    private RecyclerView recyclerView;

    public songs_fragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.songs_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        new loadData().execute("");

        return view;
    }

    public class loadData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            if (getActivity() != null) {
                songAdapter = new SongAdapter(new SongLoader().getAllSongs(getActivity()));
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String s) {
            if (getActivity() != null) {
                recyclerView.setAdapter(songAdapter);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
