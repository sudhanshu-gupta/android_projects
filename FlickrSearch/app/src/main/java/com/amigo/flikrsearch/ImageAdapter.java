package com.amigo.flikrsearch;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by sudhanshu.gupta on 19/12/15.
 */
public class ImageAdapter extends BaseAdapter {

    Context context;
    ArrayList<FlickrItem> items;
    ImageDownloaderThread downloaderThread;


    public ImageAdapter(Context context, ArrayList<FlickrItem> items) {
        this.context = context;
        this.items = items;
        downloaderThread = new ImageDownloaderThread(new Handler());
        downloaderThread.start();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mainView;
        if(convertView == null) {
            mainView = LayoutInflater.from(context).inflate(R.layout.item, null);
        } else {
            mainView = convertView;
        }

        ImageView imageView = (ImageView) mainView.findViewById(R.id.imageView);
        imageView.setTag(position);
        imageView.setImageResource(R.mipmap.ic_launcher);
        FlickrItem item = items.get(position);

        //download the image from the item.url and display it in the image view
        downloaderThread.downloadImage(imageView, item.getUrl(), position);


        return mainView;
    }
}
