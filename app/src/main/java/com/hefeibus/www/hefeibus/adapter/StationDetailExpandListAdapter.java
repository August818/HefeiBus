package com.hefeibus.www.hefeibus.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.entity.StationData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StationDetailExpandListAdapter extends ExpandListAdapter {
    private static final String TAG = "StationDetailExpandList";
    private List<StationData> mDataList;
    private Context mContext;
    private OnItemClickListener listener;

    public StationDetailExpandListAdapter(Context context) {
        mContext = context;
    }

    public void setDataList(List<StationData> dataList) {
        mDataList = dataList;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getGroupCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<String> list = new ArrayList<>();
        if (mDataList != null) {
            String lines = mDataList.get(groupPosition).getLineName();
            String[] lineArray = lines.split(",");
            Collections.addAll(list, lineArray);
        }
        return list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDataList == null ? null : mDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<String> list = new ArrayList<>();
        if (mDataList != null) {
            String lines = mDataList.get(groupPosition).getLineName();
            String[] lineArray = lines.split(",");
            Collections.addAll(list, lineArray);
        }
        return list.get(childPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.component_station_father, null);
        StationData data = (StationData) getGroup(groupPosition);
        TextView stationName = (TextView) view.findViewById(R.id.station_name);
        stationName.setText(data.getStationName());
        TextView orientation = (TextView) view.findViewById(R.id.station_orientation);
        orientation.setText(data.getOrientationInfo());
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.component_station_son, null);
        final String line = (String) getChild(groupPosition, childPosition);
        TextView lineName = (TextView) view.findViewById(R.id.line_name);
        if (line.equals("")) {
            lineName.setText("此站点无车辆停靠");
        } else {
            lineName.setText(line);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(line);
                    }
                    Log.d(TAG, "onClick: 查询 " + line);
                }
            });
        }
        return view;
    }
}
