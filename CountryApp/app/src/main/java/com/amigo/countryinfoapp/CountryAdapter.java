package com.amigo.countryinfoapp;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sudhanshu.gupta on 16/12/15.
 */
public class CountryAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> countries;
    final private String TAG = "COUNTRY_ADAPTER";

    public CountryAdapter(Context context, ArrayList<String> countries) {
        this.context = context;
        this.countries = countries;
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
        } else {
            mainView = convertView;
        }
        String counteryName = countries.get(position);
        TextView  textView = (TextView) mainView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) mainView.findViewById(R.id.imageView);

        textView.setText(counteryName);
        return mainView;
    }
}
