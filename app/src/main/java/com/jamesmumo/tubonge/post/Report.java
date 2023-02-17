package com.jamesmumo.tubonge.post;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.jamesmumo.tubonge.MainActivity;
import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.SharedPref;
import com.jamesmumo.tubonge.authEmail.SignUp;
import com.jamesmumo.tubonge.welcome.GetTimeAgo;
import com.jamesmumo.tubonge.welcome.IntroActivity;
import com.tapadoo.alerter.Alerter;


public class Report extends AppCompatActivity {

    SharedPref sharedPref;
    String AuthorName, postText, postTimeAgoText;
    TextView postAuthorName, postTimeAgo, reportedPostText;
    ProgressBar progressBar2;
    TextInputEditText reportedPostComplaint;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        progressBar2 = findViewById(R.id.progressBar2);
        reportedPostComplaint = findViewById(R.id.reportedPostComplaint);

        postAuthorName = findViewById(R.id.postAuthorName);
        postTimeAgo = findViewById(R.id.postTimeAgo);
        reportedPostText = findViewById(R.id.reportedPostText);

        Intent intent = getIntent();
        AuthorName = intent.getStringExtra("postAuthorName");
        postTimeAgoText = intent.getStringExtra("pTime");
        postText = intent.getStringExtra("pText");

        postAuthorName.setText(AuthorName);
        reportedPostText.setText(postText);

        GetTimeAgo getTimeAgo = new GetTimeAgo();
        long lastTime = Long.parseLong(postTimeAgoText);
        String lastSeenTime = GetTimeAgo.getTimeAgo(lastTime);
        postTimeAgo.setText(lastSeenTime);

    }

    public void reportSubmit(View view) {
        String reportedPostComplaintText = reportedPostComplaint.getText().toString().trim();
        if (!(TextUtils.isEmpty(reportedPostComplaintText))) {
            Alerter.create(Report.this).setTitle("Success").setIcon(R.drawable.ic_baseline_adjust_24).setBackgroundColorRes(R.color.colorAccent).setDuration(10000).setTitleTypeface(Typeface.createFromAsset(getAssets(), "bold.ttf")).setTextTypeface(Typeface.createFromAsset(getAssets(), "med.ttf")).enableSwipeToDismiss().setText("Report Submitted.").show();
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }, 2000);
        } else {
            Alerter.create(Report.this).setTitle("Error").setIcon(R.drawable.ic_error).setBackgroundColorRes(R.color.colorAccent).setDuration(10000).setTitleTypeface(Typeface.createFromAsset(getAssets(), "bold.ttf")).setTextTypeface(Typeface.createFromAsset(getAssets(), "med.ttf")).enableSwipeToDismiss().setText("Enter complaint information.").show();
        }
    }
}
