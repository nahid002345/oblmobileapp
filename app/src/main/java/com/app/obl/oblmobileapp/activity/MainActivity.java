package com.app.obl.oblmobileapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.app.obl.oblmobileapp.fragment.FragmentDrawer;
import com.app.obl.oblmobileapp.R;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private ViewFlipper imageSlider;

    private FragmentDrawer drawerFragment;

    private Button mLogin;
    private Button mLoanCalculator;
    private Button mMap;

    private Button mTestPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogin=(Button)findViewById(R.id.btn_login_req);
        mLoanCalculator=(Button)findViewById(R.id.btn_calculator);
        mMap=(Button)findViewById(R.id.btn_map);
        mTestPage=(Button)findViewById(R.id.btn_test);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Welcome");

        drawerFragment = (FragmentDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_toolbar_logo);
/*        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/

        imageSlider=(ViewFlipper)findViewById(R.id.view_flipper);
        /*Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_transition_left);
        imageSlider.startAnimation(anim);*/

        imageSlider.startFlipping();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLogin = new Intent(MainActivity.this, LoginActivity.class);
                //Bundle pageParameter= new Bundle();
                //pageParameter.putString("tokenId","SYyiwArDWF");
                //pageParameter.putString("UserId", "1");
                //intLogin.putExtras(pageParameter);
                //startActivity(oTPContext);
                startActivity(intLogin);
            }
        });

        mLoanCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLoanCal= new Intent(MainActivity.this,LoanCalActivity.class);
                startActivity(intLoanCal);
            }
        });

        mMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intMap= new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intMap);
            }
        });

        mTestPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intTest= new Intent(MainActivity.this,TestPageActivity.class);
                Bundle pageParameter= new Bundle();
                pageParameter.putString("tokenId","SYyiwArDWF");
                pageParameter.putString("CustNo", "012081995");
                intTest.putExtras(pageParameter);
                startActivity(intTest);
            }
        });

    }


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
                        System.exit(0);
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.home)
        {
            Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG);
        }
        //noinspection SimplifiableIfStatement
/*        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //setContentView(R.layout.image_flipper);
        imageSlider = (ViewFlipper) findViewById(R.id.view_flipper);
    }
}


