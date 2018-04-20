package com.hefeibus.www.hefeibus.fragment.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpFragment;
import com.hefeibus.www.hefeibus.basemvp.IPresenter;

/**
 * 搜索界面
 * Created by xyw-mac on 2018/3/18.
 */

public class SearchFragment extends BaseMvpFragment implements ISearchView {

    private TextView searchEt;
    private Switch netChanger;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return invokeMe(inflater, container);
    }

    @Override
    protected IPresenter onPresenterCreated() {
        return new SearchPresenter();
    }

    @Override
    protected void setAttributes() {

    }

    @Override
    protected void findViews(View view) {
        searchEt = (TextView) view.findViewById(R.id.search_box);
        netChanger = (Switch) view.findViewById(R.id.net_switcher);
    }

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_search;
    }
}
