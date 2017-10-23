package com.app.obl.oblmobileapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.activity.AtmMapsActivity;
import com.app.obl.oblmobileapp.adaptor.CustomAtmMapListAdaptor;
import com.app.obl.oblmobileapp.helper.AtmLocation;
import com.app.obl.oblmobileapp.helper.CustomWarningAlertDialog;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;

import java.util.ArrayList;

/**
 * Created by ONE BANK 1 on 12/4/2015.
 */
public class TabFragAtmList extends Fragment {
    View view;
    AbsListView list;
    ArrayList<AtmLocation> atmLocation;
    private ViewPager viewPager;
    private static final String MSG_LOCATION_NOT_FOUND = "Location not found!!!";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_atm_loc_list, container, false);
        atmLocation = ((AtmMapsActivity) getActivity()).GetAtmLocation();
        viewPager = (ViewPager) getActivity().findViewById(R.id.atm_loc_pager);
        SetListToListView(atmLocation);
        return view;
    }


    private void SetListToListView(final ArrayList<AtmLocation> atm_location_list) {
        CustomAtmMapListAdaptor adapter = new CustomAtmMapListAdaptor(getActivity(), atm_location_list);
        list = (AbsListView) view.findViewById(R.id.lv_atm_loc_list);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);

        animationAdapter.setAbsListView(list);
        list.setFastScrollAlwaysVisible(true);
        list.setFastScrollEnabled(true);
        list.setAdapter(animationAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (atm_location_list.get(position).atm_latitude != null
                && atm_location_list.get(position).atm_longitude != null
                &&!atm_location_list.get(position).atm_latitude.isEmpty()
                && !atm_location_list.get(position).atm_longitude.isEmpty()) {
                    TabFragAtmMap mMapFrag = new TabFragAtmMap();
                    Bundle args = new Bundle();

                    args.putFloat(TabFragAtmMap.TAG_SELECTED_LATITUDE, Float.valueOf(atm_location_list.get(position).atm_latitude));
                    args.putFloat(TabFragAtmMap.TAG_SELECTED_LONGITUDE, Float.valueOf(atm_location_list.get(position).atm_longitude));


                    mMapFrag.setArguments(args);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.atm_mapview, mMapFrag);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    viewPager.setCurrentItem(1);
                }
                else
                {
                    CustomWarningAlertDialog alert = new CustomWarningAlertDialog();
                    alert.showDialog(getActivity(),MSG_LOCATION_NOT_FOUND );
                }
            }
        });
    }


}
