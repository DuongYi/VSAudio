package com.vssyii.vsaudio.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.adapter.ViewPagerAdapter;

public class main_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout);
        ViewPager viewPager = rootView.findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.AddFragments(new songs_fragment(), "Songs");
        viewPagerAdapter.AddFragments(new artist_fragment(), "artist");
        viewPagerAdapter.AddFragments(new album_fragment(), "album");
        viewPagerAdapter.AddFragments(new playList_fragment(), "playList");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        return rootView;
    }
}
