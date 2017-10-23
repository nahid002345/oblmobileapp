package com.app.obl.oblmobileapp.adaptor;

/**
 * Created by ONE BANK 1 on 12/4/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.obl.oblmobileapp.fragment.TabFragAtmList;
import com.app.obl.oblmobileapp.fragment.TabFragAtmMap;

public class AtmMapPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public AtmMapPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragAtmList tab_frag_atm_list= new TabFragAtmList();
                return tab_frag_atm_list;
            case 1:
                TabFragAtmMap tab_frag_atm_map = new TabFragAtmMap();
                return tab_frag_atm_map;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
