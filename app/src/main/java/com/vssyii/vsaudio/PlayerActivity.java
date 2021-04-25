package com.vssyii.vsaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vssyii.vsaudio.dataload.SongLoader;
import com.vssyii.vsaudio.models.Song;

import java.util.ArrayList;
import java.util.List;

import static com.vssyii.vsaudio.adapter.SongAdapter.getImage;
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
    private Handler handler = new Handler();
    private Thread playThread, previousThread, nextThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);

        initPlayerViews();

        getIntentMethod();
        player_tvTitle.setText(listSongs.get(position).title);
        player_tvArtistName.setText(listSongs.get(position).artistName);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    durationPlayed.setText(fommatedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });


    }

    @Override
    protected void onResume() {
        playThreadBtn();
        previousThreadBtn();
        nextThreadBtn();
        super.onResume();
    }

    private void nextThreadBtn() {
        nextThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                player_btNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { 
                        nextButtonClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextButtonClicked() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % listSongs.size());
            uri = Uri.parse(listSongs.get(position).path);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            player_tvTitle.setText(listSongs.get(position).title);
            player_tvArtistName.setText(listSongs.get(position).artistName);
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
            });
            player_btPlay.setImageResource(R.drawable.pause);
            mediaPlayer.start();
        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % listSongs.size());
            uri = Uri.parse(listSongs.get(position).path);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            player_tvTitle.setText(listSongs.get(position).title);
            player_tvArtistName.setText(listSongs.get(position).artistName);
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
            });
            player_btPlay.setImageResource(R.drawable.play_button);
        }
    }

    private void previousThreadBtn() {
        previousThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                player_btPrevious.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        previousButtonClicked();
                    }
                });
            }
        };
        previousThread.start();
    }

    private void previousButtonClicked() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            uri = Uri.parse(listSongs.get(position).path);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            player_tvTitle.setText(listSongs.get(position).title);
            player_tvArtistName.setText(listSongs.get(position).artistName);
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
            });
            player_btPlay.setImageResource(R.drawable.pause);
            mediaPlayer.start();
        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            uri = Uri.parse(listSongs.get(position).path);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            player_tvTitle.setText(listSongs.get(position).title);
            player_tvArtistName.setText(listSongs.get(position).artistName);
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
            });
            player_btPlay.setImageResource(R.drawable.play_button);
        }
    }

    private void playThreadBtn() {
        playThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                player_btPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playButtonClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void playButtonClicked() {
        if(mediaPlayer.isPlaying()) {
            player_btPlay.setImageResource(R.drawable.play_button);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
            });
        }
        else {
            player_btPlay.setImageResource(R.drawable.pause);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
            });

        }
    }

    private String fommatedTime(int mCurrentPosition) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;
        if(seconds.length() == 1) {
            return totalNew;
        }
        return totalOut;
    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position", -1);
        listSongs = songList;
        if(listSongs != null) {
            player_btPlay.setImageResource(R.drawable.pause);
            Log.i("FORMAT_URI",listSongs.get(position).path);
            uri = Uri.parse(listSongs.get(position).path);
        }
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        seekBar.setMax(mediaPlayer.getDuration()/1000);
        metaData(uri);
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

    private void metaData(Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        durationTotal.setText(fommatedTime(listSongs.get(position).duration/1000));

        ImageLoader.getInstance().displayImage(getImage(listSongs.get(position).albumId).toString(), player_bg,
                new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.drawable.unknown).resetViewBeforeLoading(true).build());

    }
}