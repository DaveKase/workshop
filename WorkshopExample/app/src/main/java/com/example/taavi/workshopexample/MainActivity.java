package com.example.taavi.workshopexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log("testing logging");
    }

    private void log(String text) {
        Log.e("Main", text);
    }
}
