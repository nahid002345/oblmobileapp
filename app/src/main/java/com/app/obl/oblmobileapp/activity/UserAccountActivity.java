package com.app.obl.oblmobileapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.adaptor.CustomAcListAdaptor;
import com.app.obl.oblmobileapp.listener.AsyncTaskCompleteListener;
import com.app.obl.oblmobileapp.service.CheckNetwork;
import com.app.obl.oblmobileapp.service.UrlHTTPSRequest;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class UserAccountActivity extends BaseActivity implements AsyncTaskCompleteListener<JSONObject> {
    AbsListView list;

    //public static String RequestUrl = "https://10.156.2.19/oblmapp/api/Account/GetFCUBSCustomerInfo/";
    public static String RequestUrl = "https://203.188.255.116/oblmapp/api/Account/GetFCUBSCustomerInfo/";
    private static String CustomerNo;
    private static String tokenId;
    private JSONObject jsonResponse = new JSONObject();

    private static final String INTENT_TOKENID = "TOKENID";
    private static final String INTENT_CUSTOMERNO = "CUSTOMERNO";
    private static final String INTENT_ACCOUNTNO = "ACCOUNTNO";

    private static final String INTENT_TAG_CUSTOMER_NO = "CUSTOMER_NO";
    // JSON Node names
    private static final String TAG_STATUS = "STATUS";
    private static final String TAG_REQMSG = "REQMSG";
    private static final String TAG_ACLIST = "ACLIST";
    private static final String TAG_CUSTOMERNO = "CUSTOMERNO";
    private static final String TAG_ACNO = "ACNO";
    private static final String TAG_ACTYPE = "ACTYPE";
    private static final String TAG_ACOPENDATE = "ACOPENDATE";
    private static final String TAG_ACNAME = "ACNAME";
    private static final String TAG_ACAVAILBAL = "ACAVAILBAL";


    private static String[] ac_no_list = new String[0];
    private static String[] ac_name_list = new String[0];
    private static String[] ac_bal_list = new String[0];


    private Toolbar toolbar;

    private TextView mCustomerNo;
    private TextView mAccountCount;
    private View mPageLayout;
    private View mMsgLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        checkSessionLogin(getApplicationContext());

        Intent tokenIntent = getIntent();
         tokenId= super.getTokenId();
        CustomerNo = tokenIntent.getStringExtra(INTENT_TAG_CUSTOMER_NO);
        initiateToolbar();
        try {
            if(CheckNetwork.isConnectingToInternet(getApplicationContext(),UserAccountActivity.this)) {
                GetACRequest();
            }
            else
            {
                mMsgLayout=(View)findViewById(R.id.rel_ac_list_msg);
                mMsgLayout.setVisibility(View.VISIBLE);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initiateToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setIcon(R.drawable.ic_toolbar_logo);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }


    private void GetACRequest() throws ExecutionException, InterruptedException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("TokenId", tokenId));
        params.add(new BasicNameValuePair("CustNo", CustomerNo));
        String paramUrl;
        String paramOnly = "";
        for (NameValuePair oNamePair : params) {
            paramOnly += oNamePair.getValue() + '/';
        }
        paramUrl = RequestUrl + paramOnly;
        UrlHTTPSRequest oUrlRequest = new UrlHTTPSRequest(UserAccountActivity.this, this);

        oUrlRequest.execute(paramUrl);
    }


    @Override
    public void onTaskComplete(JSONObject jResponse) {
        jsonResponse = jResponse;
        try {
            boolean Status = (jsonResponse.get(TAG_STATUS).toString() == "true" && !jsonResponse.get(TAG_REQMSG).toString().equals("0")) ? true : false;
            if (Status) {
                mCustomerNo = (TextView) findViewById(R.id.txtCustomerNo);
                mCustomerNo.setText(jsonResponse.get(TAG_CUSTOMERNO).toString());
                mAccountCount = (TextView) findViewById(R.id.txtAcCount);
                mAccountCount.setText(jsonResponse.get(TAG_REQMSG).toString());
                JSONArray jsonAC = jsonResponse.getJSONArray(TAG_ACLIST);
                ac_no_list = new String[jsonAC.length()];
                ac_name_list = new String[jsonAC.length()];
                ac_bal_list = new String[jsonAC.length()];
                int j = 0;
                for (int i = 0; i < jsonAC.length(); i++) {
                    JSONObject jsonACList = jsonAC.optJSONObject(i);
                    if (jsonACList != null) {
                        String ac_no, ac_name, ac_bal;
                        ac_no = jsonACList.getString(TAG_ACNO);
                        ac_name = jsonACList.getString(TAG_ACNAME);
                        ac_bal = jsonACList.getString(TAG_ACAVAILBAL);
                        ac_no_list[j] = ac_no;
                        ac_name_list[j] = ac_name;
                        ac_bal_list[j] = ac_bal;
                        j++;
                    }
                }
                SetListToListView(ac_no_list, ac_name_list, ac_bal_list);
                mPageLayout=(View)findViewById(R.id.rel_ac_list_page);
                mPageLayout.setVisibility(View.VISIBLE);

                mMsgLayout=(View)findViewById(R.id.rel_ac_list_msg);
                mMsgLayout.setVisibility(View.GONE);

                Toast.makeText(this.getBaseContext(), "Data Load Successfully.", Toast.LENGTH_LONG).show();
            } else {
                mMsgLayout=(View)findViewById(R.id.rel_ac_list_msg);
                mMsgLayout.setVisibility(View.VISIBLE);
                Toast.makeText(this.getBaseContext(), "Invalid Request", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            mMsgLayout=(View)findViewById(R.id.rel_ac_list_msg);
            mMsgLayout.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    private void SetListToListView(final String[] account_no, String[] ac_name, String[] ac_bal) {
        CustomAcListAdaptor adapter = new CustomAcListAdaptor(UserAccountActivity.this, account_no, ac_name, ac_bal);
        list = (AbsListView) findViewById(R.id.ac_list);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(list);
        list.setAdapter(animationAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(UserAccountActivity.this, "You Clicked at " + account_no[position], Toast.LENGTH_SHORT).show();
                Intent intDetailAc = new Intent(UserAccountActivity.this, AcDetailActivity.class);
                Bundle viewParameter = new Bundle();
                viewParameter.putString(INTENT_TOKENID, tokenId);
                viewParameter.putString(INTENT_CUSTOMERNO, CustomerNo);
                viewParameter.putString(INTENT_ACCOUNTNO, account_no[position]);
                intDetailAc.putExtras(viewParameter);
                startActivity(intDetailAc);
                //finish();

            }
        });
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
