package com.hefeibus.www.hefeibus.view.line_detail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.Line;
import com.hefeibus.www.hefeibus.entity.Station;
import com.hefeibus.www.hefeibus.sqlite.HefeiBusDatabase;
import com.hefeibus.www.hefeibus.utils.Parameters;
import com.hefeibus.www.hefeibus.utils.PoiResultHanlder;

import java.util.ArrayList;
import java.util.List;

class LineDetailPresenter extends BaseMvpPresenter<ILineDetailView> implements ILineDetailPresenter {
    private static final String TAG = "LineDetailPresenter";

    @Override
    public void queryLineDetail(String lineName) {
        HefeiBusDatabase mDal = new HefeiBusDatabase(weakView.get().getCurrentActivity());

        final Line line = mDal.queryLineDetail(lineName);
        //如果已经从数据库中取到了数据，发回界面
        if (line.getPassStationList() != null) {
            ifViewAttached(new ViewAction<ILineDetailView>() {
                @Override
                public void run(@NonNull ILineDetailView view) {
                    view.showLineInfo(line);
                }
            });
        } else {
            ifViewAttached(new ViewAction<ILineDetailView>() {
                @Override
                public void run(@NonNull ILineDetailView view) {
                    view.showLoading();
                }
            });
            requestUidFromApi(lineName);
        }
    }

    private void requestUidFromApi(String lineName) {
        PoiSearch mSearch = PoiSearch.newInstance();
        mSearch.setOnGetPoiSearchResultListener(new PoiResultHanlder() {
            private List<String> uidList = new ArrayList<>();

            @Override
            public void onGetPoiResult(final PoiResult result) {
                //如果查询结果为空
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    if (result == null) {
                        Log.d(TAG, "onGetPoiResult: 接口查询回调为null");
                    } else {
                        Log.d(TAG, "onGetPoiResult: 接口查询回调结果状态标识为不成功 " + result.error.name());
                    }
                    ifViewAttached(new ViewAction<ILineDetailView>() {
                        @Override
                        public void run(@NonNull ILineDetailView view) {
                            view.counterApiError();
                        }
                    });
                } else {
                    for (PoiInfo poi : result.getAllPoi()) {
                        if (poi.type == PoiInfo.POITYPE.BUS_LINE || poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
                            Log.d(TAG, "onGetPoiResult: " + poi.name + "\t" + poi.uid);
                            uidList.add(poi.uid);
                            break;
                        }
                    }
                    if (uidList.size() != 0) {
                        requestBusLineFromApi(uidList);
                    } else {
                        ifViewAttached(new ViewAction<ILineDetailView>() {
                            @Override
                            public void run(@NonNull ILineDetailView view) {
                                Log.d(TAG, "run: 接口查询成功，关键字给定数组中不包含线路数据");
                                view.counterApiError();
                            }
                        });
                    }
                }
            }
        });
        mSearch.searchInCity(new PoiCitySearchOption().city(Parameters.CITY_NAME).keyword(lineName));

    }

    private void requestBusLineFromApi(List<String> uidList) {
        BusLineSearch mSearch = BusLineSearch.newInstance();
        final List<BusLineResult> lineResultList = new ArrayList<>();
        mSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
            @Override
            public void onGetBusLineResult(BusLineResult busLineResult) {
                Log.d(TAG, "onGetBusLineResult: " + busLineResult.status);
                if (busLineResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    createLineDetail(busLineResult);
                }
            }
        });

        mSearch.searchBusLine(new BusLineSearchOption().city(Parameters.CITY_NAME).uid(uidList.get(0)));


    }

    private void createLineDetail(BusLineResult lineResultList) {
        List<Station> stations = new ArrayList<>();
        for (BusLineResult.BusStation busStation : lineResultList.getStations()) {
            stations.add(new Station(busStation.getTitle()));
        }
        final Line line = new Line(lineResultList.getBusLineName());
        line.setPrice("未查询到价格数据");
        line.setDesc1(lineResultList.getStations().get(0).getTitle());
        line.setDesc2(lineResultList.getStations().get(stations.size() - 1).getTitle());
        line.setPassStationList(stations);
        line.setStationCount(stations.size());

        ifViewAttached(new ViewAction<ILineDetailView>() {
            @Override
            public void run(@NonNull ILineDetailView view) {
                view.showLineInfo(line);
            }
        });
    }
}
