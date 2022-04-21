package com.cancerhomehealth.chhprofessional.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cancerhomehealth.chhprofessional.Activities.HomeActivity;
import com.cancerhomehealth.chhprofessional.ApiResponse.UserResponse;
import com.cancerhomehealth.chhprofessional.R;
import com.cancerhomehealth.chhprofessional.Retrofit.RetrofitClient;
import com.cancerhomehealth.chhprofessional.SharedPreferences.SharedPrefManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment implements View.OnClickListener {

    // EditTexts for entering user credentials
    private EditText loginUsernameET,loginPasswordET;
    //CheckBox for remembering user credentials
    private CheckBox rememberCheck;
    //Forgot Password Button
    private TextView forgotPassBtn;
    //Login Button for login using credentials
    private Button loginBtn;


    //SharedPreferences for saving data for session
    private SharedPrefManager sharedPrefManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing shared preferences
        sharedPrefManager = new SharedPrefManager(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPrefManager = new SharedPrefManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        //method for initializing view
        initiateView(view);


        //setOnClickListener
        //Forward to ForgotPassword Fragment
        forgotPassBtn.setOnClickListener(this::onClick);
        //Perform Login User
        loginBtn.setOnClickListener(this::onClick);



        return view;
    }

    //Initialized views
    private void initiateView(View view) {

        loginUsernameET = view.findViewById(R.id.login_username_et);
        loginPasswordET = view.findViewById(R.id.login_password_et);
        rememberCheck = view.findViewById(R.id.remember_check);
        forgotPassBtn= view.findViewById(R.id.forgot_pass_btn);
        loginBtn = view.findViewById(R.id.login_btn);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.forgot_pass_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.login_container,new FogotpassFragment()).addToBackStack("login_frag").commit();
                break;

            case R.id.login_btn:
//                Intent home = new Intent(getActivity(), HomeActivity.class);
//                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(home);
                loginUser();
                break;
        }

    }

    private void loginUser() {

        try {

            String username = loginUsernameET.getText().toString().trim();
            String password = loginPasswordET.getText().toString().trim();

            if (username.equals("")){
                loginUsernameET.setError("Enter Username");
            }else if (password.equals("")){
                loginPasswordET.setError("Enter Password");
            }else {

                HashMap<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);

                Call<UserResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .operationUserLogin(params);

                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse resp = response.body();
                        if (resp != null){

                            if (resp.getSuccess() == 1){

                                sharedPrefManager.saveUser(resp.getUserData());

                                Intent home = new Intent(getActivity(),HomeActivity.class);
                                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(home);

                                Log.i("USER",resp.getUserData().toString());

                            }else {
                                Toast.makeText(getActivity(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("USER",resp.getMessage());
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("USER",t.getMessage());
                    }
                });

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}