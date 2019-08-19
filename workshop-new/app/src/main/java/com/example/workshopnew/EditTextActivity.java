package com.example.workshopnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
    }

    public void onEditClick(View v) {
        EditText field = findViewById(R.id.editText);
        String text = field.getText().toString();

        TextView invisible = findViewById(R.id.invisibleText);
        invisible.setText(text);

        if(invisible.getVisibility() == View.GONE || invisible.getVisibility() == View.INVISIBLE) {
            invisible.setVisibility(View.VISIBLE);
        }

        field.setText("");
    }
}
