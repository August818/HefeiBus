package com.hefeibus.www.hefeibus.fragment.history;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.sqlite.HistoryDatabase;

class HistoryPresenter extends BaseMvpPresenter<IHistoryView> implements IHistoryPresenter {
    private HistoryDatabase historyDatabase;

    HistoryPresenter(HistoryDatabase historyDatabase) {
        this.historyDatabase = historyDatabase;
    }


    @Override
    public void clearHistory() {
        historyDatabase.clearHistory();
    }

    @Override
    public void requestHistory() {
        historyDatabase.requestHistory();
    }
}
