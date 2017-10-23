package com.app.obl.oblmobileapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.activity.ExchangeRateActvity;
import com.app.obl.oblmobileapp.app.ArithematicOperation;
import com.app.obl.oblmobileapp.helper.CustomWarningAlertDialog;
import com.app.obl.oblmobileapp.helper.ExchangeRate;
import com.app.obl.oblmobileapp.helper.Validation;

import java.util.ArrayList;

/**
 * Created by ONE BANK 1 on 12/4/2015.
 */
public class TabFragExchangeRateCalculate extends Fragment {
    View view;
    EditText mExchangeAmount;
    Spinner mCurrency;
    Spinner mExchangeType;
    TextView mTotalAmount;

    Button btnExchangeAmount;
    Button btnReset;

    ArrayList<ExchangeRate> erList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_er_calculator, container, false);

        mCurrency=(Spinner)view.findViewById(R.id.sp_er_currency);
        mExchangeType=(Spinner)view.findViewById(R.id.sp_er_type);
        mExchangeAmount=(EditText)view.findViewById(R.id.txt_er_amount);
        mTotalAmount=(TextView)view.findViewById(R.id.txt_er_total_amt);
        btnReset=(Button)view.findViewById(R.id.btn_er_cal_reset);
        btnExchangeAmount=(Button)view.findViewById(R.id.btn_er_calculate);

        btnExchangeAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erList=((ExchangeRateActvity)getActivity()).GetExchangeRate();
                if(erList != null) {
                    if (CheckValidation()) {
                        double er_amount = Double.valueOf(mExchangeAmount.getText().toString());
                        String er_currency = mCurrency.getSelectedItem().toString();
                        String er_type = mExchangeType.getSelectedItem().toString();
                        double er_rate = 0;
                        for (ExchangeRate d : erList) {
                            if (d.er_currency != null && d.er_currency.contains(er_currency)) {
                                if (er_type.equals("BUY"))
                                    er_rate = Double.valueOf(d.er_buy_rate);
                                else
                                    er_rate = Double.valueOf(d.er_sell_rate);
                                break;
                            }
                        }
                        double totalAmt=er_amount * er_rate;
                        //String strTotalResult=er_type+" "+ String.valueOf(er_amount) + " "+er_currency+"=" +String.valueOf(ArithematicOperation.roundToDecimals(totalAmt)) +"৳";
                        String strTotalResult=er_type+" "+ String.valueOf(ArithematicOperation.roundToDecimals(totalAmt)) +"৳";
                        mTotalAmount.setText(strTotalResult);
                    }
                }
                else
                {
                    CustomWarningAlertDialog alert = new CustomWarningAlertDialog();
                    alert.showDialog(getActivity(), "Exchange Rate is not loaded !!!");
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frg = TabFragExchangeRateCalculate.this;
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
            }
        });

        return view;
    }

    private boolean CheckValidation()
    {
        boolean isValid=true;
        if(!Validation.hasText(mExchangeAmount)) isValid=false;
        return isValid;
    }



}
