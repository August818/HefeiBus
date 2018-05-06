package com.hefeibus.www.hefeibus.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hefeibus.www.hefeibus.utils.Parameters;

/**
 * 数据库操作对象
 */

public class DatabaseHelper {

    private Context context;
    private SQLiteDatabase database;

    private static final String TAG = "DatabaseHelper";

    DatabaseHelper(Context context) {
        this.context = context;
    }

    private SQLiteDatabase getDatabase(String dbName) {
        try {
            if (database != null && database.isOpen()) return database;
            if (dbName == null || dbName.equals(""))
                throw new UnsupportedOperationException("Need Database Name!");
            database = SQLiteDatabase.openDatabase(
                    context.getFilesDir().getAbsolutePath() + "/" + Parameters.DATABASE_NAME,
                    null,
                    SQLiteDatabase.OPEN_READWRITE);
            return database;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return database;
    }

    /**
     * 执行数据库 SQL 操作
     *
     * @param dbName        操作的数据库名称
     * @param cmdText       SQL语句
     * @param selectionArgs 替换内容
     * @return 查询游标
     */
    public Cursor executeCursor(String dbName, String cmdText, String[] selectionArgs) {
        return getDatabase(dbName).rawQuery(cmdText, selectionArgs);
    }

    /**
     * 执行数据库 SQL 操作
     *
     * @param dbName        操作的数据库名称
     * @param cmdText       SQL语句
     * @param selectionArgs 替换内容
     * @return 影响行数
     */
    public int executeModifyCount(String dbName, String cmdText, String[] selectionArgs) {
        Cursor cursor = getDatabase(dbName).rawQuery(cmdText, selectionArgs);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
            Log.d(TAG, "close: database close successfully");
        }
    }
}









