package com.jamesmumo.tubonge.userEvents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.SharedPref;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MoodControl extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_control);

        // Find the views and set the click listeners
        findViewById(R.id.sad).setOnClickListener(this);
        findViewById(R.id.happy).setOnClickListener(this);
        findViewById(R.id.frustrated).setOnClickListener(this);
        findViewById(R.id.angry).setOnClickListener(this);
        findViewById(R.id.confused).setOnClickListener(this);
        findViewById(R.id.insecure).setOnClickListener(this);
        findViewById(R.id.depressed).setOnClickListener(this);
        findViewById(R.id.lonely).setOnClickListener(this);
        findViewById(R.id.overwhelmed).setOnClickListener(this);


        TextView timeTextView = findViewById(R.id.current_time_textview);
        TextView dateTextView = findViewById(R.id.text_date);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");

        String currentTime = timeFormat.format(calendar.getTime());
        timeTextView.setText(currentTime.toUpperCase());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");


        String currentDate = dateFormat.format(calendar.getTime());
        dateTextView.setText(currentDate);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sad:
                startActivity(new Intent(this, WhatMakingYouFeel.class).putExtra("myMood", "Sad"));
                break;
            case R.id.happy:
                startActivity(new Intent(this, WhatMakingYouFeel.class).putExtra("myMood", "Happy"));
                break;
            case R.id.frustrated:
                startActivity(new Intent(this, WhatMakingYouFeel.class).putExtra("myMood", "Frustrated"));
                break;
            case R.id.angry:
                startActivity(new Intent(this, WhatMakingYouFeel.class).putExtra("myMood", "Angry"));
                break;
            case R.id.confused:
                startActivity(new Intent(this, WhatMakingYouFeel.class).putExtra("myMood", "Confused"));
                break;
            case R.id.insecure:
                startActivity(new Intent(this, WhatMakingYouFeel.class).putExtra("myMood", "Insecure"));
                break;
            case R.id.depressed:
                startActivity(new Intent(this, WhatMakingYouFeel.class).putExtra("myMood", "Depressed"));
                break;
            case R.id.lonely:
                startActivity(new Intent(this, WhatMakingYouFeel.class).putExtra("myMood", "Lonely"));
                break;
            case R.id.overwhelmed:
                startActivity(new Intent(this, WhatMakingYouFeel.class).putExtra("myMood", "Overwhelmed"));
                break;
            default:
                Toast.makeText(this, "Unknown view clicked: " + v.getId(), Toast.LENGTH_SHORT).show();
        }
    }

}