package com.amigo.imagecaptureapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

/**
 * Created by sudhanshu.gupta on 18/12/15.
 */
public class ImageHelper {

    LruCache<String, Bitmap> cache;

    Context context;

    private class ImageDiskLoader extends AsyncTask<String, Void, Bitmap> {
        WeakReference<ImageView> imageView;
        public ImageDiskLoader(ImageView imageView) {
            this.imageView = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                String name = params[0];
                File externalDirPath = Environment.getExternalStorageDirectory();
                File imageDirPath = new File(externalDirPath, "imagecapture");
                File imageFilePath = new File(imageDirPath, name);
                //FileInputStream inputStream = context.openFileInput(params[0]);
                FileInputStream inputStream = new FileInputStream(imageFilePath);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                cache.put(name, image);
                return image;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            this.imageView.get().setImageBitmap(bitmap);
        }
    }

    public ImageHelper(Context context) {
        this.context = context;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        Log.i("CACHE ", String.valueOf(maxMemory));

        //Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        cache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
    }

    public void displayImage(ImageView imageView, String name) {
        Bitmap image  = cache.get(name);
        if(image == null) {
            Log.i("CACHE_MISS", name);
            ImageDiskLoader imageDiskLoader = new ImageDiskLoader(imageView);
            (new ImageDiskLoader(imageView)).execute(name);
        } else {
            imageView.setImageBitmap(image);
        }
    }


}
