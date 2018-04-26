package com.hefeibus.www.hefeibus.entity;

import java.util.List;

public class TransferData {

    /**
     * TranType : 1
     * BeginLineName : 111
     * EndLineName : -1
     * StationName :
     * StationID :
     * BeginStationPoint : 20
     * EndStationPoint : 0
     * BeginStationId :
     * EndStationId :
     * StationCount : 20
     * Distance :
     * BeginOrder :
     * EndOrder :
     * Points : [{"StationPoint":"85","RouteType":"2","PointType":"1","Latitude":"31.886545","Longitude":"117.30963"},{"StationPoint":"124","RouteType":"2","PointType":"1","Latitude":"31.881938","Longitude":"117.306968"},{"StationPoint":"579","RouteType":"2","PointType":"1","Latitude":"31.877025","Longitude":"117.304163"},{"StationPoint":"951","RouteType":"2","PointType":"1","Latitude":"31.873362","Longitude":"117.309194"},{"StationPoint":"2208","RouteType":"2","PointType":"1","Latitude":"31.869373","Longitude":"117.309537"},{"StationPoint":"266","RouteType":"2","PointType":"1","Latitude":"31.860505","Longitude":"117.307093"},{"StationPoint":"1134","RouteType":"2","PointType":"1","Latitude":"31.853099","Longitude":"117.302667"},{"StationPoint":"2468","RouteType":"2","PointType":"1","Latitude":"31.849182","Longitude":"117.294888"},{"StationPoint":"603","RouteType":"2","PointType":"1","Latitude":"31.844748","Longitude":"117.295146"},{"StationPoint":"542","RouteType":"2","PointType":"1","Latitude":"31.840083","Longitude":"117.29542"},{"StationPoint":"754","RouteType":"2","PointType":"1","Latitude":"31.834193","Longitude":"117.294782"},{"StationPoint":"2063","RouteType":"2","PointType":"1","Latitude":"31.83425521","Longitude":"117.2901787"},{"StationPoint":"631","RouteType":"2","PointType":"1","Latitude":"31.832913","Longitude":"117.286485"},{"StationPoint":"388","RouteType":"2","PointType":"1","Latitude":"31.830262","Longitude":"117.278097"},{"StationPoint":"395","RouteType":"2","PointType":"1","Latitude":"31.830203","Longitude":"117.272654"},{"StationPoint":"392","RouteType":"2","PointType":"1","Latitude":"31.83029","Longitude":"117.27007"},{"StationPoint":"2901","RouteType":"2","PointType":"1","Latitude":"31.830893","Longitude":"117.263636"},{"StationPoint":"6490","RouteType":"2","PointType":"1","Latitude":"31.83153","Longitude":"117.257417"},{"StationPoint":"385","RouteType":"2","PointType":"1","Latitude":"31.832153","Longitude":"117.251282"},{"StationPoint":"383","RouteType":"2","PointType":"1","Latitude":"31.832264","Longitude":"117.244521"},{"StationPoint":"382","RouteType":"2","PointType":"1","Latitude":"31.832791","Longitude":"117.23958"}]
     */

    private String TranType;
    private String BeginLineName;
    private String EndLineName;
    private String StationName;
    private String StationID;
    private String BeginStationPoint;
    private String EndStationPoint;
    private String BeginStationId;
    private String EndStationId;
    private String StationCount;
    private String Distance;
    private String BeginOrder;
    private String EndOrder;
    private List<PointsBean> Points;

    public String getTranType() {
        return TranType;
    }

    public void setTranType(String TranType) {
        this.TranType = TranType;
    }

    public String getBeginLineName() {
        return BeginLineName;
    }

    public void setBeginLineName(String BeginLineName) {
        this.BeginLineName = BeginLineName;
    }

    public String getEndLineName() {
        return EndLineName;
    }

    public void setEndLineName(String EndLineName) {
        this.EndLineName = EndLineName;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }

    public String getStationID() {
        return StationID;
    }

    public void setStationID(String StationID) {
        this.StationID = StationID;
    }

    public String getBeginStationPoint() {
        return BeginStationPoint;
    }

    public void setBeginStationPoint(String BeginStationPoint) {
        this.BeginStationPoint = BeginStationPoint;
    }

    public String getEndStationPoint() {
        return EndStationPoint;
    }

    public void setEndStationPoint(String EndStationPoint) {
        this.EndStationPoint = EndStationPoint;
    }

    public String getBeginStationId() {
        return BeginStationId;
    }

    public void setBeginStationId(String BeginStationId) {
        this.BeginStationId = BeginStationId;
    }

    public String getEndStationId() {
        return EndStationId;
    }

    public void setEndStationId(String EndStationId) {
        this.EndStationId = EndStationId;
    }

    public String getStationCount() {
        return StationCount;
    }

    public void setStationCount(String StationCount) {
        this.StationCount = StationCount;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String Distance) {
        this.Distance = Distance;
    }

    public String getBeginOrder() {
        return BeginOrder;
    }

    public void setBeginOrder(String BeginOrder) {
        this.BeginOrder = BeginOrder;
    }

    public String getEndOrder() {
        return EndOrder;
    }

    public void setEndOrder(String EndOrder) {
        this.EndOrder = EndOrder;
    }

    public List<PointsBean> getPoints() {
        return Points;
    }

    public void setPoints(List<PointsBean> Points) {
        this.Points = Points;
    }

    public static class PointsBean {
        /**
         * StationPoint : 85
         * RouteType : 2
         * PointType : 1
         * Latitude : 31.886545
         * Longitude : 117.30963
         */

        private String StationPoint;
        private String RouteType;
        private String PointType;
        private String Latitude;
        private String Longitude;

        public String getStationPoint() {
            return StationPoint;
        }

        public void setStationPoint(String StationPoint) {
            this.StationPoint = StationPoint;
        }

        public String getRouteType() {
            return RouteType;
        }

        public void setRouteType(String RouteType) {
            this.RouteType = RouteType;
        }

        public String getPointType() {
            return PointType;
        }

        public void setPointType(String PointType) {
            this.PointType = PointType;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String Latitude) {
            this.Latitude = Latitude;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String Longitude) {
            this.Longitude = Longitude;
        }
    }
}
