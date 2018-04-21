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

    public Station(String stationName) {
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getPassLineCount() {
        return passLineCount;
    }

    public void setPassLineCount(int passLineCount) {
        this.passLineCount = passLineCount;
    }

    public List<Line> getPassLineList() {

        return passLineList;
    }

    public void setPassLineList(List<Line> passLineList) {
        this.passLineList = passLineList;
    }

    /**
     * 途径线路列表
     */
    private List<Line> passLineList;
}
