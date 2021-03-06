package com.amigo.imagedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivityAsync extends AppCompatActivity {

    String url = "http://software.imdea.org/~alessandra.gorla/images/icons/android.png";
    ImageView imageView;
    Handler myHandler;
    private static class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView;

        public ImageDownloaderTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap image = downloadImage(params[0]);
            if(image != null) {
                Log.i("ImageDownloaderTask", "Image Download successfully");
                return image;
            }
            return null;
        }

        public Bitmap downloadImage(String url) {
            long start = System.currentTimeMillis();
            Bitmap image = null;
            try {
                //create a url object
                URL link = new URL(url);

                //open the connection to the server
                URLConnection connection = link.openConnection();

                //get the stream from the connection
                InputStream instr = connection.getInputStream();

                //get the image bytes and read it and convert to bitmap
                image = BitmapFactory.decodeStream(instr);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            long end = (System.currentTimeMillis()-start);
            Log.i("TIME_TAKEN", String.valueOf(end));
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null)
                imageView.setImageBitmap(bitmap);
        }
    }

    private static class MyHandler extends Handler{
        private ImageView imageView;

        public MyHandler(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            Bitmap image = bundle.getParcelable("IMAGE");
            imageView.setImageBitmap(image);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        myHandler = new Handler();
    }

    public void download(View view) {
        ImageDownloaderTask task = new ImageDownloaderTask(imageView);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

}
