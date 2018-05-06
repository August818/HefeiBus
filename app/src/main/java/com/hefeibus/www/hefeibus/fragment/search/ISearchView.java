package com.hefeibus.www.hefeibus.fragment.search;

import com.hefeibus.www.hefeibus.basemvp.IView;
import com.hefeibus.www.hefeibus.entity.GroupInfo;

import java.util.HashMap;
import java.util.List;

interface ISearchView extends IView {

    /**
     * 将 expandableListView 替换为一个等待提示
     */
    void showLoadingLayout();

    void restoreLayout();

    void showGroupInfo(HashMap<String, GroupInfo> map, List<String> index);

    SearchFragment getCurrentFragment();
}
