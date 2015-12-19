package com.amigo.imagedownloader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivityService extends AppCompatActivity {

    String url = "http://software.imdea.org/~alessandra.gorla/images/icons/android.png";
    ImageView imageView;
    ResultReceiver receiver = new ResultReceiver(new Handler()) {
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if(resultCode == 101) {
                //Bitmap image = resultData.getParcelable("IMAGE");
                MyApplication app = (MyApplication) getApplication();
                Bitmap image = app.sharedImage;
                
                imageView.setImageBitmap(image);
            }

        }
    };

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
    }

    public void download(View view) {
        Intent intent = new Intent(this, ImageDownloaderService.class);
        intent.putExtra("RECEIVER", receiver);
        intent.putExtra("IMAGE_URL", url);
        startService(intent);
    }

}
