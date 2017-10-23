package com.app.obl.oblmobileapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.adaptor.CustomAcTranListAdaptor;
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
public class TabFragTranHistory extends Fragment implements AsyncTaskCompleteListener<JSONObject> {

    ListView list;

    private static final String INTENT_TOKENID = "TOKENID";
    private static final String INTENT_CUSTOMERNO = "CUSTOMERNO";
    private static final String INTENT_ACCOUNTNO = "ACCOUNTNO";

    //public static String RequestUrl = "https://10.156.2.19/oblmapp/api/Account/GetFCUBSAcTranList/";
    public static String RequestUrl = "https://203.188.255.116/oblmapp/api/Account/GetFCUBSAcTranList/";
    private JSONObject jsonResponse=new JSONObject();


    String[] tran_Date ;
    String[] tran_Des;
    String[] tran_amount ;
    String[] tran_type ;
    String[] tran_bal ;

    private static final String TAG_STATUS = "STATUS";
    private static final String TAG_CUSTOMERNO = "CUSTOMERNO";
    private static final String TAG_TRANLIST = "TRANLIST";
    private static final String TAG_TRANDATE = "TRANDATE";
    private static final String TAG_TRANDES= "TRANDES";
    private static final String TAG_TRANTYPE = "TRANTYPE";
    private static final String TAG_TRANAMOUNT = "TRANAMOUNT";
    private static final String TAG_TRANACBAL = "TRANACBAL";


    private static String CustomerNo;
    private static String AccountNo;
    private static String TokenId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_tran_history, container, false);

        Intent tokenIntent=getActivity().getIntent();
        TokenId=tokenIntent.getStringExtra(INTENT_TOKENID);
        CustomerNo=tokenIntent.getStringExtra(INTENT_CUSTOMERNO);
        AccountNo=tokenIntent.getStringExtra(INTENT_ACCOUNTNO);
        try {
            if(CheckNetwork.isConnectingToInternet(getContext(), getActivity())) {
                GetACTransactionDetailRequest();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return view;
    }


    private void GetACTransactionDetailRequest() throws ExecutionException, InterruptedException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(INTENT_TOKENID, TokenId));
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
                JSONArray jsonTRAN=jsonResponse.getJSONArray(TAG_TRANLIST);

                tran_Date = new String[jsonTRAN.length()];
                tran_Des = new String[jsonTRAN.length()];
                tran_amount = new String[jsonTRAN.length()];
                tran_type = new String[jsonTRAN.length()];
                tran_bal = new String[jsonTRAN.length()];
                int j = 0;
                for (int i = 0; i < jsonTRAN.length(); i++) {
                    JSONObject jsonACTranList = jsonTRAN.optJSONObject(i);
                    if (jsonACTranList != null) {
                        tran_Date[j] = jsonACTranList.getString(TAG_TRANDATE);
                        tran_Des[j] = jsonACTranList.getString(TAG_TRANDES);
                        tran_amount[j] = jsonACTranList.getString(TAG_TRANAMOUNT);
                        tran_type[j] = jsonACTranList.getString(TAG_TRANTYPE);
                        tran_bal[j] = jsonACTranList.getString(TAG_TRANACBAL);
                        j++;
                    }
                }
                SetListToListView(tran_Date, tran_Des, tran_amount,tran_type,tran_bal);
            }
            else
            {
                Toast.makeText(this.getContext(), "No Information Found! Invalid Request", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SetListToListView(final String[] tran_Date, String[] tran_Des, String[] tran_amount, String[] tran_type,String[] tran_bal) {
        CustomAcTranListAdaptor adapter =new CustomAcTranListAdaptor(getActivity(), tran_Date,tran_Des,tran_amount,tran_type,tran_bal);
        list = (ListView)getActivity().findViewById(R.id.ac_tran_list);
        list.setAdapter(adapter);
    }


}
