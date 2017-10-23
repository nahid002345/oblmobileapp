package com.app.obl.oblmobileapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.adaptor.CustomDrawerAdapter;
import com.app.obl.oblmobileapp.app.PrefManager;
import com.app.obl.oblmobileapp.fragment.FragmentOne;
import com.app.obl.oblmobileapp.item.DrawerItem;

import java.util.List;

public class MainDrawerTestActivity extends AppCompatActivity {
    public PrefManager mPrefManager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private ArrayAdapter<String> navigationDrawerAdapter;
    //private String[] leftSliderData = {"Home", "Android", "Sitemap", "About", "Contact Me"};

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;

    List<DrawerItem> dataList;


    private Button mLogin;
    private Button mLoanCalculator;
    private Button mMap;
    private ViewFlipper imageSlider;

    private Button mTestPage;
    private Button mATMLocation;

    private Button mContactCenter;
    private Button mExchangeRate;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity mBaseActivity=new BaseActivity();
        mBaseActivity.clearSession(getApplicationContext());
        //clearSession(getApplicationContext());
        setContentView(R.layout.activity_main_drawer_test);

        mLogin=(Button)findViewById(R.id.btn_login_req);
        mLoanCalculator=(Button)findViewById(R.id.btn_calculator);
        mMap=(Button)findViewById(R.id.btn_map);

        mTestPage=(Button)findViewById(R.id.btn_test);
        mATMLocation=(Button)findViewById(R.id.btn_atm_map);
        mContactCenter=(Button)findViewById(R.id.btn_wel_contact_center);
        mExchangeRate=(Button)findViewById(R.id.btn_wel_exchange_rate);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLogin = new Intent(MainDrawerTestActivity.this, LoginActivity.class);
                startActivity(intLogin);
            }
        });

        mLoanCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLoanCal= new Intent(MainDrawerTestActivity.this,LoanCalActivity.class);

                /*Bundle pageParameter = new Bundle();
                pageParameter.putString("TOKENID", "kj3dHpswCj");
                pageParameter.putString("USERID", "4");
                intLoanCal.putExtras(pageParameter);*/

                startActivity(intLoanCal);
            }
        });

        mATMLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intMap= new Intent(MainDrawerTestActivity.this,AtmMapsActivity.class);
                startActivity(intMap);
            }
        });

        mMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intMap= new Intent(MainDrawerTestActivity.this,BranchMapsActivity.class);
                startActivity(intMap);
            }
        });

        mExchangeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intERate= new Intent(MainDrawerTestActivity.this,ExchangeRateActvity.class);
                startActivity(intERate);
            }
        });

        mContactCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intMap= new Intent(MainDrawerTestActivity.this,ContactCenterActivity.class);
                startActivity(intMap);
            }
        });

        mTestPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intTest= new Intent(MainDrawerTestActivity.this,UserDashboardActivity.class);
                startActivity(intTest);*/
                Intent intTest = new Intent(MainDrawerTestActivity.this, TokenActivity.class);
                Bundle pageParameter = new Bundle();
                pageParameter.putString("TOKENID", "xDeeeoEFH6");
                pageParameter.putString("USERID", "1");
                pageParameter.putString("CUSTOMER_MOBILE", "01912006892");
                pageParameter.putString("CUSTOMER_NAME", "TEST USER");
                intTest.putExtras(pageParameter);
                startActivity(intTest);

               /* String INTENT_TAG_CUSTOMER_NO = "CUSTOMERNO";
                String INTENT_TAG_TOKEN_ID = "TOKENID";
                String INTENT_TAG_USERID="USERID";
                String INTENT_ACCOUNTNO = "ACCOUNTNO";

                Intent oUserACActivity = new Intent(getBaseContext(), AcDetailActivity.class);
                Bundle pageParameter = new Bundle();
                pageParameter.putString(INTENT_TAG_TOKEN_ID, "");
                pageParameter.putString(INTENT_TAG_USERID, "1");
                pageParameter.putString(INTENT_TAG_CUSTOMER_NO,"000119844" );
                pageParameter.putString(INTENT_ACCOUNTNO, "0122010001589");
                oUserACActivity.putExtras(pageParameter);
                startActivity(oUserACActivity);*/
            }
        });

        initViewFlipper();

        initiateToolbar();
        //initDrawer();
        //nitView();


    }

    private void initiateToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("WELCOME TO OBL MOBILE APP");
            setSupportActionBar(toolbar);
            //getSupportActionBar().setIcon(R.drawable.ic_toolbar_logo);
            /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);*/
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

/*    private void nitView() {
        dataList = new ArrayList<DrawerItem>();

        *//*dataList.add(new DrawerItem("Message", R.drawable.ic_home));
        dataList.add(new DrawerItem("Likes", R.drawable.ic_drawer));
        dataList.add(new DrawerItem("Games", R.drawable.ic_launcher));*//*

        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                dataList);
        //navigationDrawerAdapter = new ArrayAdapter<String>(MainDrawerTestActivity.this, android.R.layout.simple_list_item_1, leftSliderData);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.drawer_header, leftDrawerList,
                false);
        leftDrawerList.addHeaderView(header, null, false);
        leftDrawerList.setAdapter(adapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
                mDrawerToggle.syncState();
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
                mDrawerToggle.syncState();
            }
        };

    }*/

    private void initViewFlipper()
    {
        imageSlider=(ViewFlipper)findViewById(R.id.view_flipper);
        /*Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_transition_left);
        imageSlider.startAnimation(anim);*/

        imageSlider.startFlipping();
    }
/*    private void initDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }*/

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Do you want to exit?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
}
