package com.hefeibus.www.hefeibus.utils;

public class Parameters {
    //local database file name
    public static final String DATABASE_NAME = "local.db";

    //query city name
    public static final String API_QUERY_CITY_NAME = "合肥";

    //preference file name
    public static final String APP_PREFERENCES = "preferences";

    //Key of object transfer by Intent
    //传递线路信息键名
    public static final String INTENT_LINE_KEY = "pass_line_key";
    //传递站点信息键名
    public static final String INTENT_STATION_KEY = "pass_station_key";

    //Key string saved in Preference
    //缓存标记
    // true - 缓存 false - 关闭缓存
    public static final String IS_CACHING = "isCaching";
    //清空缓存
    //true - 下次启动时清空数据库 false - 不清空数据库
    public static final String CLEAR_CACHE = "clear_cache";

    //请求 Request Code
    //起点 Code
    public static final int CODE_START = 50;
    //终点 Code
    public static final int CODE_STOP = 60;
}
