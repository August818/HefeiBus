package com.hefeibus.www.hefeibus.view.line_detail;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.adapter.LineDetailAdapter;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpActivity;
import com.hefeibus.www.hefeibus.entity.Line;
import com.hefeibus.www.hefeibus.entity.Station;

public class LineDetailActivity extends BaseMvpActivity<ILineDetailPresenter> implements ILineDetailView {
    private Line line;
    private Toolbar toolbar;
    private TextView lineName;
    private TextView linePrice;
    private TextView lineStart;
    private TextView lineStop;
    private RecyclerView recyclerView;
    private LineDetailAdapter adapter;

    @Override
    protected ILineDetailPresenter onCreatePresenter() {
        line = (Line) getIntent().getSerializableExtra("line");
        return new LineDetailPresenter();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setAttributes() {
        setToolbar();
        lineName.setText(line.getLineName() + "路");
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.setListener(new LineDetailAdapter.onItemClickListener() {
            @Override
            public void onClick(Station station) {
                //跳转到站点信息查询
            }
        });
        presenter.queryLineDetail(line.getLineName());
        recyclerView.setAdapter(adapter);
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
                LineDetailActivity.this.onBackPressed();
            }
        });
    }

    @Override
    protected void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lineName = (TextView) findViewById(R.id.line_name);
        linePrice = (TextView) findViewById(R.id.line_price_desc);
        lineStart = (TextView) findViewById(R.id.line_start);
        lineStop = (TextView) findViewById(R.id.line_stop);
        recyclerView = (RecyclerView) findViewById(R.id.line_detail_recycler);
        adapter = new LineDetailAdapter();
    }

    @Override
    protected int setLayoutView() {
        return R.layout.activity_line_detail;
    }

    @Override
    public void showLineInfo(Line line) {
        if (line.getPassStationList() == null) {
            Toast.makeText(this, "暂未收录数据\n自动返回上一级界面", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LineDetailActivity.this.finish();
                }
            }, 1000);
            return;
        }
        linePrice.setText(line.getPrice());
        lineStart.setText(line.getDesc1());
        lineStop.setText(line.getDesc2());
        adapter.setLines(line);
    }

    @Override
    public LineDetailActivity getCurrentActicity() {
        return this;
    }
}
