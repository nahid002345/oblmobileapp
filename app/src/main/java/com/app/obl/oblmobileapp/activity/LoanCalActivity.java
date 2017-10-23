package com.app.obl.oblmobileapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.obl.oblmobileapp.R;
import com.app.obl.oblmobileapp.app.DateUtility;
import com.app.obl.oblmobileapp.app.LoanEMICalculation;
import com.app.obl.oblmobileapp.helper.Validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoanCalActivity extends BaseActivity implements View.OnClickListener {

    private static final String TOOLBAR_TITLE = "Loan Calculator";
    private Button btnLoanCal;
    private Button btnReset;
    private EditText txtLoanPrincipal;
    private EditText txtInterestRate;
    private EditText txtTenure;
    private TextView txtLoanResult;
    private TextView txtFromDate;
    private TextView txtToDate;
    private RelativeLayout rLayout;

    private DatePickerDialog fromDatePickerDialog;
    //private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loan_cal);
        initiateToolbar();

        btnReset=(Button)findViewById(R.id.btn_loan_cal_reset);
        btnLoanCal=(Button)findViewById(R.id.btnCalculate);
        txtLoanPrincipal=(EditText)findViewById(R.id.LoanPrincipal);
        txtInterestRate=(EditText)findViewById(R.id.LoanInterest);
        txtTenure=(EditText)findViewById(R.id.LoanMonth);
        txtFromDate=(TextView)findViewById(R.id.txt_loan_from_date);
        txtToDate=(TextView)findViewById(R.id.txt_loan_to_date);
        txtLoanResult=(TextView)findViewById(R.id.txtLoanResult);
        rLayout=(RelativeLayout)findViewById(R.id.relativeLayout);

        setDateTimeField();

        btnLoanCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckValidation()) {
                    double lnPrincipal=Double.valueOf(txtLoanPrincipal.getText().toString());
                    double lnInterestRate=Double.valueOf(txtInterestRate.getText().toString());
                    int lnInstallmentSize=Integer.valueOf(txtTenure.getText().toString());

                    String lnFromDate=txtFromDate.getText().toString();
                    String lnMaturityDate=txtToDate.getText().toString();
                    rLayout.setVisibility(View.VISIBLE);
                    LoanEMICalculation mLoanEMI=null;

                    if (!lnFromDate.isEmpty() && !lnMaturityDate.isEmpty() )
                    {
                        try{
                            Date dtValue= dateFormatter.parse(lnFromDate);
                            Date dtMaturity= dateFormatter.parse(lnMaturityDate);
                            mLoanEMI=new LoanEMICalculation(lnPrincipal,lnInterestRate,lnInstallmentSize,dtValue,dtMaturity);
                        }
                        catch (ParseException ex)
                        {

                        }

                    }
                    else
                    {
                        mLoanEMI=new LoanEMICalculation(lnPrincipal,lnInterestRate,lnInstallmentSize);
                    }

                    double emiAmount=mLoanEMI.GetLoanEMIDetail();
                    txtLoanResult.setText(String.valueOf(emiAmount));

                    InputMethodManager imm = (InputMethodManager)getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetIntent=getIntent();
                finish();
                startActivity(resetIntent);
            }
        });
    }

    private void setDateTimeField() {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        txtFromDate.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                int lnInstallmentSize=Integer.valueOf(txtTenure.getText().toString());
                txtFromDate.setText(dateFormatter.format(newDate.getTime()));
                txtToDate.setText(dateFormatter.format(DateUtility.addMonths(newDate.getTime(),lnInstallmentSize).getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /*toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtToDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));*/
    }

    private void initiateToolbar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(TOOLBAR_TITLE);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setIcon(R.drawable.ic_toolbar_logo);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    private boolean CheckValidation()
    {
        boolean isValid=true;
        if(!Validation.hasText(txtLoanPrincipal)) isValid=false;
        if(!Validation.hasText(txtInterestRate)) isValid=false;
        if(!Validation.hasText(txtTenure)) isValid=false;
        return isValid;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view == txtFromDate) {
            if(Validation.hasText(txtTenure)) {
                fromDatePickerDialog.show();
            }
        }
    }
}
