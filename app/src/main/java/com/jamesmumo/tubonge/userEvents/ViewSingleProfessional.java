package com.jamesmumo.tubonge.userEvents;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.adapter.AdapterPost;
import com.jamesmumo.tubonge.model.ModelPost;
import com.jamesmumo.tubonge.model.ModelUser;
import com.jamesmumo.tubonge.notifications.Data;
import com.jamesmumo.tubonge.notifications.Sender;
import com.jamesmumo.tubonge.notifications.Token;
import com.jamesmumo.tubonge.shareChat.Chat;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewSingleProfessional extends AppCompatActivity {
    ImageView imageView;
    TextView followLabel, profNumberOfClients, msgLabel, profComms;
    String professionalId;
    RecyclerView recyclerView;

    private static final int TOTAL_ITEMS_TO_LOAD = 7;
    private int mCurrenPage = 1;


    List<ModelPost> postList;
    AdapterPost adapterPost;

    private RequestQueue requestQueue;
    private boolean notify = false;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    final String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_professional);

        postList = new ArrayList<>();

        imageView = (ImageView) findViewById(R.id.docImage);
        followLabel = findViewById(R.id.followLabel);
        msgLabel = findViewById(R.id.msgLabel);
        profNumberOfClients = findViewById(R.id.profNumberOfClients);
        recyclerView = findViewById(R.id.postView);

        TextView profName = findViewById(R.id.profName);
        TextView profSpeciality = findViewById(R.id.profSpeciality);
        TextView profSchoolName = findViewById(R.id.profSchoolName);
        TextView profTherapyHours = findViewById(R.id.profTherapyHours);
        TextView profStars = findViewById(R.id.profStars);
        profComms = findViewById(R.id.profComms);

        String professionalName = getIntent().getStringExtra("profName");
        String professionalSpeciality = getIntent().getStringExtra("profSpeciality");
        String professionalSchool = getIntent().getStringExtra("profSchool");
        String professionalExperience = getIntent().getStringExtra("profExp");
        String professionalStars = getIntent().getStringExtra("profStars");
        professionalId = getIntent().getStringExtra("profID");

        profName.setText(professionalName);
        profSpeciality.setText(professionalSpeciality);
        profSchoolName.setText(professionalSchool);
        profTherapyHours.setText(professionalExperience + " Hrs Engaged");
        profStars.setText(professionalStars);

        loadPost();
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    mCurrenPage++;
//                    loadPost();
//                }
//            }
//        });


        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String profImage = getIntent().getStringExtra("profImage");

        try {
            Picasso.get().load(profImage).placeholder(R.drawable.abs111).into(imageView);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.abs111).into(imageView);
        }


        getFollowers();

        msgLabel.setOnClickListener(v -> {
            Intent intent = new Intent(ViewSingleProfessional.this, Chat.class);
            intent.putExtra("hisUid", professionalId);
            startActivity(intent);

        });

        followLabel.setOnClickListener(v -> {

            FirebaseDatabase.getInstance().getReference().child("Follow")
                    .child(firebaseUser.getUid())
                    .child("Following").child(professionalId).setValue(true);
            FirebaseDatabase.getInstance().getReference().child("Follow").child(professionalId)
                    .child("Followers").child(firebaseUser.getUid()).setValue(true);
            followLabel.setText("Following");
//            following.setVisibility(View.VISIBLE);
            addToHisNotification("" + professionalId);
            notify = true;

            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Users").child(professionalId);
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ModelUser user = snapshot.getValue(ModelUser.class);
                    if (notify) {
                        sendNotification(professionalId, Objects.requireNonNull(user).getName(), "Started following you");

                    }
                    notify = false;

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    StyleableToast st = new StyleableToast(Objects.requireNonNull(Objects.requireNonNull(getApplicationContext())), error.getMessage(), Toast.LENGTH_LONG);
                    st.setBackgroundColor(Color.parseColor("#001E55"));
                    st.setTextColor(Color.WHITE);
                    st.setIcon(R.drawable.ic_error);
                    st.setMaxAlpha();
                    st.show();
                }
            });
        });
    }

    private void addToHisNotification(String hisUid) {
        String timestamp = "" + System.currentTimeMillis();
        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("pId", "");
        hashMap.put("timestamp", timestamp);
        hashMap.put("pUid", hisUid);
        hashMap.put("notification", "Started following you");
        hashMap.put("sUid", userId);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(hisUid).child("Notifications").child(timestamp).setValue(hashMap)
                .addOnSuccessListener(aVoid -> {

                }).addOnFailureListener(e -> {

                });

    }


    private void sendNotification(final String hisId, final String name, final String message) {
        DatabaseReference allToken = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = allToken.orderByKey().equalTo(hisId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Token token = ds.getValue(Token.class);
                    Data data = new Data(userId, name + " : " + message, "New Message", hisId, R.drawable.logo);
                    Sender sender = new Sender(data, token.getToken());
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(sender));
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("JSON_RESPONSE", "onResponse" + response.toString());

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("JSON_RESPONSE", "onResponse" + error.toString());
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();
                                headers.put("Content-Type", "application/json");
                                headers.put("Authorization", "key=AAAADu5rTxA:APA91bEvZnB9PdnPsCSZGXOakuCmEoyrhraMXdOTrXbxsolCRdVwRqe_XLf8cFZnngoEtn0xDWqbVs1gv2KUFtJ02VBwatkKSpLY1cev-uj_jEWJcydOrIvYi-Ph4NBot_FG4fNt5G8f");
                                return headers;
                            }
                        };
                        requestQueue.add(jsonObjectRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getFollowers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(professionalId).child("Followers");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profNumberOfClients.setText("" + dataSnapshot.getChildrenCount() + " Contacts");
                if (dataSnapshot.getChildrenCount() > 0) {
                    followLabel.setText("Following");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadPost() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewSingleProfessional.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = ref.orderByChild("id").equalTo(professionalId).limitToLast(mCurrenPage * TOTAL_ITEMS_TO_LOAD);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelPost modelPost = ds.getValue(ModelPost.class);
                    postList.add(modelPost);
                    //check if the items fetched is more than 0
                    adapterPost = new AdapterPost(ViewSingleProfessional.this, postList);
                    recyclerView.setAdapter(adapterPost);
                }
                if(postList.size()>0)
                {
                    profComms.setText(postList.size()+" Communities");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}