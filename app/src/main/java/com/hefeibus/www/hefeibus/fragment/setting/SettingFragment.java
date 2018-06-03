package com.hefeibus.www.hefeibus.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.base.BaseMvpFragment;
import com.hefeibus.www.hefeibus.base.IPresenter;

/**
 * 个人资料界面
 * Created by xyw-mac on 2018/3/18.
 */

public class SettingFragment extends BaseMvpFragment implements View.OnClickListener {
    private TextView collectionTv, adressTv, arriveTv, clearTv, aboutRv;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_profile;
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
    protected void initViews(View view) {
        collectionTv = (TextView) view.findViewById(R.id.collection);
        adressTv = (TextView) view.findViewById(R.id.adress);
        arriveTv = (TextView) view.findViewById(R.id.arrive);
        clearTv = (TextView) view.findViewById(R.id.clear);
        aboutRv = (TextView) view.findViewById(R.id.about);
    }

    @Override
    protected void setAttributes() {
        collectionTv.setOnClickListener(this);
        adressTv.setOnClickListener(this);
        arriveTv.setOnClickListener(this);
        clearTv.setOnClickListener(this);
        aboutRv.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
        Toast.makeText(getContext(), "功能正在开发中", Toast.LENGTH_SHORT).show();
    }
}
