package com.app.obl.oblmobileapp.adaptor;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;

public class CustomAcListAdaptor extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] account_no;
    private final String[] account_name;
    private final String[] account_bal;
    //private final String[] rating;
    //private final Integer[] imageId;
    public CustomAcListAdaptor(Activity context,
                               String[] ac_no, String[] ac_name, String[] bal) {
        super(context, R.layout.list_row, ac_no);
        this.context = context;
        this.account_no = ac_no;
        this.account_name = ac_name;
        this.account_bal = bal;
        //this.rating = rating;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item_user_ac, null, true);
        TextView txtAc = (TextView) rowView.findViewById(R.id.txtACNo);
        TextView txtAcName = (TextView) rowView.findViewById(R.id.txtACName);
        TextView txtACBal = (TextView) rowView.findViewById(R.id.txtACBal);

        //TextView txtRate = (TextView) rowView.findViewById(R.id.rating);
        txtAc.setText(account_no[position]);
        txtAcName.setText(account_name[position]);
        txtACBal.setText(account_bal[position]);
        //txtRate.setText(rating[position]);

        return rowView;
    }
}