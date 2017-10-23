package com.app.obl.oblmobileapp.adaptor;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.helper.AtmLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class CustomAtmMapListAdaptor extends ArrayAdapter<AtmLocation> implements SectionIndexer{

    private final Activity context;
    ArrayList<AtmLocation> atm_location_list;
    HashMap<String,Integer> alphaIndexer;
    String[] sections;
    public CustomAtmMapListAdaptor(Activity context, ArrayList<AtmLocation> atm_location_list) {
        super(context, R.layout.list_item_atm_loc,atm_location_list);
        this.context = context;
        this.atm_location_list=atm_location_list;
        alphaIndexer=new HashMap<String,Integer>();
        int size= atm_location_list.size();

        for(int count=0; count < size ; count++)
        {
            String Character=atm_location_list.get(count).atm_name.substring(0,1).toUpperCase();
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
        AtmLocation brLocation=getItem(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item_atm_loc, null, true);
        TextView txtAtmName = (TextView) rowView.findViewById(R.id.txt_atm_name);
        TextView txtAtmAddress = (TextView) rowView.findViewById(R.id.txt_atm_address);
        TextView txtAtmThana = (TextView) rowView.findViewById(R.id.txt_atm_thana);
        TextView txtAtmDist = (TextView) rowView.findViewById(R.id.txt_atm_district);
        TextView txtAtmDiv = (TextView) rowView.findViewById(R.id.txt_atm_division);


        txtAtmName.setText(brLocation.atm_name);
        txtAtmAddress.setText(brLocation.atm_address);
        txtAtmThana.setText(brLocation.atm_thana);
        txtAtmDist.setText(brLocation.atm_district);
        txtAtmDiv.setText(brLocation.atm_division);

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