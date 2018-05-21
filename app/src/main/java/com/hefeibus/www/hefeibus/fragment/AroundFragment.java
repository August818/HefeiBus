package com.hefeibus.www.hefeibus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpFragment;
import com.hefeibus.www.hefeibus.basemvp.IPresenter;

/**
 * 附近界面
 * Created by xyw-mac on 2018/3/18.
 */

public class AroundFragment extends BaseMvpFragment {
    public LocationClient mLocationClient = null;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void init() {

    }

    @Override
    protected IPresenter onPresenterCreated() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return invokeMe(inflater, container);
    }

    @Override
    protected void setAttributes() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(5000)
                .direction(100)
                .latitude(31.49)
                .longitude(117.14)
                .build();
        mBaiduMap.setMyLocationData(locData);
    }

    @Override
    protected void initViews(View view) {
        mMapView = (MapView) view.findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();

    }

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_arround;
    }
}
