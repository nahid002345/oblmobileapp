package com.app.obl.oblmobileapp.app;

/**
 * Created by ONE BANK 1 on 12/5/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.app.obl.oblmobileapp.helper.CustomWarningAlertDialog;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Ravi on 08/07/15.
 */
public class PrefManager {

    SharedPreferences pref;
    Editor editor;
    Context _context;
    Activity activity;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "OBL Mobile App";

    // All Shared Preferences Keys
    private static final String KEY_IS_WAITING_FOR_SMS = "IsWaitingForSms";
    private static final String KEY_MOBILE_NUMBER = "MOBILENO";

    private static final String KEY_TIMESTAP = "TIMESTAMP";

    private static final String KEY_IS_LOGGED_IN = "ISLOGGEDIN";
    private static final String KEY_USERID = "USERID";
    private static final String KEY_TOKENID = "TOKENID";
    private static final String KEY_CUSTOMERNO = "CUSTOMERNO";

    private static final String KEY_EXCHANGE_RATE_LIST = "EXRATELIST";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "mobile";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public PrefManager(Context context,Activity _activity) {
        this._context = context;
        this.activity=_activity;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIsWaitingForSms(boolean isWaiting) {
        editor.putBoolean(KEY_IS_WAITING_FOR_SMS, isWaiting);
        editor.commit();
    }

    public boolean isWaitingForSms() {
        return pref.getBoolean(KEY_IS_WAITING_FOR_SMS, false);
    }

    public void setMobileNumber(String mobileNumber) {
        editor.putString(KEY_MOBILE_NUMBER, mobileNumber);
        editor.commit();
    }

    public String getMobileNumber() {
        return pref.getString(KEY_MOBILE_NUMBER, null);
    }

    public void createLogin(String name, String email) {
        editor.putString(KEY_EMAIL, email);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }


    public void SetExchangeRate(String jsonExchangeRate){
        editor.putString(KEY_EXCHANGE_RATE_LIST, jsonExchangeRate);
        editor.commit();
    }

    public String GetExchangeRate(){
        return pref.getString(KEY_EXCHANGE_RATE_LIST,null);
    }

    public void createLogin(String TokenId, String UserId, String CustomerNo) {
        editor.putString(KEY_TOKENID, TokenId);
        editor.putString(KEY_USERID, UserId);
        editor.putString(KEY_CUSTOMERNO, CustomerNo);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putLong(KEY_TIMESTAP, new Date().getTime());
        editor.commit();
    }

    public void checkLogin() {
        boolean isLogin=pref.getBoolean(KEY_IS_LOGGED_IN, false);
        if (!isLogin) {
            CustomWarningAlertDialog alert = new CustomWarningAlertDialog();

            alert.showDialog(activity, "Session Ended. Please Login");
            Toast.makeText(activity.getBaseContext(), "Session Ended. Please Login", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getTokenId() {
        return pref.getString(KEY_TOKENID, null);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> profile = new HashMap<>();
        //profile.put("name", pref.getString(KEY_NAME, null));
        profile.put(KEY_TOKENID, pref.getString(KEY_TOKENID, null));
        profile.put(KEY_USERID, pref.getString(KEY_USERID, null));
        profile.put(KEY_CUSTOMERNO, pref.getString(KEY_CUSTOMERNO, null));
        profile.put(KEY_TIMESTAP, String.valueOf(pref.getLong(KEY_TIMESTAP, 0)));
        profile.put(KEY_IS_LOGGED_IN, String.valueOf(pref.getBoolean(KEY_IS_LOGGED_IN, false)));

        return profile;
    }
}
