package com.jamesmumo.tubonge.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jamesmumo.tubonge.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

public class SearchFragment extends Fragment {

    private List<Integer> quotes = new ArrayList<>(Arrays.asList(
            R.string.quote_string,
            R.string.quote_string2,
            R.string.quote_string3,
            R.string.quote_string4,
            R.string.quote_string5,
            R.string.quote_string6,
            R.string.quote_string7
    ));

    private int quoteNumber = 0;
    private String mainText = "";

    FloatingActionButton fab_newQuote;
    TextView tv_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        fab_newQuote = view.findViewById(R.id.fab_newQuote);
        tv_text = view.findViewById(R.id.tv_text);
        quoteOnAppLoaded();
        clickNewQuote();
        return view;
    }

    private void clickNewQuote() {
        fab_newQuote.setOnClickListener(v -> {
            fab_newQuote.setEnabled(false);

            if (quoteNumber == quotes.size()) {
                quoteOnAppLoaded();
            } else {
                typeText(getString(quotes.get(quoteNumber)));
                ++quoteNumber;
            }
        });
    }

    private void quoteOnAppLoaded() {
        fab_newQuote.setEnabled(false);
        quoteNumber = 0;
        Collections.shuffle(quotes);
        typeText(getString(quotes.get(quoteNumber)));
        ++quoteNumber;
    }

    private void typeText(final String text) {
        // Initialize mainText variable
        mainText = "";
        // Set the delay between each letter
        final long textDelay = 0;

        // Create a new Thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Create a StringBuilder to build the text
                StringBuilder sb = new StringBuilder();
                String updatedText = "";
                for (int i = 0; i < text.length(); i++) {
                    mainText = sb.append(updatedText + text.charAt(i)).toString();
                    try {
                        // Sleep the thread for the specified delay
                        Thread.sleep(textDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        tv_text.setText(mainText + "|");
                        // Check if the text is fully typed out
                        if (text.equals(mainText)) {
                            // Update the text view with the final text
                            tv_text.setText(mainText);
                            // Enable the fab_newQuote button
                            fab_newQuote.setEnabled(true);
                        }
                    }
                });
            }
        });
        thread.start();
    }




}
