package com.hefeibus.www.hefeibus.view.auto_search;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.Item;
import com.hefeibus.www.hefeibus.sqlite.AppDatabase;

import java.util.List;

class AutoSearchPresenter extends BaseMvpPresenter<IAutoSearchView> implements IAutoSearchPresenter {

    private AppDatabase database;

    @Override
    public void onDestroy() {
        database.close();
    }

    @Override
    public List<Item> getResultSet() {
        if (database == null) {
            database = new AppDatabase(weakView.get().getCurrentActivity());
        }
        return database.queryResultSet();
    }
}
