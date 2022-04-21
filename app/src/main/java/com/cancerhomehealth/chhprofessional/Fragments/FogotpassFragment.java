package com.cancerhomehealth.chhprofessional.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cancerhomehealth.chhprofessional.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class FogotpassFragment extends Fragment implements View.OnClickListener {


    //Button for Sending OTP
    private Button sendOtpBtn;
    private ImageView fpBackBtn;

    private EditText fpPhoneET;

    private FirebaseAuth mAuth;

    private String otp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fogotpass, container, false);

        //method for initializing View
        initiateView(view);

        mAuth = FirebaseAuth.getInstance();

        //setOnClickListener to button
        sendOtpBtn.setOnClickListener(this::onClick);
        fpBackBtn.setOnClickListener(this::onClick);

        return view;
    }

    private void initiateView(View view) {

        fpPhoneET = view.findViewById(R.id.fp_phone_et);
        sendOtpBtn = view.findViewById(R.id.send_otp_btn);
        fpBackBtn = view.findViewById(R.id.fp_back_btn);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.send_otp_btn:
                sendOTP();
                break;

            case R.id.fp_back_btn:
                getParentFragmentManager().popBackStack("login_frag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
        }

    }

    private void sendOTP() {

        try {
            String phone = fpPhoneET.getText().toString().trim();

            if (phone.length() <= 9){
                fpPhoneET.setError("Please Enter Valid 10 digit Number");
            }else {
                String phoneNumber = "+91"+phone;
                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(getActivity())
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otp = s;
                                OtpverifyFragment ovf = new OtpverifyFragment();

                                Bundle args = new Bundle();
                                args.putString("otp",otp);
                                args.putString("phone",phone);

                                ovf.setArguments(args);
                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.login_container,ovf).addToBackStack("for_pass_frag").commit();
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getActivity(), " "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).build();

                PhoneAuthProvider.verifyPhoneNumber(options);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}