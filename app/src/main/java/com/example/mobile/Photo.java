package com.example.mobile;

import android.content.ContentValues;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Photo extends Fragment implements SurfaceHolder.Callback, AdapterView.OnTouchListener {

    private View v;
    private static Camera camera = null;
    private SurfaceView surfaceCamera;
    private Boolean isPreview;
    private FileOutputStream stream;

    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 150;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.photo_view, container, false);
        v.setOnTouchListener(this);

        isPreview = false;

        // Nous récupérons notre surface pour le preview
        surfaceCamera = (SurfaceView) v.findViewById(R.id.surfaceViewCamera);

        // Méthode d'initialisation de la caméra
        InitializeCamera();

        surfaceCamera.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Nous prenons une photo
                if (camera != null) {
                    SavePicture();
                }

            }
        });

        return v;
    }

    public void InitializeCamera() {
        // Nous attachons nos retours du holder à notre activité
        surfaceCamera.getHolder().addCallback(this);
        // Nous spécifiions le type du holder en mode SURFACE_TYPE_PUSH_BUFFERS
        surfaceCamera.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void SavePicture() {
        try {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy-MM-dd-HH.mm.ss");
            String fileName = "photo_" + timeStampFormat.format(new Date())
                    + ".jpg";

            // Metadata pour la photo
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.DESCRIPTION, "Image prise par FormationCamera");
            values.put(MediaStore.Audio.Media.DATE_TAKEN, new Date().getTime());
            values.put(MediaStore.Audio.Media.MIME_TYPE, "image/jpeg");

            // Support de stockage
            Uri taken = getActivity().getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    values);

            // Ouverture du flux pour la sauvegarde
            stream = (FileOutputStream) getActivity().getContentResolver().openOutputStream(
                    taken);

            camera.takePicture(null, pictureCallback, pictureCallback);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    // Callback pour la prise de photo
    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            if (data != null) {
                // Enregistrement de votre image
                try {
                    if (stream != null) {
                        stream.write(data);
                        stream.flush();
                        stream.close();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                // Nous redémarrons la prévisualisation
                camera.startPreview();
            }
        }
    };




    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (camera == null)
            camera = Camera.open();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {


        // Si le mode preview est lancé alors nous le stoppons
        if (isPreview) {
            camera.stopPreview();
        }
        // Nous récupérons les paramètres de la caméra
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();

        // You need to choose the most appropriate previewSize for your app
        Camera.Size previewSize = previewSizes.get(0);
        parameters.setPreviewSize(previewSize.width, previewSize.height);


        // Nous appliquons nos nouveaux paramètres
        camera.setParameters(parameters);

        try {
            // Nous attachons notre prévisualisation de la caméra au holder de la
            // surface
            camera.setPreviewDisplay(surfaceCamera.getHolder());
        } catch (IOException e) {
        }

        // Nous lançons la preview
        camera.startPreview();

        isPreview = true;

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
            isPreview = false;
            camera.release();
        }
    }

    // Retour sur l'application
    @Override
    public void onResume() {
        super.onResume();
        camera = Camera.open();
    }

    // Mise en pause de l'application
    @Override
    public void onPause() {
        super.onPause();

        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println("Coucou je suis onTouch");
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
                        Vibration vibration = new Vibration();
                        ft.replace(R.id.container, vibration, "vibration");
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                }
                break;
        }
        return false;
    }
}
