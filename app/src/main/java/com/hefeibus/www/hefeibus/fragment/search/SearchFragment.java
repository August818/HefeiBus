package com.hefeibus.www.hefeibus.fragment.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.adapter.SearchPageExpandListAdapter;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpFragment;
import com.hefeibus.www.hefeibus.entity.GroupDetail;

import java.util.HashMap;
import java.util.List;

/**
 * 搜索界面
 * Created by xyw-mac on 2018/3/18.
 */

public class SearchFragment extends BaseMvpFragment<ISearchPresenter> implements ISearchView {
    private static final String TAG = "SearchFragment";
    private TextView searchTv;
    private Switch netChanger;
    private ExpandableListView mListView;
    private RelativeLayout container;
    private SearchPageExpandListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return invokeMe(inflater, container);
    }


    @Override
    protected void init() {
        presenter.loadGroupLineData();
    }

    @Override
    protected ISearchPresenter onPresenterCreated() {
        return new SearchPresenter();
    }

    @Override
    protected void setAttributes() {
        //设置查询具体线路、站点的点击事件
        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void findViews(View view) {
        searchTv = (TextView) view.findViewById(R.id.search_box);
        netChanger = (Switch) view.findViewById(R.id.net_switcher);
        mListView = (ExpandableListView) view.findViewById(R.id.expandable_list);
        container = (RelativeLayout) view.findViewById(R.id.container);
    }

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_search;
    }


    @Override
    public void showLoadingLayout() {
        Log.d(TAG, "showLoadingLayout!");
        container.removeAllViews();
        ProgressBar progressBar = new ProgressBar(getContext());
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(params);
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.primary),
                android.graphics.PorterDuff.Mode.SRC_IN);
        container.addView(progressBar);
    }

    @Override
    public SearchFragment getCurrentActivity() {
        return this;
    }

    @Override
    public void setGroupListDetail(HashMap<String, GroupDetail> map, List<String> groupIndex) {
        mListView.setAdapter(new SearchPageExpandListAdapter(map, groupIndex, getContext()));
    }

    @Override
    public void restoreLayout() {
        Log.d(TAG, "restoreLayout!");
        container.removeAllViews();
        container.addView(mListView);
    }
}
