package com.hefeibus.www.hefeibus.view.line_detail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hefeibus.www.hefeibus.base.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.LineData;
import com.hefeibus.www.hefeibus.entity.Type;
import com.hefeibus.www.hefeibus.network.Network;
import com.hefeibus.www.hefeibus.sqlite.AppDatabase;
import com.hefeibus.www.hefeibus.utils.NoSuchDataException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

class LineDetailPresenter extends BaseMvpPresenter<ILineDetailView> implements ILineDetailPresenter {
    private static final String TAG = "LineDetailPresenter";
    private AppDatabase mAppDatabase;
    private String type = Type.线路查询.getType();
    private Disposable disposable;

    LineDetailPresenter(AppDatabase appDatabase) {
        mAppDatabase = appDatabase;
    }

    @Override
    public void queryLineDetail(final String lineName) {
        disposable = Observable.just(lineName)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        ifViewAttached(new ViewAction<ILineDetailView>() {
                            @Override
                            public void run(@NonNull ILineDetailView view) {
                                Log.d(TAG, "run: 显示等待");
                                view.showLoading();
                            }
                        });
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Function<String, LineData>() {
                    @Override
                    public LineData apply(String lineName) {
                        Log.d(TAG, "apply: 从本地查询");
                        return queryLineFromLocal(lineName);
                    }
                })
                .doOnNext(new Consumer<LineData>() {
                    @Override
                    public void accept(LineData lineData) throws NoSuchDataException {
                        if (lineData.getLineId() == 0) {
                            Log.d(TAG, "accept: 本地查询结果为空");
                            throw new NoSuchDataException();
                        }
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<LineData>>() {
                    @Override
                    public ObservableSource<LineData> apply(Throwable throwable) {
                        Log.d(TAG, "apply: 从网络查询");
                        return Network.getRxApi().getRxLineData(type, lineName);
                    }
                })
                .map(new Function<LineData, LineData>() {
                    @Override
                    public LineData apply(LineData lineData) {
                        if (weakView.get().getCurrentActivity().getMyApp().isCaching() && !lineData.isLocal()) {
                            Log.d(TAG, "apply: 来源网络");
                            Log.d(TAG, "apply: 将线路信息写入本地数据库");
                            writeLineToLocal(lineData);
                        } else {
                            Log.d(TAG, "apply: 本地");
                            Log.d(TAG, "apply: 不写入本地数据库");
                        }
                        return lineData;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LineData>() {
                    @Override
                    public void accept(final LineData lineData) {
                        ifViewAttached(new ViewAction<ILineDetailView>() {
                            @Override
                            public void run(@NonNull ILineDetailView view) {
                                Log.d(TAG, "run: 展示结果");
                                view.closeLoading();
                                view.showLineInfo(lineData);
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                        ifViewAttached(new ViewAction<ILineDetailView>() {
                            @Override
                            public void run(@NonNull ILineDetailView view) {
                                view.closeLoading();
                                view.counterApiError();
                            }
                        });
                    }
                });
    }

    private LineData queryLineFromLocal(String lineName) {
        return mAppDatabase.queryLineFromLocal(lineName);
    }

    private void writeLineToLocal(LineData lineData) {
        mAppDatabase.writeLineToLocal(lineData);
    }

    @Override
    public int onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            return 1;
        }
        return 0;
    }
}
