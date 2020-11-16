package com.example.mobile;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Vibration extends Fragment {

    private View v;
    private MainActivity main;
    private Switch switchVibration;
    private Vibrator vibrator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.vibration_view, container, false);
        main = (MainActivity) requireActivity();
        this.switchVibration = v.findViewById(R.id.vibration);

        vibrator = (Vibrator) main.getSystemService(Context.VIBRATOR_SERVICE);

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
        //switchVibration.setOnTouchListener(this);

        return v;
    }

    /*@Override
    public boolean onTouch(View v, MotionEvent event) {
        final long[] pattern = {2000, 1000};
        if (switchVibration.isChecked()){
            vibrator.vibrate(pattern, 0);
        }
        else {
            vibrator.cancel();
        }
        return false;
    }*/
}
