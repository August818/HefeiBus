package com.hefeibus.www.hefeibus.entity;

import java.util.List;

/**
 * 站点 model
 * <p>
 * stationName 火车站
 * <Br>
 * passLineCount 24
 * <Br>
 * passLineList 快速公交1号线|301|经开公交4号线
 */
public class Station {
    /**
     * 站点名称
     */
    private String stationName;
    /**
     * 途径该站点的线路数量
     */
    private int passLineCount;
    /**
     * 途径线路列表
     */
    private List<Line> passLineList;
}
