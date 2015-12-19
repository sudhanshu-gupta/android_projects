package com.amigo.contentproviderexample;

import android.database.Cursor;
import android.net.Uri;
import android.provider.Browser;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getBookmarks(View view) {
        //uri for the content provider
        //Uri uri = Uri.parse("content://browser/bookmarks");
        Uri uri = CallLog.Calls.CONTENT_URI;

        //columns exposed by the content provider
        String [] cols = {CallLog.Calls._ID, CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};

        Cursor cursor = getContentResolver().query(uri, cols, null, null, null);

        //iterate to the cursor object
        if(cursor.getCount()>0) {
            //move to the first
            cursor.moveToFirst();

            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);

            do {
                String date =  cursor.getString(dateIndex);
                String number = cursor.getString(numberIndex);
                String duration = cursor.getString(durationIndex);

                Log.i(TAG, date +" " + number + " "+duration);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
    }

    public void getRSSFeed(View view) {
        Uri uri = Uri.parse("content://com.amigo.rssreader");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if(cursor.getCount()>0) {
            //move to the first
            cursor.moveToFirst();

            int titleIndex = cursor.getColumnIndex("title");
            int linkIndex = cursor.getColumnIndex("link");

            do {
                String title =  cursor.getString(titleIndex);
                String link = cursor.getString(linkIndex);

                Log.i(TAG, title +"  " + link);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
    }
}
