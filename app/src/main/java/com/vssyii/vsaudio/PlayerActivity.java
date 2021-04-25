package com.vssyii.vsaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.vssyii.vsaudio.dataload.SongLoader;
import com.vssyii.vsaudio.models.Song;

import java.util.ArrayList;
import java.util.List;

import static com.vssyii.vsaudio.fragments.songs_fragment.songList;

public class PlayerActivity extends AppCompatActivity {

    ShapeableImageView player_bg;
    ImageView btDown, btSetting, player_btPlay, player_btPrevious, player_btNext, player_btLike, player_btRandom, player_btRepeat, player_btAddPlayList;
    TextView durationPlayed, durationTotal, player_tvTitle, player_tvArtistName;
    SeekBar seekBar;

    int position = -1;
    public static List<Song> listSongs = new ArrayList<>();
    public static Uri uri ;
    static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);

        initPlayerViews();

        getIntentMethod();


    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position", -1);
        listSongs = songList;
        if(listSongs != null) {
            player_btPlay.setImageResource(R.drawable.pause);
            uri = Uri.parse(listSongs.get(position).path);
        }
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
    }

    private void initPlayerViews() {
        btDown =findViewById(R.id.btDown);
        btSetting = findViewById(R.id.btPlayerSetting);

        player_bg = findViewById(R.id.player_bg);
        player_btPlay = findViewById(R.id.player_btPlay);
        player_btPrevious = findViewById(R.id.player_btPrevious);
        player_btNext = findViewById(R.id.player_btNext);
        player_btLike = findViewById(R.id.player_btLike);
        player_btRandom = findViewById(R.id.player_btRandom);
        player_btRepeat = findViewById(R.id.player_btRepeat);
        player_btAddPlayList = findViewById(R.id.player_btAddPlaylist);
        durationPlayed = findViewById(R.id.player_durationPlayed);
        durationTotal = findViewById(R.id.player_durationTotal);
        player_tvTitle = findViewById(R.id.playerTitle);
        player_tvArtistName = findViewById(R.id.playerArtistName);
        seekBar = findViewById(R.id.seekBar);
    }
}