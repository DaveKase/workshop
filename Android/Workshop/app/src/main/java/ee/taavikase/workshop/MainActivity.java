package ee.taavikase.workshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
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

    private String getButtonColor(Button b) {
        String btnClr = "";

        switch (b.getId()) {
            case BLUE_BUTTON:
                btnClr = COL_BLUE;
                break;
            case GREEN_BUTTON:
                btnClr = COL_GREEN;
                break;
            case RED_BUTTON:
                btnClr = COL_RED;
                break;
            case YELLOW_BUTTON:
                btnClr = COL_YELLOW;
                break;
        }

        return btnClr;
    }

    public void onClick(View v) {
        try {
            Button clickedButton = (Button) v;
            String btnClr = getButtonColor(clickedButton);
            String btnClrStr = clickedButton.getText().toString();

            Sender sender = new Sender();
            sender.execute(btnClr);
            String clickCount = sender.get();

            Toast.makeText(this, btnClrStr + " " + clickCount +  ". nupule vajutus, ",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
