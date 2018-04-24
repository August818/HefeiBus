package com.hefeibus.www.hefeibus.view.station_detail;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpActivity;

public class StationDetailActivity extends BaseMvpActivity<IStationDetailPresenter> implements IStationDetailView {
    @Override
    protected IStationDetailPresenter onCreatePresenter() {
        return new StationDetailPresenter();
    }

    @Override
    protected void setAttributes() {

    }

    @Override
    protected void findViews() {

    }

    @Override
    protected int setLayoutView() {
        return R.layout.activity_station_detail;
    }
}
