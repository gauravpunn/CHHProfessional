package com.cancerhomehealth.chhprofessional.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cancerhomehealth.chhprofessional.Fragments.DashboardFragment;
import com.cancerhomehealth.chhprofessional.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.home_container,new DashboardFragment()).commit();
    }
}