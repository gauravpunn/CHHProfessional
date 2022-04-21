package com.cancerhomehealth.chhprofessional.Fragments;

import android.app.Activity;
import android.app.admin.FactoryResetProtectionPolicy;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.cancerhomehealth.chhprofessional.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


public class OtpverifyFragment extends Fragment implements View.OnClickListener {

    //EditText for OTP
    private EditText otp1ET,otp2ET,otp3ET,otp4ET,otp5ET,otp6ET;
    private ImageView otpBackBtn;
    private String otp,phone;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_otpverify, container, false);
        
        //method for Initializing View
        initiateView(view);

        mAuth = FirebaseAuth.getInstance();

        try {
            otp = getArguments().getString("otp");
            phone = getArguments().getString("phone");
            Log.d("USER",otp);
        }catch (Exception e){
            e.printStackTrace();
        }



        //method for moving focus while entering OTP
        otpMove();

        //SetOnClickListener on buttons
        otpBackBtn.setOnClickListener(this::onClick);

        return view;
    }

    private void initiateView(View view) {

        otp1ET = view.findViewById(R.id.otp_1_et);
        otp2ET = view.findViewById(R.id.otp_2_et);
        otp3ET = view.findViewById(R.id.otp_3_et);
        otp4ET = view.findViewById(R.id.otp_4_et);
        otp5ET = view.findViewById(R.id.otp_5_et);
        otp6ET = view.findViewById(R.id.otp_6_et);

        otpBackBtn = view.findViewById(R.id.otp_back_btn);
    }

    private void otpMove() {

        otp1ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    otp2ET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otp2ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    otp3ET.requestFocus();
                }else {
                    otp1ET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otp3ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    otp4ET.requestFocus();
                }else {
                    otp2ET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otp4ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    otp5ET.requestFocus();
                }else {
                    otp3ET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otp5ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    otp6ET.requestFocus();
                }else {
                    otp4ET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otp6ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    //hiding on screen keyboard
                    String code = otp1ET.getText()+""+otp2ET.getText()+""+otp3ET.getText()+""+otp4ET.getText()+""+
                            otp5ET.getText()+""+otp6ET.getText();
                    verify(code);
                    hideSoftKeyboard();
//                    getParentFragmentManager().beginTransaction()
//                            .replace(R.id.login_container,new ChangepassFragment()).addToBackStack("otp_frag").commit();
                }else {
                    otp5ET.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();

        if (view == null){
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.otp_back_btn:
                getParentFragmentManager().popBackStack("for_pass_frag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;

        }
    }

    private void verify(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otp,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Log.d("USER","OTP is correct");
                            ChangepassFragment cpf = new ChangepassFragment();
                            Bundle args = new Bundle();
                            args.putString("phone",phone);

                            cpf.setArguments(args);
                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.login_container,cpf).addToBackStack("otp_frag").commit();

                        }else{
//                            Toast.makeText(getActivity(), "Otp is incorrect", Toast.LENGTH_SHORT).show();
                            Log.d("USER","OTP is incorrect");
                        }
                    }
                });
    }

}