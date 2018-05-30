package com.hefeibus.www.hefeibus.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hefeibus.www.hefeibus.entity.GroupInfo;
import com.hefeibus.www.hefeibus.entity.Item;
import com.hefeibus.www.hefeibus.entity.LineData;
import com.hefeibus.www.hefeibus.entity.StationData;
import com.hefeibus.www.hefeibus.entity.TransferData;
import com.hefeibus.www.hefeibus.entity.Wrapper;
import com.hefeibus.www.hefeibus.utils.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 这一层是对访问数据库操作数据的一层封装
 * 执行SQL后，取到一个数据游标 Cursor
 * 利用Cursor组成所需要的数据
 * <p>
 * Model 抽象层
 * <p>
 * 取完数据之后 Cursor 要 close()
 */
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

    /**
     * 初始化Database访问层
     */
    private void init() {
        mHelper = new DatabaseHelper(mContext);
    }

    /**
     * 查询首页的线路分组数据，返回的是一个 HashMap
     *
     * @return HashMap 中包含了 分组名 和 分组详情（GroupInfo）
     * <p>
     * GroupInfo 对象封装了 GroupName 和 线路 Line 的单链表
     */
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

    /**
     * @return 分组名称索引
     * <p>
     * index - GroupName - GroupInfo
     */
    public List<String> getGroupIndex() {
        return groupIndex;
    }

    /**
     * 写入一条线路信息到本地
     *
     * @param lineData 线路信息
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
        mHelper.executeModifyCount(dbName,
                "insert into LineCache (id,name,org,category,upLength,downLength,mainFirstTime,mainLastTime," +
                        "subFirstTime,subLastTime,upStation,downStation,timeStamp) values (?,?,?,?,?,?,?,?,?,?,?,?,?) "
                , selection);
        Log.d(TAG, "writeLineToLocal: 数据库插入成功！");
    }

    /**
     * 从数据库中，根据线路名称取出一条线路信息
     * 如果没有将返回 id 为 0 的 LineData
     *
     * @param lineName 线路名
     * @return 线路信息 or 空（id为0）
     */
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

    /**
     * 将站点信息写入本地
     *
     * @param stationData 站点信息
     */
    public void writeStationToLocal(List<StationData> stationData) {
        for (StationData data : stationData) {
            String[] selection = new String[7];
            //id
            selection[0] = data.getStationPointId();
            //name
            selection[1] = data.getStationName();
            //ori
            selection[2] = data.getOrientationInfo();
            //line
            selection[3] = data.getLineName();
            //lat
            selection[4] = data.getLat();
            //lng
            selection[5] = data.getLng();
            //time
            selection[6] = Calendar.getInstance().getTime().toString();
            mHelper.executeModifyCount(dbName,
                    "insert into StationCache (id,name,orientation,line,lat,lng,timeStamp) values (?,?,?,?,?,?,?)", selection);
            Log.d(TAG, "writeLineToLocal: 数据库插入成功！" + data.getStationName() + "  " + data.getOrientationInfo());
        }
    }

    /**
     * 从本地取出对应名字的站点信息
     * <p>
     * tip: 站点信息是一个 List，根据站点的方向分组
     *
     * @param name 站点名称
     * @return 站点信息 or 空的列表 list.size() == 0
     */
    public List<StationData> queryStationFromLocal(String name) {
        List<StationData> stationData = new ArrayList<>();
        Cursor cursor = mHelper.executeCursor(dbName, "select * from StationCache where name = ?", new String[]{name});
        while (cursor.moveToNext()) {
            StationData data = new StationData();
            //id
            data.setStationPointId(cursor.getString(cursor.getColumnIndex("id")));
            //name
            data.setStationName(cursor.getString(cursor.getColumnIndex("name")));
            //ori
            data.setOrientationInfo(cursor.getString(cursor.getColumnIndex("orientation")));
            //line
            data.setLineName(cursor.getString(cursor.getColumnIndex("line")));
            //lat
            data.setLat(cursor.getString(cursor.getColumnIndex("lat")));
            //lng
            data.setStationPointId(cursor.getColumnName(cursor.getColumnIndex("lng")));
            stationData.add(data);
        }
        cursor.close();
        return stationData;
    }

    /**
     * 为自动补全的搜索功能提供数据源
     *
     * @return 数据list 站点+线路
     */
    public List<Item> queryResultSet() {
        List<Item> resultSet = new ArrayList<>();
        Cursor cursor = mHelper.executeCursor(dbName,
                "select value from AutoComplete where name = ?",
                new String[]{"line"});
        if (cursor.moveToFirst()) {
            String largeStation = cursor.getString(0);
            String[] array = largeStation.split(",");
            for (String s : array) {
                String line = s + "路";
                resultSet.add(new Item(line, 1));
            }
        }
        cursor.close();
        cursor = mHelper.executeCursor(dbName,
                "select value from AutoComplete where name = ?",
                new String[]{"station"});
        if (cursor.moveToFirst()) {
            String largeStation = cursor.getString(0);
            String[] array = largeStation.split(",");
            for (String s : array) {
                resultSet.add(new Item(s, 0));
            }
        }
        cursor.close();
        return resultSet;
    }

    /**
     * 这个是 换乘功能 为换乘站点提供数据源
     *
     * @return List 站点信息
     */
    public List<String> getStationIndex() {
        List<String> resultSet = new ArrayList<>();
        Cursor cursor = mHelper.executeCursor(dbName,
                "select value from AutoComplete where name = ?",
                new String[]{"station"});
        if (cursor.moveToFirst()) {
            String largeStation = cursor.getString(0);
            String[] array = largeStation.split(",");
            resultSet.addAll(Arrays.asList(array));
        }
        return resultSet;
    }

    /**
     * 从本地查询换乘数据
     *
     * @param start 起点
     * @param stop  终点
     * @return 返回 长度为0 的列表，强制从网络查询
     */
    public List<TransferData> queryTransferPlanFromLocal(String start, String stop) {
        return new ArrayList<>();
    }

    /**
     * 将换乘数据写入本地
     * <p>
     * TODO：还没有加入保存换乘数据的实体类
     */
    public void writeTransferPlanToLocal(String start, String stop) {

    }

    /**
     * 释放资源
     */
    public void close() {
        mHelper.close();
        mContext = null;
    }

    /**
     * @return 线路记录
     */
    public List<String> getLineRec() {
        List<String> var = new ArrayList<>();
        Cursor cursor = mHelper.executeCursor(dbName,
                "select distinct value from HistoryRecord where type = 1 limit 5", null);
        while (cursor.moveToNext()) {
            var.add(cursor.getString(cursor.getColumnIndex("value")));
        }
        cursor.close();
        return var;
    }

    /**
     * @return 车站记录
     */
    public List<String> getStationRec() {
        List<String> var = new ArrayList<>();
        Cursor cursor = mHelper.executeCursor(dbName,
                "select distinct value from HistoryRecord where type = 2 limit 5", null);
        while (cursor.moveToNext()) {
            var.add(cursor.getString(cursor.getColumnIndex("value")));
        }
        cursor.close();
        return var;

    }

    /**
     * @return 换乘记录
     */
    public List<Wrapper> getTransferRec() {
        List<Wrapper> var = new ArrayList<>();
        Cursor cursor = mHelper.executeCursor(dbName,
                "select distinct value from HistoryRecord where type = 3 limit 5", null);
        while (cursor.moveToNext()) {
            String var2 = cursor.getString(cursor.getColumnIndex("value"));
            String[] var3 = var2.split("\\|");
            var.add(new Wrapper(var3[0], var3[1]));
        }
        cursor.close();
        return var;
    }
}
