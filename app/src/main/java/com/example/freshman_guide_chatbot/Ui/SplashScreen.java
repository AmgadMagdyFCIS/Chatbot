package com.example.freshman_guide_chatbot.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.freshman_guide_chatbot.Ui.Registration.Login;

public class SplashScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //startService(new Intent(this, PythonService.class));

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                startActivity( new Intent(SplashScreen.this, IntroActivity.class));
                finish();


            }
        } , 3000);


       }
    }
