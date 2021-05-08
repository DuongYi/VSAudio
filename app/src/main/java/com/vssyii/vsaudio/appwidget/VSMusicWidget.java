package com.vssyii.vsaudio.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.vssyii.vsaudio.MainActivity;
import com.vssyii.vsaudio.R;
import com.vssyii.vsaudio.service.MusicService;

import java.net.MalformedURLException;

/**
 * Implementation of App Widget functionality.
 */
public class VSMusicWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        RemoteViews appWidgetView = new RemoteViews(context.getPackageName(), R.layout.v_s_music_widget);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        appWidgetView.setOnClickPendingIntent(R.id.clickable_area, pendingIntent);

        appWidgetView.setImageViewResource(R.id.image, R.drawable.music_wallpaper);
        appWidgetView.setImageViewResource(R.id.widget_next, R.drawable.notinext);
        appWidgetView.setImageViewResource(R.id.widget_play_pause, R.drawable.notiplay);
        appWidgetView.setImageViewResource(R.id.widget_prev, R.drawable.notiprevious);

        appWidgetView.setTextViewText(R.id.widget_title, "On The Ground");
        appWidgetView.setTextViewText(R.id.widget_artist, "Rose");

        appWidgetManager.updateAppWidget(appWidgetId, appWidgetView);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}