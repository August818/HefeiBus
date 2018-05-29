package com.hefeibus.www.hefeibus.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hefeibus.www.hefeibus.sqlite.HistoryDatabase;
import com.hefeibus.www.hefeibus.view.mian_framework.MainActivity;

/**
 * Fragment base Activity
 * Created by xyw-mac on 2018/3/18.
 */

public abstract class BaseMvpFragment<P extends IPresenter> extends Fragment implements IView {
    protected P presenter;
    protected MainActivity mActivity;
    protected HistoryDatabase database;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
        database = mActivity.mHistoryDatabase;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = onPresenterCreated();
        if (presenter != null) presenter.onAttach(this);
    }

    protected View invokeMe(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(setLayoutView(), container, false);

        initViews(view);

        setAttributes();

        init();
        return view;
    }

    protected abstract void init();

    protected BaseMvpActivity getMyActivity() {
        return ((BaseMvpActivity) getActivity());
    }

    @Override
    public void onDetach() {
        if (presenter != null) presenter.onDetach();
        super.onDetach();
    }


    /**
     * Generate the correspond presenter layer
     *
     * @return the presenter reference;
     */
    protected abstract P onPresenterCreated();

    /**
     * init some content within views
     * eg: setAdapter addListener;
     */
    protected abstract void setAttributes();

    /**
     * find Views in the layout and set into the members
     *
     * @param view the View that contains the widgets
     */
    protected abstract void initViews(View view);

    /**
     * setting the layout resource of the Activity
     *
     * @return the resource id of layout resource
     */
    protected abstract int setLayoutView();

    @Override
    public HistoryDatabase getHistoryHandler() {
        return database;
    }
}
