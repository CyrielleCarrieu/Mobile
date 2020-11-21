package com.example.mobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Toast;


import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Photo extends Fragment {

    private View v;
    private Button buttonImage;
    private VideoView videoView;
    private ImageView imageView;
    private static final int REQUEST_ID_IMAGE_CAPTURE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.photo_view, container, false);

        this.buttonImage = (Button) v.findViewById(R.id.button_image);
        this.videoView = (VideoView) v.findViewById(R.id.videoView);
        this.imageView = (ImageView) v.findViewById(R.id.imageView);

        this.buttonImage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        return v;
    }

    private void captureImage() {
        TextView textView = v.findViewById(R.id.textViewError);
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            this.startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
            textView.setText("");
        } catch (Exception e) {
            textView.setText("Action échouée! \n Veuillez autoriser l'accès à l'appareil photo!");
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                this.imageView.setImageBitmap(bp);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Action annulée", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Action échouée", Toast.LENGTH_LONG).show();
            }
        }
    }
}