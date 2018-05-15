package com.hefeibus.www.hefeibus.network;

import com.hefeibus.www.hefeibus.entity.LineData;
import com.hefeibus.www.hefeibus.entity.StationData;
import com.hefeibus.www.hefeibus.entity.TransferData;
import com.hefeibus.www.hefeibus.entity.Type;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 通用网络请求框架，Retrofit 封装
 */
public class Network {
    public static final String baseUrl = "http://www.hfbus.cn/map/AjaxHandler/";
    private static HefeiBusApi api;
    private static HefeiBusApi rxApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static List<TransferData> transferData;
    private static LineData lineData;
    private static List<StationData> stationData;

    private Network() {
    }

    public static HefeiBusApi getApi() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(baseUrl)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            api = retrofit.create(HefeiBusApi.class);
        }
        return api;
    }

    /**
     * @return 支持 RxJava 操作
     */
    public static HefeiBusApi getRxApi() {
        if (rxApi == null) {
            synchronized (Network.class) {
                if (rxApi == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .client(okHttpClient)
                            .baseUrl(baseUrl)
                            .addConverterFactory(gsonConverterFactory)
                            .addCallAdapterFactory(rxJavaCallAdapterFactory)
                            .build();
                    rxApi = retrofit.create(HefeiBusApi.class);
                }
            }
        }
        return rxApi;
    }

    public static void main(String[] args) {

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HefeiBusApi api = retrofit.create(HefeiBusApi.class);

        try {
            stationData = api.getStationData(Type.站点查询.getType(), "南七里站").execute().body();
            Thread.sleep(3000);
            lineData = api.getLineData(Type.线路查询.getType(), "1").execute().body();
            Thread.sleep(3000);
            transferData = api.getVehicleTransfer(Type.换乘查询.getType(), "安高城市天地", "安徽轻工商城").execute().body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(transferData == null ? transferData.toString() : "null");
        System.out.println(lineData == null ? lineData.toString() : "null");
        System.out.println(stationData == null ? stationData.toString() : "null");
    }

}
