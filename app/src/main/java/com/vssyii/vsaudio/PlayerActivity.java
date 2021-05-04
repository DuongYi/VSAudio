package com.vssyii.vsaudio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.palette.graphics.Palette;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vssyii.vsaudio.MusicAction.musicAction;
import com.vssyii.vsaudio.models.Song;
import com.vssyii.vsaudio.notification.ApplicationClass;
import com.vssyii.vsaudio.notification.NotificationReceiver;
import com.vssyii.vsaudio.service.MusicService;
import com.vssyii.vsaudio.util.ActionPlaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.vssyii.vsaudio.MainActivity.likeBoolean;
import static com.vssyii.vsaudio.MainActivity.repeatBoolean;
import static com.vssyii.vsaudio.MainActivity.shuffleBoolean;
import static com.vssyii.vsaudio.adapter.AlbumSongAdapter.albumSongList;
import static com.vssyii.vsaudio.adapter.ArtistSongAdapter.artistSongList;
import static com.vssyii.vsaudio.adapter.SongAdapter.getImage;
import static com.vssyii.vsaudio.fragments.songs_fragment.songList;
import static com.vssyii.vsaudio.notification.ApplicationClass.ACTION_NEXT;
import static com.vssyii.vsaudio.notification.ApplicationClass.ACTION_PLAY;
import static com.vssyii.vsaudio.notification.ApplicationClass.ACTION_PREVIOUS;
import static com.vssyii.vsaudio.notification.ApplicationClass.CHANNEL_ID_2;

