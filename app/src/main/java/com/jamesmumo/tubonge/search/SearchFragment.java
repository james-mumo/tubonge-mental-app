package com.jamesmumo.tubonge.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.userEvents.AddictionRecovery;
import com.jamesmumo.tubonge.userEvents.MentalDisorders;
import com.jamesmumo.tubonge.userEvents.MoodControl;
import com.jamesmumo.tubonge.userEvents.PeerCounselling;
import com.jamesmumo.tubonge.userEvents.SelfDevelopment;
import com.jamesmumo.tubonge.userEvents.SuicidePrevention;
import com.jamesmumo.tubonge.userEvents.ThoughtsDiary;
import com.jamesmumo.tubonge.userEvents.ViewProfessional;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    ArrayList<ServiceModel> serviceItems;
    RecyclerView gridRecyclerView;
    GridItemAdapter gridItemAdapter;
    String[] appServices = {
            "Thought Diary",
            "Self Development",
            "Peer Counselling",
            "Addiction Recovery",
            "Mental Disorder",
            "Mood Control",
            "Professionals",
            "Suicide Prevention"
    };
    Integer[] appServicesIcon = {
            R.drawable.diary,
            R.drawable.runner,
            R.drawable.soci,
            R.drawable.addiction1,
            R.drawable.headache,
            R.drawable.an,
            R.drawable.doctor,
            R.drawable.gibbet};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        gridViewData();

        gridRecyclerView = view.findViewById(R.id.gridRecyclerView);
        gridRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        gridRecyclerView.setLayoutManager(gridLayoutManager);
        gridItemAdapter = new GridItemAdapter(getContext(), serviceItems);

        gridRecyclerView.setAdapter(gridItemAdapter);

        return view;
    }

    private void gridViewData() {
        serviceItems = new ArrayList<ServiceModel>();
        serviceItems.add(new ServiceModel(appServices[0], appServicesIcon[0]));
        serviceItems.add(new ServiceModel(appServices[1], appServicesIcon[1]));
        serviceItems.add(new ServiceModel(appServices[2], appServicesIcon[2]));
        serviceItems.add(new ServiceModel(appServices[3], appServicesIcon[3]));
        serviceItems.add(new ServiceModel(appServices[4], appServicesIcon[4]));
        serviceItems.add(new ServiceModel(appServices[5], appServicesIcon[5]));
        serviceItems.add(new ServiceModel(appServices[6], appServicesIcon[6]));
        serviceItems.add(new ServiceModel(appServices[7], appServicesIcon[7]));

    }




public class GridItemAdapter extends RecyclerView.Adapter<GridItemAdapter.MyViewHolder> {
    private final Context context;
    ArrayList<ServiceModel> arrayList;

    public GridItemAdapter(Context context, ArrayList<ServiceModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public GridItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.app_services_gridlayout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridItemAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(arrayList.get(position).getItemTitle());
        holder.image.setImageResource(arrayList.get(position).getImage());


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String serviceName = arrayList.get(position).getItemTitle();
                switch (serviceName) {

                    case "Thought Diary":
                        Intent intent = new Intent(v.getContext(), ThoughtsDiary.class);
                        v.getContext().startActivity(intent);
                        Log.d("Check", arrayList.get(position).getItemTitle());
                        break;
                    case "Self Development":
                        v.getContext().startActivity(new Intent(v.getContext(), SelfDevelopment.class));
                        break;
                    case "Peer Counselling":
                        v.getContext().startActivity(new Intent(v.getContext(), PeerCounselling.class));
                        break;
                    case "Addiction Recovery":
                        v.getContext().startActivity(new Intent(v.getContext(), AddictionRecovery.class));
                        break;
                    case "Mental Disorder":
                        v.getContext().startActivity(new Intent(v.getContext(), MentalDisorders.class));
                        break;
                    case "Mood Control":
                        v.getContext().startActivity(new Intent(v.getContext(), MoodControl.class));
                        break;
                    case "Professionals":
                        v.getContext().startActivity(new Intent(v.getContext(), ViewProfessional.class));
                        break;
                    case "Suicide Prevention":
                        v.getContext().startActivity(new Intent(v.getContext(), SuicidePrevention.class));
                        break;
                    default:
                        v.getContext().startActivity(new Intent(v.getContext(), Search.class));
                        Log.d("Check", arrayList.get(position).getItemTitle());
                }
                ;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cardServiceName);
            image = itemView.findViewById(R.id.cardServiceImage);
        }
    }
}


class ServiceModel {
    private final String itemTitle;
    private final int image;

    public ServiceModel(String itemTitle, int image) {
        this.itemTitle = itemTitle;
        this.image = image;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public int getImage() {
        return image;
    }
}


}