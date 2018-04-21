package com.hefeibus.www.hefeibus.fragment.search;

import com.hefeibus.www.hefeibus.basemvp.IView;

interface ISearchView extends IView {

    /**
     * 将 expandableListView 替换为一个等待提示
     */
    void showLoadingLayout();

    void restoreLayout();
}
