package com.vssyii.vsaudio.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.vssyii.vsaudio.R;

public class player_fragment extends Fragment {

    private static final String TAG = player_fragment.class.getSimpleName();
    public static View top_container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.player_fragment, container, false);
        top_container = view.findViewById(R.id.top_container);

        return view;
    }
}
