package com.amigo.rssreader;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;

/**
 * Created by sudhanshu.gupta on 18/12/15.
 */
public class RSSApplication extends Application {
    DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new DBHelper(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), RSSService.class);
        intent.putExtra("FEED_URL", "http://www.engadget.com/rss.xml");

        //Create a pending intent and get access to the alarm manager
        PendingIntent pIntent =
                PendingIntent.getService(getApplicationContext(), 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //get access to the alarm manager
        AlarmManager alarmManager =
                (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC,System.currentTimeMillis(), 60000, pIntent);

    }
}
