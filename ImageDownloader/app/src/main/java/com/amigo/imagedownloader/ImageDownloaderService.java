package com.amigo.imagedownloader;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class ImageDownloaderService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.amigo.imagedownloader.action.FOO";
    public static final String ACTION_BAZ = "com.amigo.imagedownloader.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.amigo.imagedownloader.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.amigo.imagedownloader.extra.PARAM2";

    public ImageDownloaderService() {
        super("ImageDownloaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String url = intent.getStringExtra("IMAGE_URL");
            ResultReceiver receiver = intent.getParcelableExtra("RECEIVER");
            Bitmap image = downloadImage(url);
            if(image != null) {
                Log.i("ImageDownloaderService", "Image Downloaded successfully");
                MyApplication app = (MyApplication) getApplication();
                app.sharedImage = image;
                Bundle b = new Bundle();
                //b.putParcelable("IMAGE", image);
                receiver.send(101, b);
            }
        }
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

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
