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
import com.vssyii.vsaudio.adapter.ArtistAdapter;
import com.vssyii.vsaudio.dataload.ArtistLoader;


public class artist_fragment extends Fragment {

    private RecyclerView recyclerView;
    private ArtistAdapter artistAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.artist_fragment, container, false);
        recyclerView = view.findViewById(R.id.artistRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        new loadData().execute("");

        return view;
    }

    public class loadData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            if (getActivity() != null) {
                artistAdapter = new ArtistAdapter(getActivity(), new ArtistLoader().artistList(getActivity()));
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String s) {
            if (getActivity() != null) {
                recyclerView.setAdapter(artistAdapter);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
