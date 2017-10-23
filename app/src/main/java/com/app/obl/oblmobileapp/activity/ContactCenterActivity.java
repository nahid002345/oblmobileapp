package com.app.obl.oblmobileapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.app.obl.oblmobileapp.R;

public class ContactCenterActivity extends AppCompatActivity {
    private static final String TOOLBAR_TITLE = "Contact Center";
    private Toolbar mToolbar;
    private Button mCallBtn;
    private FloatingActionButton mFloatingCallBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_center);
        mCallBtn=(Button)findViewById(R.id.btn_call_primary);
        mFloatingCallBtn=(FloatingActionButton)findViewById(R.id.fab_btn_call_center);
        mCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallToContactCenter();
            }
        });
        mFloatingCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallToContactCenter();
            }
        });
        SetTitleBar();
    }

    private void CallToContactCenter()
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:16269"));
        startActivity(callIntent);
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
