package com.hefeibus.www.hefeibus.fragment.history;

import com.hefeibus.www.hefeibus.base.IPresenter;

interface IHistoryPresenter extends IPresenter<IHistoryView> {
    void clearHistory();

    void requestHistory();
}
