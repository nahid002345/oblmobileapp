package com.app.obl.oblmobileapp.helper;

/**
 * Created by ONE BANK 1 on 12/5/2015.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.app.obl.oblmobileapp.activity.TokenActivity;
import com.app.obl.oblmobileapp.app.AppConfig;

/**
 * Created by Ravi on 09/07/15.
 */
public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();
    private static final String TAG_USERID= "USERID";
    private static final String TAG_TOKENID = "TOKENID";
    private static final String TAG_OTP = "OTP";

    private String UserId;
    private String OTP;
    private String TokenId;
    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        try {

            if (bundle != null) {

                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    // if the SMS is not from our gateway, ignore the message
                    if (!senderAddress.toLowerCase().contains(AppConfig.SMS_ORIGIN.toLowerCase())) {
                        return;
                    }

                    // verification code from sms
                    String otpCode = getotpCode(message);

                    Log.e(TAG, "OTP received: " + otpCode);
                    Toast.makeText(context,"OTP received: " + otpCode + " from SMS",Toast.LENGTH_LONG).show();
                    Intent tokenIntent = new Intent(context, TokenActivity.class);
                    tokenIntent.putExtra(TAG_OTP, otpCode);
                    context.startActivity(tokenIntent);


                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Getting the OTP from sms message body
     * ':' is the separator of OTP from the message
     *
     * @param message
     * @return
     */
    private String getotpCode(String message) {
        String code = null;
        int index = message.indexOf(AppConfig.OTP_DELIMITER);

        if (index != -1) {
            int start = index + 2;
            int length = 5;
            code = message.substring(start, start + length);
            return code;
        }

        return code;
    }
}
