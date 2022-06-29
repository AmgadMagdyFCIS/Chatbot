package com.example.freshman_guide_chatbot.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.freshman_guide_chatbot.R;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPageAdapter introviewpageadapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        List<ScreenItem> mlist = new ArrayList<>();
        mlist.add( new ScreenItem("Welcome","Welcome to Your Friendly Chatbot",R.drawable.hichatbot));
        mlist.add( new ScreenItem("Your Schedule","We can tell you your schedule",R.drawable.schedule));
        mlist.add( new ScreenItem("The Professors` schedule","We can tell you the Professors` schedule either",R.drawable.professors));




        screenPager= findViewById(R.id.screen_viewpager);

    }
}