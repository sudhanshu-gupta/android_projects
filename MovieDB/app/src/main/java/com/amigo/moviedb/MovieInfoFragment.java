package com.amigo.moviedb;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieInfoFragment extends Fragment {

    TextView textView;
    int position;

    VolleyHelper volleyHelper;
    MovieHelper movieHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volleyHelper = VolleyHelper.getInstance(getActivity());
        movieHelper = new MovieHelper();
    }

    public MovieInfoFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_movie_info, container, false);
        textView = (TextView) mainView.findViewById(R.id.textView);

        position = getArguments().getInt("POSITION");
        textView.setText(Integer.toString(position));

        StringRequest request = new StringRequest(movieHelper.getPopularMoviesUrl(), new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                movieInfoItems = movieHelper.parseMovies(response);



            }}, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        return mainView;
    }

}
