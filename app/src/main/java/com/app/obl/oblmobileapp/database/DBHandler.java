package com.app.obl.oblmobileapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

/**
 * Created by ONE BANK 1 on 1/19/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "OBLMOBILEAPP";

    // Account List table name
    private static final String TABLE_ACCOUNT_LIST = "ACLIST";

    // Account List Table Columns names
    private static final String KEY_AC_LIST_ID = "ID";
    private static final String KEY_AC_LIST_NAME = "ACNAME";
    private static final String KEY_AC_LIST_NO = "ACNO";
    private static final String KEY_AC_LIST_BAL = "ACBAL";


    // Account List table name
    private static final String TABLE_EXCHANGE_RATE = "EXCHANGERATE";

    // Account List Table Columns names
    private static final String KEY_ER_ID = "ID";
    private static final String KEY_ER_DATE = "DATE";
    private static final String KEY_ER_CURRENCY = "CURRENCY";
    private static final String KEY_ER_TYPE = "TYPE";
    private static final String KEY_ER_BUY_RATE = "BUYRATE";
    private static final String KEY_ER_SALE_RATE = "SALERATE";
    public static  DBTable dbEr=null;
    public static  DBTable dbAcList=null;


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private DBTable createAcListTable(){
        dbAcList=new DBTable(TABLE_ACCOUNT_LIST);

        TblColumn tbl_Id= new TblColumn(KEY_AC_LIST_ID,DBDataType.DT_INTEGER,false,true);
        TblColumn tbl_ac_list_name= new TblColumn(KEY_AC_LIST_NAME,DBDataType.DT_TEXT);
        TblColumn tbl_ac_list_no= new TblColumn(KEY_AC_LIST_NO,DBDataType.DT_TEXT);
        TblColumn tbl_ac_list_balance= new TblColumn(KEY_AC_LIST_BAL,DBDataType.DT_REAL);

        dbAcList.tbl_column_list.add(tbl_Id);
        dbAcList.tbl_column_list.add(tbl_ac_list_name);
        dbAcList.tbl_column_list.add(tbl_ac_list_no);
        dbAcList.tbl_column_list.add(tbl_ac_list_balance);

        return  dbAcList;
    }

    private DBTable createExChangeRateTable(){
        dbEr=new DBTable(TABLE_EXCHANGE_RATE);

        TblColumn tbl_Id= new TblColumn(KEY_ER_ID,DBDataType.DT_INTEGER,false,true);
        TblColumn tbl_er_date= new TblColumn(KEY_ER_DATE,DBDataType.DT_NUMERIC);
        TblColumn tbl_currency= new TblColumn(KEY_ER_CURRENCY,DBDataType.DT_TEXT);
        TblColumn tbl_er_type= new TblColumn(KEY_ER_TYPE,DBDataType.DT_TEXT);
        TblColumn tbl_er_buy= new TblColumn(KEY_ER_BUY_RATE,DBDataType.DT_REAL);
        TblColumn tbl_er_sale= new TblColumn(KEY_ER_SALE_RATE,DBDataType.DT_REAL);

        dbEr.tbl_column_list.add(tbl_Id);
        dbEr.tbl_column_list.add(tbl_er_date);
        dbEr.tbl_column_list.add(tbl_currency);
        dbEr.tbl_column_list.add(tbl_er_type);
        dbEr.tbl_column_list.add(tbl_er_buy);
        dbEr.tbl_column_list.add(tbl_er_sale);

        return  dbEr;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "";
        DBTable tbl_ac_list=createAcListTable();
       /* if(tbl_ac_list != null) {
            CREATE_TABLE_QUERY = tbl_ac_list.createTableStatement();
            db.execSQL(CREATE_TABLE_QUERY);
        }*/

        DBTable tbl_er=createExChangeRateTable();
        if(tbl_er != null) {
            CREATE_TABLE_QUERY = tbl_er.createTableStatement();
            db.execSQL(CREATE_TABLE_QUERY);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ");
        onCreate(db);
    }

    public <T> void InsertData (DBTable para_table, ArrayList<T> para_data_list,Class<T> type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(Object data : para_data_list ){
            Class<T> persistentClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            if(type.isAssignableFrom(data.getClass())){
                Field[] field_list=persistentClass.getFields();
                for(Field f : field_list){
                }
            }
        }
/*

        values.put(KEY_TODO, todo.getNote());
        values.put(KEY_STATUS, todo.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long todo_id = db.insert(TABLE_TODO, null, values);*/

    }
}
