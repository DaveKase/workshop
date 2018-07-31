package ee.taavikase.workshop;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

/*
* To be implemented only if we have enough time at the end of the workshop
* If not then nothing will be implemented here
* */
@SuppressWarnings("ALL")
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Log.e("SecondActivity", "No actionbar");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
