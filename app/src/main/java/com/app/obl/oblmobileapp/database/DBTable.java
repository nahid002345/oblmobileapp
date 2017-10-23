package com.app.obl.oblmobileapp.database;

import java.util.ArrayList;

/**
 * Created by ONE BANK 1 on 1/19/2016.
 */
public class DBTable {
    public String tbl_name;
    public boolean flag_if_not_exist=true;
    public ArrayList<TblColumn> tbl_column_list;
    public static String TAG_PRIMARY="PRIMARY";
    public static String TAG_NULL="NULL";
    public static String TAG_NOT_NULL="NOT NULL";
    public static String TAG_AUTO_INCREMENT="AUTOINCREMENT";

    public DBTable(String para_tabl_name)
    {
        tbl_name=para_tabl_name;
        tbl_column_list= new ArrayList<TblColumn>();
    }

    public DBTable(String para_tabl_name,ArrayList<TblColumn> para_column_list)
    {
        tbl_name=para_tabl_name;
        tbl_column_list= para_column_list;
    }

    public int getColumnSize(){
        return tbl_column_list.size();
    }

    public String createTableStatement()
    {
        String str_create_tbl=new String();
        if (!tbl_name.isEmpty()){
            if (!flag_if_not_exist)
                str_create_tbl+="CREATE TABLE "+tbl_name;
            else
                str_create_tbl+="CREATE TABLE IF NOT EXISTS "+tbl_name;
            if(tbl_column_list.size() > 0) {
                str_create_tbl+=" ( ";
                for(int loop=0; loop < tbl_column_list.size();loop++){
                    if(!tbl_column_list.get(loop).col_name.trim().isEmpty() && !tbl_column_list.get(loop).col_type.trim().isEmpty()) {
                        str_create_tbl += tbl_column_list.get(loop).col_name + " " +tbl_column_list.get(loop).col_type +" ";
                        if(tbl_column_list.get(loop).col_is_primary)
                            str_create_tbl += " " +TAG_PRIMARY +" ";

                        if(tbl_column_list.get(loop).col_is_auto_increment && tbl_column_list.get(loop).col_type.equals(DBDataType.DT_INTEGER))
                            str_create_tbl += " " +TAG_AUTO_INCREMENT +" ";

                        if(tbl_column_list.get(loop).col_is_null)
                            str_create_tbl += " " + TAG_NULL + " ";
                        else
                            str_create_tbl += " " + TAG_NOT_NULL + " ";

                        if(loop < (tbl_column_list.size() - 1))
                            str_create_tbl += " , ";
                    }
                }
                str_create_tbl+=" ); ";
            }
        }
        return str_create_tbl;
    }
}
