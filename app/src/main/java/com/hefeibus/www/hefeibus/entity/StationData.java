package com.hefeibus.www.hefeibus.entity;

public class StationData {

    /**
     * StationPointId : 4413
     * StationName : 三孝口
     * AliasName : 三孝口
     * OrientationInfo : (金寨路-西)
     * LineName : 17路,709路,133路,126路,122路
     * Lat : 31.86128500
     * Lng : 117.27022900
     */

    private String StationPointId;
    private String StationName;
    private String AliasName;
    private String OrientationInfo;
    private String LineName;
    private String Lat;
    private String Lng;

    public String getStationPointId() {
        return StationPointId;
    }

    public void setStationPointId(String StationPointId) {
        this.StationPointId = StationPointId;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }

    public String getAliasName() {
        return AliasName;
    }

    public void setAliasName(String AliasName) {
        this.AliasName = AliasName;
    }

    public String getOrientationInfo() {
        return OrientationInfo;
    }

    public void setOrientationInfo(String OrientationInfo) {
        this.OrientationInfo = OrientationInfo;
    }

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String LineName) {
        this.LineName = LineName;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String Lat) {
        this.Lat = Lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String Lng) {
        this.Lng = Lng;
    }
}
