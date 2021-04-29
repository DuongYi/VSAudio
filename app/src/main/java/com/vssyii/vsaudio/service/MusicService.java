package com.vssyii.vsaudio.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.vssyii.vsaudio.models.Song;

import java.util.ArrayList;
import java.util.List;

import static com.vssyii.vsaudio.PlayerActivity.listSongs;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    IBinder mBinder = new MyBinder();
    MediaPlayer mediaPlayer;
    List<Song> songList = new ArrayList<>();
    private Uri uri;
    int position = -1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Bind", "Method");
        return mBinder;
    }

    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int myPosition = intent.getIntExtra("servicePosition", -1);
        if (myPosition != -1) {
            playMedia(myPosition);
        }
        return START_STICKY;
    }

    private void playMedia(int startPosition) {
        songList = listSongs;
        position = startPosition;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        createMediaPlayer(position);
        mediaPlayer.start();
    }

    public void start() {
        mediaPlayer.start();
    }
    public void pause() {
        mediaPlayer.pause();
    }
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }
    public void stop() {
        mediaPlayer.stop();
    }
    public void release() {
        mediaPlayer.release();
    }
    public int getDuration() {
        return mediaPlayer.getDuration();
    }
    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }
    public void createMediaPlayer(int position) {
        uri = Uri.parse(songList.get(position).path);
        mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
    }
    public void onCompleted() {
        mediaPlayer.setOnCompletionListener(this);
    }
    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
