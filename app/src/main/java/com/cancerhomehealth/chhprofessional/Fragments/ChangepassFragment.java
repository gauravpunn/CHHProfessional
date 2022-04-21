package com.cancerhomehealth.chhprofessional.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cancerhomehealth.chhprofessional.ApiResponse.ChangePasswordResponse;
import com.cancerhomehealth.chhprofessional.R;
import com.cancerhomehealth.chhprofessional.Retrofit.RetrofitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangepassFragment extends Fragment implements View.OnClickListener {


    //Button for saving password
    private Button savePassBtn;
    private ImageView changePassBackBtn;

    //Edittext
    private EditText cpNewpassET,cpConfirmpassET;

    private String phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_changepass, container, false);

        //Method for initiating View
        initiateView(view);

        phone = getArguments().getString("phone");

        //SetOnClickListener on button
        savePassBtn.setOnClickListener(this::onClick);
        changePassBackBtn.setOnClickListener(this::onClick);

        return view;
    }

    private void initiateView(View view) {

        cpNewpassET = view.findViewById(R.id.cp_newpass_et);
        cpConfirmpassET = view.findViewById(R.id.cp_confirmpass_et);

        savePassBtn = view.findViewById(R.id.save_pass_btn);
        changePassBackBtn = view.findViewById(R.id.change_pass_back_btn);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.save_pass_btn:
                changePassword();
//                getParentFragmentManager().beginTransaction()
//                        .replace(R.id.login_container,new PasswordSaveFragment()).addToBackStack("change_pass_frag").commit();
                break;

            case R.id.change_pass_back_btn:
                getParentFragmentManager()
                        .popBackStack("otp_frag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
        }
    }

    private void changePassword() {

        try {
            String newPass = cpNewpassET.getText().toString().trim();
            String confirmPass = cpConfirmpassET.getText().toString().trim();

            if (newPass.isEmpty()){
                cpNewpassET.setError("PLease enter new password");
            }else if (confirmPass.isEmpty()){
                cpConfirmpassET.setError("Please enter password to confirm");
            }else if (!confirmPass.equals(newPass)){
                cpConfirmpassET.setError("Password does not match");
            }else {

                HashMap<String, String> params = new HashMap<>();
                params.put("mobile_no",phone);
                params.put("new_password",newPass);
                params.put("confirm_password",confirmPass);

                Call<ChangePasswordResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .operationChangePassword(params);

                call.enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                        ChangePasswordResponse cpr = response.body();

                        if (cpr != null){
                            if (cpr.getSuccess() == 1){
                                PasswordSaveFragment psf = new PasswordSaveFragment();
                                Bundle args = new Bundle();
                                args.putString("title","Congrats !");
                                args.putString("message",cpr.getMessage());

                                psf.setArguments(args);

                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.login_container,psf).addToBackStack("change_pass_frag").commit();

                                Log.d("USER",cpr.getMessage().toString());

                            }else {
                                Log.d("USER",cpr.getMessage());
                                String title = "Oops !";
                                String message = cpr.getMessage().toString();

                                PasswordSaveFragment psf = new PasswordSaveFragment();
                                Bundle args = new Bundle();
                                args.putString("title",title);
                                args.putString("message",message);

                                psf.setArguments(args);

                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.login_container,psf).addToBackStack("change_pass_frag").commit();

                            }
                        }else {
                            Toast.makeText(getActivity(), cpr.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("USER",cpr.getMessage());

                            String title = "Oops !";
                            String message = cpr.getMessage().toString();
                            PasswordSaveFragment psf = new PasswordSaveFragment();
                            Bundle args = new Bundle();
                            args.putString("title",title);
                            args.putString("message",message);

                            psf.setArguments(args);

                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.login_container,psf).addToBackStack("change_pass_frag").commit();
                        }

                    }

                    @Override
                    public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                        Log.e("USER",t.getMessage());
                    }
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}