package com.example.bloodbankapp;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng you = new LatLng(MainActivity.lat, MainActivity.lng);
        mMap.addMarker(new MarkerOptions().position(you).title("Your Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(you , 15.0f) );


        for(int i=0; i<DonorList.donorInfo.size(); i++){
            Log.d("Donor", String.valueOf(i));


            Donor donor = DonorList.donorInfo.get(i);
            Double lat = Double.valueOf(donor.lat);
            Double lng = Double.valueOf(donor.lan);

            Log.d("Donor", donor.lat);
            Log.d("Donor", donor.lan);


            LatLng donar = new LatLng(lat, lng);
            String donorName = donor.name+ " " + donor.contuctNumber;
            mMap.addMarker(new MarkerOptions().position(donar).title(donorName));
        }
    }
}