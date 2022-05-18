package com.example.freshman_guide_chatbot.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.freshman_guide_chatbot.R;
import com.example.freshman_guide_chatbot.Ui.Registration.Login;

public class SplashScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity( new Intent(SplashScreen.this, Login.class));
                finish();

            }
        } , 3000);


       }
    }
