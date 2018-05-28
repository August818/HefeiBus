package com.hefeibus.www.hefeibus.view.station_detail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.StationData;
import com.hefeibus.www.hefeibus.entity.Type;
import com.hefeibus.www.hefeibus.network.Network;
import com.hefeibus.www.hefeibus.sqlite.AppDatabase;
import com.hefeibus.www.hefeibus.sqlite.HistoryDatabase;
import com.hefeibus.www.hefeibus.utils.NoSuchDataException;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

class StationDetailPresenter extends BaseMvpPresenter<IStationDetailView> implements IStationDetailPresenter {
    private static final String TAG = "StationDetailPresenter";
    private AppDatabase database;
    private String type = Type.站点查询.getType();
    private Disposable disposable;
    private boolean isLocal;
    private HistoryDatabase historyDatabase;

    StationDetailPresenter(HistoryDatabase historyDatabase) {
        this.historyDatabase = historyDatabase;
    }


    @Override
    public void queryStationDetail(final String station) {
        if (database == null) {
            database = new AppDatabase(weakView.get().getCurrentActivity());
        }

        disposable = Observable.just(station)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        ifViewAttached(new ViewAction<IStationDetailView>() {
                            @Override
                            public void run(@NonNull IStationDetailView view) {
                                Log.d(TAG, "run: 展示loading");
                                view.showLoading();
                            }
                        });
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Function<String, List<StationData>>() {
                    @Override
                    public List<StationData> apply(String name) {
                        Log.d(TAG, "apply: 从本地查询");
                        return queryStationFromLocal(name);
                    }
                })
                .doOnNext(new Consumer<List<StationData>>() {
                    @Override
                    public void accept(List<StationData> stationData) throws NoSuchDataException {
                        if (stationData.size() == 0) {
                            Log.d(TAG, "accept: 本地查询结果为空");
                            throw new NoSuchDataException();
                        } else {
                            isLocal = true;
                        }
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends List<StationData>>>() {
                    @Override
                    public ObservableSource<? extends List<StationData>> apply(Throwable throwable) {
                        Log.d(TAG, "apply: 从网络查询");
                        return Network.getRxApi().getRxStationData(type, station);
                    }
                })
                .map(new Function<List<StationData>, List<StationData>>() {
                    @Override
                    public List<StationData> apply(List<StationData> stationData) {
                        if (weakView.get().getCurrentActivity().getMyApp().isCaching() && !isLocal) {
                            Log.d(TAG, "apply: 来源网络");
                            Log.d(TAG, "apply: 将站点信息写入本地数据库");
                            writeStationToLocal(stationData);
                        } else {
                            Log.d(TAG, "apply: 本地");
                            Log.d(TAG, "apply: 不写入本地数据库");
                        }
                        return stationData;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<StationData>>() {
                    @Override
                    public void accept(final List<StationData> stationData) {
                        historyDatabase.appendStation(station);
                        ifViewAttached(new ViewAction<IStationDetailView>() {
                            @Override
                            public void run(@NonNull IStationDetailView view) {
                                Log.d(TAG, "run: 显示结果");
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
                                Log.d(TAG, "run: 出错返回");
                                view.closeLoading();
                                view.counterApiError();
                            }
                        });
                    }
                });
    }

    private void writeStationToLocal(List<StationData> stationData) {
        database.writeStationToLocal(stationData);
    }

    private List<StationData> queryStationFromLocal(String name) {
        return database.queryStationFromLocal(name);
    }

    @Override
    public boolean isLocal() {
        return isLocal;
    }

    @Override
    public int onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            return 1;
        }
        if (database != null) {
            database.close();
        }
        return 0;
    }
}
