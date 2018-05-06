package com.hefeibus.www.hefeibus.fragment.search;

import android.support.annotation.NonNull;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.GroupInfo;
import com.hefeibus.www.hefeibus.sqlite.AppDatabase;

import java.util.HashMap;
import java.util.List;

class SearchPresenter extends BaseMvpPresenter<ISearchView> implements ISearchPresenter {
    private static final String TAG = "SearchPresenter";

    @Override
    public void loadGroupLineData() {

        ifViewAttached(new ViewAction<ISearchView>() {
            @Override
            public void run(@NonNull ISearchView view) {
                view.showLoadingLayout();
            }
        });

        AppDatabase database = new AppDatabase(weakView.get().getCurrentFragment().getContext());
        final HashMap<String, GroupInfo> map = database.queryGroupInfo();
        final List<String> index = database.getGroupIndex();
        database.close();

        ifViewAttached(new ViewAction<ISearchView>() {
            @Override
            public void run(@NonNull ISearchView view) {
                view.restoreLayout();
                view.showGroupInfo(map, index);
            }
        });

    }
}
