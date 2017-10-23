package com.app.obl.oblmobileapp.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.app.obl.oblmobileapp.helper.CustomWarningAlertDialog;
import com.app.obl.oblmobileapp.listener.AsyncTaskCompleteListenerXml;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//
public class UrlHttpRequestXml extends AsyncTask<String, Integer, String> {
    TextView t;
    String result = "fail";
    String url = "";
    ProgressDialog dialog;

    AsyncTaskCompleteListenerXml<String> listener = null;

    final int HTTP_STATUS_SUCCESS=200;

    //private static Context context;
    private static Activity activity;

    public UrlHttpRequestXml(Context context, AsyncTaskCompleteListenerXml<String> listener) {
        UrlHttpRequestXml.activity = (Activity) context;
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
    protected String doInBackground(String... params) {
        url = params[0];
        return GetRequest(url);
    }

    final String GetRequest(String url) {

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
                //json = new JSONObject(result);
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
        return result;
    }
    protected void onPostExecute(String response) {
        result = response;
        if (response == null || response == "fail") {
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
