package com.app.obl.oblmobileapp.adaptor;

/**
 * Created by ONE BANK 1 on 12/4/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.obl.oblmobileapp.fragment.TabFragExchangeRateCalculate;
import com.app.obl.oblmobileapp.fragment.TabFragExchangeRateList;

public class ExchangeRatePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ExchangeRatePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragExchangeRateList tab_frag_er_list= new TabFragExchangeRateList();
                return tab_frag_er_list;
            case 1:
                TabFragExchangeRateCalculate tab_frag_er_calculator = new TabFragExchangeRateCalculate();
                return tab_frag_er_calculator;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
