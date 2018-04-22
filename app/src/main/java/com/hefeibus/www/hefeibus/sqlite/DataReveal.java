package com.hefeibus.www.hefeibus.sqlite;

import android.content.Context;

public abstract class DataReveal {

    protected Context mContext;
    protected DatabaseHelper dbHelper;
    protected String dbName;

    public DataReveal(Context context) {
        mContext = context;
        dbHelper = new DatabaseHelper(mContext);
        dbName = specifyDBname();
    }

    abstract String specifyDBname();


}
