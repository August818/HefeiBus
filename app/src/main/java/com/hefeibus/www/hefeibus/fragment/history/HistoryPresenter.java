package com.hefeibus.www.hefeibus.fragment.history;

import android.support.annotation.NonNull;

import com.hefeibus.www.hefeibus.base.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.Wrapper;
import com.hefeibus.www.hefeibus.sqlite.AppDatabase;

import java.util.List;

class HistoryPresenter extends BaseMvpPresenter<IHistoryView> implements IHistoryPresenter {

    private AppDatabase mAppdatabase;

    HistoryPresenter(AppDatabase mAppDatabase) {
        this.mAppdatabase = mAppDatabase;
    }


    @Override
    public void clearHistory() {
        mAppdatabase.clearHisRec();

    }

    @Override
    public void getHistory() {
        final List<String> line;
        final List<String> station;
        final List<Wrapper> transfer;

        line = mAppdatabase.getLineRec();
        station = mAppdatabase.getStationRec();
        transfer = mAppdatabase.getTransferRec();

        ifViewAttached(new ViewAction<IHistoryView>() {
            @Override
            public void run(@NonNull IHistoryView view) {
                view.setDatum(line, station, transfer);
            }
        });
    }
}
