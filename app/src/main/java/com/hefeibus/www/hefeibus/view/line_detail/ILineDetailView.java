package com.hefeibus.www.hefeibus.view.line_detail;

import com.hefeibus.www.hefeibus.basemvp.IView;
import com.hefeibus.www.hefeibus.entity.Line;

interface ILineDetailView extends IView {
    LineDetailActivity getCurrentActivity();

    void showLineInfo(Line line);

    void counterApiError();

    void showLoading();

    void closeLoading();
}
