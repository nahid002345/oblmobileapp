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
import com.app.obl.oblmobileapp.activity.BranchMapsActivity;
import com.app.obl.oblmobileapp.adaptor.CustomBranchMapListAdaptor;
import com.app.obl.oblmobileapp.helper.CustomWarningAlertDialog;
import com.app.obl.oblmobileapp.helper.BranchLocation;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;

import java.util.ArrayList;

/**
 * Created by ONE BANK 1 on 12/4/2015.
 */
public class TabFragBranchList extends Fragment {
    View view;
    AbsListView list;

    private ViewPager viewPager;

    private static final String INTENT_CUSTOMERNO = "CUSTOMERNO";
    private static final String INTENT_ACCOUNTNO = "ACCOUNTNO";

    ArrayList<BranchLocation> branchLocation;


    private static final String MSG_LOCATION_NOT_FOUND = "Location not found!!!";


    private static String CustomerNo;
    private static String AccountNo;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_branch_loc_list, container, false);
        branchLocation = ((BranchMapsActivity) getActivity()).GetBranchLocation();
        viewPager = (ViewPager) getActivity().findViewById(R.id.br_loc_pager);
        SetListToListView(branchLocation);
        return view;
    }


    private void SetListToListView(final ArrayList<BranchLocation> br_location_list) {
        CustomBranchMapListAdaptor adapter = new CustomBranchMapListAdaptor(getActivity(), br_location_list);
        list = (AbsListView) view.findViewById(R.id.lv_br_loc_list);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);

        animationAdapter.setAbsListView(list);
        list.setFastScrollAlwaysVisible(true);
        list.setFastScrollEnabled(true);
        list.setAdapter(animationAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (br_location_list.get(position).branch_latitude != null
                        && br_location_list.get(position).branch_longitude != null
                        && !br_location_list.get(position).branch_latitude.isEmpty()
                        && !br_location_list.get(position).branch_longitude.isEmpty()) {
                    TabFragBranchMap mMapFrag = new TabFragBranchMap();
                    Bundle args = new Bundle();

                    args.putFloat(TabFragBranchMap.TAG_SELECTED_LATITUDE, Float.valueOf(br_location_list.get(position).branch_latitude));
                    args.putFloat(TabFragBranchMap.TAG_SELECTED_LONGITUDE, Float.valueOf(br_location_list.get(position).branch_longitude));

                    mMapFrag.setArguments(args);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.br_mapview, mMapFrag);
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
