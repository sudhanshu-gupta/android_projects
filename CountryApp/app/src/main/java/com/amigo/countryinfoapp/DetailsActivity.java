package com.amigo.countryinfoapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<String> countries;
    int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //get access to the launching intent and parameters
        Intent launchingIntent = getIntent();
        countries = launchingIntent.getStringArrayListExtra("COUNTRIES");
        selectedPosition = launchingIntent.getIntExtra("SELECTED_POSITION", 0);

        //get access to the view pager
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        //Create a Fragment Manager
        FragmentManager manager = getSupportFragmentManager();

        //set the adapter using the fragment state pager adapter
        viewPager.setAdapter(new FragmentStatePagerAdapter(manager) {

            @Override
            public int getCount() {
                return countries.size();
            }

            @Override
            public Fragment getItem(int position) {

                String countryName = countries.get(position);

                WikiFragment wf = new WikiFragment();
                Bundle bundle = new Bundle();
                bundle.putString("COUNTRY_NAME", countryName);
                wf.setArguments(bundle);

                return wf;
            }
        });

        //set the current position of the pager adapter, default is the 0th position
        viewPager.setCurrentItem(selectedPosition);
    }
}
