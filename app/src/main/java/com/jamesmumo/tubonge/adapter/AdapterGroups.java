package com.jamesmumo.tubonge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.groups.GroupProfile;
import com.jamesmumo.tubonge.model.ModelGroups;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterGroups extends RecyclerView.Adapter<AdapterGroups.MyHolder> {

    final Context context;
    final List<ModelGroups> modelGroups;

    public AdapterGroups(Context context, List<ModelGroups> modelGroups) {
        this.context = context;
        this.modelGroups = modelGroups;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_display, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String GroupId = modelGroups.get(position).getGroupId();
        String GroupName = modelGroups.get(position).getgName();
        String GroupUsername = modelGroups.get(position).getgUsername();
        String GroupIcon = modelGroups.get(position).getgIcon();
        String GroupBio = modelGroups.get(position).getgBio();

        holder.mName.setText(GroupName);
        holder.mUsername.setText(GroupUsername);
        holder.gBio.setText(GroupBio);
        try {
            Picasso.get().load(GroupIcon).placeholder(R.drawable.abs111).into(holder.avatar);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.abs111).into(holder.avatar);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GroupProfile.class);
            intent.putExtra("groupId", GroupId);
            context.startActivity(intent);
        });


        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference()
                .child("Groups").child(GroupId).child("Participants");
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.noMembers.setText(dataSnapshot.getChildrenCount()+ " Peers");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return modelGroups.size();
    }


    static class MyHolder extends RecyclerView.ViewHolder {

        final ImageView avatar;
        final TextView mName;
        final TextView gBio;
        final TextView mUsername;
        final TextView noMembers;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.circleImageView);
            mName = itemView.findViewById(R.id.name);
            mUsername = itemView.findViewById(R.id.username);
            gBio = itemView.findViewById(R.id.groupBio);
            noMembers = itemView.findViewById(R.id.noMembers);
        }
    }
}

