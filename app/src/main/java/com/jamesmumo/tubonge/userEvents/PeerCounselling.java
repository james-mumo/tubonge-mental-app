package com.jamesmumo.tubonge.userEvents;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.SharedPref;
import com.jamesmumo.tubonge.adapter.AdapterGroups;
import com.jamesmumo.tubonge.model.ModelGroups;

import java.util.ArrayList;
import java.util.List;

public class PeerCounselling extends AppCompatActivity {

    RecyclerView recyclerView;
    private int mCurrenPage = 1;
    private static final int TOTAL_ITEMS_TO_LOAD = 7;

    //Groups
    AdapterGroups adapterGroups;
    List<ModelGroups> modelGroupsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peer_counselling_page);

        recyclerView = findViewById(R.id.allGroupsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelGroupsList = new ArrayList<>();
        getAllGroups();

    }

    private void getAllGroups() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelGroupsList.clear(); // clear the list to avoid duplicates
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelGroups modelGroups = ds.getValue(ModelGroups.class);
                    modelGroupsList.add(modelGroups);
                }
//                adapterGroups.notifyDataSetChanged(); // notify the adapter of data changes
                // initialize the adapter outside of the ValueEventListener
                adapterGroups = new AdapterGroups(PeerCounselling.this, modelGroupsList);
                recyclerView.setAdapter(adapterGroups);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // handle error
            }
        });

    }

}