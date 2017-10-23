package com.app.obl.oblmobileapp.database;

/**
 * Created by ONE BANK 1 on 1/19/2016.
 */
public class TblColumn {
    public String col_name;
    public String col_type;
    public boolean col_is_null=true;
    public boolean col_is_auto_increment=false;
    public boolean col_is_primary=false;
    public boolean col_is_valid=true;

    public TblColumn(String para_col_name,String para_col_type){
        col_name=para_col_name;
        col_type=para_col_type;
    }

    public TblColumn(String para_col_name,String para_col_type,boolean para_is_null){
        col_name=para_col_name;
        col_type=para_col_type;
        col_is_null=para_is_null;

    }

    public TblColumn(String para_col_name,String para_col_type,boolean para_is_null,boolean para_is_primary){
        col_name=para_col_name;
        col_type=para_col_type;
        col_is_null=false;
        col_is_primary=para_is_primary;
    }

    public void setPrimaryColumn(boolean para_is_primary){
        this.col_is_primary=para_is_primary;
    }
}
