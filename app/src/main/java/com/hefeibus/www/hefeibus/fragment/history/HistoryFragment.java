package com.hefeibus.www.hefeibus.fragment.history;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.adapter.DisplayHistoryAdapter;
import com.hefeibus.www.hefeibus.adapter.OnItemClickListener;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpFragment;
import com.hefeibus.www.hefeibus.entity.Wrapper;
import com.hefeibus.www.hefeibus.utils.Parameters;
import com.hefeibus.www.hefeibus.view.line_detail.LineDetailActivity;
import com.hefeibus.www.hefeibus.view.station_detail.StationDetailActivity;

/**
 * 附近界面
 * Created by xyw-mac on 2018/3/18.
 */

public class HistoryFragment extends BaseMvpFragment<IHistoryPresenter> implements IHistoryView {

    private RecyclerView stationRv, lineRv, transRv;
    private Button clearBtn;
    private DisplayHistoryAdapter<String> stationAdapter;
    private DisplayHistoryAdapter<String> lineAdapter;
    private DisplayHistoryAdapter<Wrapper> transAdapter;


    @Override
    protected int setLayoutView() {
        return R.layout.fragment_history;
    }

    @Override
    protected IHistoryPresenter onPresenterCreated() {
        return new HistoryPresenter(database);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return invokeMe(inflater, container);
    }

    @Override
    protected void initViews(View view) {
        stationRv = (RecyclerView) view.findViewById(R.id.recent_station);
        lineRv = (RecyclerView) view.findViewById(R.id.recent_line);
        transRv = (RecyclerView) view.findViewById(R.id.recent_transfer);
        clearBtn = (Button) view.findViewById(R.id.clear_history);
    }

    @Override
    protected void setAttributes() {
        stationAdapter = new DisplayHistoryAdapter<>(getContext());
        lineAdapter = new DisplayHistoryAdapter<>(getContext());
        transAdapter = new DisplayHistoryAdapter<>(getContext());

        stationAdapter.setListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(String itemName) {
                Intent intent = new Intent(getContext(), StationDetailActivity.class);
                intent.putExtra(Parameters.INTENT_STATION_KEY, itemName);
                getContext().startActivity(intent);
            }
        });

        lineAdapter.setListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(String itemName) {
                String name = itemName.substring(0, itemName.indexOf("路"));
                Intent intent = new Intent(getContext(), LineDetailActivity.class);
                intent.putExtra(Parameters.INTENT_LINE_KEY, name);
                getContext().startActivity(intent);
            }
        });

        transAdapter.setListener(new OnItemClickListener<Wrapper>() {
            @Override
            public void onClick(Wrapper wrapper) {
                mActivity.requestTransferOutSide(wrapper);
            }
        });

        stationRv.setAdapter(stationAdapter);
        lineRv.setAdapter(lineAdapter);
        transRv.setAdapter(transAdapter);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onClick(View v) {
                presenter.clearHistory();
                refreshData();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) return;
        refreshData();
    }

    private void refreshData() {
        presenter.requestHistory();
        transAdapter.notifyDataSetChanged();
        lineAdapter.notifyDataSetChanged();
        stationAdapter.notifyDataSetChanged();
    }

    @Override
    protected void init() {
        refreshData();
    }
}