public class PlayerActivity extends AppCompatActivity
                            implements ActionPlaying, ServiceConnection {

    ShapeableImageView player_bg;
    ImageView btDown, btSetting, player_btPlay, player_btPrevious, player_btNext, player_btLike,
            player_btRandom, player_btRepeat, player_btAddPlayList;
    TextView durationPlayed, durationTotal, player_tvTitle, player_tvArtistName;
    SeekBar seekBar;
    private static final int LAUNCH_SECOND_ACTIVITY = 0;

    int position = -1;
    public static List<Song> listSongs = new ArrayList<>();
    public static Uri uri ;
    // public static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Thread playThread, previousThread, nextThread;
    MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);

        initPlayerViews();

        getIntentMethod();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(musicService != null && fromUser) {
                    musicService.seekTo(progress * 1000);
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
                if(musicService != null) {
                    int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    durationPlayed.setText(fommatedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });

        //Random
        player_btRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shuffleBoolean == 1) {
                    shuffleBoolean = 2;
                    player_btRandom.setImageResource(R.drawable.shuffle_onclick);
                    return;
                }
                else {
                    shuffleBoolean = 1;
                    player_btRandom.setImageResource(R.drawable.random_button);
                    return;
                }
            }
        });
        //Repeat
        player_btRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repeatBoolean == 1) {
                    repeatBoolean = 2;
                    player_btRepeat.setImageResource(R.drawable.repeat_onclick);
                    return;
                }
                if(repeatBoolean == 2) {
                    repeatBoolean = 3;
                    player_btRepeat.setImageResource(R.drawable.repeat_onclick1);
                    return;
                }
                if(repeatBoolean == 3) {
                    repeatBoolean = 1;
                    player_btRepeat.setImageResource(R.drawable.repeat);
                    return;
                }
            }
        });
        //Like
        player_btLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeBoolean == 1) {
                    likeBoolean = 2;
                    player_btLike.setImageResource(R.drawable.like_onclick);
                    Toast.makeText(getApplicationContext(), "Loved", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    likeBoolean = 1;
                    player_btLike.setImageResource(R.drawable.bt_like);
                    return;
                }
            }
        });
        //AddOnPlaylist
        player_btAddPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Add on My Music playlist", Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
        playThreadBtn();
        previousThreadBtn();
        nextThreadBtn();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
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

    public void nextButtonClicked() {
        if(musicService.isPlaying()) {
            musicService.stop();
            musicService.release();

            if(shuffleBoolean == 2 && repeatBoolean != 3) {
                position = getRandom(listSongs.size() - 1);
            }
            else if (shuffleBoolean == 1 && shuffleBoolean == 1 && repeatBoolean == 1 || repeatBoolean == 2 ) {
                position = ((position + 1) % listSongs.size());
            }

            uri = Uri.parse(listSongs.get(position).path);
            musicService.createMediaPlayer(position);
            metaData(uri);
            player_tvTitle.setText(listSongs.get(position).title);
            player_tvArtistName.setText(listSongs.get(position).artistName);

            new musicAction(btSetting, getApplicationContext()).createAction(songList, position);

            seekBar.setMax(musicService.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
            });

            musicService.onCompleted();
            musicService.showNotification(R.drawable.notipause);
            musicService.start();

        }
        else {
            musicService.stop();
            musicService.release();

            if(shuffleBoolean == 2 && repeatBoolean != 3) {
                position = getRandom(listSongs.size() - 1);
            }
            else if (shuffleBoolean == 1 && repeatBoolean == 1 || repeatBoolean == 2 ) {
                position = ((position + 1) % listSongs.size());
            }

            uri = Uri.parse(listSongs.get(position).path);
            musicService.createMediaPlayer(position);
            metaData(uri);
            player_tvTitle.setText(listSongs.get(position).title);
            player_tvArtistName.setText(listSongs.get(position).artistName);

            new musicAction(btSetting, getApplicationContext()).createAction(songList, position);

            seekBar.setMax(musicService.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
            });
            musicService.onCompleted();
            musicService.showNotification(R.drawable.notiplay);
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

    public void previousButtonClicked() {
        if(musicService.isPlaying()) {
            musicService.stop();
            musicService.release();

            if(shuffleBoolean == 2 && repeatBoolean != 3) {
                position = getRandom(listSongs.size() - 1);
            }
            else if (shuffleBoolean == 1 && repeatBoolean == 1 || repeatBoolean == 2) {
                position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            }

            uri = Uri.parse(listSongs.get(position).path);
            musicService.createMediaPlayer(position);
            metaData(uri);
            player_tvTitle.setText(listSongs.get(position).title);
            player_tvArtistName.setText(listSongs.get(position).artistName);

            new musicAction(btSetting, getApplicationContext()).createAction(songList, position);

            seekBar.setMax(musicService.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
            });
            musicService.onCompleted();
            musicService.showNotification(R.drawable.notipause);
            musicService.start();
        }
        else {
            musicService.stop();
            musicService.release();

            if(shuffleBoolean == 2 && repeatBoolean != 3) {
                position = getRandom(listSongs.size() - 1);
            }
            else if (shuffleBoolean == 1 && repeatBoolean == 1 || repeatBoolean == 2) {
                position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            }

            uri = Uri.parse(listSongs.get(position).path);
            musicService.createMediaPlayer(position);
            metaData(uri);
            player_tvTitle.setText(listSongs.get(position).title);
            player_tvArtistName.setText(listSongs.get(position).artistName);

            new musicAction(btSetting, getApplicationContext()).createAction(songList, position);

            seekBar.setMax(musicService.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
            });
            musicService.onCompleted();
            musicService.showNotification(R.drawable.notiplay);
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

    public void playButtonClicked() {
        if(musicService.isPlaying()) {
            player_btPlay.setImageResource(R.drawable.play_button);
            musicService.showNotification(R.drawable.notiplay);
            musicService.pause();
            seekBar.setMax(musicService.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                }
            });
        }
        else {
            player_btPlay.setImageResource(R.drawable.pause);
            musicService.showNotification(R.drawable.notipause);
            musicService.start();
            seekBar.setMax(musicService.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(musicService != null) {
                        int mCurrentPosition = musicService.getCurrentPosition() / 1000;
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
        String sender = getIntent().getStringExtra("sender");
        if (sender != null && sender.equals("AlbumSongAdapter")) {
            listSongs = albumSongList;
        }
        else if(sender != null && sender.equals("ArtistSongAdapter")) {
            listSongs = artistSongList;
        }
        else {
            listSongs = songList;
        }

        if(listSongs != null) {
            player_btPlay.setImageResource(R.drawable.pause);
            uri = Uri.parse(listSongs.get(position).path);
        }

        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra("servicePosition", position);
        startService(intent);
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
        durationTotal.setText(fommatedTime(listSongs.get(position).duration / 1000));
        Bitmap bitmap = null;
        byte[] art = retriever.getEmbeddedPicture();

        if (art != null) {
            bitmap = BitmapFactory.decodeByteArray(art, 0, art.length);
            ImageAnimation(this, player_bg, bitmap);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(@Nullable Palette palette) {
                    Palette.Swatch swatch = palette.getDominantSwatch();
                    if (swatch != null) {
                        ImageView player_bgGradian = findViewById(R.id.playerBgGradian);
                        RelativeLayout playerFragment = findViewById(R.id.playerFragment);
                        player_bgGradian.setBackgroundResource(R.drawable.gradian_bg);
                        playerFragment.setBackgroundResource(R.drawable.player_bg);
                        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                                new int[]{swatch.getRgb(), 0x00000000});
                        player_bgGradian.setBackground(gradientDrawable);
                        GradientDrawable gradientDrawableBg = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                                new int[]{0x00000000, 0x00000000,swatch.getRgb(), swatch.getRgb(), swatch.getRgb()});
                        player_bgGradian.setBackground(gradientDrawableBg);
                    } else {
                        ImageView player_bgGradian = findViewById(R.id.playerBgGradian);
                        RelativeLayout playerFragment = findViewById(R.id.playerFragment);
                        player_bgGradian.setBackgroundResource(R.drawable.gradian_bg);
                        playerFragment.setBackgroundResource(R.drawable.player_bg);
                        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                                new int[]{0xff000000, 0x00000000});
                        player_bgGradian.setBackground(gradientDrawable);
                        GradientDrawable gradientDrawableBg = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                                new int[]{0xff000000, 0xff000000});
                        player_bgGradian.setBackground(gradientDrawableBg);
                    }
                }
            });
        }
        else {
            ImageLoader.getInstance().displayImage(getImage(listSongs.get(position).albumId).toString(), player_bg,
                             new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnFail(R.drawable.unknown).resetViewBeforeLoading(true).build());
        }
    }

    public void ImageAnimation(Context context, ImageView imageView, Bitmap bitmap) {
        Animation animOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        Animation animIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Glide.with(context).load(bitmap).into(imageView);
                animIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView.startAnimation(animIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animOut);
    }

    private int getRandom(int i) {
        Random random = new Random();
        return random.nextInt(i + 1);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
        musicService = myBinder.getService();
        musicService.setCallBack(this);
        seekBar.setMax(musicService.getDuration()/1000);
        metaData(uri);
        player_tvTitle.setText(listSongs.get(position).title);
        player_tvArtistName.setText(listSongs.get(position).artistName);
        new musicAction(btSetting, getApplicationContext()).createAction(songList, position);
        musicService.onCompleted();
        musicService.showNotification(R.drawable.notipause);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicService = null;
    }

}