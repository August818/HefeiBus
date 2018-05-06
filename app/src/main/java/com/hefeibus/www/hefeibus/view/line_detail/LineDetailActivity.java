package com.hefeibus.www.hefeibus.view.line_detail;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.adapter.LineDetailAdapter;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpActivity;
import com.hefeibus.www.hefeibus.entity.LineData;
import com.hefeibus.www.hefeibus.entity.StationData;

public class LineDetailActivity extends BaseMvpActivity<ILineDetailPresenter> implements ILineDetailView {
    private LineData line;
    private Toolbar toolbar;
    private TextView lineName;
    private TextView linePrice;
    private TextView lineStart;
    private TextView lineStop;
    private RecyclerView recyclerView;
    private LineDetailAdapter adapter;
    private static final String TAG = "LineDetailActivity";
    private Dialog mDialog;

    @Override
    protected ILineDetailPresenter onCreatePresenter() {
        return new LineDetailPresenter();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setAttributes() {
        setToolbar();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.setListener(new LineDetailAdapter.onItemClickListener() {
            @Override
            public void onClick(StationData station) {
                //跳转到站点信息查询
            }
        });
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
        createDialog();
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
        /*Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);*/
    }

    @Override
    protected int setLayoutView() {
        return R.layout.activity_line_detail;
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void closeLoading() {
        mDialog.dismiss();
    }

    @Override
    public void counterApiError() {
        closeLoading();
        Toast.makeText(this, "接口查询出错\n自动返回", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LineDetailActivity.this.finish();
            }
        }, 1000);
    }

    @Override
    public void showLineInfo(LineData line) {

    }

    @Override
    public LineDetailActivity getCurrentActivity() {
        return this;
    }
}
