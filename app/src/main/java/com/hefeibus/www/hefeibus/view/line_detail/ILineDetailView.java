package com.hefeibus.www.hefeibus.view.line_detail;

import com.hefeibus.www.hefeibus.base.IView;
import com.hefeibus.www.hefeibus.entity.LineData;

interface ILineDetailView extends IView {
    LineDetailActivity getCurrentActivity();

    void showLineInfo(LineData line);

    void counterApiError();

    void showLoading();

    void closeLoading();
}
