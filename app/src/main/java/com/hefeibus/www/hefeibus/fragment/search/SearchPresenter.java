package com.hefeibus.www.hefeibus.fragment.search;

import com.hefeibus.www.hefeibus.basemvp.BaseMvpPresenter;
import com.hefeibus.www.hefeibus.sqlite.DatabaseDal;

class SearchPresenter extends BaseMvpPresenter<ISearchView> implements ISearchPresenter {

    /**
     * 数据库操作层
     */
    private DatabaseDal mDal;

    public SearchPresenter() {
        mDal = new DatabaseDal();

    }

    @Override
    public void loadGroupLineData() {

    }
}
