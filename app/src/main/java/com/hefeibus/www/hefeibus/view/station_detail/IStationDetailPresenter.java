package com.hefeibus.www.hefeibus.view.station_detail;

import com.hefeibus.www.hefeibus.basemvp.IPresenter;

interface IStationDetailPresenter extends IPresenter<IStationDetailView> {
    void queryStationDetail(String station);

    int onDestroy();

    boolean isLocal();
}
