package com.example.fetchandroidengineerjules;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView linkTextView = findViewById(R.id.about_link);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}