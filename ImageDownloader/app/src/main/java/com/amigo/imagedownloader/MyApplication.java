package com.amigo.imagedownloader;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by sudhanshu.gupta on 17/12/15.
 */
public class MyApplication extends Application {

    Bitmap sharedImage;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
