package com.example.mobile;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class TextInput extends Fragment {

    private Button buttonRoboto;
    private Button buttonSCP;
    private Button buttonUbuntu;
    private Button buttonNoir;
    private Button buttonRouge;
    private AutoCompleteTextView myTextField;
    private View v;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.text_input_view, container, false);

        this.myTextField = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView);
        this.buttonRoboto = (Button) v.findViewById(R.id.button1);
        this.buttonSCP = (Button) v.findViewById(R.id.button2);
        this.buttonUbuntu = (Button) v.findViewById(R.id.button3);
        this.buttonNoir = (Button) v.findViewById(R.id.button4);
        this.buttonRouge = (Button) v.findViewById(R.id.button5);


        this.buttonRoboto.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Typeface typeface = getResources().getFont(R.font.roboto);
                myTextField.setTypeface(typeface);
            }
        });
        this.buttonSCP.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Typeface typeface = getResources().getFont(R.font.sourcecodepro);
                myTextField.setTypeface(typeface);
            }
        });
        this.buttonUbuntu.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Typeface typeface = getResources().getFont(R.font.ubuntu);
                myTextField.setTypeface(typeface);
            }
        });
        this.buttonNoir.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                myTextField.setTextColor(Color.BLACK);
            }
        });

        this.buttonRouge.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                myTextField.setTextColor(Color.RED);
            }
        });

        return v;

    }
}
