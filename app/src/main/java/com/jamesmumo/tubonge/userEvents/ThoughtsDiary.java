package com.jamesmumo.tubonge.userEvents;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.SharedPref;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThoughtsDiary extends AppCompatActivity {

    private List<NoteModel> noteList;
    private NotesAdapter notesAdapter;
    private static SharedPreferences sharedPrefs;
    private static Gson gson;
    private TextView addNote, deleteAllNotes;
    private CardView cardView;
    private RelativeLayout topAddNoteBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoughts_diary);

        // Initialize shared preferences and gson
        sharedPrefs = getSharedPreferences("notes_prefs", Context.MODE_PRIVATE);
        gson = new Gson();

        cardView = findViewById(R.id.addNoteCardView);
        topAddNoteBar = findViewById(R.id.topAddNoteBar);
        addNote = findViewById(R.id.addNote);
        deleteAllNotes = findViewById(R.id.deleteAllNotes);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
                topAddNoteBar.setVisibility(View.GONE);
            }
        });

        // Initialize note list and adapter
        noteList = getSavedNotes();
        notesAdapter = new NotesAdapter(noteList);

        // Set up recycler view
        RecyclerView recyclerView = findViewById(R.id.note_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(notesAdapter);

        // Set up submit button
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get note form input and current timestamp
                EditText headerInput = findViewById(R.id.note_header);
                String header = headerInput.getText().toString();

                EditText textInput = findViewById(R.id.note_text);
                String text = textInput.getText().toString();

                long timestamp = System.currentTimeMillis(); // get current timestamp

                // Create new note model and add to list
                NoteModel note = new NoteModel(header, text, timestamp); // pass timestamp to constructor
                noteList.add(note);

                // Save updated note list to shared preferences
                saveNotes(noteList);

                // Update adapter and clear form input
                notesAdapter.notifyDataSetChanged();
                headerInput.setText("");
                textInput.setText("");

                cardView.setVisibility(View.GONE);
                topAddNoteBar.setVisibility(View.VISIBLE);
            }
        });

    }


    // Retrieve saved notes from shared preferences
    private List<NoteModel> getSavedNotes() {
        String json = sharedPrefs.getString("notes", "");
        Type type = new TypeToken<List<NoteModel>>() {
        }.getType();
        List<NoteModel> notes = gson.fromJson(json, type);
        if (notes == null) {
            notes = new ArrayList<>();
        }
        return notes;
    }

    // Save note list to shared preferences
    private static void saveNotes(List<NoteModel> notes) {
        String json = gson.toJson(notes);
        sharedPrefs.edit().putString("notes", json).apply();
    }

    public static class NoteModel {
        private String header;
        private String text;
        private long timestamp; // new field for timestamp

        public NoteModel(String header, String text, long timestamp) {
            this.header = header;
            this.text = text;
            this.timestamp = timestamp; // initialize timestamp field
        }

        // getter methods for all fields
        public String getHeader() {
            return header;
        }


        public String getText() {
            return text;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }


    public static class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

        private List<NoteModel> notesList;

        public NotesAdapter(List<NoteModel> notesList) {
            this.notesList = notesList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            NoteModel note = notesList.get(position);

            holder.noteHeaderTextView.setText(note.getHeader());
            holder.noteTextView.setText(note.getText());

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.getDefault());
            String formattedDate = dateFormat.format(new Date(note.getTimestamp()));
            holder.timestampTextView.setText(formattedDate);

            // Set click listener for delete button
            holder.delete.setOnClickListener(v -> {
                notesList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, notesList.size());
                // Save updated note list to shared preferences
                saveNotes(notesList);
            });
        }

        @Override
        public int getItemCount() {
            return notesList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView noteHeaderTextView;
            public TextView noteTextView;
            public TextView timestampTextView;
            public Button delete;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                noteHeaderTextView = itemView.findViewById(R.id.note_header_textview);
                noteTextView = itemView.findViewById(R.id.note_text_textview);
                timestampTextView = itemView.findViewById(R.id.timestamp_textview);
                delete = itemView.findViewById(R.id.note_item_delete);
            }
        }
    }


}