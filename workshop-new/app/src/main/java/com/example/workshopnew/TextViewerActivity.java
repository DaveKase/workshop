package com.example.workshopnew;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TextViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_viewer);
    }

    public void viewText(View v) {
        EditText edit = findViewById(R.id.viewerEditText);
        String insertedText = edit.getText().toString();

        TextView invisibleView = findViewById(R.id.viewerInvisibleText);
        invisibleView.setText(insertedText);

        if(invisibleView.getVisibility() == View.GONE || invisibleView.getVisibility() == View.INVISIBLE) {
            invisibleView.setVisibility(View.VISIBLE);
        }

        edit.setText("");
    }
}
