package com.hefeibus.www.hefeibus.fragment.search;

import android.content.Intent;
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
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.adapter.SearchPageExpandListAdapter;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpFragment;
import com.hefeibus.www.hefeibus.entity.GroupDetail;
import com.hefeibus.www.hefeibus.entity.Line;
import com.hefeibus.www.hefeibus.view.line_detail.LineDetailActivity;

import java.util.ArrayList;
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
    private PoiSearch mSearch;
    private List<String> busLineIDList = new ArrayList<>();
    private BusLineSearch mBusLineSearch;


    /**
     * 这里是 Fragment 执行的入口
     *
     * @return View - 抽象对象，界面，看到的 View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(getContext().getApplicationContext());

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

        adapter.setListener(new SearchPageExpandListAdapter.onLineItemClickListener() {
            @Override
            public void onClick(Line line) {
                Intent intent = new Intent(getContext(), LineDetailActivity.class);
                intent.putExtra("line", line);
                getContext().startActivity(intent);
            }
        });

        mBusLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
            @Override
            public void onGetBusLineResult(BusLineResult busLineResult) {
                Log.d(TAG, "onGetBusLineResult: " + busLineResult.status);
            }
        });

        mSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getContext(), "抱歉，未找到结果",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                // 遍历所有poi，找到类型为公交线路的poi
                busLineIDList.clear();
                for (PoiInfo poi : result.getAllPoi()) {
                    if (poi.type == PoiInfo.POITYPE.BUS_LINE || poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
                        busLineIDList.add(poi.uid);
                    }
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });

    }

    /**
     * 初始化 你需要用的 所有的 控件
     *
     * @param view the View that contains the widgets
     */
    @Override
    protected void findViews(View view) {
        searchTv = (TextView) view.findViewById(R.id.search_box);
        netChanger = (Switch) view.findViewById(R.id.net_switcher);
        mListView = (ExpandableListView) view.findViewById(R.id.expandable_list);
        container = (RelativeLayout) view.findViewById(R.id.container);
        adapter = new SearchPageExpandListAdapter(getContext());
        mSearch = PoiSearch.newInstance();
        mBusLineSearch = BusLineSearch.newInstance();

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
        adapter.setGroupNameIndex(groupIndex);
        adapter.setMap(map);
        mListView.setAdapter(adapter);
    }

    @Override
    public void restoreLayout() {
        Log.d(TAG, "restoreLayout!");
        container.removeAllViews();
        container.addView(mListView);
    }


}
