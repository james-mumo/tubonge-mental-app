package com.jamesmumo.tubonge.userEvents;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.jamesmumo.tubonge.R;

/*
 */
public class MentalDisordersProfessionalsFragment extends Fragment {
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mental_disorders_professionals, container, false);


        textView = view.findViewById(R.id.seeProfessinals);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ViewProfessional.class).putExtra("myMood", "Sad"));
            }
        });

        return view;
    }
}