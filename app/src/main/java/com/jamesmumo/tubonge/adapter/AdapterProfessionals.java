package com.jamesmumo.tubonge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.model.ModelProfessional;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProfessionals extends RecyclerView.Adapter<AdapterProfessionals.MyViewHolder> {
    Context context;
    List<ModelProfessional> allProfessionals;

    public AdapterProfessionals(Context context, List<ModelProfessional> allProfessionals) {
        this.context = context;
        this.allProfessionals = allProfessionals;
    }

    @NonNull
    @Override
    public AdapterProfessionals.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.professional_card_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProfessionals.MyViewHolder holder, int position) {
        holder.profSpeciality.setText(allProfessionals.get(position).getSpeciality());
        holder.profName.setText(allProfessionals.get(position).getName());
        holder.profStars.setText(allProfessionals.get(position).getStars());
        holder.imageView.setImageResource(allProfessionals.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return allProfessionals.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView profName;
        TextView profSpeciality;
        TextView profStars;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.professionalProfileImage);
            profName = itemView.findViewById(R.id.cardProfessionalName);
            profSpeciality = itemView.findViewById(R.id.cardProfessionalSpeciality);
            profStars = itemView.findViewById(R.id.cardProfessionalStars);
        }
    }
}
