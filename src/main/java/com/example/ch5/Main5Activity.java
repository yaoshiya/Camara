package com.example.ch5;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Main5Activity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mOrientation;
    private Sensor mLight;
    private TextView tAccelerometer;
    private TextView tOrientation;
    private TextView tLight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        tAccelerometer = (TextView) this.findViewById(R.id.tAccelerometer);
        tOrientation = (TextView) this.findViewById(R.id.tOrientation);
        tLight = (TextView) this.findViewById(R.id.tLight);
        // 获得传感器管理器
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获得加速度传感器
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //获得方向度传感器
        mOrientation = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ORIENTATION);
        //获得光线传感器
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    protected void onResume() {
        super.onResume();
        //对加速度传感器注册传感器监听器
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        //对方向传感器注册传感器监听器
        mSensorManager.registerListener(this, mOrientation,
                SensorManager.SENSOR_DELAY_NORMAL);
        //对光线传感器注册传感器监听器
        mSensorManager.registerListener(this, mLight,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        // 取消传感器监听器的注册
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 在此方法中，编写当某个传感器的精度发生变化时应执行的操作
    }

    public void onSensorChanged(SensorEvent event) {
        // 在此方法中，编写当某个传感器的数值发生变化时应执行的操作
        // 得到方向的值
        float x = event.values[SensorManager.DATA_X];
        float y = event.values[SensorManager.DATA_Y];
        float z = event.values[SensorManager.DATA_Z];
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            tOrientation.setText("方位: " + x + ", " + y + ", " + z);
        }
        // 得到加速度的值
        else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            tAccelerometer.setText("加速度: " + x + ", " + y + ", " + z);
        } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            tLight.setText("光线: " + event.values[0]);

        }
    }


}
