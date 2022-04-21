package com.cancerhomehealth.chhprofessional.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.cancerhomehealth.chhprofessional.R;
import com.cancerhomehealth.chhprofessional.SharedPreferences.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

    private ImageView logoIV;

    //Shared PrefManager
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Initializing sharedPrefManager
        sharedPrefManager = new SharedPrefManager(this);

        //initializing views
        logoIV = findViewById(R.id.logo_iv);
        //logo animation
        logoIV.animate().alpha(1.0f).setDuration(1500);

        //Delay on Splash Screen
        Handler splash = new Handler();
        splash.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateUI();
            }
        },2500);
    }

    private void updateUI(){


        try {

            String username = sharedPrefManager.getUserData().getUsername().toString();

            if (username.equals("") || username.isEmpty()){

                Intent login = new Intent(SplashActivity.this,LoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);

            }else {
                Intent home = new Intent(SplashActivity.this,HomeActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(home);
            }

        }catch (Exception e){
            e.printStackTrace();
            Intent login = new Intent(SplashActivity.this,LoginActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login);
        }
    }
}