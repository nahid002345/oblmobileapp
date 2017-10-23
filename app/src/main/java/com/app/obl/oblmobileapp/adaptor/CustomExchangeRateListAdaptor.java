package com.app.obl.oblmobileapp.adaptor;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.helper.ExchangeRate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class CustomExchangeRateListAdaptor extends ArrayAdapter<ExchangeRate> implements SectionIndexer{

    private final Activity context;
    ArrayList<ExchangeRate> exchange_rate_list;
    HashMap<String,Integer> alphaIndexer;
    String[] sections;
    public CustomExchangeRateListAdaptor(Activity context, ArrayList<ExchangeRate> exchange_rate_list) {
        super(context, R.layout.list_item_exchange_rate,exchange_rate_list);
        this.context = context;
        this.exchange_rate_list=exchange_rate_list;
        alphaIndexer=new HashMap<String,Integer>();
        int size= exchange_rate_list.size();

        for(int count=0; count < size ; count++)
        {
            String Character=exchange_rate_list.get(count).er_currency.substring(0,1).toUpperCase();
            if (!alphaIndexer.containsKey(Character))
                alphaIndexer.put(Character,count);

        }
        Set<String> sectionLetters=alphaIndexer.keySet();
        ArrayList<String> sectionList= new ArrayList<String>(sectionLetters);
        Collections.sort(sectionList);
        sections=new String[sectionList.size()];
        sectionList.toArray(sections);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ExchangeRate brLocation=getItem(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item_exchange_rate, null, true);
        TextView txtERCurrency = (TextView) rowView.findViewById(R.id.txt_er_currency);
        TextView txtERBuyRate = (TextView) rowView.findViewById(R.id.txt_er_buy_rate);
        TextView txtERSellRate = (TextView) rowView.findViewById(R.id.txt_er_sell_rate);


        txtERCurrency.setText(brLocation.er_currency);
        txtERBuyRate.setText(brLocation.er_buy_rate);
        txtERSellRate.setText(brLocation.er_sell_rate);

        return rowView;
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return alphaIndexer.get(sections[sectionIndex]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}