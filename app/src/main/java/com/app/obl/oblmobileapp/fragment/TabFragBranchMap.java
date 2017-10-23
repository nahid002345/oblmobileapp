package com.app.obl.oblmobileapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.activity.BranchMapsActivity;
import com.app.obl.oblmobileapp.helper.BranchLocation;
import com.app.obl.oblmobileapp.helper.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by ONE BANK 1 on 12/4/2015.
 */
public class TabFragBranchMap extends Fragment {
    MapView mMapView;
    private GoogleMap gMap;
    private ArrayList<BranchLocation> branchLocation;

    public static final String TAG_SELECTED_LONGITUDE = "LONGITUDE";
    public static final String TAG_SELECTED_LATITUDE = "LATITUDE";

    public float selected_place_long,selected_place_lat;
    private CameraPosition cameraPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle selectedBundle=getArguments();
        if(selectedBundle != null)
        {
            selected_place_long=selectedBundle.getFloat(TAG_SELECTED_LONGITUDE);
            selected_place_lat=selectedBundle.getFloat(TAG_SELECTED_LATITUDE);
        }
        View v = inflater.inflate(R.layout.tab_fragment_branch_loc_map, container,false);
        branchLocation = ((BranchMapsActivity) getActivity()).GetBranchLocation();
        try {
            mMapView = (MapView) v.findViewById(R.id.br_mapview);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume();// needed to get the map to display immediately

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    gMap = googleMap;
                }
            });

            try {
                MapsInitializer.initialize(getActivity().getBaseContext());
                SetMapMarker();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (StackOverflowError exception) {
            exception.printStackTrace();
        }

        // Perform any camera updates here
        return v;
    }

    private void SetMapMarker()
    {

        gMap = mMapView.getMap();
        int listSize= branchLocation.size();
        String strLat,strLong,strBrName,strBrAdd;
        double dbLat,dbLong;
        MarkerOptions marker;
        for(int loop=0; loop < listSize ; loop++) {
            strLat=branchLocation.get(loop).branch_latitude;
            strLong=branchLocation.get(loop).branch_longitude;
            if (strLat != null && strLong != null && !strLat.isEmpty() && !strLong.isEmpty())
            {
                strBrAdd=branchLocation.get(loop).branch_address;
                strBrName=branchLocation.get(loop).branch_name;
                dbLat=Double.parseDouble(strLat);
                dbLong=Double.parseDouble(strLong);
                marker = new MarkerOptions().position(new LatLng(dbLat, dbLong)).title(strBrName).snippet(strBrAdd);
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_branch_location_pin));
                gMap.addMarker(marker);
            }
        }
        GPSTracker mGPSTracker=new GPSTracker(getContext());
        if(mGPSTracker.location != null)
        {
            LatLng currentLocation= new LatLng(mGPSTracker.location.getLatitude(),mGPSTracker.location.getLongitude());
            gMap.addMarker(new MarkerOptions().position(currentLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user_position)).title(getString(R.string.string_user_current_location_tag)));
        }
        if(selected_place_lat != 0 && selected_place_long != 0)
        {
            cameraPosition = new CameraPosition.Builder().target(new LatLng(selected_place_lat, selected_place_long)).zoom(15).build();
        }
        else {
            if(mGPSTracker.location == null)
                cameraPosition = new CameraPosition.Builder().target(new LatLng(23.754106, 90.392418)).zoom(12).build();
            else
                cameraPosition = new CameraPosition.Builder().target(new LatLng(mGPSTracker.getLatitude(),mGPSTracker.getLongitude())).zoom(15).build();


        }
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }

}
