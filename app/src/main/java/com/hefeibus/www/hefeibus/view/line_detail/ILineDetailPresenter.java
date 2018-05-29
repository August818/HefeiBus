package com.hefeibus.www.hefeibus.view.line_detail;

import com.hefeibus.www.hefeibus.base.IPresenter;

interface ILineDetailPresenter extends IPresenter<ILineDetailView> {
    void queryLineDetail(String lineName);

    int onDestroy();
}
