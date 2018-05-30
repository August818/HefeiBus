package com.hefeibus.www.hefeibus.fragment.history;

import com.hefeibus.www.hefeibus.base.IView;
import com.hefeibus.www.hefeibus.entity.Wrapper;

import java.util.List;

interface IHistoryView extends IView {
    void setDatum(List<String> line, List<String> station, List<Wrapper> transfer);
}

