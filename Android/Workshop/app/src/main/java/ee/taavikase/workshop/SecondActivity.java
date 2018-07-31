package ee.taavikase.workshop;

import android.app.Activity;
import android.os.Bundle;

import java.util.Objects;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Objects.requireNonNull(getActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
