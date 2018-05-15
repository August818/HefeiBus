package com.hefeibus.www.hefeibus.view.select_station;

import com.hefeibus.www.hefeibus.basemvp.IPresenter;

import java.util.List;

interface ISelectStationPresenter extends IPresenter<ISelectStationView> {

    List<String> getStationSet();

    void onDestroy();
}
