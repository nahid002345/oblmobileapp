package com.app.obl.oblmobileapp.app;

/**
 * Created by ONE BANK 1 on 12/5/2015.
 */
public class AppConfig {
    // server URL configuration
    public static final String URL_REQUEST_SMS = "http://192.168.0.101/android_sms/msg91/request_sms.php";
    public static final String URL_VERIFY_OTP = "http://192.168.0.101/android_sms/msg91/verify_otp.php";

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    public static final String SMS_ORIGIN = "03591111111";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";
}
