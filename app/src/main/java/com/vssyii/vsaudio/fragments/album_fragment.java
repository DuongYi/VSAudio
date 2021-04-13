package com.vssyii.vsaudio.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.adapter.AlbumAdapter;
import com.vssyii.vsaudio.adapter.GridSpacingItemDecoration;
import com.vssyii.vsaudio.dataload.AlbumLoader;


public class album_fragment extends Fragment {
    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    int spanCount = 2;
    int spacing = 30;
    boolean includeEdge = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.album_fragment, container, false);

        recyclerView = view.findViewById(R.id.albumRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));

        new loadData().execute("");


        return view;
    }

    public class loadData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            if (getActivity() != null) {
                albumAdapter = new AlbumAdapter(new AlbumLoader().albumList(getActivity()));
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String s) {
            recyclerView.setAdapter(albumAdapter);
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
