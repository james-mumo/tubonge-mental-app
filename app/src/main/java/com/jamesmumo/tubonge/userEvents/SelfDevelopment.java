package com.jamesmumo.tubonge.userEvents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class SelfDevelopment extends AppCompatActivity {
    List<MySelfDevItem> myListItems;
    CustomListAdapter customListAdapter;
    // Array of item names and images
    String[] itemNames = {"Journaling", "Meditation", "Goal Planning", "Exercise", "Learning", "Gratitude", "Socializing", "Volunteering", "Traveling", "Creativity"};


    int[] itemImages = {R.drawable.diary, R.drawable.meditation, R.drawable.a, R.drawable.runner, R.drawable.consultant, R.drawable.disk, R.drawable.soci, R.drawable.lesbian, R.drawable.travel, R.drawable.thoughts};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_development);

        // Find the list view and create a custom adapter
        ListView listView = findViewById(R.id.list_view);

        myListItems = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            myListItems.add(new MySelfDevItem(itemImages[i], itemNames[i]));
        }
        customListAdapter = new CustomListAdapter(this, myListItems);
        listView.setAdapter(customListAdapter);
    }

    public static class MySelfDevItem {
        private int img;
        private String title;

        public MySelfDevItem(int img, String title) {
            this.img = img;
            this.title = title;
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

        public void setTitle(String title) {
            this.title = title;
        }
    }

    // Custom adapter for the list view
    class CustomListAdapter extends BaseAdapter {
        Context context;
        List<MySelfDevItem> listItems;

        public CustomListAdapter(Context context, List<MySelfDevItem> listItems) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.self_development_item, parent, false);

            ImageView image = convertView.findViewById(R.id.selfDevImage);
            TextView title = convertView.findViewById(R.id.selfDevText);

            image.setImageResource(listItems.get(position).getImg());
            title.setText(listItems.get(position).getTitle());

            String[] pagetTitles = {"Journaling:", "Mindfulness meditation:", "Goal Setting", "Positive affirmations:", "Self Growth: ", "Cognitive restructuring:",

            };
            String[] pagePoints1 = {"Find a quiet and private space to write down your thoughts and feelings, such as in a notebook or journal.\n \n" + "Write freely without judgment or self-editing, allowing your thoughts to flow naturally.", "Find a quiet and comfortable space where you won't be disturbed.\n" + "Sit comfortably and focus your attention on your breath, taking deep, slow breaths in and out.", "Identify specific and achievable goals that align with your values and interests, such as completing a project or learning a new skill.\n \n" + "Break down larger goals into smaller, manageable steps to make them more achievable. \n", "\n" + "Identify negative self-talk and beliefs that you want to counteract with positive affirmations.\n" + "\n" + "Create a list of specific and positive statements that are personal and believable to you, such as \"I am capable and competent.\"\n", "\n" + "Prioritize your physical and emotional health by engaging in regular exercise, eating well, and getting enough sleep.\n" + "\n" + "Set boundaries and learn to say no to avoid burnout and overwhelm.\n", "Identify and challenge negative thought patterns by asking yourself if they are true, helpful, and balanced.\n" + "\n" + "Practice replacing negative thoughts with positive and realistic ones, such as \"I made a mistake, but I can learn from it and do better next time.\"\n",


            };

            String[] pagePoints2 = {"Use journaling as a tool for self-reflection and problem-solving, exploring your emotions and identifying patterns in your thinking.\n" + "Experiment with different types of journaling, such as gratitude journaling or goal-setting journaling.", "If your mind wanders, acknowledge your thoughts and gently bring your focus back to your breath.\n \n" + "Start with short sessions of 5-10 minutes and gradually increase the duration as you become more comfortable with the practice.", "Create a plan and hold yourself accountable by tracking your progress and celebrating your successes.\n \n" + "\n" + "Adjust your goals as necessary and be flexible in your approach.", "Practice repeating the affirmations regularly, such as in the morning or before bed, to internalize them and shift your thinking towards positivity.\n" + "\n" + "Use visualization techniques to imagine the positive statements as if they are already true.\n", "\n" + "Engage in activities that bring you joy and help you relax, such as reading, listening to music, or spending time in nature.\n" + "\n" + "Practice self-compassion and treat yourself with kindness and understanding.\n", "Seek professional help if you need additional support in identifying and challenging negative thought patterns, such as from a therapist or counselor.\n" + "\n" + "Use cognitive restructuring techniques as a tool for improving your self-esteem and emotional well-being.\n" + "\n"

            };


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (listItems.get(position).getTitle()) {
                        case "Journaling":
                            startActivity(new Intent(SelfDevelopment.this, DevelopmentItem.class).putExtra("point1", pagePoints1[0]).putExtra("point2", pagePoints2[0]).putExtra("title", pagetTitles[0])

                            );
                            break;
                        case "Meditation":
                            startActivity(new Intent(SelfDevelopment.this, DevelopmentItem.class).putExtra("point1",
                                    pagePoints1[1]).putExtra("point2", pagePoints2[1]).putExtra("title", pagetTitles[1])

                            );
                        case "Goal Planning":
                            startActivity(new Intent(SelfDevelopment.this, DevelopmentItem.class).putExtra("point1", pagePoints1[2]).putExtra("point2", pagePoints2[2]).putExtra("title", pagetTitles[2])

                            );
                        case "Exercise":
                            startActivity(new Intent(SelfDevelopment.this, DevelopmentItem.class).putExtra("point1", pagePoints1[3]).putExtra("point2", pagePoints2[3]).putExtra("title", pagetTitles[3])

                            );
                        case "Learning":
                            startActivity(new Intent(SelfDevelopment.this, DevelopmentItem.class).putExtra("point1", pagePoints1[4]).putExtra("point2", pagePoints2[4]).putExtra("title", pagetTitles[4])

                            );
                            break;
                        case "Gratitude":
                            startActivity(new Intent(SelfDevelopment.this, DevelopmentItem.class).putExtra("point1", pagePoints1[5]).putExtra("point2", pagePoints2[5]).putExtra("title", pagetTitles[5])

                            );
                            break;
                        default:
                            Toast.makeText(context, "Not Added", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return convertView;
        }
    }
}
