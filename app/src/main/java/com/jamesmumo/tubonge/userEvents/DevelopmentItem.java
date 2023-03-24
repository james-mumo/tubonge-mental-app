package com.jamesmumo.tubonge.userEvents;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jamesmumo.tubonge.R;

public class DevelopmentItem extends AppCompatActivity {
    TextView pagetitle, point1, point2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development_item);
        pagetitle = findViewById(R.id.pagetitle);
        point1 = findViewById(R.id.point1);
        point2 = findViewById(R.id.point2);

        String pageTitle = getIntent().getStringExtra("title");
        String pointOne = getIntent().getStringExtra("point1");
        String pointTwo = getIntent().getStringExtra("point2");

        pagetitle.setText(pageTitle);
        point1.setText(pointOne);
        point2.setText(pointTwo);
    }
}