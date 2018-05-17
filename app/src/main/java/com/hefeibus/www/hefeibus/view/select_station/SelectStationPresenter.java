package com.hefeibus.www.hefeibus.view.select_station;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.sqlite.AppDatabase;

import java.util.List;

class SelectStationPresenter extends BaseMvpPresenter<ISelectStationView> implements ISelectStationPresenter {

    private static final String TAG = "SelectStationPresenter";
    private AppDatabase database;

    @Override
    public List<String> getStationSet() {
        if (database == null) {
            database = new AppDatabase(weakView.get().getCurrentActivity());
        }
        return database.getStationIndex();
    }

    @Override
    public void onDestroy() {
        if (database != null) {
            database.close();
        }
    }
}
