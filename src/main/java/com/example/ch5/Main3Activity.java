package com.example.ch5;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Main3Activity extends AppCompatActivity {
private Button button;
private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        button =(Button)findViewById(R.id.button);
        iv = (ImageView)findViewById(R.id.imageView2);

        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                  captureImage();
            }
        });

    }
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.startActivityForResult(intent,100);

    }

    @Override
    protected  void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap)data.getExtras().get("data");
                iv.setImageBitmap(bp);
                Toast.makeText(this,"拍照成功！", Toast.LENGTH_LONG).show();
             /*   try {
                   // save1(bp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
            }
        }
    }


    private void save(Bitmap bm) throws FileNotFoundException {
        File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "tmp");


        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image" + n + ".jpg";
        File file = new File(directory, fname);
        directory.mkdirs();

        try (@SuppressLint("NewApi") FileOutputStream out = new FileOutputStream(file)) {
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(this, "存储成功！", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save1(Bitmap bm) throws FileNotFoundException {
        File directory = new File(Environment.getDataDirectory() + File.separator + "tmp");


        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image" + n + ".jpg";
        File file = new File(directory, fname);
        directory.mkdirs();

        try (@SuppressLint("NewApi") FileOutputStream out = new FileOutputStream(file)) {
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(this, "存储成功！", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
