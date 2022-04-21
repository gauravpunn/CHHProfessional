package com.cancerhomehealth.chhprofessional.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.cancerhomehealth.chhprofessional.Model.Professionals;
import com.cancerhomehealth.chhprofessional.R;

import java.util.ArrayList;

public class SelectProfFragment extends Fragment {

    //TextInputLayout for professional selection
    private TextInputLayout selectUserTIL;
    private AutoCompleteTextView selectUserACT;
    //String for getting selected User
    private String professional = null;

    //ArrayList for getting array of professionals;
    private ArrayList<String> professionals;

    //Button for continuing to next screen with selection
    private Button selectUserBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_prof, container, false);

        //initializing views
        selectUserTIL = view.findViewById(R.id.select_user_til);
        selectUserACT = view.findViewById(R.id.select_user_act);
        selectUserBtn = view.findViewById(R.id.select_user_btn);

        //Fetching professional arrayList from Professionals Class
        professionals = Professionals.getProfessionals();

        //Array Adapter for showing Professionals list in DropDown
        ArrayAdapter<String> professionalAdapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,professionals);
        selectUserACT.setAdapter(professionalAdapter);

        //setOnItemClickListener on DropDown for getting the selected user type
        selectUserACT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                professional = parent.getItemAtPosition(position).toString().trim();

                if (position >= 0){
                    selectUserTIL.setError("");
                }else {
                    selectUserTIL.setError("Please select user type");
                }
            }
        });

        //setOnClickListener on Button for continuing with selection
        selectUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (professional.isEmpty() || professional == null){
                        selectUserTIL.setError("Please Select Valid User Type");
                    }else {
                        selectUserTIL.setError("");
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.login_container,new LoginFragment()).addToBackStack("select_prof_frag").commit();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    selectUserTIL.setError("Please Select Valid User Type");
                }

            }
        });


        return view;
    }


}