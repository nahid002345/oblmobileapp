package com.app.obl.oblmobileapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;

public class UnderProcessActivity extends BaseActivity {

    private static final String TOOLBAR_TITLE = "Under Process...";
    private static final String TAG_UNDER_PROCESS_MSG = "MSG";


    private Toolbar mToolbar;
    private Button mBack;
    private TextView mUnderProcessMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_process);
        mUnderProcessMsg=(TextView)findViewById(R.id.txt_uprocess_msg);

        mBack=(Button)findViewById(R.id.btn_go_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initiateToolbar();

        if(getIntent() == null) {
            Intent upIntent = getIntent();
            String msg = upIntent.getStringExtra(TAG_UNDER_PROCESS_MSG);
            if (!msg.isEmpty()) {
                mUnderProcessMsg.setText(msg);
            }
        }
    }

    private void initiateToolbar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(TOOLBAR_TITLE);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setIcon(R.drawable.ic_toolbar_logo);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
