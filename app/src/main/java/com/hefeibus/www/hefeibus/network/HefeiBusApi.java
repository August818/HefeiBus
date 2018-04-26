package com.hefeibus.www.hefeibus.network;

import com.hefeibus.www.hefeibus.entity.LineData;
import com.hefeibus.www.hefeibus.entity.StationData;
import com.hefeibus.www.hefeibus.entity.TransferData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HefeiBusApi {

    /**
     * @param type     业务类型
     * @param lineName 线路名称
     * @return 线路操作符
     */
    @GET("BusLineHandler.ashx")
    Call<LineData> getLineData(@Query("type") String type, @Query("lineName") String lineName);


    /**
     * @param type        业务类型
     * @param stationName 站点名称
     * @return 站点操作符
     */
    @GET("BusLineHandler.ashx")
    Call<List<StationData>> getStationData(@Query("type") String type, @Query("StationName") String stationName);

    /**
     * @param type           业务类型
     * @param startPointName 起点名称
     * @param endPointName   终点名称
     * @return 换乘方案
     */
    @GET("BusLineHandler.ashx")
    Call<List<TransferData>> getVehicleTransfer(@Query("type") String type, @Query("StartPointName") String startPointName, @Query("EndPointName") String endPointName);

}
