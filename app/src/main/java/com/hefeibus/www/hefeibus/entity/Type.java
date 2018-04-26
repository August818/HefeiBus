package com.hefeibus.www.hefeibus.entity;

public enum Type {
    线路查询("GetLineData"),
    站点查询("GetStationInfoList"),
    换乘查询("TransferVehicle");

    private String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
