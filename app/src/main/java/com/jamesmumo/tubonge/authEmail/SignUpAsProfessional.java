package com.jamesmumo.tubonge.authEmail;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jamesmumo.tubonge.R;
import com.tapadoo.alerter.Alerter;

import java.util.HashMap;
import java.util.Objects;

public class SignUpAsProfessional extends AppCompatActivity {
    TextView name, passwordEdt, speciality, school, email;
    Button submitBtn;

    ProgressBar progressBar3;
    private FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_professional);
        name = findViewById(R.id.name);
        passwordEdt = findViewById(R.id.passwordEdt);
        progressBar3 = findViewById(R.id.progressBarf);
        submitBtn = findViewById(R.id.submitBtn);
        school = findViewById(R.id.school);
        email = findViewById(R.id.email);
        speciality = findViewById(R.id.speciality);

        mAuth = FirebaseAuth.getInstance();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar3.setVisibility(View.VISIBLE);
                final String pName = name.getText().toString().trim();
                final String pPassword = passwordEdt.getText().toString().trim();
                final String pEmail = email.getText().toString().trim();
                final String pSchool = school.getText().toString().trim();
                final String pSpeciality = speciality.getText().toString().trim();

                Query emailQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("email").equalTo(pEmail);
                emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0) {
                            Alerter.create(SignUpAsProfessional.this)
                                    .setTitle("Error")
                                    .setIcon(R.drawable.ic_error)
                                    .setBackgroundColorRes(R.color.colorAccent)
                                    .setDuration(10000)
                                    .setTitleTypeface(Typeface.createFromAsset(getAssets(), "bold.ttf"))
                                    .setTextTypeface(Typeface.createFromAsset(getAssets(), "med.ttf"))
                                    .enableSwipeToDismiss()
                                    .setText("Email already exist")
                                    .show();
                            progressBar3.setVisibility(View.INVISIBLE);

                        } else {
                            if (TextUtils.isEmpty(pName) || TextUtils.isEmpty(pEmail) || TextUtils.isEmpty(pPassword)) {
                                Alerter.create(SignUpAsProfessional.this)
                                        .setTitle("Error")
                                        .setIcon(R.drawable.ic_error)
                                        .setBackgroundColorRes(R.color.colorAccent)
                                        .setDuration(10000)
                                        .setTitleTypeface(Typeface.createFromAsset(getAssets(), "bold.ttf"))
                                        .setTextTypeface(Typeface.createFromAsset(getAssets(), "med.ttf"))
                                        .enableSwipeToDismiss()
                                        .setText("Enter email & name & password")
                                        .show();
                                progressBar3.setVisibility(View.INVISIBLE);

                            } else if (pPassword.length() < 6) {
                                Alerter.create(SignUpAsProfessional.this)
                                        .setTitle("Error")
                                        .setIcon(R.drawable.ic_error)
                                        .setBackgroundColorRes(R.color.colorPrimary)
                                        .setDuration(10000)
                                        .setTitleTypeface(Typeface.createFromAsset(getAssets(), "bold.ttf"))
                                        .setTextTypeface(Typeface.createFromAsset(getAssets(), "med.ttf"))
                                        .enableSwipeToDismiss()
                                        .setText("Password should have minimum 6 characters")
                                        .show();
                                progressBar3.setVisibility(View.INVISIBLE);
                            } else {
                                add_professional(pName, pEmail, pPassword, pSpeciality, pSchool);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Alerter.create(SignUpAsProfessional.this)
                                .setTitle("Error")
                                .setIcon(R.drawable.ic_error)
                                .setBackgroundColorRes(R.color.colorPrimary)
                                .setDuration(10000)
                                .setTitleTypeface(Typeface.createFromAsset(getAssets(), "bold.ttf"))
                                .setTextTypeface(Typeface.createFromAsset(getAssets(), "med.ttf"))
                                .enableSwipeToDismiss()
                                .setText(error.getMessage())
                                .show();
                        progressBar3.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    private void add_professional(String pName, String pEmail, String pPassword, String pSpeciality, String pSchool) {
        mAuth.createUserWithEmailAndPassword(pEmail, pPassword).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {

                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                String userid = Objects.requireNonNull(firebaseUser).getUid();

                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", userid);
                hashMap.put("name", pName);
                hashMap.put("email", pEmail);
                hashMap.put("school", pSchool);
                hashMap.put("speciality", pSpeciality);
                hashMap.put("experience", "");
                hashMap.put("isProfessional", 1);
                hashMap.put("username", "");
                hashMap.put("stars", 0);
                hashMap.put("bio", "");
                hashMap.put("verified", "");
                hashMap.put("location", "");
                hashMap.put("status", "online");
                hashMap.put("typingTo", "noOne");
                hashMap.put("link", "");
                hashMap.put("photo", "https://firebasestorage.googleapis.com/v0/b/memespace-34a96.appspot.com/o/avatar.jpg?alt=media&token=8b875027-3fa4-4da4-a4d5-8b661d999472");
                reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Intent intent = new Intent(SignUpAsProfessional.this, FinishAsProfessional.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                String msg = Objects.requireNonNull(task.getException()).getMessage();
                Alerter.create(SignUpAsProfessional.this)
                        .setTitle("Error")
                        .setIcon(R.drawable.ic_error)
                        .setBackgroundColorRes(R.color.colorPrimary)
                        .setDuration(10000)
                        .setTitleTypeface(Typeface.createFromAsset(getAssets(), "bold.ttf"))
                        .setTextTypeface(Typeface.createFromAsset(getAssets(), "med.ttf"))
                        .enableSwipeToDismiss()
                        .setText(Objects.requireNonNull(msg))
                        .show();
                progressBar3.setVisibility(View.INVISIBLE);

            }
        });

    }
}