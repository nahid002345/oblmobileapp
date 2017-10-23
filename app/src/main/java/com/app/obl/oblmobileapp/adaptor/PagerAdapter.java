package com.app.obl.oblmobileapp.adaptor;

/**
 * Created by ONE BANK 1 on 12/4/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.obl.oblmobileapp.fragment.TabFragAcDetail;
import com.app.obl.oblmobileapp.fragment.TabFragTranHistory;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragAcDetail tab_frag_ac_detail= new TabFragAcDetail();
                return tab_frag_ac_detail;
            case 1:
                TabFragTranHistory tab_frag_tran_history = new TabFragTranHistory();
                return tab_frag_tran_history;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
