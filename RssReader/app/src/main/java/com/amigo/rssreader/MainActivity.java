package com.amigo.rssreader;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    ListView feedItemListView;
    SimpleCursorAdapter adapter;
    Cursor cursor;
    DBHelper dbHelper;
    SQLiteDatabase database;
    static String TAG = "MAIN_ACTIVITY";

    static class RefreshBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Received refresh from service");
        }
    }

    RefreshBroadcastReceiver refreshBroadcastReceiver = new RefreshBroadcastReceiver();

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.amigo.rssreader.REFRESH");
        registerReceiver(refreshBroadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(refreshBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedItemListView = (ListView) findViewById(R.id.listView);
        feedItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] args = {Long.toString(id)};
                Cursor c = database.query(DBHelper.FEED_ITEM_TABLE, null, "_id = ?", args, null, null, null, null);

                //read the item details from the cursor
                c.moveToFirst();

                int linkIndex = c.getColumnIndex("link");

                String link = c.getString(linkIndex);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        });

        dbHelper = ((RSSApplication) getApplication()).dbHelper;
        database = dbHelper.getReadableDatabase();
        cursor = database.query(DBHelper.FEED_ITEM_TABLE, null, null, null, null, null, null);

        String[] from = {"title", "link"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_2, cursor, from, to);

        feedItemListView.setAdapter(adapter);
    }

    public void refresh(View view) {
        Intent intent = new Intent(this, RSSService.class);
        intent.putExtra("FEED_URL", "http://www.engadget.com/rss.xml");

        //Create a pending intent and get access to the alarm manager
        PendingIntent pIntent =
                PendingIntent.getService(getApplicationContext(), 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //get access to the alarm manager
        AlarmManager alarmManager =
                (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, pIntent);
    }


}
