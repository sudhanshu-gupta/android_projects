package com.amigo.rssreader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by sudhanshu.gupta on 18/12/15.
 */
public class FeedAdapter extends BaseAdapter {

    Context context;
    ArrayList<FeedItem> feeds;

    public FeedAdapter(Context context, ArrayList<FeedItem> feeds) {
        this.context = context;
        this.feeds = feeds;
    }

    @Override
    public int getCount() {
        return feeds.size();
    }

    @Override
    public Object getItem(int position) {
        return feeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return feeds.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
