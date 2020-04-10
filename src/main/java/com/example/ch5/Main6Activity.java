package com.example.ch5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class Main6Activity extends Activity implements OnClickListener,Callback {

    private SurfaceView   mSurfaceView;//相机视频浏览
    private ImageView     mImageView;  //相片
    private SurfaceHolder mSurfaceHolder;
    private ImageView     shutter;    //快照按钮
    private Camera        mCamera = null;    //相机
    private boolean       mPreviewRunning;//运行相机浏览
    private static final int MENU_START = 1;
    private static final int MENU_SENSOR = 2;
    private Bitmap bitmap;           //相片Bitmap
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //隐藏标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置布局文件
        setContentView(R.layout.activity_main6);
        mSurfaceView = (SurfaceView) findViewById(R.id.camera);
        mImageView = (ImageView) findViewById(R.id.image);
        shutter= (ImageView) findViewById(R.id.shutter);
        //设置快照按钮事件
        shutter.setOnClickListener(this);
        mImageView.setVisibility(View.GONE);
        mSurfaceHolder = mSurfaceView.getHolder();
        //设置SurfaceHolder回调事件
        mSurfaceHolder.addCallback(this);

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


    }
    //快照按钮拍照事件
    public void onClick(View v) {

        //判断是否可以进行拍照
        if (mPreviewRunning) {

            shutter.setEnabled(false);
            //设置自动对焦
            mCamera.autoFocus(new AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    //聚集后进行拍照
                    mCamera.takePicture(mShutterCallback,
                            null, mPictureCallback);
                }});
        }
    }
    //相机图片拍照回调函数
    PictureCallback mPictureCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera1) {
            //断定相片数据是否不为空
            if (data != null) {
                saveAndShow(data);
            }
        }
    };
    //快照回调函数
    ShutterCallback mShutterCallback = new ShutterCallback() {

        public void onShutter() {
            System.out.println("快照回调函数.....");
        }
    };
    //SurfaceView改变时调用
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        try{
            //判断是否运行相机，运行就停止掉
            if (mPreviewRunning) {
                mCamera.stopPreview();
            }
            //启动相机
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            mPreviewRunning = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //SurfaceView创建时调用
    public void surfaceCreated(SurfaceHolder holder) {

        setCameraParams();
    }
    //设置Camera参数
    public  void  setCameraParams()
    {
        if(mCamera != null)
        {
            return ;
        }
        //创建相机，打开相机
        mCamera = Camera.open();
        //设置相机参数
        Parameters params = mCamera.getParameters();
        //拍照自动对焦
        params.setFocusMode(Parameters.FOCUS_MODE_AUTO);
        //设置预览帧速率
        params.setPreviewFrameRate(3);
        //设置预览格式
        params.setPreviewFormat(PixelFormat.YCbCr_422_SP);
        //设置图片质量百分比
        params.set("jpeg-quality",85);
        //获取相机支持图片分辨率
        List<Size> list = params.getSupportedPictureSizes();
        Size size = list.get(0);
        int w = size.width;
        int h = size.height;
        //设置图片大小
        params.setPictureSize(w, h);
        //设置自动闪光灯
        params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
    }

    //SurfaceView消亡时调用
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mCamera!=null)
        {
            //停止相机预览
            mCamera.stopPreview();
            mPreviewRunning = false;
            //回收相机
            mCamera.release();
            mCamera = null;
        }
    }
    //创建菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_START,0,  "重拍");
        menu.add(0, MENU_SENSOR,0, "打开相册");
        return super.onCreateOptionsMenu(menu);
    }
    //菜单事件
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_START) {
            //重启相机拍照
            setRequestedOrientation(ActivityInfo.
                    SCREEN_ORIENTATION_PORTRAIT);
            setRequestedOrientation(ActivityInfo.
                    SCREEN_ORIENTATION_LANDSCAPE);
            return true;
        }else if(item.getItemId() == MENU_SENSOR)
        {
            //Intent intent = new Intent(this,AlbumActivity.class);
            //startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    //保存和显示图片
    public void  saveAndShow(byte[] data)
    {
        try
        {
            //图片id
            String imageId=System.currentTimeMillis()+"";
            //相片保存路径
            String pathName = android.os.Environment.
                    getExternalStorageDirectory().getPath()
                    +"/com.demo.pr4";
            //创建文件
            File file=new File(pathName);
            if(!file.exists())
            {
                file.mkdirs();
            }
            //创建文件
            pathName+="/"+imageId+".jpeg";
            file=new File(pathName);
            if(!file.exists()){
                file.createNewFile();//文件不存在则新建
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            //AlbumActivity album=new AlbumActivity();

            //读取相片Bitmap
           // bitmap = album.loadImage(pathName);
            //bitmap = BitmapFactory.decodeFile(pathName,options);
            //设置到控件上显示
           // mImageView.setImageBitmap(bitmap);
            //mImageView.setVisibility(View.VISIBLE);
            mSurfaceView.setVisibility(View.GONE);
            //停止相机浏览
            if(mPreviewRunning) {
                mCamera.stopPreview();
                mPreviewRunning = false;
            }
            shutter.setEnabled(true);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
