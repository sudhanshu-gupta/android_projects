package com.amigo.flikrsearch;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText searchEditText;
    GridView imageGridView;
    FlickrGetter flickrGetter;
    ArrayList<FlickrItem> items;
    private static String TAG = "MAIN_ACTIVITY";
    ImageAdapter adapter;

    private static class FlickrJSONTask extends AsyncTask<String, Void, ArrayList<FlickrItem>> {

        FlickrGetter flickrGetter;
        ArrayList<FlickrItem> items;
        ImageAdapter imageAdapter;

        public FlickrJSONTask(FlickrGetter flickrGetter, ArrayList<FlickrItem> items, ImageAdapter adapter) {
            this.flickrGetter = flickrGetter;
            this.items = items;
            imageAdapter = adapter;
        }

        @Override
        protected ArrayList<FlickrItem> doInBackground(String... params) {
            return flickrGetter.fetchItems(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<FlickrItem> flickrItems) {
            super.onPostExecute(flickrItems);
            if(flickrItems != null)
                items.addAll(flickrItems);
            //update the UI with new images
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = (EditText) findViewById(R.id.editText);
        imageGridView = (GridView) findViewById(R.id.gridView);

        flickrGetter  = new FlickrGetter();
        items = new ArrayList<>();

        adapter = new ImageAdapter(this, items);
        imageGridView.setAdapter(adapter);
    }

    public void search(View view) {
        items.clear();
        adapter.notifyDataSetChanged();
        String searchText = searchEditText.getText().toString();
        FlickrJSONTask task = new FlickrJSONTask(flickrGetter, items, adapter);
        task.execute(searchText);
    }
}
