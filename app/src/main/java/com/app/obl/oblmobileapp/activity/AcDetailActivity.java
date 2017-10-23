package com.app.obl.oblmobileapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.adaptor.PagerAdapter;

public class AcDetailActivity extends BaseActivity {

    ListView list;
    private static final String TOOLBAR_TITLE = "Detail Information";
    private static String CustomerNo;
    private static String AccountNo;
    private static final String INTENT_CUSTOMERNO = "CUSTOMERNO";
    private static final String INTENT_ACCOUNTNO = "ACCOUNTNO";
    // JSON Node names
    private TextView mCustomerNo;

    private TabLayout tabLayout;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_detail);
        Intent tokenIntent=getIntent();
        CustomerNo=tokenIntent.getStringExtra(INTENT_CUSTOMERNO);
        AccountNo=tokenIntent.getStringExtra(INTENT_ACCOUNTNO);
        SetTitleBar();
        SetTab();
    }

    private void SetTab()
    {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Detail"));
        tabLayout.addTab(tabLayout.newTab().setText("Transactions"));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_accounts);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tran_summary);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
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

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
