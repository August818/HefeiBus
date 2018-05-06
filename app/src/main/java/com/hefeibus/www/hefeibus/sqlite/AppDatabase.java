package com.hefeibus.www.hefeibus.sqlite;

import android.content.Context;
import android.database.Cursor;

import com.hefeibus.www.hefeibus.entity.GroupInfo;
import com.hefeibus.www.hefeibus.entity.LineData;
import com.hefeibus.www.hefeibus.utils.Parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppDatabase {
    private Context mContext;
    private DatabaseHelper mHelper;
    private String dbName = Parameters.DATABASE_NAME;
    private ArrayList<String> groupIndex;

    public AppDatabase(Context context) {
        mContext = context;
        init();
    }

    public AppDatabase(Context context, String dbName) {
        mContext = context;
        this.dbName = dbName;
        init();
    }

    private void init() {
        mHelper = new DatabaseHelper(mContext);
    }

    public HashMap<String, GroupInfo> queryGroupInfo() {
        HashMap<String, GroupInfo> infoHashMap = new HashMap<>();
        Cursor cursor = mHelper.executeCursor(dbName, "select * from GroupInfo", null);
        //分组索引
        groupIndex = new ArrayList<>();
        while (cursor.moveToNext()) {
            //线路列表
            List<LineData> lineList = new ArrayList<>();

            //分组详情
            GroupInfo info = new GroupInfo();

            //分组名
            String groupName = cursor.getString(cursor.getColumnIndex("name"));
            groupIndex.add(groupName);
            info.setGroupName(groupName);

            //线路详情
            String lines = cursor.getString(cursor.getColumnIndex("lines"));
            String[] lineArray = lines.split("\\|");
            for (String s : lineArray) {
                lineList.add(new LineData(s));
            }
            info.setLineList(lineList);
            info.setLineCount(lineList.size());

            //压入HashMap
            infoHashMap.put(groupName, info);
        }
        cursor.close();
        return infoHashMap;
    }

    public List<String> getGroupIndex() {
        return groupIndex;
    }

    public void close() {
        mHelper.close();
        mContext = null;
    }
}
