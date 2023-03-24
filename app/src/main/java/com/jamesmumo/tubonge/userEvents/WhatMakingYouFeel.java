package com.jamesmumo.tubonge.userEvents;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.SharedPref;
import com.tapadoo.alerter.Alerter;

public class WhatMakingYouFeel extends AppCompatActivity {
    TextInputEditText suggestionBoxEd;
    CardView mainCardBox;
    String myMood;
    TextView myFeeling, itsOkayTextView, whatMakingYou;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_making_you_feel);


        suggestionBoxEd = findViewById(R.id.suggestionBox);
        mainCardBox = findViewById(R.id.enterReasonBox);

        myFeeling = findViewById(R.id.myFeeling);
        whatMakingYou = findViewById(R.id.textView20);
        itsOkayTextView = findViewById(R.id.itsOkayFeelingThat);

        whatMakingYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainCardBox.setVisibility(View.VISIBLE);
            }
        });

        myMood = getIntent().getStringExtra("myMood");
        myFeeling.setText("Hey, feeling " + myMood + " is okay.");

        setTheOkayMessage();

//"


    }
//    private void setTheMessage() {
//        switch (myMood) {
//            case "Excited":
//                itsOkayTextView.setText("It's great to feel excited! Use this energy to pursue your goals and dreams.");
//                break;
//            case "Anxious":
//                itsOkayTextView.setText("Feeling anxious is tough, but it's important to remember that it's a temporary state. Take deep breaths, practice self-care, and talk to someone if you need support.");
//                break;
//            case "Grateful":
//                itsOkayTextView.setText("Gratitude is a powerful emotion that can improve your overall well-being. Take a moment to appreciate the people and things in your life that you're thankful for.");
//                break;
//            case "Nostalgic":
//                itsOkayTextView.setText("Nostalgia can bring up a mix of emotions, both happy and sad. It's okay to reminisce about the past, but try to focus on the present moment and create new memories.");
//                break;
//            case "Stressed":
//                itsOkayTextView.setText("Stress is a common feeling, but it can be managed with healthy coping strategies. Try exercise, mindfulness, or spending time in nature to reduce stress.");
//                break;
//            case "Hopeful":
//                itsOkayTextView.setText("Feeling hopeful can give you the strength to face challenges and overcome obstacles. Hold on to this feeling and trust in your ability to create a better future.");
//                break;
//            case "Envious":
//                itsOkayTextView.setText("Jealousy can be a difficult emotion to manage, but it's important to remember that everyone has their own unique journey. Focus on your own goals and accomplishments, and be happy for the successes of others.");
//                break;
//            case "Bored":
//                itsOkayTextView.setText("Feeling bored is natural, but it's also an opportunity to explore new hobbies and activities. Challenge yourself to try something new and step out of your comfort zone.");
//                break;
//            default:
//                itsOkayTextView.setText("Remember, whatever you're feeling is valid and okay. Take care of yourself and seek support if needed.");
//                break;
//        }
//    }

    private void setTheOkayMessage() {
        switch (myMood) {
            case "Sad":
                itsOkayTextView.setText("Hey there, " + myMood + ". Just wanted to remind you that it's perfectly okay to feel sad sometimes. Everyone goes through tough times, and it's important to allow yourself to feel your emotions and process them in your own time. Remember to be kind to yourself and reach out for support if you need it");
                break;
            case "Happy":
                itsOkayTextView.setText("Great to hear that you're feeling happy! Enjoy the moment.");
                break;
            case "Frustrated":
                itsOkayTextView.setText("It's alright to feel frustrated sometimes. Take a deep breath and try to find a solution.");
                break;
            case "Angry":
                itsOkayTextView.setText("It's okay to be angry, but make sure you express it in a healthy way. Talk to someone or find an activity to channel your energy.");
                break;
            case "Confused":
                itsOkayTextView.setText("Feeling confused is a natural part of learning and growing. Take it as an opportunity to explore new perspectives and ideas.");
                break;
            case "Insecure":
                itsOkayTextView.setText("It's normal to have insecurities, but don't let them hold you back. Remember your strengths and focus on them.");
                break;
            case "Depressed":
                itsOkayTextView.setText("Depression is a serious illness, but it's treatable. Reach out to a mental health professional or someone you trust for help.");
                break;
            case "Lonely":
                itsOkayTextView.setText("Feeling lonely is common, but it doesn't mean you're alone. Connect with loved ones or join a community that shares your interests.");
                break;
            case "Overwhelmed":
                itsOkayTextView.setText("Feeling overwhelmed is understandable, but take it one step at a time. Prioritize tasks and ask for help if needed.");
                break;
            default:
                itsOkayTextView.setText("It's okay to feel whatever you're feeling. Remember to take care of yourself and reach out for support if needed.");
                break;
        }
    }

    public void submitReason(View view) {

        String reportedPostComplaintText = suggestionBoxEd.getText().toString().trim();
        if (!(TextUtils.isEmpty(reportedPostComplaintText))) {
            Alerter.create(WhatMakingYouFeel.this).setTitle("Success").setIcon(R.drawable.ic_baseline_adjust_24).setBackgroundColorRes(R.color.colorAccent).setDuration(10000).setTitleTypeface(Typeface.createFromAsset(getAssets(), "bold.ttf")).setTextTypeface(Typeface.createFromAsset(getAssets(), "med.ttf")).enableSwipeToDismiss().setText("Successfully Submitted.").show();
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(), ViewProfessional.class);
                intent.putExtra("msg", reportedPostComplaintText);
                startActivity(intent);
                finish();
            }, 1000);
        } else {
            Alerter.create(WhatMakingYouFeel.this).setTitle("Error").setIcon(R.drawable.ic_error).setBackgroundColorRes(R.color.colorAccent).setDuration(10000).setTitleTypeface(Typeface.createFromAsset(getAssets(), "bold.ttf")).setTextTypeface(Typeface.createFromAsset(getAssets(), "med.ttf")).enableSwipeToDismiss().setText("Enter Reason.").show();
        }

    }

}