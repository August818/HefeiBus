package com.hefeibus.www.hefeibus.fragment.search;

import com.hefeibus.www.hefeibus.base.IPresenter;

interface ISearchPresenter extends IPresenter<ISearchView> {
    /**
     * 载入分组路线数据
     */
    void loadGroupLineData();
}
