package com.hefeibus.www.hefeibus.view.auto_search;

import com.hefeibus.www.hefeibus.base.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.Item;
import com.hefeibus.www.hefeibus.sqlite.AppDatabase;

import java.util.List;

class AutoSearchPresenter extends BaseMvpPresenter<IAutoSearchView> implements IAutoSearchPresenter {
    private AppDatabase mAppDatabase;

    AutoSearchPresenter(AppDatabase mAppDatabase) {
        this.mAppDatabase = mAppDatabase;
    }

    @Override
    public List<Item> getResultSet() {
        return mAppDatabase.queryResultSet();
    }
}
