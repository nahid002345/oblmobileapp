package com.app.obl.oblmobileapp.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.activity.ExchangeRateActvity;
import com.app.obl.oblmobileapp.adaptor.CustomExchangeRateListAdaptor;
import com.app.obl.oblmobileapp.helper.ExchangeRate;
import com.app.obl.oblmobileapp.listener.AsyncTaskCompleteListener;
import com.app.obl.oblmobileapp.service.CheckNetwork;
import com.app.obl.oblmobileapp.service.UrlHTTPSRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ONE BANK 1 on 12/4/2015.
 */
public class TabFragExchangeRateList extends Fragment implements AsyncTaskCompleteListener<JSONObject> {
    View view;
    AbsListView list;

    ArrayList<ExchangeRate> erList;
    Activity erListActivity;
    //public static String RequestUrl = "https://10.156.2.19/oblmapp/api/Currency/GetRateOnlyCashList";
    public static String RequestUrl = "https://203.188.255.116/oblmapp/api/Currency/GetRateOnlyCashList";
    private JSONObject jsonResponse=new JSONObject();

    private static final String TAG_STATUS = "STATUS";
    private static final String TAG_REQMSG = "REQMSG";

    private static final String TAG_RATELIST = "RATELIST";
    private static final String TAG_EXCHANGERATEINFO = "EXCHANGERATEINFO";
    private static final String TAG_BUYRATE= "BUYRATE";
    private static final String TAG_CURRENCY = "CURRENCY";
    private static final String TAG_RATEDATE = "RATEDATE";
    private static final String TAG_RATETYPE = "RATETYPE";
    private static final String TAG_SALERATE = "SALERATE";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_exchange_rate_list, container, false);
        erListActivity=getActivity();
        try {
                if(CheckNetwork.isConnectingToInternet(erListActivity.getApplicationContext(), erListActivity)) {
                    GetExchangeRateRequest();
                }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return view;
    }


    private void GetExchangeRateRequest() throws ExecutionException, InterruptedException {

        String paramUrl;
        String paramOnly="";
        paramUrl=RequestUrl;
        UrlHTTPSRequest oUrlRequest= new UrlHTTPSRequest(erListActivity,this);
        oUrlRequest.execute(paramUrl);
    }

    @Override
    public void onTaskComplete(JSONObject jResponse) {
        jsonResponse=jResponse;
        try {
            boolean Status= (jsonResponse.get(TAG_STATUS).toString() == "true") ? true : false;
            if(Status){
                JSONArray jsonTRAN=jsonResponse.getJSONArray(TAG_RATELIST);
                erList=new ArrayList<ExchangeRate>();

                int j = 0;
                for (int i = 0; i < jsonTRAN.length(); i++) {
                    JSONObject jsonACTranList = jsonTRAN.optJSONObject(i);
                    if (jsonACTranList != null) {
                        ExchangeRate mExchangeRate= new ExchangeRate();
                        mExchangeRate.er_currency = jsonACTranList.getString(TAG_CURRENCY);
                        mExchangeRate.er_type = jsonACTranList.getString(TAG_RATETYPE);
                        mExchangeRate.er_buy_rate = jsonACTranList.getString(TAG_BUYRATE);
                        mExchangeRate.er_sell_rate = jsonACTranList.getString(TAG_SALERATE);
                        mExchangeRate.er_date= jsonACTranList.getString(TAG_RATEDATE);
                        erList.add(mExchangeRate);
                        j++;
                    }
                }
                SetListToListView(erList);
                ((ExchangeRateActvity)getActivity()).SetExchangeRate(erList);
            }
            else
            {
                Toast.makeText(this.getContext(), "No Information Found! Invalid Request", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void SetListToListView(final ArrayList<ExchangeRate> exchange_rate_list) {
        CustomExchangeRateListAdaptor adapter =new CustomExchangeRateListAdaptor(erListActivity, exchange_rate_list);
        list = (ListView)erListActivity.findViewById(R.id.exchange_rate_list);
        list.setAdapter(adapter);
    }

}
