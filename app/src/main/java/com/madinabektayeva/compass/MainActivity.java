package com.madinabektayeva.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ImageView tv_arrow;
    TextView tv_txt;

    private static SensorManager sensorService;
    private Sensor sensor;

    private float currentDegree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_arrow = (ImageView) findViewById(R.id.tv_back);
        tv_txt = (TextView) findViewById(R.id.tv_txt);

        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(sensor != null){
            sensorService.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        }else{
            Toast.makeText(MainActivity.this, "Not supported", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onPause(){
        super.onPause();
        sensorService.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent sensorEvent){

        int degree = Math.round(sensorEvent.values[0]);
        tv_txt.setText(Integer.toString(degree)+ (char) 0x00B0);

        RotateAnimation ra = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        ra.setDuration(1000);
        ra.setFillAfter(true);

        tv_arrow.startAnimation(ra);
        currentDegree = -degree;
    }
    public void onAccuracyChanged(Sensor sensor, int i){
        //
    }


}
