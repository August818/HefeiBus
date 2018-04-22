package com.hefeibus.www.hefeibus.sqlite;

import android.content.Context;
import android.database.Cursor;

import com.hefeibus.www.hefeibus.entity.GroupDetail;
import com.hefeibus.www.hefeibus.entity.Line;
import com.hefeibus.www.hefeibus.utils.Parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupLineDatabase extends DataReveal {
    private List<String> groupIndex;

    public GroupLineDatabase(Context context) {
        super(context);
    }

    @Override
    String specifyDBname() {
        return Parameters.LINE_GROUP_DATABASE_NAME;
    }

    public HashMap<String, GroupDetail> queryAllGroupDetail() {
        HashMap<String, GroupDetail> detailHashMap = new HashMap<>();
        Cursor cursor = dbHelper.ExecuteCursor(dbName,
                "select * from LineGroup", null);
        //分组索引
        groupIndex = new ArrayList<>();
        while (cursor.moveToNext()) {
            //线路列表
            List<Line> lineList = new ArrayList<>();
            //分组详情
            GroupDetail detail = new GroupDetail();

            //分组名
            String groupName = cursor.getString(cursor.getColumnIndex("name"));
            //线路数量
            int lineCount = cursor.getInt(cursor.getColumnIndex("count"));
            //线路详情
            String lines = cursor.getString(cursor.getColumnIndex("lines"));
            String[] lineArray = lines.split("\\|");
            for (String s : lineArray) {
                lineList.add(new Line(s));
            }
            detail.setGroupName(groupName);
            detail.setLineList(lineList);
            detail.setLineCount(lineCount);
            groupIndex.add(groupName);
            detailHashMap.put(groupName, detail);
        }
        return detailHashMap;
    }

    public void close() {
        dbHelper.close();
    }


    public GroupDetail queryGroupDetail(String groupName) {
        Cursor cursor = dbHelper.ExecuteCursor(dbName,
                "select * from LineGroup where name = ?", new String[]{groupName});
        GroupDetail groupDetail = new GroupDetail();
        if (cursor.moveToFirst()) {
            groupDetail.setGroupName(cursor.getString(cursor.getColumnIndex("name")));
            groupDetail.setLineCount(cursor.getInt(cursor.getColumnIndex("count")));
        }
        return groupDetail;
    }

    public List<String> queryGroupIndex() {
        return groupIndex;
    }
}
