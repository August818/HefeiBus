package com.hefeibus.www.hefeibus.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hefeibus.www.hefeibus.utils.Parameters;

/**
 * 数据库操作对象
 */

public class DatabaseHelper {

    private Context context;
    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        this.context = context;
    }


    private SQLiteDatabase getDatabase(String dbName) {
        try {
            if (database != null && database.isOpen()) return database;
            if (dbName == null || dbName.equals(""))
                throw new NoSuchFieldError("Need Database Name!");

            database = SQLiteDatabase.openDatabase(
                    context.getFilesDir().getAbsolutePath() + "/" + Parameters.LINE_GROUP_DATABASE_NAME,
                    null,
                    SQLiteDatabase.OPEN_READONLY);

            return database;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return database;
    }

    public Cursor ExecuteCursor(String dbName, String cmdText, String[] selectionArgs) {
        return ExecuteCursor(getDatabase(dbName), cmdText, selectionArgs);
    }

    private Cursor ExecuteCursor(SQLiteDatabase database, String cmdText,
                                 String[] selectionArgs) {
        return database.rawQuery(cmdText, selectionArgs);
    }


    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}









