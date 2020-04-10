package com.example.ch5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    private String imgPath = "/sdcard/";
    private ImageView imageView;
    private File file;
    private  String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        boolean isSdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSdCardExist){
            String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
           filePath= sdpath + File.separator + "image1.jpg";
            ImageView iv = (ImageView) findViewById(R.id.imageView1);


            InputStream inputStream =null;
            AssetManager assetManager = getAssets() ;

            try {
                inputStream = assetManager.open("image1.jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap bm = BitmapFactory.decodeStream(inputStream);

            Bitmap bm1 = BitmapFactory.decodeFile(filePath);
        iv.setImageBitmap(bm);

    }
}

}
