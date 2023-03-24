package com.jamesmumo.tubonge.userEvents;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.SharedPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewEvents extends AppCompatActivity {

    ExpandableListView eventsView;
    HashMap<String, List<String>> listChild;
    List<String> listHeader;
    CustomExpandableListAdapter customExpandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        eventsView = findViewById(R.id.eventsView);
        listChild = ExpandableListData.getData();
        listHeader = new ArrayList<String>(listChild.keySet());
        customExpandableListAdapter = new CustomExpandableListAdapter(this, listHeader, listChild);
        eventsView.setAdapter(customExpandableListAdapter);
    }


    static class ExpandableListData {
        public static HashMap<String, List<String>> getData() {
            HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

            List<String> Gretsa = new ArrayList<String>();
            Gretsa.add("Speaker : Dr James from IKEA Intl");
            Gretsa.add("Theme : Not Being Okay Is Okay");
            Gretsa.add("Venue : Hall 2 at 11 Am");

            List<String> MindFactoryInc = new ArrayList<String>();
            MindFactoryInc.add("Theme : Not Being Okay Is Okay");
            MindFactoryInc.add("Venue : Hall 2 at 11 Am");

            List<String> MKU = new ArrayList<String>();
//        MKU.add("Hall 2 at 11 Am");
            MKU.add("Speaker : Dr James from IKEA Intl");
            MKU.add("Theme : Not Being Okay Is Okay");
            MKU.add("Venue : Varisty Hall at 1 Pm, Carry A Notebook");

            expandableListDetail.put("Gretsa Mental Health Forum", Gretsa);
            expandableListDetail.put("Mount kenya - Better Thinking", MKU);
            expandableListDetail.put("Clear Thinking Candid Talk", MindFactoryInc);
            return expandableListDetail;
        }
    }

    class CustomExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private HashMap<String, List<String>> childtitles;
        List<String> headertitles;

        public CustomExpandableListAdapter(Context context, List<String> headertitles, HashMap<String, List<String>> childtitles) {
            this.context = context;
            this.childtitles = childtitles;
            this.headertitles = headertitles;
        }

        @Override
        public int getGroupCount() {
            return this.headertitles.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this.childtitles.get(this.headertitles.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.headertitles.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return this.childtitles.get(this.headertitles.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String listTitle = (String) getGroup(groupPosition);
//        int listImage = (int) getGroup(groupPosition);

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandable_group_header, null);

            }
            TextView listTitleTextView = (TextView) convertView.findViewById(R.id.expandableItemTitle);
//        ImageView listImageView = (ImageView) convertView.findViewById(R.id.eventImage);
            listTitleTextView.setText(listTitle);
//        listImageView.setImageResource(listImage);
            return convertView;
        }


        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final String expandableListText = (String) getChild(groupPosition, childPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandable_list_child_item, null);
            }

            TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandableChildItemSpeaker);
            expandedListTextView.setText(expandableListText);


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}