package com.example.taavi.workshop_short;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        try {
            Button clickedButton = (Button) v;
            String btnClr = "blue";
            String btnClrStr = clickedButton.getText().toString();

            Sender sender = new Sender();
            sender.execute(btnClr);
            String clickCount = sender.get();
<<<<<<< HEAD
            Toast.makeText(this, btnClrStr + clickCount + ". nupule vajutus", Toast.LENGTH_SHORT).show();
=======
            Toast.makeText(this, btnClrStr + " " + clickCount + ". nupule vajutus", Toast.LENGTH_SHORT).show();
>>>>>>> parent of 942d319... Changed short Android project to fit time
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
