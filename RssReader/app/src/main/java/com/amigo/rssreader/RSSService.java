package com.amigo.rssreader;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class RSSService extends IntentService {

    RSSParser rssParserHandler = new RSSParser();
    NotificationManager notificationManager;
    DBHelper dbHelper;
    SQLiteDatabase database;

    public RSSService() {
        super("RSSService");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        dbHelper = ((RSSApplication) getApplication()).dbHelper;
        //using the dbhelper get access to the readable database object
        database = dbHelper.getReadableDatabase();
        if (intent != null) {
            String url = intent.getStringExtra("FEED_URL");
            getNewsFeed(url);
        }
    }

    boolean alreadyExists(FeedItem item) {
        int count = getCount(item.getTitle());
        return count > 0;
    }

    public int getCount(String title) {
        Cursor c = null;
        String query = "select * from feedItem where title = ?";
        c = database.rawQuery(query, new String[] {title});
        if(c.moveToFirst()) {
            return c.getInt(0);
        }
        return 0;
    }

    public void getNewsFeed(String url) {
        try {
            URL link = new URL(url);
            URLConnection connection = link.openConnection();
            InputStream inStr = connection.getInputStream();

            //Parse the rss xml
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            //parsing the stream of rss parser
            parser.parse(inStr,  rssParserHandler);

            //get the array of feedItems from the parser
            ArrayList<FeedItem> items = rssParserHandler.items;

            //iterate and insert record into the db
            for(FeedItem item: items) {
                //create a content values object
                ContentValues row = new ContentValues();
                row.put("title", item.getTitle());
                row.put("link", item.getLink());
                if(!alreadyExists(item))
                    database.insert(DBHelper.FEED_ITEM_TABLE, null, row);
                    sendNotification(item);
            }
            Intent intent = new Intent("com.amigo.rssreader.REFRESH");
            sendBroadcast(intent);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(FeedItem item) {
        //create a notification object using the notificationCompat builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setContentTitle("RSSReader");
        builder.setContentText(item.getTitle());

        //Create a pending intent to attach with the notif
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 101, i, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Notification notif = builder.build();

        notificationManager.notify(101, notif);

    }

}
