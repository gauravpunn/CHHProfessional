package com.cancerhomehealth.chhprofessional.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.cancerhomehealth.chhprofessional.Fragments.ChangepassFragment;
import com.cancerhomehealth.chhprofessional.Fragments.PasswordSaveFragment;
import com.cancerhomehealth.chhprofessional.Fragments.SelectProfFragment;
import com.cancerhomehealth.chhprofessional.R;

public class LoginActivity extends AppCompatActivity {

    private boolean doubleBackExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.login_container,new SelectProfFragment()).commit();
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.login_container);

        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            if (fragment instanceof ChangepassFragment){
                getSupportFragmentManager().popBackStack("for_pass_frag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }else if (fragment instanceof PasswordSaveFragment){
                getSupportFragmentManager().popBackStack("login_frag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }else{
                getSupportFragmentManager().popBackStackImmediate();
            }
        }else {
            if (doubleBackExit){
                super.onBackPressed();
                return;
            }
            this.doubleBackExit = true;
            Toast.makeText(this, "Press BACK again to Exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackExit = false;
                }
            },2000);
        }
    }
}