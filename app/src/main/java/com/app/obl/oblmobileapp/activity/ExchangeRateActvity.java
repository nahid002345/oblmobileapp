package com.app.obl.oblmobileapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.adaptor.ExchangeRatePagerAdapter;
import com.app.obl.oblmobileapp.helper.ExchangeRate;

import java.util.ArrayList;

public class ExchangeRateActvity extends AppCompatActivity {
    private static final String TOOLBAR_TITLE = "Exchange Rate";
    private static final String TAB_NAME_LIST = "LIST";
    private static final String TAB_NAME_CALCULATER = "CALCULATER";
    public static ArrayList<ExchangeRate> exchangeRateList;
    public static String er_request_date;

    private TabLayout tabLayout;
    private Toolbar mToolbar;
    private TextView mExRateDate;
    private Button mExRateUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate_actvity);
        mExRateDate = (TextView) findViewById(R.id.txt_header_exchange_rate);
        mExRateUpdate=(Button)findViewById(R.id.btn_er_update);

        mExRateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_er_update = getIntent();
                finish();
                startActivity(int_er_update);
            }
        });

        SetTitleBar();
        SetTab();

    }

    public void SetExchangeRate(ArrayList<ExchangeRate> exchange_rate_list) {
        if(exchange_rate_list != null) {
            exchangeRateList = exchange_rate_list;
            er_request_date=exchange_rate_list.get(0).er_date;
            SetExchangeRateHeader();
        }
    }

    private void SetExchangeRateHeader()
    {
        if(er_request_date != null && !er_request_date.isEmpty()) {
            mExRateDate.setText(er_request_date);
        }
    }

    public ArrayList<ExchangeRate> GetExchangeRate(){
        return exchangeRateList;
    }



    private void SetTab()
    {
        tabLayout = (TabLayout) findViewById(R.id.tab_xchg_rate_layout);
        tabLayout.addTab(tabLayout.newTab().setText(TAB_NAME_LIST));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_NAME_CALCULATER));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_list_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_er_result);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.xchg_rate_pager);
        final ExchangeRatePagerAdapter adapter = new ExchangeRatePagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    private void SetTitleBar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(TOOLBAR_TITLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setIcon(R.drawable.ic_toolbar_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
