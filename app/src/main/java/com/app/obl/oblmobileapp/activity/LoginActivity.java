package com.app.obl.oblmobileapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.helper.JSONParser;
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

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements AsyncTaskCompleteListener<JSONObject> {

    private static final String TAG_UNDER_PROCESS_MSG = "MSG";
    private static final String UNDER_PROCESS_MSG = "ADD NEW USER IS CURRENTLY DISABLED. PLEASE WAIT....";

    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String TOOLBAR_TITLE = "Log In";
    public static String RequestUrl = "https://203.188.255.116/oblmapp/api/login/check/";
    //public static String RequestUrl = "https://10.156.2.19/oblmapp/api/login/check/";
    private static final String REQ_DEVICEID = "DId";
    private static final String REQ_MOBILENO = "MNo";
    private static String REQ_USERID = "UId";
    private static String REQ_PASSWORD = "Pwd";


    private static final String TAG_STATUS = "STATUS";
    private static final String TAG_USERID= "USERID";
    private static final String TAG_TOKENID = "TOKENID";
    private static final String TAG_CUSTOMER_NAME= "CUSTOMER_NAME";
    private static final String TAG_CUSTOMER_MOBILE= "CUSTOMER_MOBILE";
    private static final String TAG_RES_MSG = "RES_MSG";
    private static final String TAG_CUSTOMER_NO = "CUSTOMER_NO";

    private Button mEmailSignInButton=null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private JSONObject jsonResponse = new JSONObject();
    private JSONParser jsonParser = new JSONParser();
    ImageView imageView;
    private Toolbar mToolbar;
    private Button mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginFormView = findViewById(R.id.login_form);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.UserId);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignUp=(Button) findViewById(R.id.btn_sign_up_button);

        mSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mUProcessActivity = new Intent(getBaseContext(), UnderProcessActivity.class);
                Bundle pageParameter = new Bundle();
                pageParameter.putString(TAG_UNDER_PROCESS_MSG,UNDER_PROCESS_MSG);
                mUProcessActivity.putExtras(pageParameter);
                startActivity(mUProcessActivity);
            }
        });

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (CheckValidation()) {
                        if (CheckNetwork.isConnectingToInternet(getApplicationContext(), LoginActivity.this)) {
                            attemptLogin();
                        }
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        initToolbar();

    }

    private void initToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(TOOLBAR_TITLE);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setIcon(R.drawable.ic_toolbar_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private boolean CheckValidation() {
        boolean isValid = true;
        if (!Validation.hasText(mEmailView)) isValid = false;
        if (!Validation.hasText(mPasswordView)) isValid = false;
        return isValid;
    }

    private void attemptLogin() throws ExecutionException, InterruptedException {
        String mobileNo="";
        //Log.i("btn test", "clicked");
        TelephonyManager TM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = TM.getDeviceId();
        mobileNo=TM.getLine1Number();
        if(mobileNo.isEmpty())
            mobileNo=TM.getSimSerialNumber();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(REQ_DEVICEID, deviceId));
        params.add(new BasicNameValuePair(REQ_MOBILENO, mobileNo));
        params.add(new BasicNameValuePair(REQ_USERID, mEmailView.getText().toString()));
        params.add(new BasicNameValuePair(REQ_PASSWORD, mPasswordView.getText().toString()));
        TextView textView = (TextView) findViewById(R.id.textView);
        String paramUrl;
        String paramOnly = "";
        for (NameValuePair oNamePair : params) {
            paramOnly += oNamePair.getValue() + '/';
        }
        paramUrl = RequestUrl + paramOnly;
        UrlHTTPSRequest oUrlRequest = new UrlHTTPSRequest(LoginActivity.this, this);
        JSONObject jsonResponse;
        oUrlRequest.execute(paramUrl);

    }


    @Override
    public void onTaskComplete(JSONObject jResponse) {
        jsonResponse = jResponse;
        if(jResponse != null) {
            try {
                boolean logStatus = (jsonResponse.get(TAG_STATUS).toString() == "true") ? true : false;
                if (logStatus) {
                    mEmailView = (AutoCompleteTextView) findViewById(R.id.UserId);
                    String tokenId = jsonResponse.get(TAG_TOKENID).toString();
                    String CustMobile=jsonResponse.get(TAG_CUSTOMER_MOBILE).toString();
                    Intent oTPContext = new Intent(this.getBaseContext(), TokenActivity.class);
                    Bundle pageParameter = new Bundle();
                    pageParameter.putString(TAG_TOKENID, tokenId);
                    pageParameter.putString(TAG_USERID, jsonResponse.get(TAG_USERID).toString());
                    pageParameter.putString(TAG_CUSTOMER_MOBILE, jsonResponse.get(TAG_CUSTOMER_MOBILE).toString());
                    pageParameter.putString(TAG_CUSTOMER_NAME, jsonResponse.get(TAG_CUSTOMER_NAME).toString());
                    oTPContext.putExtras(pageParameter);

                    InputMethodManager imm = (InputMethodManager)getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    startActivity(oTPContext);

                } else {
                    Toast.makeText(this.getBaseContext(), "Invalid User ID/ Password", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

