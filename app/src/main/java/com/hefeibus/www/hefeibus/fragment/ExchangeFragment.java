package com.hefeibus.www.hefeibus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpFragment;

/**
 * 换乘界面
 * Created by xyw-mac on 2018/3/18.
 */

public class ExchangeFragment extends BaseMvpFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return invokeMe(inflater, container);

    }

    @Override
    protected void setAttributes() {

    }

    @Override
    protected void findViews(View view) {

    }

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_exchange;
    }
}
