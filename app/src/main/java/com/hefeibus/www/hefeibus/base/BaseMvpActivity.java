package com.hefeibus.www.hefeibus.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hefeibus.www.hefeibus.sqlite.AppDatabase;
import com.hefeibus.www.hefeibus.sqlite.HistoryDatabase;
import com.hefeibus.www.hefeibus.utils.ActivityController;

/**
 * 基础Activity 负责初始化
 * Created by cx on 2018/3/17.
 */

public abstract class BaseMvpActivity<P extends IPresenter> extends AppCompatActivity implements IView {
    protected P presenter;
    protected HistoryDatabase mHistoryDatabase;
    protected AppDatabase mAppDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHistoryDatabase = getMyApp().getHistoryDataBase();
        mAppDatabase = getMyApp().getAppDatabase();
        presenter = onCreatePresenter();
        if (presenter != null) presenter.onAttach(this);
        ActivityController.getInstance().add(this);
        setContentView(setLayoutView());
        initViews();
        setAttributes();
        init();
    }

    public App getMyApp() {
        return ((App) getApplication());
    }

    protected abstract void init();


    /**
     * Create the presenter for control
     *
     * @return Current Activity's presenter
     */
    protected abstract P onCreatePresenter();


    /**
     * init some content within views
     * eg: setAdapter addListener;
     */
    protected abstract void setAttributes();

    /**
     * find Views in the layout and set into the members
     */
    protected abstract void initViews();

    /**
     * setting the layout resource of the Activity
     *
     * @return the resource id of layout resource
     */
    protected abstract int setLayoutView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDetach();
        ActivityController.getInstance().remove(this);
    }

    @Override
    public HistoryDatabase getHistoryHandler() {
        return null;
    }
}
