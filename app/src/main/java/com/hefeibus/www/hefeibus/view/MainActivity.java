package com.hefeibus.www.hefeibus.view;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.base.BaseActivity;

/**
 * Main Activity
 * Created by cx on 2018/3/17.
 */

public class MainActivity extends BaseActivity {
    private TabLayout mTablayout;
    private android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void setAttributes() {
        mToolbar.setTitle("合肥城市公交");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTablayout.addTab(mTablayout.newTab().setText("搜索"));
        mTablayout.addTab(mTablayout.newTab().setText("换乘"));
        mTablayout.addTab(mTablayout.newTab().setText("附近"));
        mTablayout.addTab(mTablayout.newTab().setText("我的"));
    }

    @Override
    protected void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTablayout = (TabLayout) findViewById(R.id.activity_main_tablayout);
    }

    @Override
    protected int setLayoutView() {
        return R.layout.activity_main;
    }
}
