package com.hefeibus.www.hefeibus.view.auto_search;

import com.hefeibus.www.hefeibus.base.IPresenter;
import com.hefeibus.www.hefeibus.entity.Item;

import java.util.List;

interface IAutoSearchPresenter extends IPresenter<IAutoSearchView> {
    List<Item> getResultSet();

}
