package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private HomeListFragment homeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeListFragment = new HomeListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeListFragment).commit();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }


}
