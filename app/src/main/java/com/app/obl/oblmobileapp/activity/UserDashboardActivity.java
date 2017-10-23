package com.app.obl.oblmobileapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.adaptor.CustomDrawerAdapter;
import com.app.obl.oblmobileapp.app.PrefManager;
import com.app.obl.oblmobileapp.fragment.FragmentOne;
import com.app.obl.oblmobileapp.item.DrawerItem;

import java.util.ArrayList;
import java.util.List;

public class UserDashboardActivity extends BaseActivity {
    public PrefManager mPrefManager;
    private static String CustomerNo;
    private static String TokenId;
    private static String UserId,CustomerName,CustomerMobile;

    private static final String INTENT_CUSTOMERNO = "CUSTOMERNO";
    private static final String INTENT_ACCOUNTNO = "ACCOUNTNO";

    private static final String INTENT_TAG_CUSTOMER_NAME = "CUSTOMER_NAME";
    private static final String INTENT_TAG_CUSTOMER_MOBILE = "CUSTOMER_MOBILE";
    private static final String INTENT_TAG_CUSTOMER_NO = "CUSTOMER_NO";
    private static final String INTENT_TAG_TOKEN_ID = "TOKENID";
    private static final String INTENT_TAG_USERID="USERID";

    private Toolbar toolbar;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;

    List<DrawerItem> dataList;

    private DrawerLayout drawerLayout;
    private ListView leftDrawerList;

    private Button mAccount;
    private Button mLoanAc;
    private Button mCreditCard;
    private Button mFundTransfer;
    private Button mBillPay;
    private Button mBranchLocation;
    private Button mATMLocation;
    private Button mCallCenter;
    private Button mExchangeRate;
    private Button mLogout;
    private Button mLoanCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        mPrefManager=new PrefManager(getApplicationContext());
        checkSessionLogin(getApplicationContext());
        //if(mPrefManager.isLoggedIn()) {
        Intent tokenIntent = getIntent();
        TokenId = tokenIntent.getStringExtra(INTENT_TAG_TOKEN_ID);
        CustomerNo = tokenIntent.getStringExtra(INTENT_TAG_CUSTOMER_NO);
        UserId = tokenIntent.getStringExtra(INTENT_TAG_USERID);

        CustomerName = tokenIntent.getStringExtra(INTENT_TAG_CUSTOMER_NAME);
        CustomerMobile = tokenIntent.getStringExtra(INTENT_TAG_CUSTOMER_MOBILE);
            initiateToolbar();
            nitView();
            DashboardButtonInit();


