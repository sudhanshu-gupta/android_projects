package com.amigo.countryinfoapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountryListFragment extends Fragment{

    TextView countryListTextView;
    Button addButton;
    private AddCountryDelegate delegate;
    ArrayList<String> countries;

    public CountryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_country_list, container, false);
        countryListTextView = (TextView) fragView.findViewById(R.id.textView);
        addButton = (Button) fragView.findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(delegate != null)
                    delegate.switchToAddCountry();
            }
        });
        return fragView;
    }

    public void setDelegate(AddCountryDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countries = new ArrayList<>();
    }

    private void updateUI() {
        StringBuilder builder = new StringBuilder();
        for(String str:countries)
            builder.append(str).append("\n");
        countryListTextView.setText(builder.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                countries.add(data.getStringExtra("COUNTRY_NAME"));
            }
        }
    }
}
