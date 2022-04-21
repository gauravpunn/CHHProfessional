package com.cancerhomehealth.chhprofessional.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cancerhomehealth.chhprofessional.R;


public class PasswordSaveFragment extends Fragment implements View.OnClickListener {

    //Button for Login Fragment
    private Button loginFragBtn;
    private ImageView passSaveBackBtn;

    private TextView titleET,messageET;

    private String title;
    private String message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_password_save, container, false);

        //Method for Initializing View
        initiateView(view);

        try {
            title = getArguments().getString("title");
            message = getArguments().getString("message");

            titleET.setText(title);
            messageET.setText(message);
        }catch (Exception e){
            e.printStackTrace();
        }



        //setOnClickListener Implementation
        loginFragBtn.setOnClickListener(this::onClick);
        passSaveBackBtn.setOnClickListener(this::onClick);

        return view;
    }

    private void initiateView(View view) {

        titleET = view.findViewById(R.id.title_et);
        messageET = view.findViewById(R.id.message_et);

        loginFragBtn = view.findViewById(R.id.login_frag_btn);
        passSaveBackBtn = view.findViewById(R.id.pass_save_back_btn);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.login_frag_btn:
                getParentFragmentManager().popBackStack("login_frag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;

            case R.id.pass_save_back_btn:
                getParentFragmentManager().popBackStack("for_pass_frag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
        }

    }
}