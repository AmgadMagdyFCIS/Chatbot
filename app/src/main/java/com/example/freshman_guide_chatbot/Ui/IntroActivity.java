package com.example.freshman_guide_chatbot.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.freshman_guide_chatbot.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPageAdapter introviewpageadapter ;
    TabLayout tabindicator;
    Button Nextbtn ;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        tabindicator = findViewById(R.id.tabindicator);
        Nextbtn = findViewById(R.id.FirstPageButton);

        List<ScreenItem> mlist = new ArrayList<>();
        mlist.add( new ScreenItem("Welcome","Welcome to Your Friendly Chatbot",R.drawable.hichatbot));
        mlist.add( new ScreenItem("Your Schedule","We can tell you your schedule",R.drawable.schedule));
        mlist.add( new ScreenItem("The Professors` schedule","We can tell you the Professors` schedule either",R.drawable.professors));
        screenPager= findViewById(R.id.screen_viewpager);
        introviewpageadapter= new IntroViewPageAdapter(this,mlist);
        screenPager.setAdapter(introviewpageadapter);

        tabindicator.setupWithViewPager(screenPager);

        Nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if(position < mlist.size())
                {
                    position++;
                    screenPager.setCurrentItem(position);
                }
            }
        });

    }
}