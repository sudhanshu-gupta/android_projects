package com.amigo.imagecaptureapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    ListView imageListView;
    private static int IMAGE_CAPTURE_REQUEST_CODE = 101;
    String [] files;
    ImageAdapter imageAdapter;
    boolean externalStorageAvailable;
    private String TAG = "MAIN_ACTIVITY";
    boolean imageCaptureDirAvailable = false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("EXTERNAL", true);
        editor.commit();

        externalStorageAvailable = isExternalStorageAvailable();

        if(externalStorageAvailable) {
            Log.i(TAG, "External Storge Available");
            //get a path to the external storage
            File externalDirPath = Environment.getExternalStorageDirectory();
            File imageDirPath = new File(externalDirPath, "image");

            if(!imageDirPath.exists()) {
                boolean success = imageDirPath.mkdirs();
                if(success) {
                    Log.i(TAG, "Creating imagecapture dir");
                    imageCaptureDirAvailable = true;
                } else {
                    Log.i(TAG, "Failed to create image capture dir");
                }
            } else {
                imageCaptureDirAvailable = true;
            }
        }

        imageListView = (ListView) findViewById(R.id.listView);

        //files = fileList();
        files = getFilesFromExternalFolder();
        imageAdapter = new ImageAdapter(this, files);

        //attach the adapter to the list view
        imageListView.setAdapter(imageAdapter);
    }

    public void getImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_CAPTURE_REQUEST_CODE);
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if(state.equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //get the list of files in the file folder
   //     files = fileList();
        files = getFilesFromExternalFolder();
        imageAdapter = new ImageAdapter(this, files);

        //attach the adapter to the list view
        imageListView.setAdapter(imageAdapter);

        //refresh the adapter once picture clicked
        //imageAdapter.notifyDataSetChanged();
    }

    private String [] getFilesFromExternalFolder() {
        File externalDirPath = Environment.getExternalStorageDirectory();
        File imageDirPath = new File(externalDirPath, "imagecapture");
        return imageDirPath.list();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(IMAGE_CAPTURE_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
            Bitmap image = data.getParcelableExtra("data");

            //create a random name for the image
            UUID uuid = UUID.randomUUID();

            //write the file to the "files" folder of the application
            try {
                //FileOutputStream fout = openFileOutput(uuid.toString(), MODE_PRIVATE);
                File externalDirPath = Environment.getExternalStorageDirectory();
                File imageDirPath = new File(externalDirPath, "imagecapture");
                File imageFileName = new File(imageDirPath, uuid.toString());
                FileOutputStream fout = new FileOutputStream(imageFileName);
                image.compress(Bitmap.CompressFormat.PNG, 90, fout);

                //refresh the list view

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
