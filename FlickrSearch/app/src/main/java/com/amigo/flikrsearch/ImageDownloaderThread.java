package com.amigo.flikrsearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sudhanshu.gupta on 19/12/15.
 */
public class ImageDownloaderThread extends HandlerThread {

    Handler downloaderThreadHandler;
    Handler uiThreadHandler;
    ExecutorService executor;

    public void downloadImage(ImageView vi, String url, int position) {
        Message msg = Message.obtain();
        msg.obj = vi;
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION",  position);
        bundle.putString("URL", url);
        msg.setData(bundle);

        downloaderThreadHandler.sendMessage(msg);
    }

    public ImageDownloaderThread(Handler handler) {
        super("ImageDownlaoderThread");
        uiThreadHandler = handler;
        executor = Executors.newFixedThreadPool(10);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        downloaderThreadHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                final String url = bundle.getString("URL");
                final int position = bundle.getInt("POSITION");

                final ImageView view = (ImageView) msg.obj;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        final Bitmap image = getImage(url);

                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                int tagPosition = (int) view.getTag();
                                if(tagPosition == position)
                                    view.setImageBitmap(image);
                            }
                        };

                        uiThreadHandler.post(r);
                    }
                };
                executor.submit(runnable);

            }
        };
    }

    private Bitmap getImage(@NonNull String url) {
        try {
            URL link = new URL(url);
            URLConnection connection = link.openConnection();
            InputStream inputStream = connection.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}