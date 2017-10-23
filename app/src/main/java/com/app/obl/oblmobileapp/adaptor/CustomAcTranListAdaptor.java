package com.app.obl.oblmobileapp.adaptor;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;

public class CustomAcTranListAdaptor extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] tran_date;
    private final String[] tran_des;
    private final String[] tran_amount;
    private final String[] tran_type;
    private final String[] tran_bal;

    public CustomAcTranListAdaptor(Activity context, String[] tran_date, String[] tran_des, String[] tran_amount, String[] tran_type, String[] tran_bal) {
        super(context, R.layout.list_item_user_ac_tran, tran_des);
        this.context = context;
        this.tran_date = tran_date;
        this.tran_des=tran_des;
        this.tran_amount = tran_amount;
        this.tran_type = tran_type;
        this.tran_bal=tran_bal;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item_user_ac_tran, null, true);
        TextView txtTranDate = (TextView) rowView.findViewById(R.id.txt_tran_date);
        TextView txtTranDes = (TextView) rowView.findViewById(R.id.txt_tran_no);
        TextView txtTranAmount = (TextView) rowView.findViewById(R.id.txt_tran_amount);
        TextView txtTranType = (TextView) rowView.findViewById(R.id.txt_tran_type);
        TextView txtTranBal = (TextView) rowView.findViewById(R.id.txt_tran_amount_bal);

        txtTranDate.setText(tran_date[position]);
        txtTranDes.setText(tran_des[position]);
        txtTranAmount.setText(tran_amount[position]);
        txtTranType.setText(tran_type[position]);
        txtTranBal.setText(tran_bal[position]);

        return rowView;
    }
}