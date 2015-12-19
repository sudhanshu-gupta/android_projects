package com.amigo.rssreader;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {
    public BootCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent i) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i("BootCompleteReceiver", "RSS Service Starting after reboot");
        Intent intent = new Intent(context, RSSService.class);
        intent.putExtra("FEED_URL", "http://www.engadget.com/rss.xml");

        //Create a pending intent and get access to the alarm manager
        PendingIntent pIntent =
                PendingIntent.getService(context, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //get access to the alarm manager
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, pIntent);

    }
}