        //}

//        TokenId="";
//        CustomerNo="012083232";
//        UserId="4";

    }

    private void DashboardButtonInit()
    {
        mAccount=(Button)findViewById(R.id.btn_accounts);
        mLoanAc=(Button)findViewById(R.id.btn_loan_account);
        mCreditCard=(Button)findViewById(R.id.btn_card_credit);
        mFundTransfer=(Button)findViewById(R.id.btn_fund_transfer);
        mBillPay=(Button)findViewById(R.id.btn_bill_payment);
        mBranchLocation=(Button)findViewById(R.id.btn_branch_loc);
        mATMLocation=(Button)findViewById(R.id.btn_atm_loc);
        mCallCenter=(Button)findViewById(R.id.btn_contact_center);
        mExchangeRate=(Button)findViewById(R.id.btn_exchange_rate);
        mLogout=(Button)findViewById(R.id.btn_logout);
        mLoanCalculator=(Button)findViewById(R.id.btn_loan_calculator);

        mAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oUserACActivity = new Intent(getBaseContext(), UserAccountActivity.class);
                Bundle pageParameter = new Bundle();
                pageParameter.putString(INTENT_TAG_TOKEN_ID, TokenId);
                pageParameter.putString(INTENT_TAG_USERID, UserId);
                pageParameter.putString(INTENT_TAG_CUSTOMER_NO, CustomerNo);
                oUserACActivity.putExtras(pageParameter);
                startActivity(oUserACActivity);
            }
        });

        mLoanAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oOnProcessActivity = new Intent(getBaseContext(), UnderProcessActivity.class);
                startActivity(oOnProcessActivity);
            }
        });

        mCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UserId = "1";
                Intent oUserCardActivity = new Intent(getBaseContext(), UserCardListActivity.class);
                Bundle pageParameter = new Bundle();
                //pageParameter.putString(INTENT_TAG_TOKEN_ID, TokenId);
                pageParameter.putString(INTENT_TAG_USERID, UserId);
                pageParameter.putString(INTENT_TAG_CUSTOMER_NO, CustomerNo);
                oUserCardActivity.putExtras(pageParameter);
                startActivity(oUserCardActivity);
            }
        });

        mFundTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oOnProcessActivity = new Intent(getBaseContext(), UnderProcessActivity.class);
                startActivity(oOnProcessActivity);
            }
        });

        mBillPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oOnProcessActivity = new Intent(getBaseContext(), UnderProcessActivity.class);
                startActivity(oOnProcessActivity);
            }
        });



        mBranchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oOnProcessActivity = new Intent(getBaseContext(), BranchMapsActivity.class);
                startActivity(oOnProcessActivity);
            }
        });

        mATMLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oOnProcessActivity = new Intent(getBaseContext(), AtmMapsActivity.class);
                startActivity(oOnProcessActivity);
            }
        });

        mCallCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intContactCenter = new Intent(getBaseContext(), ContactCenterActivity.class);
                startActivity(intContactCenter);
            }
        });

        mExchangeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intContactCenter = new Intent(getBaseContext(), ExchangeRateActvity.class);
                startActivity(intContactCenter);
            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutSession(getApplicationContext());
                Toast.makeText(UserDashboardActivity.this, "Logout Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mLoanCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLoanCalc = new Intent(getBaseContext(), LoanCalActivity.class);
                startActivity(intLoanCalc);
            }
        });
    }

    private void selectNavDrawerItem(int position){
        Intent intSelectedItem = null;
        Bundle pageParameter=null;

        switch (position){
            case 1:
                intSelectedItem = new Intent(getBaseContext(), UserAccountActivity.class);
                pageParameter = new Bundle();
                pageParameter.putString(INTENT_TAG_TOKEN_ID, TokenId);
                pageParameter.putString(INTENT_TAG_USERID, UserId);
                pageParameter.putString(INTENT_TAG_CUSTOMER_NO, CustomerNo);
                intSelectedItem.putExtras(pageParameter);
                startActivity(intSelectedItem);
                break;
            case 2:
                intSelectedItem = new Intent(getBaseContext(), UserCardListActivity.class);
                pageParameter = new Bundle();
                //pageParameter.putString(INTENT_TAG_TOKEN_ID, TokenId);
                pageParameter.putString(INTENT_TAG_USERID, UserId);
                pageParameter.putString(INTENT_TAG_CUSTOMER_NO, CustomerNo);
                intSelectedItem.putExtras(pageParameter);
                startActivity(intSelectedItem);
                break;
            case 3:
                intSelectedItem = new Intent(getBaseContext(), LoanCalActivity.class);
                startActivity(intSelectedItem);
                break;
            case 4:
                intSelectedItem = new Intent(getBaseContext(), ExchangeRateActvity.class);
                startActivity(intSelectedItem);
                break;
            case 5:
                intSelectedItem = new Intent(getBaseContext(), BranchMapsActivity.class);
                startActivity(intSelectedItem);
                break;
            case 6:
                intSelectedItem = new Intent(getBaseContext(), AtmMapsActivity.class);
                startActivity(intSelectedItem);
                break;
            case 7:
                intSelectedItem = new Intent(getBaseContext(), ContactCenterActivity.class);
                startActivity(intSelectedItem);
                break;
            case 8:
                logOutSession(getApplicationContext());
                Toast.makeText(UserDashboardActivity.this, "Logout Button Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void SelectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 0:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());
                break;

            default:
                break;
        }

        fragment.setArguments(args);
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                .commit();

        mDrawerList.setItemChecked(possition, true);
        setTitle(dataList.get(possition).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    private void nitView() {
        dataList = new ArrayList<DrawerItem>();
        DrawerItem nav_ac= new DrawerItem("ACCOUNT",R.drawable.ic_account_profile);
        DrawerItem nav_card= new DrawerItem("CARD",R.drawable.ic_credit_card);
        DrawerItem nav_loan= new DrawerItem("LOAN CALCULATOR",R.drawable.ic_loan_calculator);
        DrawerItem nav_er= new DrawerItem("EXCHANGE RATE",R.drawable.ic_exchange_rate);
        DrawerItem nav_br= new DrawerItem("BRANCH LOCATION",R.drawable.ic_branch_location);
        DrawerItem nav_atm= new DrawerItem("ATM LOCATION",R.drawable.ic_atm_location);
        DrawerItem nav_ccenter= new DrawerItem("CONTACT CENTER",R.drawable.ic_contact_center);
        DrawerItem nav_logout= new DrawerItem("LOG OUT",R.drawable.ic_logout_red);

        dataList.add(nav_ac);
        dataList.add(nav_card);
        dataList.add(nav_loan);
        dataList.add(nav_er);
        dataList.add(nav_br);
        dataList.add(nav_atm);
        dataList.add(nav_ccenter);
        dataList.add(nav_logout);


        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                dataList);
        //navigationDrawerAdapter = new ArrayAdapter<String>(MainDrawerTestActivity.this, android.R.layout.simple_list_item_1, leftSliderData);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header_drawer, leftDrawerList,
                false);
        TextView mCustomerName=(TextView)header.findViewById(R.id.txt_drawer_user_id);
        TextView mCustomerMobile=(TextView)header.findViewById(R.id.txt_drawer_user_mobile);
        mCustomerName.setText(CustomerName);
        mCustomerMobile.setText(CustomerMobile);

        leftDrawerList.addHeaderView(header, null, false);
        leftDrawerList.setAdapter(adapter);

        leftDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectNavDrawerItem(position);
                Toast.makeText(UserDashboardActivity.this,"clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to
                mDrawerToggle.syncState();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to
                mDrawerToggle.syncState();
            }
        };
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

    }
    private void initiateToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(" Dashboard");
            setSupportActionBar(toolbar);
            getSupportActionBar().setIcon(R.drawable.ic_toolbar_logo);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (id == R.id.action_logout) {
            logOutSession(getApplicationContext());
            Toast.makeText(UserDashboardActivity.this, "Logout Button Clicked", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
