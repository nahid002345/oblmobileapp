package com.app.obl.oblmobileapp.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by ONE BANK 1 on 1/13/2016.
 */
public class LoanEMICalculation {
    private SimpleDateFormat dateFormatter=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    public static double loanInterestMethod= 360.0d;
    private double loanPrincipal;
    private double loanInterestRate;
    private double loanNoOfInstallment;

    private double loanMonthlyDays;

    public boolean isActualTimeLine;
    public boolean isDateRangeGiven;
    private Date loanValueDate;
    private Date loanMaturityDate;

    public double[] loanDaySize;
    public Date[] loanStartDate;
    public Date[] loanEndDate;

    private double[] loanSchFactor;
    private double[] loanInstallment;
    private double[] loanInstalDenom;

    public double[] loanPrincipalList;
    public double[] loanInterestList;
    public double[] loanOutStanding;
    public double[] loanEMI;

    public LoanEMICalculation(){

    }

    public LoanEMICalculation(double principal, double interestRate, double noOfInstallment, Date valueDate,Date maturityDate)
    {
        this.loanPrincipal=principal;
        this.loanInterestRate=interestRate/(100);
        this.loanNoOfInstallment=noOfInstallment;
        this.loanValueDate=valueDate;
        this.loanMaturityDate=maturityDate;
    }

    public LoanEMICalculation(double principal, double interestRate, int noOfInstallment)
    {
        this.loanPrincipal=principal;
        this.loanInterestRate=interestRate/(100);
        this.loanNoOfInstallment=noOfInstallment;
    }

    public double GetLoanEMIDetail()
    {
        double resultLoanEMIDetail=0;
        if (IsDateRangeGiven())
        {
            setDaySize(loanValueDate,loanMaturityDate,loanNoOfInstallment);
        }
        else
        {
            setDaySize(30.0,loanNoOfInstallment);
        }

        resultLoanEMIDetail=CalculateEMI(loanNoOfInstallment);

        return resultLoanEMIDetail;
    }
    public double CalculateEMI(double noOfInstallment)
    {
        double resultEMI=0;
        double totalSumInstallmentDenom=0;
        int queueSize=(int)noOfInstallment +1;
        loanSchFactor=new double[queueSize];
        loanInstalDenom=new double[queueSize];
        loanInstallment= new double[queueSize];
        //calculate sch factor & installment and store in queue
        loanSchFactor[0]=1.0f;
        loanInstallment[0]=1.0f;
        for(int loop=1;loop < queueSize; loop++ )
        {
            double divResult=((double)(loanDaySize[loop] / loanInterestMethod));
            loanSchFactor[loop]= 1+(loanInterestRate * divResult);
            loanInstallment[loop]=loanInstallment[loop - 1] * loanSchFactor[loop];
        }
        //calculate installment denom and store in queue
        loanInstalDenom[0]=0f;
        loanInstalDenom[1]= loanInstallment[loanInstallment.length - 1] / loanInstallment[1];
        for(int loop=2;loop < queueSize; loop++ )
        {
            loanInstalDenom[loop]=(double)(loanInstalDenom[loop-1]/loanSchFactor[loop]);
        }
        totalSumInstallmentDenom=ArithematicOperation.doubleArraySum(loanInstalDenom);
        resultEMI=ArithematicOperation.roundToDecimals((loanInstallment[(int) noOfInstallment] * loanPrincipal) / totalSumInstallmentDenom);

        if(isActualTimeLine) {
            //calculate principal,interest,outstanding and store in queue

            for (int loop = 1; loop < queueSize; loop++) {
                loanInterestList[loop] = (loanOutStanding[loop - 1] * loanInterestRate * loanDaySize[loop]) / loanInterestMethod;
                loanPrincipalList[loop] = loanInterestList[loop] - resultEMI;
                loanOutStanding[loop] = loanOutStanding[loop - 1] - loanPrincipalList[loop];
            }
        }


        return resultEMI;
    }

    private void setDaySize(double fixedDaySize,double noOfInstallment)
    {
        int queueSize=(int)noOfInstallment +1;
        loanDaySize=new double[queueSize];
        loanDaySize[0]=0;
        for(int loop=1;loop < queueSize; loop++ )
        {
            loanDaySize[loop]=fixedDaySize;
        }
    }


    private void setDaySize(Date valueDate, Date maturiyDate,double noOfInstallment)
    {
        int queueSize=(int)noOfInstallment +1;
        //declare detail variables
        loanDaySize=new double[queueSize];
        loanPrincipalList=new double[queueSize];
        loanInterestList= new double[queueSize];
        loanOutStanding=new double[queueSize];
        loanStartDate=new Date[queueSize];
        loanEndDate=new Date[queueSize];
        
        //initiate detail variables
        loanDaySize[0]=0;
        loanPrincipalList[0]=0;
        loanInterestList[0]=0;
        loanOutStanding[0]=loanPrincipal;

        Date lnStartDate=valueDate;
        for(int loop=1;loop < queueSize; loop++ )
        {
            loanStartDate[loop]=lnStartDate;
            lnStartDate= DateUtility.addMonths(lnStartDate,1);
            loanEndDate[loop]=lnStartDate;
            long dayDiff= loanEndDate[loop].getTime() - loanStartDate[loop].getTime();
            loanDaySize[loop]= TimeUnit.MILLISECONDS.toDays(dayDiff);

        }
    }



    private boolean IsDateRangeGiven()
    {
        boolean result=false;

        if(loanValueDate != null && loanMaturityDate != null) {
            isDateRangeGiven = true;
            isActualTimeLine = true;
            result=true;
        }
        else{
            isDateRangeGiven=false;
            isActualTimeLine=false;
            result=false;
        }

        return result;
    }
}
