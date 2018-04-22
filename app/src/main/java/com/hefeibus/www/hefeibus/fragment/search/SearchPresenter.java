package com.hefeibus.www.hefeibus.fragment.search;

import android.support.annotation.NonNull;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.entity.GroupDetail;
import com.hefeibus.www.hefeibus.sqlite.GroupLineDatabase;

import java.util.HashMap;
import java.util.List;

class SearchPresenter extends BaseMvpPresenter<ISearchView> implements ISearchPresenter {

    /**
     * 数据库操作层
     */
    private GroupLineDatabase mDal;

    public SearchPresenter() {
    }

    @Override
    public void loadGroupLineData() {
        ifViewAttached(new ViewAction<ISearchView>() {
            @Override
            public void run(@NonNull ISearchView view) {
                view.showLoadingLayout();
            }
        });

        mDal = new GroupLineDatabase(weakView.get().getCurrentActivity().getContext());
        final HashMap<String, GroupDetail> map = mDal.queryAllGroupDetail();
        final List<String> groupIndex = mDal.queryGroupIndex();
        mDal.close();
        ifViewAttached(new ViewAction<ISearchView>() {
            @Override
            public void run(@NonNull ISearchView view) {
                view.restoreLayout();
                view.setGroupListDetail(map, groupIndex);
            }
        });

    }
}
