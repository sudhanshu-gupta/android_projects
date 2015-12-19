package com.amigo.rssreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sudhanshu.gupta on 18/12/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static String FEED_ITEM_TABLE = "feedItem";

    public DBHelper(Context context) {
        super(context, "rssreader.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE feedItem( _id INTEGER PRIMARY KEY, " +
                "title TEXT NOT NULL, " +
                "link TEXT NOT NULL)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists feedItem";
        db.execSQL(query);
    }
}
