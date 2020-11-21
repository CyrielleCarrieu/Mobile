package com.example.mobile;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static android.content.Context.SENSOR_SERVICE;

public class MotionSensors  extends Fragment implements SensorEventListener,  AdapterView.OnTouchListener {
    View v;
    MainActivity main;
    SensorManager mySensorManager;
    Sensor accelerometerSensor;
    Sensor gyroscopeSensor;
    Sensor magneticSensor;
    Sensor rotationSensor;
    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 150;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.motion_sensors_view, container, false);
        //main = (MainActivity) requireActivity();
        mySensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        v.setOnTouchListener(this);

        accelerometerSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        magneticSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        rotationSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        return v;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView myText;
        DecimalFormat df = new DecimalFormat("###.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                myText = v.findViewById(R.id.textViewAccelerometre);
                myText.setText(df.format(event.values[0])+"\n"+df.format(event.values[1])+"\n"+
                        df.format(event.values[2]));
                break;
            case Sensor.TYPE_GYROSCOPE:
                myText = v.findViewById(R.id.textViewGyroscope);
                myText.setText(df.format(event.values[0])+"\n"+df.format(event.values[1])+"\n"+
                        df.format(event.values[2]));
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                myText = v.findViewById(R.id.textViewMagnetometer);
                myText.setText(df.format(event.values[0])+"\n"+df.format(event.values[1])+"\n"+
                        df.format(event.values[2]));
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                myText = v.findViewById(R.id.textViewRotation);
                myText.setText(df.format(event.values[0])+"\n"+df.format(event.values[1])+"\n"+
                        df.format(event.values[2])+"\n"+df.format(event.values[3])+"\n"+
                        df.format(event.values[4]));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + event.sensor.getName());
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nothing
    }

    @Override
    public void onStart() {
        super.onStart();
        if (accelerometerSensor != null) {
            mySensorManager.registerListener((SensorEventListener) this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (gyroscopeSensor != null) {
            mySensorManager.registerListener((SensorEventListener) this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (magneticSensor != null) {
            mySensorManager.registerListener((SensorEventListener) this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (rotationSensor != null) {
            mySensorManager.registerListener((SensorEventListener) this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mySensorManager.unregisterListener((SensorEventListener) this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                return true;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                float valueX = x2 - x1;
                float valueY = y2 - y1;

                if (Math.abs(valueX) > MIN_DISTANCE){
                    if (x2 < x1){
                        Vibration vibration = new Vibration();
                        ft.replace(R.id.container, vibration, "vibration");
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                }
        }
        return false;
    }
}