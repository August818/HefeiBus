package com.hefeibus.www.hefeibus.view.station_detail;

import android.support.annotation.NonNull;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.StationData;
import com.hefeibus.www.hefeibus.entity.Type;
import com.hefeibus.www.hefeibus.network.Network;
import com.hefeibus.www.hefeibus.sqlite.AppDatabase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

class StationDetailPresenter extends BaseMvpPresenter<IStationDetailView> implements IStationDetailPresenter {
    private static final String TAG = "StationDetailPresenter";
    private AppDatabase database;
    private String type = Type.站点查询.getType();
    private Disposable disposable;

    @Override
    public void queryStationDetail(String station) {
        database = new AppDatabase(weakView.get().getCurrentActivity());
        disposable = Network.getRxApi()
                .getRxStationData(type, station)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        ifViewAttached(new ViewAction<IStationDetailView>() {
                            @Override
                            public void run(@NonNull IStationDetailView view) {
                                // view.showLoading();
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<StationData>>() {
                    @Override
                    public void accept(final List<StationData> stationData) {
                        ifViewAttached(new ViewAction<IStationDetailView>() {
                            @Override
                            public void run(@NonNull IStationDetailView view) {
                                view.closeLoading();
                                view.showStationInfo(stationData);
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                        ifViewAttached(new ViewAction<IStationDetailView>() {
                            @Override
                            public void run(@NonNull IStationDetailView view) {
                                view.closeLoading();
                                view.counterApiError();
                            }
                        });
                    }
                });
    }
}
