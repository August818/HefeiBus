package com.hefeibus.www.hefeibus.view.select_station;

import com.hefeibus.www.hefeibus.base.IPresenter;

import java.util.List;

interface ISelectStationPresenter extends IPresenter<ISelectStationView> {

    List<String> getStationSet();

}
