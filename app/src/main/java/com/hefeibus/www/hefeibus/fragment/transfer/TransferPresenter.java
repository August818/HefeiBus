package com.hefeibus.www.hefeibus.fragment.transfer;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.ProgramStatus;
import com.hefeibus.www.hefeibus.entity.TransferData;
import com.hefeibus.www.hefeibus.entity.Type;
import com.hefeibus.www.hefeibus.network.Network;
import com.hefeibus.www.hefeibus.sqlite.AppDatabase;
import com.hefeibus.www.hefeibus.utils.NoSuchDataException;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

class TransferPresenter extends BaseMvpPresenter<ITransferView> implements ITransferPresenter {
    private static final String TAG = "TransferPresenter";
    private Disposable dispose;
    private AppDatabase database;
    private String type = Type.换乘查询.getType();
    private boolean isLocal;

    @Override
    public void onDestroy() {
        if (dispose != null && !dispose.isDisposed()) {
            dispose.dispose();
        }
        database.close();
    }

    @Override
    public void onCancel() {
        if (dispose != null && !dispose.isDisposed()) {
            dispose.dispose();
            Log.d(TAG, "onCancel: 网络请求取消");
        }
        ifViewAttached(new ViewAction<ITransferView>() {
            @Override
            public void run(@NonNull ITransferView view) {
                view.setStatus(ProgramStatus.NORMAL);
            }
        });
    }

    @Override
    public void commitQuery(final String start, final String stop) {
        if (database == null) {
            database = new AppDatabase(weakView.get().getCurrentActivity().getContext());
        }
        TransferPojo transferPojo = new TransferPojo(start, stop);

        dispose = Observable.just(transferPojo)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        ifViewAttached(new ViewAction<ITransferView>() {
                            @Override
                            public void run(@NonNull ITransferView view) {
                                view.setStatus(ProgramStatus.IS_LOADING);
                            }
                        });
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Function<TransferPojo, List<TransferData>>() {
                    @Override
                    public List<TransferData> apply(TransferPojo pojo) {
                        return queryTransferPlanFromLocal(pojo.getStart(), pojo.getStop());
                    }
                })
                .doOnNext(new Consumer<List<TransferData>>() {
                    @Override
                    public void accept(List<TransferData> transferData) throws NoSuchDataException {
                        if (transferData.size() == 0) {
                            throw new NoSuchDataException();
                        } else {
                            isLocal = true;
                        }
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends List<TransferData>>>() {
                    @Override
                    public ObservableSource<? extends List<TransferData>> apply(Throwable throwable) {
                        //本地数据库查询结果为空
                        return Network.getRxApi().getRxVehicleTransfer(type, start, stop);
                    }
                })
                .map(new Function<List<TransferData>, List<TransferData>>() {
                    @Override
                    public List<TransferData> apply(List<TransferData> transferData) {
                        if (weakView.get().getCurrentActivity().getMyApp().isCaching() && !isLocal) {
                            writeTransferPlanToLocal(start, stop);
                        }
                        return transferData;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TransferData>>() {
                    @Override
                    public void accept(final List<TransferData> transferData) {
                        ifViewAttached(new ViewAction<ITransferView>() {
                            @Override
                            public void run(@NonNull ITransferView view) {
                                view.setStatus(ProgramStatus.SUCCESS_DATA_FROM_LOCAL);
                                view.repostTransferData(transferData, start, stop);
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                        if (throwable instanceof NullPointerException) {
                            ifViewAttached(new ViewAction<ITransferView>() {
                                @Override
                                public void run(@NonNull ITransferView view) {
                                    view.setStatus(ProgramStatus.SUCCESS_DATA_EMPTY);
                                    view.makeToast("没有查询到换乘结果，打车吧～～");
                                }
                            });
                            return;
                        }
                        ifViewAttached(new ViewAction<ITransferView>() {
                            @Override
                            public void run(@NonNull ITransferView view) {
                                view.setStatus(ProgramStatus.ERROR_NETWORK_UNREACHED);
                            }
                        });
                    }
                });
    }

    private void writeTransferPlanToLocal(String start, String stop) {
        database.writeTransferPlanToLocal(start, stop);
    }

    private List<TransferData> queryTransferPlanFromLocal(String start, String stop) {
        return database.queryTransferPlan(start, stop);
    }

    private class TransferPojo {
        private String start, stop;

        private TransferPojo(String start, String stop) {
            this.start = start;
            this.stop = stop;
        }

        public String getStart() {
            return start;
        }

        public String getStop() {
            return stop;
        }

    }
}

