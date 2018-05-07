package com.hefeibus.www.hefeibus.view.line_detail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.LineData;
import com.hefeibus.www.hefeibus.entity.Type;
import com.hefeibus.www.hefeibus.network.Network;
import com.hefeibus.www.hefeibus.sqlite.AppDatabase;

import java.io.IOException;
import java.util.NoSuchElementException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

class LineDetailPresenter extends BaseMvpPresenter<ILineDetailView> implements ILineDetailPresenter {
    private static final String TAG = "LineDetailPresenter";
    private AppDatabase database;
    private String type = Type.线路查询.getType();
    private Disposable disposable;


    @Override
    public void queryLineDetail(final String lineName) {
        database = new AppDatabase(weakView.get().getCurrentActivity());
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
                    public void accept(LineData lineData) throws NoSuchElementException {
                        if (lineData.getLineId() == 0) {
                            Log.d(TAG, "accept: 本地查询结果为空");
                            throw new NoSuchElementException();
                        }
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends LineData>>() {
                    @Override
                    public ObservableSource<? extends LineData> apply(Throwable throwable) {
                        Log.d(TAG, "apply: 从网络查询");
                        return Network.getRxApi().getRxLineData(type, lineName);
                    }
                })
                .map(new Function<LineData, LineData>() {
                    @Override
                    public LineData apply(LineData lineData) {
                        if (weakView.get().getCurrentActivity().getMyApp().isCaching()) {
                            Log.d(TAG, "apply: 写入本地数据库");
                            try {
                                writeLineToLocal(lineData);
                            } catch (IOException e) {
                                Log.d(TAG, "apply: 数据库写入出错---");
                                e.printStackTrace();
                                Log.d(TAG, "apply: 数据库写入出错---");
                            }
                        } else {
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

/*        disposable = Network.getRxApi()
                .getRxLineData(type, lineName)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        ifViewAttached(new ViewAction<ILineDetailView>() {
                            @Override
                            public void run(@NonNull ILineDetailView view) {
                                view.showLoading();
                            }
                        });
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Function<LineData, LineData>() {
                    @Override
                    public LineData apply(LineData lineData) {
                        if (weakView.get().getCurrentActivity().getMyApp().isCaching()) {
                            try {
                                writeLineToLocal(lineData);
                            } catch (IOException e) {
                                Log.d(TAG, "apply: 数据库写入出错---");
                                e.printStackTrace();
                                Log.d(TAG, "apply: 数据库写入出错---");
                            }
                        }
                        return lineData;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LineData>() {
                    @Override
                    public void accept(final LineData lineData) {
                        ifViewAttached(new ViewAction<ILineDetailView>() {
                            @Override
                            public void run(@NonNull ILineDetailView view) {
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
                })*/
    }

    private LineData queryLineFromLocal(String lineName) {
        return database.queryLineFromLocal(lineName);
    }

    private void writeLineToLocal(LineData lineData) throws IOException {
        database.writeLineToLocal(lineData);
    }

    @Override
    public int onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            return 1;
        }
        database.close();
        return 0;
    }
}
