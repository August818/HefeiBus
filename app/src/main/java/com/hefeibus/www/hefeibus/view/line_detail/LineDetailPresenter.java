package com.hefeibus.www.hefeibus.view.line_detail;

import android.support.annotation.NonNull;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.LineData;
import com.hefeibus.www.hefeibus.entity.Type;
import com.hefeibus.www.hefeibus.network.Network;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

class LineDetailPresenter extends BaseMvpPresenter<ILineDetailView> implements ILineDetailPresenter {

    private String type = Type.线路查询.getType();
    private Disposable disposable;


    @Override
    public void queryLineDetail(String lineName) {
        disposable = Network.getRxApi()
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
                });
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
