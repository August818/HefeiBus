package com.hefeibus.www.hefeibus.view.line_detail;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.adapter.LineDetailAdapter;
import com.hefeibus.www.hefeibus.adapter.OnItemClickListener;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpActivity;
import com.hefeibus.www.hefeibus.entity.LineData;
import com.hefeibus.www.hefeibus.utils.Parameters;
import com.hefeibus.www.hefeibus.view.station_detail.StationDetailActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class LineDetailActivity extends BaseMvpActivity<ILineDetailPresenter> implements ILineDetailView {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView info1, info2, info3, info4, info5, info6;
    private Button reverse_btn;
    private Button favour_Btn;
    private LineDetailAdapter adapter = new LineDetailAdapter();
    private static final String TAG = "LineDetailActivity";
    private Dialog mDialog;
    private Disposable disposable;
    private LineData lineData;
    private TextView lineName;
    //上下行切换指示器
    private boolean isDown;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_line_detail;
    }

    @Override
    protected ILineDetailPresenter onCreatePresenter() {
        return new LineDetailPresenter();
    }


    @Override
    protected void initViews() {
        createDialog();
        recyclerView = (RecyclerView) findViewById(R.id.line_detail_recycler);
        lineName = (TextView) findViewById(R.id.line_name);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        reverse_btn = (Button) findViewById(R.id.line_reverse_btn);
        favour_Btn = (Button) findViewById(R.id.line_favour_icon);
        //起点名称
        info1 = (TextView) findViewById(R.id.line_info2);
        //终点名称
        info2 = (TextView) findViewById(R.id.line_info4);
        //运营时间
        info3 = (TextView) findViewById(R.id.line_info6);
        //运营公司
        info4 = (TextView) findViewById(R.id.line_info8);
        //线路站点
        info5 = (TextView) findViewById(R.id.line_info10);
        //线路长度
        info6 = (TextView) findViewById(R.id.line_info12);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setAttributes() {
        setToolbar();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onClick(String station) {
                Intent intent = new Intent(LineDetailActivity.this, StationDetailActivity.class);
                intent.putExtra(Parameters.INTENT_STATION_KEY, station);
                LineDetailActivity.this.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        reverse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上下行切换
            }
        });
        favour_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //写入是否收藏
            }
        });
        reverse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LineDetailActivity.this, "切换方向", Toast.LENGTH_SHORT).show();
                //默认为 false - 即为显示上行
                if (isDown) {
                    //此处切换上行
                    info1.setText(lineData.getUpStation().get(0).getStationName());
                    info2.setText(lineData.getUpStation().get(lineData.getUpCount() - 1).getStationName());
                    info3.setText(lineData.getMainFirstTime() + " - " + lineData.getMainLastTime());
                    info4.setText(lineData.getOrg());
                    info5.setText(lineData.getUpCount() + " 站");
                    info6.setText(lineData.getUpLen() + " 千米");
                    adapter.setStations(lineData.getUpStation());
                    isDown = false;
                } else {
                    //此处切换下行
                    //此处切换上行
                    info1.setText(lineData.getDownStation().get(0).getStationName());
                    info2.setText(lineData.getDownStation().get(lineData.getDownCount() - 1).getStationName());
                    info3.setText(lineData.getSubFirstTime() + " - " + lineData.getSubLastTime());
                    info4.setText(lineData.getOrg());
                    info5.setText(lineData.getDownCount() + " 站");
                    info6.setText(lineData.getDownLen() + " 千米");
                    adapter.setStations(lineData.getDownStation());
                    isDown = true;
                }
            }
        });
    }

    @Override
    protected void init() {
        String lineName = getIntent().getStringExtra(Parameters.INTENT_LINE_KEY);
        presenter.queryLineDetail(lineName);
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
    public void showLineInfo(final LineData line) {
        disposable = Observable.just(line)
                .map(new Function<LineData, List<LineData.StationBean>>() {
                    @Override
                    public List<LineData.StationBean> apply(LineData lineData) {
                        if (lineData.isLocal()) {
                            Toast.makeText(LineDetailActivity.this, "本地数据", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LineDetailActivity.this, "网络数据", Toast.LENGTH_SHORT).show();
                        }
                        lineName.setText(lineData.getLineName() + "路");
                        //存储上行站点数量
                        int upCount = lineData.getUpStation().size();
                        lineData.setUpCount(upCount);

                        //如果为1或3类型，则有下行
                        if (lineData.getRunCateGory() == 1 || lineData.getRunCateGory() == 3) {
                            int downCount = lineData.getDownStation().size();
                            lineData.setDownCount(downCount);
                            reverse_btn.setVisibility(View.VISIBLE);
                        } else {
                            reverse_btn.setVisibility(View.INVISIBLE);
                        }
                        info1.setText(lineData.getUpStation().get(0).getStationName());
                        info2.setText(lineData.getUpStation().get(upCount - 1).getStationName());
                        info3.setText(lineData.getMainFirstTime() + " - " + lineData.getMainLastTime());
                        info4.setText(lineData.getOrg());
                        info5.setText(upCount + " 站");
                        info6.setText(lineData.getUpLen() + " 千米");
                        LineDetailActivity.this.lineData = lineData;
                        return lineData.getUpStation();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<LineData.StationBean>>() {
                    @Override
                    public void accept(List<LineData.StationBean> stationBeans) {
                        adapter.setStations(stationBeans);
                    }
                });

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
        Toast.makeText(this, "网络出错了，请重试", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LineDetailActivity.this.finish();
            }
        }, 1000);
    }

    @Override
    public LineDetailActivity getCurrentActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        int i = presenter == null ? 0 : presenter.onDestroy();
        if (i == 1) Log.d(TAG, "onDestroy: RxJava has disposed");
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }
}
