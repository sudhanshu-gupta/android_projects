package com.amigo.pendingintentexample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    AlarmManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get access to the class alarm manager
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    public void register(View v) {
        //create an intent for the activity that will be launched by alarm service
        Intent intent = new Intent(this, MainActivity.class);

        //wrap the intent into a pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // tell teh alarm manager to run the activity in every one minute
        manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, pendingIntent);
    }

    public void unregister(View v) {
        Intent intent = new Intent(this, MainActivity.class);

        //wrap the intent into a pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //cancel the pending intent
        manager.cancel(pendingIntent);
    }
}
