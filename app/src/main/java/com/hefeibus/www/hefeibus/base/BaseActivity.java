package com.hefeibus.www.hefeibus.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hefeibus.www.hefeibus.utils.ActivityController;

/**
 * 基础Activity 负责初始化
 * Created by cx on 2018/3/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityController.getInstance().add(this);
        setContentView(setLayoutView());
        findViews();
        setAttributes();

    }


    protected abstract void setAttributes();

    protected abstract void findViews();

    protected abstract int setLayoutView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.getInstance().remove(this);
    }
}
