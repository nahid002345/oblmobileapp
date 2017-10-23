package com.app.obl.oblmobileapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.app.obl.oblmobileapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Map<LatLng,String> branchLocation= new HashMap<LatLng,String>() {
        {
            put(new LatLng(23.753888, 90.39236024),"ONE Bank Ltd. Corporate HQ");
            put(new LatLng(23.7880925, 90.4162145),"ONE Bank Ltd. Gulshan Branch");
            put(new LatLng(23.793309, 90.4087244),"ONE Bank Ltd. Banani Branch");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(23.753888, 90.39236024);
        for(Map.Entry<LatLng,String> mapCordinateKey : branchLocation.entrySet()){
            mMap.addMarker(new MarkerOptions().position(mapCordinateKey.getKey()).title(mapCordinateKey.getValue()));
        }
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14.0f));
    }
}
