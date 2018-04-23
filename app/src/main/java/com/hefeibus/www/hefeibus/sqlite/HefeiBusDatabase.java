package com.hefeibus.www.hefeibus.sqlite;

import android.content.Context;
import android.database.Cursor;

import com.hefeibus.www.hefeibus.entity.GroupDetail;
import com.hefeibus.www.hefeibus.entity.Line;
import com.hefeibus.www.hefeibus.entity.Station;
import com.hefeibus.www.hefeibus.utils.Parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HefeiBusDatabase extends DataReveal {
    private List<String> groupIndex;

    public HefeiBusDatabase(Context context) {
        super(context);
    }

    @Override
    String specifyDBname() {
        return Parameters.LINE_GROUP_DATABASE_NAME;
    }

    /**
     * 查询线路分组信息
     *
     * @return 分组信息 HashMap
     */
    public HashMap<String, GroupDetail> queryAllGroupDetail() {
        HashMap<String, GroupDetail> detailHashMap = new HashMap<>();
        Cursor cursor = dbHelper.ExecuteCursor(dbName, "select * from LineGroup", null);
        //分组索引
        groupIndex = new ArrayList<>();
        while (cursor.moveToNext()) {
            //线路列表
            List<Line> lineList = new ArrayList<>();
            //分组详情
            GroupDetail detail = new GroupDetail();

            //分组名
            String groupName = cursor.getString(cursor.getColumnIndex("name"));
            detail.setGroupName(groupName);
            groupIndex.add(groupName);

            //线路数量
            int lineCount = cursor.getInt(cursor.getColumnIndex("count"));
            detail.setLineCount(lineCount);

            //线路详情
            String lines = cursor.getString(cursor.getColumnIndex("lines"));
            String[] lineArray = lines.split("\\|");
            for (String s : lineArray) {
                lineList.add(new Line(s));
            }
            detail.setLineList(lineList);

            //压入HashMap
            detailHashMap.put(groupName, detail);
        }
        return detailHashMap;
    }

    public List<String> queryGroupIndex() {
        return groupIndex;
    }


    public Line queryLineDetail(String lineName) {
        Cursor cursor = dbHelper.ExecuteCursor(dbName, "select * from LineDetail where linenumber = ?", new String[]{lineName});
        Line line = new Line(lineName);
        if (cursor.moveToFirst()) {
            line.setPrice(cursor.getString(cursor.getColumnIndex("pricedesc")));
            line.setDesc1(cursor.getString(cursor.getColumnIndex("start")));
            line.setDesc2(cursor.getString(cursor.getColumnIndex("end")));
            line.setStationCount(cursor.getInt(cursor.getColumnIndex("stationcount")));

            String stations = cursor.getString(cursor.getColumnIndex("station"));
            String[] stationArray = stations.split("\\|");
            List<Station> stationList = new ArrayList<>();
            for (String s : stationArray) {
                stationList.add(new Station(s));
            }
            line.setPassStationList(stationList);
            line.setSite1(stationList.get(0));
            line.setSite2(stationList.get(stationList.size() - 1));
        }
        return line;
    }


    public void close() {
        dbHelper.close();
    }
}
