package com.app.obl.oblmobileapp.adaptor;

/**
 * Created by ONE BANK 1 on 12/4/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.obl.oblmobileapp.fragment.TabFragBranchList;
import com.app.obl.oblmobileapp.fragment.TabFragBranchMap;

public class BranchMapPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public BranchMapPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragBranchList tab_frag_branch_list= new TabFragBranchList();
                return tab_frag_branch_list;
            case 1:
                TabFragBranchMap tab_frag_branch_map = new TabFragBranchMap();
                return tab_frag_branch_map;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
