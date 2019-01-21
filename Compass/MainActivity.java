package com.example.compass;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.hardware.SensorEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.hardware.SensorEventListener;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    // senzor
    private SensorManager SensorManage;
    // iamaginea compasului
    private ImageView compassimage;
    // registreaza ultimul unghi
    private float DegreeStart = 0f;
    TextView DegreeTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        compassimage = (ImageView) findViewById(R.id.compass_image);
        // Gradele
        DegreeTV = (TextView) findViewById(R.id.DegreeTV);
        // initializare senzor
        SensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // battery save
        SensorManage.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
		// orientarea senzorului inregistrat
        SensorManage.registerListener(this, SensorManage.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
		// unghiul in jurul axei Z
        float degree = Math.round(event.values[0]);
        DegreeTV.setText("Heading: " + Float.toString(degree) + " degrees");
		//animatia de rotatie
        RotateAnimation ra = new RotateAnimation(
                DegreeStart,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setFillAfter(true);
		//timpul alocat animatiei
        ra.setDuration(210);
        // inceputul animatiei compasului
        compassimage.startAnimation(ra);
        DegreeStart = -degree;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nu se foloseste
    }
}