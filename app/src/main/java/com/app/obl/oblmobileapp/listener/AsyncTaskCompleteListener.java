package com.app.obl.oblmobileapp.listener;

import org.json.JSONObject;

/**
 * Created by ONE BANK 1 on 11/18/2015.
 */
public interface AsyncTaskCompleteListener<T> {
    public void onTaskComplete (JSONObject jsonResponse);
}
