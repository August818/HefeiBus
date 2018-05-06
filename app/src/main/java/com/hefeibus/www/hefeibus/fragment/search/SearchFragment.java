package com.hefeibus.www.hefeibus.fragment.search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.adapter.SearchPageExpandListAdapter;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpFragment;
import com.hefeibus.www.hefeibus.entity.GroupInfo;
import com.hefeibus.www.hefeibus.utils.Parameters;
import com.hefeibus.www.hefeibus.view.line_detail.LineDetailActivity;

import java.util.HashMap;
import java.util.List;

/**
 * 搜索界面
 * Created by xyw-mac on 2018/3/18.
 */

public class SearchFragment extends BaseMvpFragment<ISearchPresenter> implements ISearchView {
    private static final String TAG = "SearchFragment";
    //搜索框
    private TextView searchTv;
    //是否开启缓存
    private Switch cacheSwitcher;
    //线路列表
    private ExpandableListView mListView;
    //容器
    private RelativeLayout container;
    //适配器
    private SearchPageExpandListAdapter adapter;


    /**
     * 这里是 Fragment 执行的入口
     *
     * @return View - 抽象对象，界面，看到的 View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return invokeMe(inflater, container);
    }

    @Override
    protected ISearchPresenter onPresenterCreated() {
        return new SearchPresenter();
    }

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_search;
    }

    /**
     * 初始化控件
     *
     * @param view the View that contains the widgets
     */
    @Override
    protected void initViews(View view) {
        searchTv = (TextView) view.findViewById(R.id.search_box);
        cacheSwitcher = (Switch) view.findViewById(R.id.cache_switcher);
        mListView = (ExpandableListView) view.findViewById(R.id.expandable_list);
        container = (RelativeLayout) view.findViewById(R.id.container);
        adapter = new SearchPageExpandListAdapter(getContext());
    }

    @Override
    protected void setAttributes() {
        //点击开始查询线路信息
        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        //开关缓存功能
        cacheSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = SearchFragment.this.getContext().getSharedPreferences(Parameters.APP_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if (isChecked) {
                    Toast.makeText(buttonView.getContext(), "已开启数据缓存", Toast.LENGTH_SHORT).show();
                    editor.putBoolean(Parameters.IS_CACHING, true);
                } else {
                    Toast.makeText(buttonView.getContext(), "已关闭数据缓存", Toast.LENGTH_SHORT).show();
                    editor.putBoolean(Parameters.IS_CACHING, false);
                }
                editor.apply();
            }
        });
        //点击开始查询线路详情
        adapter.setListener(new SearchPageExpandListAdapter.onLineItemClickListener() {
            @Override
            public void onClick(String lineName) {
                Intent intent = new Intent(getContext(), LineDetailActivity.class);
                intent.putExtra(Parameters.INTENT_LINE_KEY, lineName);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void init() {
        presenter.loadGroupLineData();
    }


    @Override
    public SearchFragment getCurrentFragment() {
        return this;
    }

    @Override
    public void showGroupInfo(HashMap<String, GroupInfo> map, List<String> index) {
        adapter.setGroupNameIndex(index);
        adapter.setMap(map);
        mListView.setAdapter(adapter);
    }

    @Override
    public void showLoadingLayout() {
        Log.d(TAG, "showLoadingLayout!");
        container.removeAllViews();
        ProgressBar progressBar = new ProgressBar(getContext());
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(params);
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.primary),
                android.graphics.PorterDuff.Mode.SRC_IN);
        container.addView(progressBar);
    }

    @Override
    public void restoreLayout() {
        Log.d(TAG, "restoreLoadingLayout!");
        container.removeAllViews();
        container.addView(mListView);
    }


}
