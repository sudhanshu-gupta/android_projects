package com.amigo.countryinfoapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements AddCountryDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            //Load country list frag
            loadCountryListFragment();
        } else {
            FragmentManager manager = getSupportFragmentManager();
            CountryListFragment fragment = (CountryListFragment) manager.findFragmentByTag("CLF");
            fragment.setDelegate(this);
        }
    }

    private void loadCountryListFragment() {
        //create the frag obj
        CountryListFragment clf = new CountryListFragment();

        //get access to the fragment manager
        FragmentManager manager = getSupportFragmentManager();

        clf.setDelegate(this);

        //create a fragment transaction
        FragmentTransaction trans = manager.beginTransaction();

        //add fragment to the transaction
        trans.add(R.id.mainLayout, clf, "CLF");
        trans.commit();
    }

    @Override
    public void switchToAddCountry() {
        FragmentManager manager = getSupportFragmentManager();
        AddCountryFragment acf = new AddCountryFragment();
        FragmentTransaction trans = manager.beginTransaction();
        Fragment clf = manager.findFragmentByTag("CLF");
        acf.setTargetFragment(clf, 101);
        //remove the country list fragment if found
        if(clf != null) {
            trans.remove(clf);
            trans.add(R.id.mainLayout, acf, "ACF");
            trans.addToBackStack(null);
            trans.commit();
        }
    }
}
