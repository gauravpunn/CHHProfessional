package com.cancerhomehealth.chhprofessional.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cancerhomehealth.chhprofessional.R;

public class PatientAssessmentFormFragment extends Fragment implements View.OnClickListener{

    private TextView pbiBtn,ceBtn,rbiBtn,esasBtn,dwprBtn,nfvBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_assessment_form, container, false);
        
        //method for initializing views
        initiateViews(view);

        pbiBtn.setOnClickListener(this::onClick);
        ceBtn.setOnClickListener(this::onClick);
        rbiBtn.setOnClickListener(this::onClick);
        esasBtn.setOnClickListener(this::onClick);
        dwprBtn.setOnClickListener(this::onClick);
        nfvBtn.setOnClickListener(this::onClick);
        
        return view;
    }

    private void initiateViews(View view) {

        pbiBtn = view.findViewById(R.id.pbi_btn);
        ceBtn = view.findViewById(R.id.ce_btn);
        rbiBtn = view.findViewById(R.id.rbi_btn);
        esasBtn = view.findViewById(R.id.esas_btn);
        dwprBtn = view.findViewById(R.id.dwpr_btn);
        nfvBtn = view.findViewById(R.id.nfv_btn);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.pbi_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_container,new PBIFragment()).addToBackStack("paf_frag").commit();
                break;

            case R.id.ce_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_container,new ClinicalExaminationFragment()).addToBackStack("paf_frag").commit();
                break;

            case R.id.rbi_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_container,new RBIFragment()).addToBackStack("paf_frag").commit();
                break;

            case R.id.esas_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_container,new ESASFragment()).addToBackStack("paf_frag").commit();
                break;

            case R.id.dwpr_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_container,new DPRFragment()).addToBackStack("paf_frag").commit();
                break;

            case R.id.nfv_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_container,new NFVFragment()).addToBackStack("paf_frag").commit();
                break;
        }

    }
}