package com.app.obl.oblmobileapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.listener.AsyncTaskCompleteListener;
import com.app.obl.oblmobileapp.service.UrlHTTPSRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CardDetailActivity extends BaseActivity implements AsyncTaskCompleteListener<JSONObject> {

    ListView list;
    public static String RequestUrl = "https://203.188.255.116/oblmapp/api/card/getcardinfo/";
    //public static String RequestUrl = "https://10.156.2.19/oblmapp/api/card/getcardinfo/";
    private static final String TOOLBAR_TITLE = "Card Detail";
    private static String CustomerNo;
    private static String CardNo;
    private JSONObject jsonResponse = new JSONObject();


    private static final String INTENT_CUSTOMERNO = "CUSTOMERNO";
    private static final String INTENT_CARDNO = "CARDNO";
    // JSON Node names
    private static final String TAG_STATUS = "STATUS";
    private static final String TAG_REQMSG = "REQMSG";
    private static final String TAG_CARDINFO = "CARDINFO";

    private static final String TAG_ADDRESS="ADDRESS";
    private static final String TAG_BDTAVAILABLECASHLIMIT="BDTAVAILABLECASHLIMIT";
    private static final String TAG_BDTAVAILABLECRLIMIT="BDTAVAILABLECRLIMIT";
    private static final String TAG_BDTCASHADV="BDTCASHADV";
    private static final String TAG_BDTCASHLIMIT="BDTCASHLIMIT";
    private static final String TAG_BDTCREDITLIMIT="BDTCREDITLIMIT";
    private static final String TAG_BDTCREDITS="BDTCREDITS";
    private static final String TAG_BDTCURRBALANCE="BDTCURRBALANCE";
    private static final String TAG_BDTCURRENCY="BDTCURRENCY";
    private static final String TAG_BDTMINAMNTDUE="BDTMINAMNTDUE";
    private static final String TAG_BDTPAYMENTS="BDTPAYMENTS";
    private static final String TAG_BDTPREVBALANCE="BDTPREVBALANCE";
    private static final String TAG_BDTTOTALINTR="BDTTOTALINTR";
    private static final String TAG_BDTTOTALPURCHASE="BDTTOTALPURCHASE";
    private static final String TAG_BILLINGDATE="BILLINGDATE";
    private static final String TAG_CARDNO="CARDNO";
    private static final String TAG_CLIENTNAME="CLIENTNAME";
    private static final String TAG_MASKCARDNO="MASKCARDNO";
    private static final String TAG_PAYMENTDATE="PAYMENTDATE";
    private static final String TAG_USDAVAILABLECASHLIMIT="USDAVAILABLECASHLIMIT";
    private static final String TAG_USDAVAILABLECRLIMIT="USDAVAILABLECRLIMIT";
    private static final String TAG_USDCASHADV="USDCASHADV";
    private static final String TAG_USDCASHLIMIT="USDCASHLIMIT";
    private static final String TAG_USDCREDITLIMIT="USDCREDITLIMIT";
    private static final String TAG_USDCREDITS="USDCREDITS";
    private static final String TAG_USDCURRBALANCE="USDCURRBALANCE";
    private static final String TAG_USDCURRENCY="USDCURRENCY";
    private static final String TAG_USDMINAMNTDUE="USDMINAMNTDUE";
    private static final String TAG_USDPAYMENTS="USDPAYMENTS";
    private static final String TAG_USDPREVBALANCE="USDPREVBALANCE";
    private static final String TAG_USDTOTALINTR="USDTOTALINTR";
    private static final String TAG_USDTOTALPURCHASE="USDTOTALPURCHASE";



    private TextView mCardNo;
    private TextView mCurrentBalBdt;
    private TextView mCurrentBalUsd;
    private TextView mCardName;
    private TextView mCardPayDate;
    private TextView mCardBillDate;

    private TextView mBDTPayDueDate;
    private TextView mBDTMinPayDue;
    private TextView mBDTCreditLimit;
    private TextView mBDTCashLimit;
    private TextView mBDTTotalOut;

    private TextView mUSDMinPayDue;
    private TextView mUSDCreditLimit;
    private TextView mUSDCashLimit;
    private TextView mUSDTotalOut;

    private TabLayout tabLayout;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        Intent tokenIntent=getIntent();
        CustomerNo=tokenIntent.getStringExtra(INTENT_CUSTOMERNO);
        CardNo=tokenIntent.getStringExtra(INTENT_CARDNO);
        SetTitleBar();
        try {
            GetCardDetailRequest();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    private void GetCardDetailRequest() throws ExecutionException, InterruptedException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(INTENT_CARDNO, CardNo));
        String paramUrl;
        String paramOnly = "";
        for (NameValuePair oNamePair : params) {
            paramOnly += oNamePair.getValue() + '/';
        }
        paramUrl = RequestUrl + paramOnly;
        UrlHTTPSRequest oUrlRequest = new UrlHTTPSRequest(CardDetailActivity.this, this);

        oUrlRequest.execute(paramUrl);
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

    @Override
    public void onTaskComplete(JSONObject jResponse) {
        jsonResponse = jResponse;
        try {
            boolean Status = (jsonResponse.get(TAG_STATUS).toString() == "true") ? true : false;
            if (Status) {
                JSONObject jsonCard = jsonResponse.getJSONObject(TAG_CARDINFO);
                mCardNo=(TextView)findViewById(R.id.txt_header_card_no);
                mCurrentBalBdt=(TextView)findViewById(R.id.txt_header_bal_tk);
                mCurrentBalUsd=(TextView)findViewById(R.id.txt_header_bal_dollar);

                mCardName=(TextView)findViewById(R.id.txt_detail_CardName);
                mCardPayDate=(TextView)findViewById(R.id.txt_card_pay_date);
                mCardBillDate=(TextView)findViewById(R.id.txt_card_bill_date);

                mBDTPayDueDate=(TextView)findViewById(R.id.txt_pay_due_date_bdt);
                mBDTMinPayDue=(TextView)findViewById(R.id.txt_min_pay_due_bdt);
                mBDTCreditLimit=(TextView)findViewById(R.id.txt_credit_limit_bdt);
                mBDTCashLimit=(TextView)findViewById(R.id.txt_cash_limit_bdt);
                mBDTTotalOut=(TextView)findViewById(R.id.txt_outstanding_bdt);

                mUSDMinPayDue=(TextView)findViewById(R.id.txt_min_pay_due_usd);
                mUSDCreditLimit=(TextView)findViewById(R.id.txt_credit_limit_usd);
                mUSDCashLimit=(TextView)findViewById(R.id.txt_cash_limit_usd);
                mUSDTotalOut=(TextView)findViewById(R.id.txt_outstanding_usd);

                mCardNo.setText(jsonCard.getString(TAG_CARDNO));
                if(jsonCard.getString(TAG_BDTCURRBALANCE) != null)
                mCurrentBalBdt.setText(jsonCard.getString(TAG_BDTCURRBALANCE));
                if(jsonCard.getString(TAG_USDCURRBALANCE) != null)
                mCurrentBalUsd.setText(jsonCard.getString(TAG_USDCURRBALANCE));

                if(jsonCard.getString(TAG_CLIENTNAME) != null)
                mCardName.setText(jsonCard.getString(TAG_CLIENTNAME));
                if(jsonCard.getString(TAG_PAYMENTDATE) != null)
                mCardPayDate.setText(jsonCard.getString(TAG_PAYMENTDATE));
                if(jsonCard.getString(TAG_BILLINGDATE) != null)
                mCardBillDate.setText(jsonCard.getString(TAG_BILLINGDATE));

                if(jsonCard.getString(TAG_PAYMENTDATE) != null)
                mBDTPayDueDate.setText(jsonCard.getString(TAG_PAYMENTDATE));
                if(jsonCard.getString(TAG_BDTPAYMENTS) != null)
                mBDTMinPayDue.setText(jsonCard.getString(TAG_BDTPAYMENTS));
                if(jsonCard.getString(TAG_BDTCREDITLIMIT) != null)
                mBDTCreditLimit.setText(jsonCard.getString(TAG_BDTCREDITLIMIT));
                if(jsonCard.getString(TAG_BDTCASHLIMIT) != null)
                mBDTCashLimit.setText(jsonCard.getString(TAG_BDTCASHLIMIT));
                if(jsonCard.getString(TAG_BDTCURRBALANCE) != null)
                mBDTTotalOut.setText(jsonCard.getString(TAG_BDTCURRBALANCE));

                if(jsonCard.getString(TAG_USDPAYMENTS) != null)
                mUSDMinPayDue.setText(jsonCard.getString(TAG_USDPAYMENTS));
                if(jsonCard.getString(TAG_USDCREDITLIMIT) != null)
                mUSDCreditLimit.setText(jsonCard.getString(TAG_USDCREDITLIMIT));
                if(jsonCard.getString(TAG_USDCASHLIMIT) != null)
                mUSDCashLimit.setText(jsonCard.getString(TAG_USDCASHLIMIT));
                if(jsonCard.getString(TAG_USDCURRBALANCE) != null)
                mUSDTotalOut.setText(jsonCard.getString(TAG_USDCURRBALANCE));

                Toast.makeText(this.getBaseContext(), "Data Load Successfully.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this.getBaseContext(), "Invalid Request", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
