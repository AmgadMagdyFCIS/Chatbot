package com.example.freshman_guide_chatbot.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.freshman_guide_chatbot.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPageAdapter introviewpageadapter ;
    TabLayout tabindicator;
    Button Nextbtn  , btngetStarted;
    int position;
    TextView skip;
    Animation btnAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        try {
            String nav=getIntent().getExtras().getString("nav");

        }catch (Exception e){
            if(restorePrefData())
            {
                Intent mainAcitivity = new Intent(getApplicationContext(),NavigationActivity.class);
                startActivity(mainAcitivity);
                finish();
            }

        }





        getSupportActionBar().hide();


        setContentView(R.layout.activity_intro);
        tabindicator = findViewById(R.id.tabindicator);
        Nextbtn = findViewById(R.id.FirstPageButton);
        btngetStarted= findViewById(R.id.btnGet_Started);
        btnAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        skip=findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);
            }
        });

        List<ScreenItem> mlist = new ArrayList<>();
        mlist.add( new ScreenItem("Welcome","Welcome to Your Friendly Chatbot",R.drawable.hichatbot));
        mlist.add( new ScreenItem("Your Schedule","We can tell you your schedule",R.drawable.schedule));
        mlist.add( new ScreenItem("The Professors` schedule","We can tell you the Professors` schedules and their location either",R.drawable.professors));
        mlist.add( new ScreenItem("The Professors` Emails","You can find the Professors` Emails as well",R.drawable.mails));
        mlist.add( new ScreenItem("The Professors` offices","You will find info about the Faculty and its departments too",R.drawable.info));




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
                if(position == mlist.size()-1)
                {
                    loadlastscreen();

                }
            }
        });

        tabindicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if(tab.getPosition() == mlist.size()-1)
                {
                    loadlastscreen();

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        btngetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainActivity = new Intent(getApplicationContext(),NavigationActivity.class);
                startActivity(mainActivity);

                savePrefData();
                finish();

            }
        });



    }

    private  boolean restorePrefData()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myprefs", MODE_PRIVATE);
        Boolean isIntroAcitivityOpenedBefore = pref.getBoolean("isIntroOpened",false);
        return isIntroAcitivityOpenedBefore;



    }
    private void savePrefData()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myprefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();



    }

    private   void  loadlastscreen()
    {
        Nextbtn.setVisibility(View.INVISIBLE);
        btngetStarted.setVisibility(View.VISIBLE);
        tabindicator.setVisibility(View.INVISIBLE);

        btngetStarted.setAnimation(btnAnim);



    }
}