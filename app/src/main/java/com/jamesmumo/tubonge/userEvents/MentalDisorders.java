package com.jamesmumo.tubonge.userEvents;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.SharedPref;

public class MentalDisorders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_disorders);

        ViewPager2 viewPager;
        TabLayout tabLayout;


        viewPager = findViewById(R.id.mview_pager);
        tabLayout = findViewById(R.id.mtab_layout);

        viewPager.setAdapter(new MentalDisorders.MyMentalAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Chat");
                    } else {
                        tab.setText("Forum");
                    }
                }
        ).attach();
    }

    private static class MyMentalAdapter extends FragmentStateAdapter {

        public MyMentalAdapter(MentalDisorders activity) {
            super(activity);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new MentalDisordersAllFragment();
            } else {
                return new MentalDisordersProfessionalsFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }


}