package com.hefeibus.www.hefeibus.view.station_detail;

import com.hefeibus.www.hefeibus.base.IView;
import com.hefeibus.www.hefeibus.entity.StationData;

import java.util.List;

interface IStationDetailView extends IView {
    StationDetailActivity getCurrentActivity();

    void showLoading();

    void closeLoading();

    void counterApiError();

    void showStationInfo(List<StationData> stationData);
}
