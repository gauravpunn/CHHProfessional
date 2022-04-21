package com.cancerhomehealth.chhprofessional.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cancerhomehealth.chhprofessional.Activities.LoginActivity;
import com.cancerhomehealth.chhprofessional.SharedPreferences.SharedPrefManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.cancerhomehealth.chhprofessional.Model.Constants;
import com.cancerhomehealth.chhprofessional.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class DashboardFragment extends Fragment implements View.OnClickListener {


    //Top Bar View
    private TextView topBarName,topBarLocation;

    private CardView pafBtn,fufBtn;

    //for showing drawer menu
    private DrawerLayout accountDrawer;
    private NavigationView accountNav;
    private ImageView accountMenu;

    //Header Buttons
    private TextView logoutBtn;

    //back button for closing the drawer
    private ImageView headerMenuBack;

    private SharedPrefManager sharedPrefManager;

    //request code for Location Permission
    private final int PERMISSION_REQUEST_CODE = 1001;

    //Declaring FusedLocationProviderClient for getting the location
    private FusedLocationProviderClient mFusedLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefManager = new SharedPrefManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        //method for initializing view
        initiateView(view);

        Log.i("USERS",sharedPrefManager.getUserData().getName());

        topBarName.setText(sharedPrefManager.getUserData().getName());

        mFusedLocation = LocationServices.getFusedLocationProviderClient(getActivity());

//        saleSearchView.clearFocus();
//        saleSearchView.setQueryHint("Search patients");

        //SetOnClickListener on Button
        pafBtn.setOnClickListener(this::onClick);
        fufBtn.setOnClickListener(this::onClick);
        accountMenu.setOnClickListener(this::onClick);
        headerMenuBack.setOnClickListener(this::onClick);
        logoutBtn.setOnClickListener(this::onClick);


        getLastLocation();


       return view;
    }

    private void initiateView(View view) {

        topBarName = view.findViewById(R.id.top_bar_name);
        topBarLocation = view.findViewById(R.id.top_bar_location);

        pafBtn = view.findViewById(R.id.paf_btn);
        fufBtn = view.findViewById(R.id.fuf_btn);
        accountMenu = view.findViewById(R.id.account_menu);

        accountDrawer = view.findViewById(R.id.account_drawer);
        accountNav = view.findViewById(R.id.account_nav);
        View headerView = accountNav.getHeaderView(0);
        headerMenuBack = headerView.findViewById(R.id.header_menu_back);
        logoutBtn = headerView.findViewById(R.id.logout_btn);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.paf_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_container,new PatientAssessmentFormFragment()).addToBackStack(Constants.DASHBOARD).commit();
                break;

            case R.id.fuf_btn:
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_container,new FollowUpConsultationFragment()).addToBackStack(Constants.DASHBOARD).commit();

            case R.id.account_menu:
                accountDrawer.openDrawer(GravityCompat.START);
                break;

            case R.id.header_menu_back:
                accountDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.logout_btn:
                logout();
                break;
        }



    }

    private void logout() {

        sharedPrefManager.logout();
        Intent login = new Intent(getActivity(), LoginActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(login);

    }

    //method for fetching the last location
    @SuppressLint("MissingPermission")
    private void getLastLocation() {

        if (checkPermissions()){

            if (isLocationEnabled()){

                mFusedLocation.getLastLocation()
                        .addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null){
                                    requestNewLocationData();
                                }else {
                                    try {
                                        convertToAddress(location.getLatitude(),location.getLongitude());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
            }else {
                Toast.makeText(getActivity(), "Please turn on your Location", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }else {

            //calling requestPermissions if permission is not granted
            requestPermissions();
        }
    }

    //Requesting for new location
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5);
        locationRequest.setInterval(0);
        locationRequest.setNumUpdates(1);

        mFusedLocation = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocation.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
    }

    //Location callback
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location mlocation = locationResult.getLastLocation();
            try {
                convertToAddress(mlocation.getLatitude(),mlocation.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    //Calling Geocoder for getting location name from the coordinates
    private void convertToAddress(double myLat, double myLong) throws IOException {

        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(myLat,myLong,1);
        String cityName = addresses.get(0).getLocality();

        topBarLocation.setText(cityName);
    }

    //checking location permission was granted or not
    private boolean checkPermissions(){

        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    //sending runtime permission request
    private void requestPermissions(){
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_CODE);
    }

    //checking GPS is enabled or not
    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //after requesting the permission on runtime and passing to the onRequestPermissionResult for the results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getLastLocation();
        }else {

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()){
            getLastLocation();
        }
    }
}