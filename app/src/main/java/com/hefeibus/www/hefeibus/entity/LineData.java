package com.hefeibus.www.hefeibus.entity;

import java.util.List;

public class LineData {

    /**
     * 线路ID 线路名称 线路公司 线路类型 上下行长度 上下行起止时间 上下行途径站点 上行途径站点数
     */
    private int LineId;
    private String LineName;
    private String Org;

    private int RunCateGory;
    private double UpLen;
    private double DownLen;

    private String MainFirstTime;
    private String MainLastTime;
    private String SubFirstTime;
    private String SubLastTime;
    private List<UpStationBean> UpStation;
    private List<DownStationBean> DownStation;

    public LineData(String lineName) {
        this.LineName = lineName;
    }

    public double getDownLen() {
        return DownLen;
    }

    public void setDownLen(double DownLen) {
        this.DownLen = DownLen;
    }

    public int getLineId() {
        return LineId;
    }

    public void setLineId(int LineId) {
        this.LineId = LineId;
    }

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String LineName) {
        this.LineName = LineName;
    }

    public String getMainFirstTime() {
        return MainFirstTime;
    }

    public void setMainFirstTime(String MainFirstTime) {
        this.MainFirstTime = MainFirstTime;
    }

    public String getMainLastTime() {
        return MainLastTime;
    }

    public void setMainLastTime(String MainLastTime) {
        this.MainLastTime = MainLastTime;
    }

    public String getOrg() {
        return Org;
    }

    public void setOrg(String Org) {
        this.Org = Org;
    }

    public int getRunCateGory() {
        return RunCateGory;
    }

    public void setRunCateGory(int RunCateGory) {
        this.RunCateGory = RunCateGory;
    }

    public String getSubFirstTime() {
        return SubFirstTime;
    }

    public void setSubFirstTime(String SubFirstTime) {
        this.SubFirstTime = SubFirstTime;
    }

    public String getSubLastTime() {
        return SubLastTime;
    }

    public void setSubLastTime(String SubLastTime) {
        this.SubLastTime = SubLastTime;
    }

    public double getUpLen() {
        return UpLen;
    }

    public void setUpLen(double UpLen) {
        this.UpLen = UpLen;
    }

    public List<DownStationBean> getDownStation() {
        return DownStation;
    }

    public void setDownStation(List<DownStationBean> DownStation) {
        this.DownStation = DownStation;
    }

    public List<UpStationBean> getUpStation() {
        return UpStation;
    }

    public void setUpStation(List<UpStationBean> UpStation) {
        this.UpStation = UpStation;
    }

    public static class DownStationBean {
        /**
         * 经纬度、站点名称、站点ID
         */
        private double Latitude;
        private double Longitude;
        private String StationName;
        private int StationPointId;

        public double getLatitude() {
            return Latitude;
        }

        public void setLatitude(double Latitude) {
            this.Latitude = Latitude;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double Longitude) {
            this.Longitude = Longitude;
        }


        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public int getStationPointId() {
            return StationPointId;
        }

        public void setStationPointId(int StationPointId) {
            this.StationPointId = StationPointId;
        }
    }

    public static class UpStationBean {
        /**
         * 经纬度、站点名称、站点ID
         */
        private double Latitude;
        private double Longitude;
        private String StationName;
        private int StationPointId;

        public double getLatitude() {
            return Latitude;
        }

        public void setLatitude(double Latitude) {
            this.Latitude = Latitude;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double Longitude) {
            this.Longitude = Longitude;
        }

        public String getStationName() {
            return StationName;
        }

        public void setStationName(String StationName) {
            this.StationName = StationName;
        }

        public int getStationPointId() {
            return StationPointId;
        }

        public void setStationPointId(int StationPointId) {
            this.StationPointId = StationPointId;
        }
    }
}
