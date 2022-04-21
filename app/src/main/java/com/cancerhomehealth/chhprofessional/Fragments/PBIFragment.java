package com.cancerhomehealth.chhprofessional.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cancerhomehealth.chhprofessional.R;

public class PBIFragment extends Fragment implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_p_b_i, container, false);

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