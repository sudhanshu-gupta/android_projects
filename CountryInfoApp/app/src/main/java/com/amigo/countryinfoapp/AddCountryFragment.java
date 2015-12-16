package com.amigo.countryinfoapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCountryFragment extends Fragment {

    EditText countryNameEditText;
    Button done, cancel;

    public AddCountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_add_country, container, false);
        countryNameEditText = (EditText) fragView.findViewById(R.id.editText);
        done = (Button) fragView.findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("COUNTRY_NAME", countryNameEditText.getText().toString());
                getTargetFragment().onActivityResult(101, Activity.RESULT_OK, intent);

                //reverse the transaction to the backstack
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });
        cancel = (Button) fragView.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //reverse the transaction to the backstack
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });
        return fragView;
    }


}
