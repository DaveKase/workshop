package ee.taavikase.workshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String COL_BLUE = "blue";
    private static final String COL_GREEN = "green";
    private static final String COL_RED = "red";
    private static final String COL_YELLOW = "yellow";

    private static final int BLUE_BUTTON =  R.id.blue_btn;
    private static final int GREEN_BUTTON = R.id.green_btn;
    private static final int RED_BUTTON = R.id.red_btn;
    private static final int YELLOW_BUTTON = R.id.yellow_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String getButtons(Button b) {
        String buttonId = "";

        switch (b.getId()) {
            case BLUE_BUTTON:
                buttonId = COL_BLUE;
                break;
            case GREEN_BUTTON:
                buttonId = COL_GREEN;
                break;
            case RED_BUTTON:
                buttonId = COL_RED;
                break;
            case YELLOW_BUTTON:
                buttonId = COL_YELLOW;
                break;
        }

        return buttonId;
    }

    public void onClick(View v) {
        try {
            Log.e(TAG, "onClick");
            Button clickedButton = (Button) v;
            String buttonId = getButtons(clickedButton);
            String btnColor = clickedButton.getText().toString();

            Sender sender = new Sender();
            sender.execute(buttonId);
            String clickCount = sender.get();

            Toast.makeText(this, btnColor + " " + clickCount +  ". nupule vajutus, ",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "error in main: " + e.getMessage());
        }
    }
}
