package com.example.mobile;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static android.content.Context.SENSOR_SERVICE;

public class Vibration extends Fragment implements AdapterView.OnTouchListener, SensorEventListener {

    private View v;
    private Switch switchVibration;
    private Vibrator vibrator;
    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 150;
    private Sensor gyroscopeSensor;
    private SensorManager mySensorManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.vibration_view, container, false);
        this.switchVibration = v.findViewById(R.id.vibration);
        v.setOnTouchListener(this);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        switchVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final long[] pattern = {0, 1000};
                if (switchVibration.isChecked()){
                    vibrator.vibrate(pattern, 0);
                }
                else {
                    vibrator.cancel();
                }
            }
        });

        mySensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        return v;
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

                if (Math.abs(valueX) > MIN_DISTANCE) {
                    if (x2 < x1) {
                        Photo photo = new Photo();
                        ft.replace(R.id.container, photo, "photo");
                        ft.addToBackStack(null);
                        ft.commit();
                    } else {
                        MotionSensors motionSensors = new MotionSensors();
                        ft.replace(R.id.container, motionSensors, "motion sensors");
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                }
                vibrator.cancel();
                break;
        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        DecimalFormat df = new DecimalFormat("###.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        if( event.sensor.getType() == Sensor.TYPE_GYROSCOPE ) {
            // Test si le telephone est statique
            if( (df.format(event.values[0]).equals("0") || df.format(event.values[0]).equals("-0") ) &&
                    (df.format(event.values[1]).equals("0")  || df.format(event.values[1]).equals("-0") ) &&
                    (df.format(event.values[2]).equals("0")  || df.format(event.values[2]).equals("-0") ) ) {
                final long[] pattern = {0, 1000};
                vibrator.vibrate(pattern, 0);
                switchVibration.setChecked(true);
            }
            else if(!switchVibration.isChecked()){
                vibrator.cancel();
                switchVibration.setChecked(false);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No need
    }

    @Override
    public void onStart() {
        super.onStart();
        if (gyroscopeSensor != null) {
            mySensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mySensorManager.unregisterListener(this);
    }
}