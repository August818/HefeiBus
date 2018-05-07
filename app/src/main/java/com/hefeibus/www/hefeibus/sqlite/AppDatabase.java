package com.hefeibus.www.hefeibus.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hefeibus.www.hefeibus.entity.GroupInfo;
import com.hefeibus.www.hefeibus.entity.LineData;
import com.hefeibus.www.hefeibus.utils.Parameters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AppDatabase {
    private static final String TAG = "AppDatabase";
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

    /**
     * @param lineData 将线路信息写入本地
     */
    public void writeLineToLocal(LineData lineData) {
        String[] selection = new String[13];
        //id
        selection[0] = String.valueOf(lineData.getLineId());
        //name
        selection[1] = lineData.getLineName();
        //org
        selection[2] = lineData.getOrg();
        //category
        selection[3] = String.valueOf(lineData.getRunCateGory());
        //upLength
        selection[4] = String.valueOf(lineData.getUpLen());
        //downLength
        selection[5] = String.valueOf(lineData.getDownLen());
        //mainTime
        selection[6] = lineData.getMainFirstTime();
        selection[7] = lineData.getMainLastTime();
        //subTime
        selection[8] = lineData.getSubFirstTime();
        selection[9] = lineData.getSubLastTime();
        //upStation
        StringBuilder sb = new StringBuilder();
        for (LineData.StationBean bean : lineData.getUpStation()) {
            sb.append(bean.getStationName());
            sb.append("|");
        }
        selection[10] = sb.toString();
        //downStation
        if (lineData.getRunCateGory() == 1 || lineData.getRunCateGory() == 3) {
            sb = new StringBuilder();
            for (LineData.StationBean bean : lineData.getDownStation()) {
                sb.append(bean.getStationName());
                sb.append("|");
            }
            selection[11] = sb.toString();
        } else {
            selection[11] = "";
        }
        selection[12] = Calendar.getInstance().getTime().toString();
        int count = mHelper.executeModifyCount(dbName,
                "insert into LineCache (id,name,org,category,upLength,downLength,mainFirstTime,mainLastTime," +
                        "subFirstTime,subLastTime,upStation,downStation,timeStamp) values (?,?,?,?,?,?,?,?,?,?,?,?,?) "
                , selection);
        if (count != -1) {
            Log.d(TAG, "writeLineToLocal: 数据库插入成功！");
        }
    }

    public LineData queryLineFromLocal(String lineName) {
        LineData lineData = new LineData(lineName);
        Cursor cursor = mHelper.executeCursor(dbName, "select * from LineCache where name = ?", new String[]{lineName});
        if (cursor.moveToFirst()) {
            //id
            lineData.setLineId(cursor.getInt(cursor.getColumnIndex("id")));
            //name
            lineData.setLineName(cursor.getString(cursor.getColumnIndex("name")));
            //org
            lineData.setOrg(cursor.getString(cursor.getColumnIndex("org")));
            //category
            lineData.setRunCateGory(cursor.getInt(cursor.getColumnIndex("category")));
            //Length
            lineData.setUpLen(cursor.getDouble(cursor.getColumnIndex("upLength")));
            lineData.setDownLen(cursor.getDouble(cursor.getColumnIndex("downLength")));
            //time
            lineData.setMainFirstTime(cursor.getString(cursor.getColumnIndex("mainFirstTime")));
            lineData.setMainLastTime(cursor.getString(cursor.getColumnIndex("mainLastTime")));
            lineData.setSubFirstTime(cursor.getString(cursor.getColumnIndex("subFirstTime")));
            lineData.setSubLastTime(cursor.getString(cursor.getColumnIndex("subLastTime")));
            //upStation
            List<LineData.StationBean> upStation = new ArrayList<>();
            String upStationString = cursor.getString(cursor.getColumnIndex("upStation"));
            String[] up = upStationString.split("\\|");
            for (String s : up) {
                upStation.add(new LineData.StationBean(s));
            }
            lineData.setUpCount(upStation.size());
            lineData.setUpStation(upStation);
            //downStation
            if (lineData.getRunCateGory() == 1 || lineData.getRunCateGory() == 3) {
                List<LineData.StationBean> downStation = new ArrayList<>();
                String downStationString = cursor.getString(cursor.getColumnIndex("downStation"));
                String[] down = downStationString.split("\\|");
                for (String s : down) {
                    downStation.add(new LineData.StationBean(s));
                }
                lineData.setDownCount(downStation.size());
                lineData.setDownStation(downStation);
            }
            lineData.isFromLocal();
        } else {
            lineData.setLineId(0);
        }
        return lineData;
    }
}
