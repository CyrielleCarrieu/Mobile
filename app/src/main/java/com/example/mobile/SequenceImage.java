package com.example.mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SequenceImage extends Fragment implements AdapterView.OnTouchListener{

    View v;
    int pos = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.sequence_image_view, container, false);

        v.setOnTouchListener(this);

        return v;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int[] images = {R.drawable.chaton, R.drawable.chiot, R.drawable.alpaga, R.drawable.lapin, R.drawable.loutre};
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            ImageView myImageView = (ImageView)v.findViewById(R.id.sequenceimage);
            if( event.getX() < v.getWidth() / 2 ) {
                if( pos == 0) {
                    pos = 4;
                }
                else {
                    pos = (pos - 1)%5;
                }
            }
            else {
                pos = (pos + 1)%5;
            }
            myImageView.setImageResource(images[pos]);
        }
        return true;
    }
}
