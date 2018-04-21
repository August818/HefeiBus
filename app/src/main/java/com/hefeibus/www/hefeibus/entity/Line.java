package com.hefeibus.www.hefeibus.entity;

import java.util.List;

/**
 * 公交线路 model
 * <Br>
 * price    3、4、10月份票价1元，其他月份票价2元
 * * <Br>
 * <p>
 * lineName 快速公交1号线
 * * <Br>
 * <p>
 * desc1    5：30-22：30
 * * <Br>
 * <p>
 * desc2    6:00-23:00
 * * <Br>
 * <p>
 * site1    南门换乘中心
 * * <Br>
 * <p>
 * site2    火车站
 * * <Br>
 * <p>
 * stationCount 34
 * * <Br>
 * <p>
 * passStationList 南门换乘中心|王下份|世纪金源饭店|……
 */
public class Line {

    /**
     * 线路价格
     */
    private String price;

    /**
     * 线路名称或者编号
     */
    private String lineName;

    /**
     * 线路站点1 运营描述
     */
    private String desc1;

    /**
     * 线路站点2 运营描述
     */
    private String desc2;

    /**
     * 站点1
     */
    private Station site1;

    /**
     * 站点2
     */
    private Station site2;

    /**
     * 站点计数
     */
    private int stationCount;

    /**
     * 途径站点列表
     */
    private List<Station> passStationList;
}
