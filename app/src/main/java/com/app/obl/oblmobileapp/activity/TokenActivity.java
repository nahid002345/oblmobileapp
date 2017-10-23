package com.app.obl.oblmobileapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.app.PrefManager;
import com.app.obl.oblmobileapp.helper.SmsReceiver;
import com.app.obl.oblmobileapp.helper.Validation;
import com.app.obl.oblmobileapp.listener.AsyncTaskCompleteListener;
import com.app.obl.oblmobileapp.service.CheckNetwork;
import com.app.obl.oblmobileapp.service.UrlHTTPSRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TokenActivity extends BaseActivity implements AsyncTaskCompleteListener<JSONObject> {

    public PrefManager mPrefManager;
    public static String RequestUrl = "https://203.188.255.116/oblmapp/api/login/checkOTP/";
    //public static String RequestUrl = "https://10.156.2.19/oblmapp/api/login/checkOTP/";
    private static final String TOOLBAR_TITLE = "OTP Token";

    private static final String REQ_TOKENID = "TOKEN_ID";
    private static final String REQ_USERID = "UId";
    private static final String REQ_OTP = "Pwd";

    private static final String TAG_STATUS = "STATUS";
    private static final String TAG_USERID = "USERID";
    private static final String TAG_TOKENID = "TOKENID";
    private static final String TAG_OTP = "OTP";
    private static final String TAG_CUSTOMER_NO = "CUSTOMER_NO";
    private static final String TAG_CUSTOMER_NAME = "CUSTOMER_NAME";
    private static final String TAG_CUSTOMER_MOBILE = "CUSTOMER_MOBILE";


    private static final String INTENT_TAG_CUSTOMER_NAME = "CUSTOMER_NAME";
    private static final String INTENT_TAG_CUSTOMER_MOBILE = "CUSTOMER_MOBILE";
    private static final String INTENT_TAG_CUSTOMER_NO = "CUSTOMER_NO";
    private static final String INTENT_TAG_TOKEN_ID = "TOKENID";
    private static final String INTENT_TAG_USERID = "USERID";

    private static String UserId,CustomerMobile,CustomerName;
    private static String tokenId;
    private String msgOTP;
    private JSONObject jsonResponse = new JSONObject();

    private Button mLogin;
    private Toolbar mToolbar;
    private TextView mTokenId;
    private EditText mOTP;
    private TextView mAnnouncment;
    SmsReceiver mSmsReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSmsReceiver = new SmsReceiver();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);


        initToolbar();
        Intent tokenIntent = getIntent();
        msgOTP = tokenIntent.getStringExtra(TAG_OTP);
        mOTP = (EditText) findViewById(R.id.txtOtp);
        mTokenId = ((TextView) findViewById(R.id.TokenId));
        mAnnouncment=(TextView)findViewById(R.id.txt_token_announcement);

        mLogin = (Button) findViewById(R.id.btn_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (CheckValidation()) {
                        if (CheckNetwork.isConnectingToInternet(getApplicationContext(), TokenActivity.this)) {
                            attemptOTP();
                        }
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        if (msgOTP == null) {

            tokenId = tokenIntent.getStringExtra(TAG_TOKENID);
            UserId = tokenIntent.getStringExtra(TAG_USERID);
            CustomerMobile=tokenIntent.getStringExtra(TAG_CUSTOMER_MOBILE);
            CustomerName=tokenIntent.getStringExtra(TAG_CUSTOMER_NAME);
            mTokenId.setText(tokenId);
            String msg="A SMS HAS BEEN SENT TO +8801*******" + CustomerMobile.substring(7,9)+"**";
            mAnnouncment.setText(msg);
            Intent smsReceiverInt = new Intent();
            Bundle pageParameter = new Bundle();
            pageParameter.putString(TAG_TOKENID, tokenId);
            pageParameter.putString(TAG_USERID, UserId);
            smsReceiverInt.putExtras(pageParameter);
            mSmsReceiver.onReceive(getApplicationContext(), smsReceiverInt);
        } else {
            mOTP.setText(msgOTP);
        }

        setLoginLastActivityTime();
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

    private boolean CheckValidation() {
        boolean isValid = true;
        if (!Validation.hasText(mOTP)) isValid = false;
        return isValid;
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(TOOLBAR_TITLE);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setIcon(R.drawable.ic_toolbar_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void attemptOTP() throws ExecutionException, InterruptedException {
        TelephonyManager TM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = TM.getDeviceId();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(REQ_TOKENID, mTokenId.getText().toString()));
        params.add(new BasicNameValuePair(REQ_USERID, UserId));
        params.add(new BasicNameValuePair(REQ_OTP, mOTP.getText().toString()));
        TextView textView = (TextView) findViewById(R.id.textView);
        String paramUrl;
        String paramOnly = "";
        for (NameValuePair oNamePair : params) {
            paramOnly += oNamePair.getValue() + '/';
        }
        paramUrl = RequestUrl + paramOnly;
        UrlHTTPSRequest oUrlRequest = new UrlHTTPSRequest(TokenActivity.this, this);

        oUrlRequest.execute(paramUrl);
    }

    @Override
    public void onTaskComplete(JSONObject jResponse) {
        mPrefManager= new PrefManager(getApplicationContext());
        jsonResponse = jResponse;
        try {
            boolean logStatus = (jsonResponse.get(TAG_STATUS).toString() == "true") ? true : false;
            if (logStatus) {
                //tokenId = jsonResponse.get(TAG_TOKENID).toString();
                String CustomerNo = jsonResponse.get(TAG_CUSTOMER_NO).toString();
                Intent mDashboardActivity = new Intent(getBaseContext(), UserDashboardActivity.class);
                Bundle pageParameter = new Bundle();
                pageParameter.putString(INTENT_TAG_TOKEN_ID, tokenId);
                pageParameter.putString(INTENT_TAG_USERID, UserId);
                pageParameter.putString(INTENT_TAG_CUSTOMER_NO, CustomerNo);
                pageParameter.putString(INTENT_TAG_CUSTOMER_NAME, CustomerName);
                pageParameter.putString(INTENT_TAG_CUSTOMER_MOBILE, CustomerMobile);
                mDashboardActivity.putExtras(pageParameter);

                InputMethodManager imm = (InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                startActivity(mDashboardActivity);
                //session entry
                mPrefManager.createLogin(tokenId,UserId,CustomerNo);
                setLoginLastActivityTime();

            } else {
                Toast.makeText(this.getBaseContext(), "Invalid OTP", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
