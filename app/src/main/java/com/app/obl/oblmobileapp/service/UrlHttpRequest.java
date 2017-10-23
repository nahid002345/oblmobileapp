package com.app.obl.oblmobileapp.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.app.obl.oblmobileapp.helper.CustomWarningAlertDialog;
import com.app.obl.oblmobileapp.listener.AsyncTaskCompleteListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//
public class UrlHttpRequest extends AsyncTask<String, Integer, JSONObject> {
    TextView t;
    String result = "fail";
    String url = "";
    ProgressDialog dialog;
    JSONObject json = null;
    AsyncTaskCompleteListener<JSONObject> listener = null;

    final int HTTP_STATUS_SUCCESS=200;

    //private static Context context;
    private static Activity activity;

    public UrlHttpRequest(Context context, AsyncTaskCompleteListener<JSONObject> listener) {
        //UrlHttpRequest.context = super.getClass().getApplicationContext();
        UrlHttpRequest.activity = (Activity) context;
        dialog = new ProgressDialog(activity);
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle("Connecting...");
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        url = params[0];
        return GetRequest(url);
    }

    final JSONObject GetRequest(String url) {

        BufferedReader inStream = null;
        try {

            HttpClient httpClient = new DefaultHttpClient();

            HttpGet httpRequest = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpRequest);
            if(response.getStatusLine().getStatusCode()==HTTP_STATUS_SUCCESS) {
                inStream = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer buffer = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = inStream.readLine()) != null) {
                    buffer.append(line + NL);
                }
                inStream.close();
                result = buffer.toString();
                json = new JSONObject(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return json;
    }

    final JSONObject JsonGetRequest(String url) {

        BufferedReader inStream = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpRequest = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpRequest);
            //inStream = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));

            result = EntityUtils.toString(response.getEntity());
            //inStream.close();
            //result = buffer.toString();
            json = new JSONObject(result);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return json;
    }

    protected void onPostExecute(JSONObject response) {
        json = response;
        if (response == null) {
            CustomWarningAlertDialog alert = new CustomWarningAlertDialog();
            alert.showDialog(activity, "Server Request is not Successful");
            Toast.makeText(activity.getBaseContext(), "Server Request is not Successful", Toast.LENGTH_LONG)
                    .show();
        }
        super.onPostExecute(response);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        listener.onTaskComplete(response);
    }
}
