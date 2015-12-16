package com.amigo.countryinfoapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by sudhanshu.gupta on 16/12/15.
 */
public class CountryAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> countries;
    final private String TAG = "COUNTRY_ADAPTER";

    LruCache<String, Bitmap> cache;

    public CountryAdapter(Context context, ArrayList<String> countries) {
        this.context = context;
        this.countries = countries;


        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        Log.i("CACHE ", String.valueOf(maxMemory));

        final int cacheSize = maxMemory / 8;
        cache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
    }

    private static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position+1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mainView;

        if(convertView == null) {

            Log.i(TAG, "getView(" + position + ")");
            //get access to the layout inflater
            LayoutInflater inflater = LayoutInflater.from(context);


            //inflate row.xml
            mainView = inflater.inflate(R.layout.row, null);

            ViewHolder holder = new ViewHolder();

            holder.textView = (TextView) mainView.findViewById(R.id.textView);
            holder.imageView = (ImageView) mainView.findViewById(R.id.imageView);

            //attach the view holder to the view
            mainView.setTag(holder);

        } else {
            mainView = convertView;
        }
        String counteryName = countries.get(position);

        //get access to the view holder of the view
        ViewHolder vh = (ViewHolder) mainView.getTag();
        vh.textView.setText(counteryName);

        //display the counter name of the textView
        AssetManager manager = context.getAssets();
        String filePath = "flags-32/" + counteryName + ".png";
        try {
            Bitmap image  = cache.get(filePath.replaceAll(".png", "").replace("flags-32/", ""));
            if(image == null) {
                Log.i("CACHE_MISS", filePath);
                InputStream inStr = manager.open(filePath);
                image = BitmapFactory.decodeStream(inStr);
                cache.put(filePath.replaceAll(".png", "").replace("flags-32/", ""), image);
            }
            vh.imageView.setImageBitmap(image);
        } catch (IOException e) {
            e.printStackTrace();
            vh.imageView.setImageResource(R.drawable.vector_a_arrow_logo);
        }
        return mainView;
    }
}
