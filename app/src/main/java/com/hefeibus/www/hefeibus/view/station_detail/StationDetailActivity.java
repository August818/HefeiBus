package com.hefeibus.www.hefeibus.view.station_detail;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.adapter.OnItemClickListener;
import com.hefeibus.www.hefeibus.adapter.StationDetailExpandListAdapter;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpActivity;
import com.hefeibus.www.hefeibus.entity.StationData;
import com.hefeibus.www.hefeibus.utils.Parameters;
import com.hefeibus.www.hefeibus.view.line_detail.LineDetailActivity;

import java.util.List;

public class StationDetailActivity extends BaseMvpActivity<IStationDetailPresenter> implements IStationDetailView {
    private TextView stationName;
    private ExpandableListView mExpandableListView;
    private StationDetailExpandListAdapter adapter;
    private Dialog mDialog;
    private Toolbar toolbar;
    private static final String TAG = "StationDetailActivity";

    @Override
    protected IStationDetailPresenter onCreatePresenter() {
        return new StationDetailPresenter();
    }

    @Override
    protected void initViews() {
        createDialog();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        stationName = (TextView) findViewById(R.id.station_name);
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandable_list);
    }

    @Override
    protected void setAttributes() {
        setToolbar();
        adapter = new StationDetailExpandListAdapter(this);
        adapter.setListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(String itemName) {
                String name = itemName.substring(0, itemName.indexOf("路"));
                Intent intent = new Intent(StationDetailActivity.this, LineDetailActivity.class);
                intent.putExtra(Parameters.INTENT_LINE_KEY, name);
                StationDetailActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void init() {
        String station = getIntent().getStringExtra(Parameters.INTENT_STATION_KEY);
        stationName.setText(station);
        presenter.queryStationDetail(station);
    }

    @Override
    protected int setLayoutView() {
        return R.layout.activity_station_detail;
    }

    @Override
    public void showStationInfo(List<StationData> stationData) {
        String msg = presenter.isLocal() ? "本地数据" : "网络数据";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        adapter.setDataList(stationData);
        mExpandableListView.setAdapter(adapter);
    }

    @Override
    public void counterApiError() {
        Toast.makeText(this, "网络出错了，请重试", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StationDetailActivity.this.finish();
            }
        }, 1000);
    }

    @Override
    public void closeLoading() {
        mDialog.dismiss();
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    private void setToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StationDetailActivity.this.onBackPressed();
            }
        });
    }

    @Override
    public StationDetailActivity getCurrentActivity() {
        return this;
    }

    private void createDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.component_loading_dialog, null);
        TextView tip = (TextView) view.findViewById(R.id.tips);
        tip.setText("正在加载数据");
        mDialog = new Dialog(this);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(view, new LinearLayout.LayoutParams(500, 500));
    }


    @Override
    protected void onDestroy() {
        int i = presenter == null ? 0 : presenter.onDestroy();
        if (i == 1) Log.d(TAG, "onDestroy: RxJava has disposed");
        super.onDestroy();
    }
}
