package com.vssyii.vsaudio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.Manifest;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vssyii.vsaudio.fragments.main_fragment;
import com.vssyii.vsaudio.service.MusicService;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    static int shuffleBoolean = 1;
    static int repeatBoolean = 1;
    static int likeBoolean = 1;
    public static final String MUSIC_LAST_PLAYED = "LAST_PLAYED";
    public static final String MUSIC_FILE = "STORED_MUSIC";
    public static final String ARTIST_NAME = "ARTIST NAME";
    public static final String SONG_NAME = "SONG NAME";
    public static boolean SHOW_MINI_PLAYER = false;
    public static String PATH_TO_FRAG = null;
    public static String ARTIST_TO_FRAG = "unknown";
    public static String SONG_NAME_TO_FRAG = "Unknown";
    public static int POSITION_TO_FRAG = -1;
    public static final String SONG_POSITION = "POSITION";
    private FrameLayout bottomMinimized;
    public static final String AA_POSITION = "KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomMinimized = findViewById(R.id.frag_bottom_player);
        bottomMinimized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Open Big", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                startService(intent);
            }
        });

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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initViewPager();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initViewPager() {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        Fragment fragment = new main_fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(MUSIC_LAST_PLAYED, MODE_PRIVATE);
        String path = preferences.getString(MUSIC_FILE, null);
        String artist_path = preferences.getString(ARTIST_NAME, null);
        String song_path = preferences.getString(SONG_NAME, null);
        int position_path = preferences.getInt(SONG_POSITION, -1);
        if (path != null) {
            SHOW_MINI_PLAYER = true;
            PATH_TO_FRAG = path;
            ARTIST_TO_FRAG = artist_path;
            SONG_NAME_TO_FRAG = song_path;
            POSITION_TO_FRAG = position_path;
        }
        else {
            SHOW_MINI_PLAYER = false;
            PATH_TO_FRAG = null;
            ARTIST_TO_FRAG = "unknown";
            SONG_NAME_TO_FRAG = "unknown";
            POSITION_TO_FRAG = -1;
        }
    }
}