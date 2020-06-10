package com.example.gleaming;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        boolean isLoggedIn = LoginActivity.AppPreferences.getInstance(this).getBoolean(LoginActivity.AppPreferences.Keys.IS_LOGGED_IN);
//
        Intent intent;

        intent = new Intent(this, LoginActivity.class);
//
//        if(!isLoggedIn){
//            intent = new Intent(this, LoginActivity.class);
//        }else{
//            intent = new Intent(this, com.example.gleaming.MainActivity.class);
//        }
//
        startActivity(intent);
        finish();
    }

}
