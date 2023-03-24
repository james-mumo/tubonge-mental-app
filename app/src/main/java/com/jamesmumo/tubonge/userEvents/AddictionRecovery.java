package com.jamesmumo.tubonge.userEvents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class AddictionRecovery extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addiction_recovery);

        // Initialize views
//        imageView = findViewById(R.id.image_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Create dummy data for RecyclerView
        List<MyItem> itemList = new ArrayList<>();
        itemList.add(new MyItem(R.drawable.beeraddiction, "Alcohol Addiction ", "Join millions who are working on their Recovery to Alcohol Addiction "));
        itemList.add(new MyItem(R.drawable.wine2, "Substance Abuse", "One step at a time and eventually you will be free of Drug Dependencies "));
        itemList.add(new MyItem(R.drawable.overage, "Porn Addiction", "This is a tough one to admit but with the right help you can overcome it. "));
        itemList.add(new MyItem(R.drawable.comment, "Social Media Addiction", "Haven't we all been here, need of help don't hesitate to reach out."));
        itemList.add(new MyItem(R.drawable.gamepad, "Gaming Addiction", "5 more minutes and it's been 5 hours, becomes an addiction before you know it, reach out for help. "));
        itemList.add(new MyItem(R.drawable.pressure, "Dealing with Peer Pressure", "We've all struggled to fit and be accepted in Clique but sometimes it's a little too much, right?"));
        itemList.add(new MyItem(R.drawable.swearing, "Dealing with Temper", "Learn to hold your horses before they cost you your job bro, I'm kidding, reach out for help"));


        // Set up adapter and attach to RecyclerView
        MyAdapter adapter = new MyAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }


    public class MyItem {
        private int imageResId;
        private String title;
        private String subtitle;

        public MyItem(int imageResId, String title, String subtitle) {
            this.imageResId = imageResId;
            this.title = title;
            this.subtitle = subtitle;
        }

        public int getImageResId() {
            return imageResId;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<MyItem> mItems;

        public MyAdapter(List<MyItem> items) {
            mItems = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_addiction_recovery_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MyItem item = mItems.get(position);
            holder.imageView.setImageResource(item.getImageResId());

//            holder.imageView.setVisibility(View.VISIBLE);  // make the imageView visible
//            if (position % 2 == 0) {
//                holder.imageView.setImageResource(item.getLeftImageResId()); // set the left image
//            } else {
//                holder.imageView.setImageResource(item.getRightImageResId()); // set the right image
//            }


            holder.textView1.setText(item.getTitle());
            holder.textView2.setText(item.getSubtitle());
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView1;
            public TextView textView2;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageViewAddictionItem);
                textView1 = itemView.findViewById(R.id.titleTextView);
                textView2 = itemView.findViewById(R.id.descriptionTextView);
            }
        }
    }

}
