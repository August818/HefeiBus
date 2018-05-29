package com.hefeibus.www.hefeibus.view.select_station;

import com.hefeibus.www.hefeibus.base.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.sqlite.AppDatabase;

import java.util.List;

class SelectStationPresenter extends BaseMvpPresenter<ISelectStationView> implements ISelectStationPresenter {

    private static final String TAG = "SelectStationPresenter";
    private AppDatabase database;

    SelectStationPresenter(AppDatabase database) {
        this.database = database;
    }

    @Override
    public List<String> getStationSet() {
        return database.getStationIndex();
    }


}
