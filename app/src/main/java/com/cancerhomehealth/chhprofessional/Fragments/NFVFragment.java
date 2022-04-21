package com.cancerhomehealth.chhprofessional.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.cancerhomehealth.chhprofessional.R;

public class NFVFragment extends Fragment {

    private CalendarView calendarView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_n_f_v, container, false);

        //initiating views
        initiateView(view);

        //Calendar View onS
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i2+"/"+(i1 +1)+"/"+i;
                Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initiateView(View view) {

        calendarView = view.findViewById(R.id.calendar_view);

    }
}