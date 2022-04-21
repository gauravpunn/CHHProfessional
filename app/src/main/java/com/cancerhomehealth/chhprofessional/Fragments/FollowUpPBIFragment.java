package com.cancerhomehealth.chhprofessional.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cancerhomehealth.chhprofessional.R;

public class FollowUpPBIFragment extends Fragment implements View.OnClickListener {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_up_p_b_i, container, false);

        //Initializing Views
        initiateViews(view);


        return view;
    }

    private void initiateViews(View view) {
    }

    @Override
    public void onClick(View view) {

    }
}
