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
import com.app.obl.oblmobileapp.adaptor.CustomCardListAdaptor;
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


public class UserCardListActivity extends BaseActivity implements AsyncTaskCompleteListener<JSONObject> {
    AbsListView list;
    public static String RequestUrl = "https://203.188.255.116/oblmapp/api/card/getcardlist/";
    //public static final String RequestUrl = "https://10.156.2.19/oblmapp/api/card/getcardlist/";
    private String CustomerNo;
    private String UserId;
    private String TokenId;
    private JSONObject jsonResponse = new JSONObject();


    private static final String INTENT_CUSTOMERNO = "CUSTOMERNO";
    private static final String INTENT_CARDNO = "CARDNO";

    private static final String INTENT_TAG_CUSTOMER_NO = "CUSTOMER_NO";
    private static final String INTENT_TAG_USERID="USERID";
    // JSON Node names
    private static final String TAG_STATUS = "STATUS";
    private static final String TAG_REQMSG = "REQMSG";
    private static final String TAG_CARDLIST = "CARDLIST";
    private static final String TAG_CUSTOMERNO = "CUSTOMERNO";
    private static final String TAG_CARDNO = "CARDNO";
    private static final String TAG_CARDHOLDERNAME = "CARDHOLDERNAME";
    private static final String TAG_CARDOUTSTANDING = "CARDOUTSTANDING";

    private static String[] card_no_list = new String[0];
    private static String[] card_name_list = new String[0];
    private static String[] card_bal_list = new String[0];

    private Toolbar toolbar;

    private TextView mCustomerNo;
    private TextView mAccountCount;

    private View mPageLayout;
    private View mMsgLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_card_list);
        checkSessionLogin(getApplicationContext());
        initiateToolbar();

        Intent tokenIntent = getIntent();
        TokenId = super.getTokenId();
        CustomerNo = tokenIntent.getStringExtra(INTENT_TAG_CUSTOMER_NO);
        UserId=tokenIntent.getStringExtra(INTENT_TAG_USERID);
        try {
            if(CheckNetwork.isConnectingToInternet(getApplicationContext(), UserCardListActivity.this)) {
                GetCardListRequest();
            }
            else{
                mMsgLayout=(View)findViewById(R.id.rel_card_list_msg);
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


    private void GetCardListRequest() throws ExecutionException, InterruptedException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("UserId", UserId));
        String paramUrl;
        String paramOnly = "";
        for (NameValuePair oNamePair : params) {
            paramOnly += oNamePair.getValue() + '/';
        }
        paramUrl = RequestUrl + paramOnly;
        UrlHTTPSRequest oUrlRequest = new UrlHTTPSRequest(UserCardListActivity.this, this);

        oUrlRequest.execute(paramUrl);
    }


    @Override
    public void onTaskComplete(JSONObject jResponse) {
        jsonResponse = jResponse;
        try {
            boolean Status = (jsonResponse.get(TAG_STATUS).toString() == "true") ? true : false;
            if (Status) {
                /*mCustomerNo = (TextView) findViewById(R.id.txtCardCustomerNo);
                mCustomerNo.setText(jsonResponse.get(TAG_CUSTOMERNO).toString());*/
                JSONArray jsonAC = jsonResponse.getJSONArray(TAG_CARDLIST);
                card_no_list = new String[jsonAC.length()];
                card_name_list = new String[jsonAC.length()];
                card_bal_list = new String[jsonAC.length()];
                int j = 0;
                for (int i = 0; i < jsonAC.length(); i++) {
                    JSONObject jsonACList = jsonAC.optJSONObject(i);
                    if (jsonACList != null) {
                        String card_no;
                        card_no = jsonACList.getString(TAG_CARDNO);
                        card_no_list[j] = card_no;
                        card_name_list[j] = jsonACList.getString(TAG_CARDHOLDERNAME);
                        card_bal_list[j] = jsonACList.getString(TAG_CARDOUTSTANDING);
                        j++;
                    }

                }
                SetListToListView(card_no_list,card_name_list,card_bal_list);
                Toast.makeText(this.getBaseContext(), "Data Load Successfully.", Toast.LENGTH_LONG).show();
                mPageLayout=(View)findViewById(R.id.rel_card_list_page);
                mPageLayout.setVisibility(View.VISIBLE);

                mMsgLayout=(View)findViewById(R.id.rel_card_list_msg);
                mMsgLayout.setVisibility(View.GONE);

            } else {
                mMsgLayout=(View)findViewById(R.id.rel_card_list_msg);
                mMsgLayout.setVisibility(View.VISIBLE);
                Toast.makeText(this.getBaseContext(), "Invalid Request", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SetListToListView(final String[] card_no,String[] card_name,String[] card_bal) {
        CustomCardListAdaptor adapter = new CustomCardListAdaptor(UserCardListActivity.this, card_no,card_name,card_bal);
        list = (AbsListView) findViewById(R.id.card_list);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(list);
        list.setAdapter(animationAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(UserCardListActivity.this, "You Clicked at " + card_no[position], Toast.LENGTH_SHORT).show();
                Intent intDetailCard = new Intent(UserCardListActivity.this, CardDetailActivity.class);
                Bundle viewParameter = new Bundle();
                viewParameter.putString(INTENT_CUSTOMERNO, CustomerNo);
                viewParameter.putString(INTENT_CARDNO , card_no[position]);
                intDetailCard.putExtras(viewParameter);
                startActivity(intDetailCard);
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
    @Override
    public void onBackPressed() {
        this.finish();
    }

}
