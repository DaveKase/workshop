package ee.taavikase.workshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int BLUE_BUTTON =  R.id.blue_btn;
    private static final int GREEN_BUTTON = R.id.green_btn;
    private static final int RED_BUTTON = R.id.red_btn;
    private static final int YELLOW_BUTTON = R.id.yellow_btn;

    private static final String COL_BLUE = "blue";
    private static final String COL_GREEN = "green";
    private static final String COL_RED = "red";
    private static final String COL_YELLOW = "yellow";
    private static final String TAG = "MainActivity";
    private String mButtonId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        String btn_color = "";
        Log.e(TAG, "onClick");

        switch (v.getId()) {
            case BLUE_BUTTON:
                mButtonId = COL_BLUE;
                btn_color = "Sinine";
                break;
            case GREEN_BUTTON:
                mButtonId = COL_GREEN;
                btn_color = "Roheline";
                break;
            case RED_BUTTON:
                mButtonId = COL_RED;
                btn_color = "Punane";
                break;
            case YELLOW_BUTTON:
                mButtonId = COL_YELLOW;
                btn_color = "Kollane";
                break;
        }

        new Sender().execute(mButtonId);
        Toast.makeText(this, btn_color + " nupp sündmus", Toast.LENGTH_LONG).show();
    }
}
