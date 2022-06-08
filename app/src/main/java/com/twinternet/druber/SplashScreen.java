package com.twinternet.druber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        Thread splashThread = new Thread(){


            @Override
            public void run() {
                try {
                    //Sleep screen for 3 seconds
                    sleep(3*1000);

                    //Move to login activity
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish(); //Remove the activity
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        splashThread.start(); //Start the tread
    }


}