package com.app.obl.oblmobileapp.adaptor;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;

public class CustomCardListAdaptor extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] card_no;
    private final String[] card_name;
    private final String[] card_bal;
    public CustomCardListAdaptor(Activity context,
                                 String[] card_no,String[] card_name,String[] card_bal) {
        super(context, R.layout.list_row, card_no);
        this.context = context;
        this.card_no = card_no;
        this.card_name = card_name;
        this.card_bal = card_bal;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item_user_card, null, true);
        TextView txtCardNo = (TextView) rowView.findViewById(R.id.txtCardNo);
        TextView txtCardName = (TextView) rowView.findViewById(R.id.txtCardName);
        TextView txtCardBal = (TextView) rowView.findViewById(R.id.txtCardBal);
        txtCardNo.setText(card_no[position]);
        txtCardName.setText(card_name[position]);
        txtCardBal.setText(card_bal[position]);
        return rowView;
    }
}