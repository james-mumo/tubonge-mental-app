package com.jamesmumo.tubonge.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.authEmail.SignIn;
import com.jamesmumo.tubonge.authEmail.SignUpAsProfessional;

public class IntroLast extends AppCompatActivity {

    Button button5, button4, button3;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_last);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
//        button5 = findViewById(R.id.button55);
        button3.setOnClickListener(v -> {
//            Intent intent = new Intent(IntroLast.this, GenerateOTP.class);
            startActivity(new Intent(IntroLast.this, SignUpAsProfessional.class));
        });
        button4.setOnClickListener(v -> {
            Intent intent = new Intent(IntroLast.this, SignIn.class);
            startActivity(intent);
        });

//        signUp =(TextView) findViewById(R.id.profsignuptext);
//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(IntroLast.this, SignUpAsProfessional.class));
//            }
//        });
    }
}
