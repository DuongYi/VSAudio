package com.vssyii.vsaudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vssyii.vsaudio.adapter.ViewPagerAdapter;
import com.vssyii.vsaudio.dataload.SongLoader;
import com.vssyii.vsaudio.fragments.album_fragment;
import com.vssyii.vsaudio.fragments.artist_fragment;
import com.vssyii.vsaudio.fragments.songs_fragment;
import com.vssyii.vsaudio.models.Song;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permission();
    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , REQUEST_CODE );
            return;
        }
        else {
            initViewPager();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initViewPager();
                }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initViewPager() {

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.AddFragments(new songs_fragment(), "Songs");
        viewPagerAdapter.AddFragments(new artist_fragment(), "artist");
        viewPagerAdapter.AddFragments(new album_fragment(), "album");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        List<Song> songList = new SongLoader().getAllSongs(this);

        for (Song song : songList) {
            Log.d("Path:" , "Name" + song.title);
        }
    }


}