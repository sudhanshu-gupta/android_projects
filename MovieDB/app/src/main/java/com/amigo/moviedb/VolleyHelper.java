package com.amigo.moviedb;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by sudhanshu.gupta on 19/12/15.
 */
public class VolleyHelper {
    RequestQueue requestQueue;
    private static VolleyHelper instance;
    Context context;

    private VolleyHelper(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyHelper getInstance(Context context) {
        if(instance == null) {
            instance = new VolleyHelper(context);
        }
        return instance;
    }
}
