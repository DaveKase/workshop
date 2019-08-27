package com.example.workshopnew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMainButtonClick(View v) {
        Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TextViewerActivity.class);
        startActivity(intent);
    }
}
