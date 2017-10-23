package com.app.obl.oblmobileapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.listener.AsyncTaskCompleteListener;
import com.app.obl.oblmobileapp.service.CheckNetwork;
import com.app.obl.oblmobileapp.service.UrlHTTPSRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ONE BANK 1 on 12/4/2015.
 */
public class TabFragAcDetail extends Fragment implements AsyncTaskCompleteListener<JSONObject> {

    ListView list;
    private static final String TOOLBAR_TITLE = "Detail";
    public static String RequestUrl = "https://203.188.255.116/oblmapp/api/Account/GetFCUBSAcInfo/";
    //public static String RequestUrl = "https://10.156.2.19/oblmapp/api/Account/GetFCUBSAcInfo/";
    private static String CustomerNo;
    private static String AccountNo;
    private static String TokenId;

    private JSONObject jsonResponse=new JSONObject();

    private static final String INTENT_TOKENID = "TOKENID";
    private static final String INTENT_CUSTOMERNO = "CUSTOMERNO";
    private static final String INTENT_ACCOUNTNO = "ACCOUNTNO";

    // JSON Node names
    private static final String TAG_STATUS = "STATUS";
    private static final String TAG_CUSTOMERNO = "CUSTOMERNO";
    private static final String TAG_ACLIST = "ACLIST";
    private static final String TAG_ACNO= "ACNO";
    private static final String TAG_ACNAME = "ACNAME";
    private static final String TAG_ACTYPE = "ACTYPE";
    private static final String TAG_ACBALANCE = "ACAVAILBAL";
    private static final String TAG_ACCURBALANCE = "ACCURRENTBAL";

    private TextView mAccountType;
    private TextView mAccountNo;
    private TextView mAccountName;
    private TextView mAcAvailBal;
    private TextView mAcCurrentBal;
    private TextView mAcHeaderBal;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_ac_detail, container, false);
        Intent tokenIntent=getActivity().getIntent();
        TokenId=tokenIntent.getStringExtra(INTENT_TOKENID);
        CustomerNo=tokenIntent.getStringExtra(INTENT_CUSTOMERNO);
        AccountNo=tokenIntent.getStringExtra(INTENT_ACCOUNTNO);

        try {
            if(CheckNetwork.isConnectingToInternet(getContext(), getActivity())) {
                GetACDetailRequest();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void GetACDetailRequest() throws ExecutionException, InterruptedException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(INTENT_TOKENID, TokenId));
        params.add(new BasicNameValuePair(INTENT_CUSTOMERNO, CustomerNo));
        params.add(new BasicNameValuePair(INTENT_ACCOUNTNO, AccountNo));
        String paramUrl;
        String paramOnly="";
        for(NameValuePair oNamePair : params)
        {
            paramOnly += oNamePair.getValue() + '/';
        }
        paramUrl=RequestUrl + paramOnly;
        UrlHTTPSRequest oUrlRequest= new UrlHTTPSRequest(getActivity(),this);

        oUrlRequest.execute(paramUrl);
    }

    @Override
    public void onTaskComplete(JSONObject jResponse) {
        jsonResponse=jResponse;
        try {
            boolean Status= (jsonResponse.get(TAG_STATUS).toString() == "true") ? true : false;
            if(Status){
                JSONArray jsonAC=jsonResponse.getJSONArray(TAG_ACLIST);
                JSONObject jsonACList=jsonAC.optJSONObject(0);

                mAccountType=(TextView)getView().findViewById(R.id.txt_detail_ACType);
                mAccountName=(TextView)getView().findViewById(R.id.txt_detail_ACName);
                mAcCurrentBal=(TextView)getView().findViewById(R.id.txt_detail_CurrentBal);
                mAcAvailBal = (TextView) getView().findViewById(R.id.txt_detail_ACBal);
                mAccountNo=(TextView)getView().findViewById(R.id.txt_header_ac_no);
                mAcHeaderBal=(TextView)getView().findViewById(R.id.txt_header_bal);

                mAccountType.setText(jsonACList.getString(TAG_ACTYPE));
                mAccountName.setText(jsonACList.getString(TAG_ACNAME));
                mAccountNo.setText(jsonACList.getString(TAG_ACNO));
                mAcAvailBal.setText(jsonACList.getString(TAG_ACBALANCE));
                mAcCurrentBal.setText(jsonACList.getString(TAG_ACCURBALANCE));
                mAcHeaderBal.setText(jsonACList.getString(TAG_ACCURBALANCE));

            }
            else
            {
                Toast.makeText(this.getContext(), "No Information Found! Invalid Request", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
