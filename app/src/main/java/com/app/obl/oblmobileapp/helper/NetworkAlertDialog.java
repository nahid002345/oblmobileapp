package com.app.obl.oblmobileapp.helper;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;

/**
 * Created by ONE BANK 1 on 11/26/2015.
 */
public class NetworkAlertDialog {
    String txt_error_msg;
    TextView txt_msg;
    ImageView img_msg;
    public NetworkAlertDialog()
    {

    }
    public void showDialog(Activity activity, int network_msg_type){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.content_network_alert_dialog);
        txt_msg = (TextView) dialog.findViewById(R.id.txt_nt_error_msg);

        img_msg=(ImageView)dialog.findViewById(R.id.img_icon_nt_error);

        switch (network_msg_type){
            case 0:
                img_msg.setImageResource(R.drawable.ic_nt_not_connected);
                txt_error_msg="NOT CONNECTED TO INTERNET !!!";
                break;
            case 1:
                img_msg.setImageResource(R.drawable.ic_nt_not_connected);
                txt_error_msg="SERVER REQUEST NOT SUCCESSFUL !!!";
                break;
            default:
                break;
        }


        txt_msg.setText(txt_error_msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_nt_dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
