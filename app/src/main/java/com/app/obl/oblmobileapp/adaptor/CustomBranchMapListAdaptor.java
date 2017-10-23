package com.app.obl.oblmobileapp.adaptor;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.helper.BranchLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class CustomBranchMapListAdaptor extends ArrayAdapter<BranchLocation> implements SectionIndexer{

    private final Activity context;
    ArrayList<BranchLocation> br_location_list;
    HashMap<String,Integer> alphaIndexer;
    String[] sections;
    public CustomBranchMapListAdaptor(Activity context, ArrayList<BranchLocation> branch_location_list) {
        super(context, R.layout.list_item_branch_loc,branch_location_list);
        this.context = context;
        this.br_location_list=branch_location_list;
        alphaIndexer=new HashMap<String,Integer>();
        int size= branch_location_list.size();

        for(int count=0; count < size ; count++)
        {
            String Character=branch_location_list.get(count).branch_name.substring(0,1).toUpperCase();
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
        BranchLocation brLocation=getItem(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item_branch_loc, null, true);
        TextView txtBrName = (TextView) rowView.findViewById(R.id.txt_br_name);
        TextView txtBrAddress = (TextView) rowView.findViewById(R.id.txt_br_address);
        TextView txtBrPhone = (TextView) rowView.findViewById(R.id.txt_br_phone);
        TextView txtBrMobile = (TextView) rowView.findViewById(R.id.txt_br_mobile);
        TextView txtBrFax = (TextView) rowView.findViewById(R.id.txt_br_fax);
        TextView txtBrEmail = (TextView) rowView.findViewById(R.id.txt_br_email);

        txtBrName.setText(brLocation.branch_name);
        txtBrAddress.setText(brLocation.branch_address);
        txtBrPhone.setText(brLocation.branch_phone);
        txtBrMobile.setText(brLocation.branch_mobile);
        txtBrFax.setText(brLocation.branch_fax);
        txtBrEmail.setText(brLocation.branch_email);

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