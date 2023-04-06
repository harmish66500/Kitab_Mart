package com.example.kitabmart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_btn.setOnClickListener {

            // ...

            Handler(Looper.getMainLooper()).postDelayed({
                    // Dismiss progress bar after 4 seconds
                    progressDialog.dialog.dismiss()
            }, 4000)
      */  super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences = getSharedPreferences("User",MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preferences.getString("email",null) == null){
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            }
        },4000);
    }
}