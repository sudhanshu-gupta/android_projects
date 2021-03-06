package com.amigo.countryinfoapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountryListFragment extends Fragment{

    ListView countryListTextView;
    Button addButton;
    private AddCountryDelegate delegate;
    ArrayList<String> countries;
    CountryAdapter countryAdapter;

    public CountryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_country_list, container, false);
        countryListTextView = (ListView) fragView.findViewById(R.id.listView);
        countryListTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get the intent for Details Activity and set the arguments
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putStringArrayListExtra("COUNTRIES", countries);
                intent.putExtra("SELECTED_POSITION", position);
                startActivity(intent);
            }
        });

        countryAdapter = new CountryAdapter(getActivity(), countries);

        //attach the adapter to the listview
        countryListTextView.setAdapter(countryAdapter);

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
        countryAdapter = new CountryAdapter(getActivity(), countries);
    }

    private void updateUI() {
        countryAdapter.notifyDataSetChanged();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.countrylist_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addAction) {
            if(delegate != null) {
                delegate.switchToAddCountry();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
