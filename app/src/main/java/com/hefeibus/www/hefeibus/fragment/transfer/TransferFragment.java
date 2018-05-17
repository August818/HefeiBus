package com.hefeibus.www.hefeibus.fragment.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.adapter.TransferPlanExpandListAdapter;
import com.hefeibus.www.hefeibus.basemvp.App;
import com.hefeibus.www.hefeibus.basemvp.BaseMvpFragment;
import com.hefeibus.www.hefeibus.entity.ProgramStatus;
import com.hefeibus.www.hefeibus.entity.TransferData;
import com.hefeibus.www.hefeibus.utils.Parameters;
import com.hefeibus.www.hefeibus.view.select_station.SelectStationActivity;

import java.util.ArrayList;
import java.util.List;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static android.app.Activity.RESULT_OK;

/**
 * 换乘界面
 * Created by xyw-mac on 2018/3/18.
 */

public class TransferFragment extends BaseMvpFragment<ITransferPresenter> implements ITransferView {
    private static final String TAG = "TransferFragment";
    private Switch cacheSwitcher;
    private TextView startTv, stopTv;
    private Button reverseBtn, commitBtn, clearBtn;
    private ExpandableListView mListView;
    private StatusLayoutManager mLayoutManager;
    private TransferPlanExpandListAdapter adapter;
    private ProgramStatus status = ProgramStatus.NORMAL;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //设置switch状态
        refreshSwitch();
    }


    @Override
    protected ITransferPresenter onPresenterCreated() {
        return new TransferPresenter();
    }

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_transfer;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return invokeMe(inflater, container);

    }

    @Override
    protected void initViews(View view) {
        cacheSwitcher = (Switch) view.findViewById(R.id.cache_switcher);
        startTv = (TextView) view.findViewById(R.id.transfer_start);
        stopTv = (TextView) view.findViewById(R.id.transfer_stop);
        reverseBtn = (Button) view.findViewById(R.id.transfer_reverse);
        clearBtn = (Button) view.findViewById(R.id.clear);
        commitBtn = (Button) view.findViewById(R.id.transfer_commit_search);
        mListView = (ExpandableListView) view.findViewById(R.id.expandable_list);
        mLayoutManager = new StatusLayoutManager.Builder(mListView)
                .setEmptyLayout(R.layout.layout_empty)
                .setLoadingLayout(R.layout.layout_loading)
                .setErrorLayout(R.layout.layout_error)
                .build();
    }


    @Override
    protected void setAttributes() {
        //设置列表
        adapter = new TransferPlanExpandListAdapter(getContext());
        mListView.setAdapter(adapter);

        //重置搜索条件
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTv.setText(getString(R.string.transfer_start));
                stopTv.setText(getString(R.string.transfer_stop));
            }
        });

        //开关缓存功能
        cacheSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getMyActivity().getMyApp().setCachingStatus(isChecked);
            }
        });

        //反转起始站点
        reverseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = startTv.getText().toString();
                String stop = stopTv.getText().toString();
                if (start.equals(getString(R.string.transfer_start)) || stop.equals(getString(R.string.transfer_stop))) {
                    Toast.makeText(getContext(), "请选择起点和终点", Toast.LENGTH_SHORT).show();
                    return;
                }
                startTv.setText(stop);
                stopTv.setText(start);
            }
        });

        //设置起点
        startTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectStationActivity.class);
                startActivityForResult(intent, Parameters.CODE_START);
            }
        });

        //设置终点
        stopTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectStationActivity.class);
                startActivityForResult(intent, Parameters.CODE_STOP);

            }
        });

        //提交查询
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status) {
                    case IS_LOADING:
                        presenter.onCancel();
                        commitBtn.setText(getString(R.string.transfer_search_commit));
                        break;
                    default:
                        performQuery();
                        break;
                }
            }
        });
    }

    /**
     * 执行查询条件
     */
    private void performQuery() {
        String start = startTv.getText().toString();
        String stop = stopTv.getText().toString();
        Log.d(TAG, "onClick: \t起点 " + start + "\t终点 " + stop);
        if (start.equals(getString(R.string.transfer_start)) || stop.equals(getString(R.string.transfer_stop))) {
            Toast.makeText(getContext(), "请选择起点和终点", Toast.LENGTH_SHORT).show();
            return;
        }
        if (start.equals(stop)) {
            Toast.makeText(getContext(), "起点终点一致，请原地转圈～", Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.commitQuery(start, stop);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        String station = data.getStringExtra(Parameters.INTENT_STATION_KEY);
        switch (requestCode) {
            case Parameters.CODE_START:
                startTv.setText(station);
                break;

            case Parameters.CODE_STOP:
                stopTv.setText(station);
                break;
        }
    }

    @Override
    protected void init() {
        refreshSwitch();
        showEmptyLayout();
    }

    public void showEmptyLayout() {
        mLayoutManager.showEmptyLayout();
    }

    public void showLoadingLayout() {
        Log.d(TAG, "showLoadingLayout!");
        mLayoutManager.showLoadingLayout();
    }


    public void restoreLayout() {
        Log.d(TAG, "restoreLoadingLayout!");
        mLayoutManager.showSuccessLayout();
    }

    @Override
    public TransferFragment getCurrentActivity() {
        return this;
    }

    @Override
    public void setStatus(ProgramStatus status) {
        this.status = status;

        switch (status) {
            case NORMAL:
                mLayoutManager.showSuccessLayout();
                mLayoutManager.showEmptyLayout();
                commitBtn.setText(getString(R.string.transfer_search_commit));
                break;
            case IS_LOADING:
                mLayoutManager.showLoadingLayout();
                commitBtn.setText("取消查询");
                break;
            case SUCCESS_DATA_EMPTY:
                mLayoutManager.showEmptyLayout();
                commitBtn.setText(getString(R.string.transfer_search_commit));
                break;
            case ERROR_NETWORK_UNREACHED:
                mLayoutManager.showErrorLayout();
                commitBtn.setText(getString(R.string.transfer_search_commit));
                break;
            case SUCCESS_DATA_FROM_LOCAL:
            case SUCCESS_DATA_FROM_NETWORK:
                mLayoutManager.showSuccessLayout();
                commitBtn.setText(getString(R.string.transfer_search_commit));
                break;
        }
    }

    @Override
    public void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void repostTransferData(List<TransferData> transferData, String start, String stop) {
        Log.d(TAG, "repostTransferData: " + transferData.size());
        while (transferData.size() > 6) {
            transferData.remove(transferData.size() - 1);
        }
        adapter.setStartStation(start);
        adapter.setStopStation(stop);
        adapter.setDataList(transferData);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    private void refreshSwitch() {
        if (getMyActivity() != null && getMyActivity().getMyApp() != null) {
            cacheSwitcher.setChecked(getMyActivity().getMyApp().isCaching());
        }
    }

    public App getMyApp() {
        return getMyActivity().getMyApp();
    }
}
