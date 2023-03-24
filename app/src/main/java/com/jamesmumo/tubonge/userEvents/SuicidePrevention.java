package com.jamesmumo.tubonge.userEvents;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class SuicidePrevention extends AppCompatActivity {
    CustomSuicidalListAdapter customSuicidalListAdapter;
    List<MySuicideDataItem> suicideDataItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suicide_prevention);
        suicideDataItems = new ArrayList<>();
        int[] itemImgs = {R.drawable.comp, R.drawable.medical, R.drawable.creative, R.drawable.mapp};
        String[] itemTitles = {"Connect To Professionals", "Get Self-Guidance Materials", "Peer Counselling", "Connect With Others"};
        String[] itemSubtitles = {
                "Get connected to Professionals near your location for help immediately.",
                "Get Access To Materials Created And Approved By Professionals That will guide you.",
                "Connect To Peers Online and Get To Share Your Experiences With Others as You Get Helped.",
                "Join Online Peer Support Communities around the world, share your Experiences and help Others."
        };

        for (int i = 0; i < 4; i++) {
            suicideDataItems.add(new MySuicideDataItem(itemImgs[i], itemTitles[i], itemSubtitles[i]));
        }

        customSuicidalListAdapter = new CustomSuicidalListAdapter(this, suicideDataItems);
        ListView listView = findViewById(R.id.suicide_list_view);
        listView.setAdapter(customSuicidalListAdapter);
    }


    public static class MySuicideDataItem {
        private int img;
        private String title;
        private String subtitle;

        public MySuicideDataItem(int img, String title, String subtitle) {
            this.img = img;
            this.title = title;
            this.subtitle = subtitle;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    // Custom adapter for the list view
    static class CustomSuicidalListAdapter extends BaseAdapter {
        Context context;
        List<SuicidePrevention.MySuicideDataItem> listItems;

        public CustomSuicidalListAdapter(Context context, List<SuicidePrevention.MySuicideDataItem> listItems) {
            this.context = context;
            this.listItems = listItems;
        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.suicide_prevention_item, parent, false);

            ImageView image = convertView.findViewById(R.id.suicidePrevImage);
            TextView title = convertView.findViewById(R.id.suicidePrevText);
            TextView subtitle = convertView.findViewById(R.id.suicidePrevSubText);

            image.setImageResource(listItems.get(position).getImg());
            title.setText(listItems.get(position).getTitle());
            subtitle.setText(listItems.get(position).getSubtitle());

            return convertView;
        }
    }
}