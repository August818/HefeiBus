package com.hefeibus.www.hefeibus.fragment.history;

import com.hefeibus.www.hefeibus.basemvp.IPresenter;

interface IHistoryPresenter extends IPresenter<IHistoryView> {
    void clearHistory();

    void requestHistory();
}
