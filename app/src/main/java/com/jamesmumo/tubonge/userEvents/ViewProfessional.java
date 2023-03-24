package com.jamesmumo.tubonge.userEvents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.adapter.AdapterProfessionals;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewProfessional extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ModelProfessional> professionalsList;
    AdapterProfessionals adapterProfessionals;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_professional);
        recyclerView = findViewById(R.id.professionals_recycler_view);
        recyclerView.setHasFixedSize(true);

        professionalsList = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        getAllProfessionals();

    }

    private void getAllProfessionals() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                professionalsList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.hasChild("isProfessional") && ds.child("isProfessional").getValue(Integer.class) == 1) {
                        ModelProfessional modelProfessional = ds.getValue(ModelProfessional.class);
                        professionalsList.add(modelProfessional);
                    }
                }
                MyProfessionalsAdapter myProfessionalsAdapter = new MyProfessionalsAdapter(ViewProfessional.this, professionalsList);
                recyclerView.setAdapter(myProfessionalsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public class MyProfessionalsAdapter extends RecyclerView.Adapter<MyProfessionalsAdapter.MyViewHolder> {
        Context context;
        ArrayList<ModelProfessional> allProfessionals;

        public MyProfessionalsAdapter(Context context, ArrayList<ModelProfessional> allProfessionals) {
            this.context = context;
            this.allProfessionals = allProfessionals;
        }

        @NonNull
        @Override
        public MyProfessionalsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.professional_card_item, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyProfessionalsAdapter.MyViewHolder holder, int position) {
            final String hisUID = allProfessionals.get(position).getId();

            holder.profName.setText(allProfessionals.get(position).getName());
            holder.profSpeciality.setText(allProfessionals.get(position).getSpeciality());

            String numberOfStars = allProfessionals.get(position).getStars() + " Stars";
            holder.profStars.setText(numberOfStars);

            String profImage = allProfessionals.get(position).getPhoto();
//            holder.imageView.setBackgroundResource(allProfessionals.get(position).getPhoto());

            try {
                Picasso.get().load(profImage).placeholder(R.drawable.abs111).into(holder.imageView);
            } catch (Exception e) {
                Picasso.get().load(R.drawable.abs111).into(holder.imageView);
            }

            int num = position;

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(getApplicationContext(), ViewSingleProfessional.class)
                            .putExtra("profSchool", allProfessionals.get(num).getSchool())
                            .putExtra("profStars", numberOfStars)
                            .putExtra("profSpeciality", allProfessionals.get(num).getSpeciality())
                            .putExtra("profImage", allProfessionals.get(num).getPhoto())
                            .putExtra("profExp", allProfessionals.get(num).getExperience())
                            .putExtra("profName", allProfessionals.get(num).getName())
                            .putExtra("profID", hisUID));


                }
            });
        }

        @Override
        public int getItemCount() {
            return allProfessionals.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            CircleImageView imageView;
            TextView profName;
            TextView profSpeciality;
            TextView profStars;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = (CircleImageView) itemView.findViewById(R.id.professionalProfileImage);
                profName = itemView.findViewById(R.id.cardProfessionalName);
                profSpeciality = itemView.findViewById(R.id.cardProfessionalSpeciality);
                profStars = itemView.findViewById(R.id.cardProfessionalStars);
            }
        }
    }


    public static class ModelProfessional {
        private String name;
        private String experience;
        private String photo;
        private String school;
        private String speciality;
        private int stars;
        private String id;

        public ModelProfessional() {
        }

        public ModelProfessional(String name, String experience, String photo, String school, String speciality, int stars, String id) {
            this.name = name;
            this.experience = experience;
            this.photo = photo;
            this.school = school;
            this.speciality = speciality;
            this.stars = stars;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getSpeciality() {
            return speciality;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }
    }


}