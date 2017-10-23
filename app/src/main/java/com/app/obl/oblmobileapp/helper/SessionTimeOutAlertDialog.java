package com.app.obl.oblmobileapp.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.activity.MainDrawerTestActivity;

/**
 * Created by ONE BANK 1 on 11/26/2015.
 */
public class SessionTimeOutAlertDialog {
    String txt_error_msg;
    TextView txt_msg;
    ImageView img_msg;
    public SessionTimeOutAlertDialog()
    {

    }
    public void showDialog(final Activity activity, int session_msg_type){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.content_network_alert_dialog);
        txt_msg = (TextView) dialog.findViewById(R.id.txt_nt_error_msg);

        img_msg=(ImageView)dialog.findViewById(R.id.img_icon_nt_error);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_nt_dialog_ok);

        switch (session_msg_type){

            case 2:
                img_msg.setImageResource(R.drawable.ic_session_timeout);
                txt_error_msg="SESSION TIMEOUT !!! PLEASE LOGIN.";
                break;
            default:
                break;
        }


        txt_msg.setText(txt_error_msg);


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intWelCome= new Intent(activity,MainDrawerTestActivity.class);
                intWelCome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intWelCome);
            }
        });
        dialog.show();

    }

}
