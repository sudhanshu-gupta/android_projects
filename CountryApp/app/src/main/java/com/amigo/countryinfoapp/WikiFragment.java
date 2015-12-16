package com.amigo.countryinfoapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class WikiFragment extends Fragment {

    WebView webView;
    public WikiFragment() {
        // Required empty public constructor
    }

    final String WIKI_URL = "https://en.wikipedia.org/wiki/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag = inflater.inflate(R.layout.fragment_wiki, container, false);
        webView = (WebView) frag.findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        Bundle bundle = getArguments();
        String countryName = bundle.getString("COUNTRY_NAME");
        String countryWikiPath = WIKI_URL + countryName;
        webView.loadUrl(countryWikiPath);
        return frag;
    }

}
