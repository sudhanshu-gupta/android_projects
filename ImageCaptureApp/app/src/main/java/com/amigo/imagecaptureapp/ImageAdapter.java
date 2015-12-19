package com.amigo.imagecaptureapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by sudhanshu.gupta on 18/12/15.
 */
public class ImageAdapter extends BaseAdapter {

    String [] files;
    Context context;
    ImageHelper imageHelper;

    public ImageAdapter(Context context, String[] files) {
        this.files = files;
        this.context = context;
        imageHelper = new ImageHelper(context);
    }

    @Override
    public int getCount() {
        return files.length;
    }

    @Override
    public Object getItem(int position) {
        return files[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mainView = null;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);

            mainView = inflater.inflate(R.layout.row, null);
        } else {
            mainView = convertView;
        }

        //display th eimage in hte image view
        //get the imageView reference from the row.xml view

        ImageView imageView = (ImageView) mainView.findViewById(R.id.imageView);
        imageHelper.displayImage(imageView, files[position]);

        return mainView;
    }
}
