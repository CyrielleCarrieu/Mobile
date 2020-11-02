package com.example.mobile;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TextInput extends Fragment implements AdapterView.OnTouchListener {

    private Button buttonArial;
    private Button buttonCalibri;
    private Button buttonConsolas;
    private Button buttonNoir;
    private Button buttonRouge;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.text_input_view, container, false);

        this.buttonArial = (Button) v.findViewById(R.id.button1);
        this.buttonCalibri = (Button) v.findViewById(R.id.button2);
        this.buttonConsolas = (Button) v.findViewById(R.id.button3);
        this.buttonNoir = (Button) v.findViewById(R.id.button4);
        this.buttonRouge = (Button) v.findViewById(R.id.button5);

        this.buttonRouge.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                AutoCompleteTextView myTextField = (AutoCompleteTextView)v.findViewById(R.id.autoCompleteTextView);
                myTextField.setTextColor(Color.RED);
            }
        });;

        return v;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
