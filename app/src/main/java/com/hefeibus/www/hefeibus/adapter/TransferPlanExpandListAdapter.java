package com.hefeibus.www.hefeibus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.entity.TransferData;
import com.hefeibus.www.hefeibus.utils.Parameters;
import com.hefeibus.www.hefeibus.view.line_detail.LineDetailActivity;
import com.hefeibus.www.hefeibus.view.station_detail.StationDetailActivity;

import java.util.List;


public class TransferPlanExpandListAdapter extends ExpandListAdapter {
    private List<TransferData> mDataList;
    private String startStation, stopStation;
    private Context mContext;

    public TransferPlanExpandListAdapter(Context context) {
        mContext = context;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public void setStopStation(String stopStation) {
        this.stopStation = stopStation;
    }

    public void setDataList(List<TransferData> dataList) {
        if (mDataList != null) mDataList.clear();
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        TransferData data = mDataList.get(groupPosition);
        return new Title()
                .setDirect(data.getTranType().equals("1"))
                .setFirstLine(data.getBeginLineName())
                .setSecondLine(data.getEndLineName())
                .setCount(data.getStationCount());
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        TransferData data = mDataList.get(groupPosition);
        return new Detail()
                .setStartStation(startStation)
                .setStartLine(data.getBeginLineName())
                .setMiddleStation(data.getStationName())
                .setMiddleLine(data.getEndLineName())
                .setStopStation(stopStation)
                .setFirstCount(data.getBeginStationPoint())
                .setSecondCount(data.getEndStationPoint())
                .setType(data.getTranType());
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.transfer_plan_title, null);
        TextView firstLine = (TextView) view.findViewById(R.id.first_line);
        TextView type = (TextView) view.findViewById(R.id.type);
        TextView secondLine = (TextView) view.findViewById(R.id.second_line);
        TextView total = (TextView) view.findViewById(R.id.total);
        Title title = (Title) getGroup(groupPosition);
        firstLine.setText(title.getFirstLine());
        type.setText(title.isDirect ? "直达" : "转乘");
        total.setText(title.getCount());
        secondLine.setText(title.getSecondLine());
        secondLine.setVisibility(title.isDirect ? View.INVISIBLE : View.VISIBLE);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Detail detail = (Detail) getChild(groupPosition, 0);
        View view = LayoutInflater.from(mContext).inflate(R.layout.transfer_plan_detail, null);

        //设置起点终点信息
        TextView start = (TextView) view.findViewById(R.id.start);
        start.setText(startStation);
        TextView stop = (TextView) view.findViewById(R.id.stop);
        stop.setText(stopStation);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StationDetailActivity.class);
                intent.putExtra(Parameters.INTENT_STATION_KEY, startStation);
                mContext.startActivity(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StationDetailActivity.class);
                intent.putExtra(Parameters.INTENT_STATION_KEY, stopStation);
                mContext.startActivity(intent);
            }
        });


        TextView info1 = (TextView) view.findViewById(R.id.transfer_info1);
        if (detail.getType().equals("1")) {
            //处理直达
            LinearLayout container = (LinearLayout) view.findViewById(R.id.transfer_container);
            container.setVisibility(View.GONE);
            //起点，路，数，下站
            info1.setText(String.format(mContext.getString(R.string.info), startStation, detail.getStartLine(), detail.getFirstCount(), stopStation));
            info1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LineDetailActivity.class);
                    intent.putExtra(Parameters.INTENT_LINE_KEY, detail.getStartLine());
                    mContext.startActivity(intent);
                }
            });
        } else {
            //处理换乘
            TextView middle = (TextView) view.findViewById(R.id.middle);
            middle.setText(detail.getMiddleStation());
            middle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, StationDetailActivity.class);
                    intent.putExtra(Parameters.INTENT_STATION_KEY, detail.getMiddleStation());
                    mContext.startActivity(intent);
                }
            });
            TextView info2 = (TextView) view.findViewById(R.id.transfer_info2);
            info1.setText(String.format(mContext.getString(R.string.info), startStation, detail.getStartLine(), detail.getFirstCount(), detail.getMiddleStation()));
            info1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LineDetailActivity.class);
                    intent.putExtra(Parameters.INTENT_LINE_KEY, detail.getStartLine());
                    mContext.startActivity(intent);
                }
            });
            info2.setText(String.format(mContext.getString(R.string.info), detail.getMiddleStation(), detail.getMiddleLine(), detail.getSecondCount(), stopStation));
            info2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LineDetailActivity.class);
                    intent.putExtra(Parameters.INTENT_LINE_KEY, detail.getMiddleLine());
                    mContext.startActivity(intent);
                }
            });
        }
        return view;
    }

    private class Title {
        //是否直达
        private boolean isDirect;
        //第一线路名
        private String firstLine;
        //第二线路名
        private String secondLine;
        //站点总数
        private String count;

        boolean isDirect() {
            return isDirect;
        }

        Title setDirect(boolean direct) {
            isDirect = direct;
            return this;
        }

        String getFirstLine() {
            return firstLine;
        }

        Title setFirstLine(String firstLine) {
            this.firstLine = firstLine;
            return this;
        }

        String getSecondLine() {
            return secondLine;
        }

        Title setSecondLine(String secondLine) {
            this.secondLine = secondLine;
            return this;
        }

        String getCount() {
            return count;
        }

        Title setCount(String count) {
            this.count = count;
            return this;
        }
    }


    private class Detail {
        private String startStation;
        private String middleStation;
        private String stopStation;
        private String startLine;
        private String middleLine;
        private String firstCount;
        private String secondCount;
        private String type;

        String getStartStation() {
            return startStation;
        }

        Detail setStartStation(String startStation) {
            this.startStation = startStation;
            return this;
        }

        String getMiddleStation() {
            return middleStation;
        }

        Detail setMiddleStation(String middleStation) {
            this.middleStation = middleStation;
            return this;

        }

        String getStopStation() {
            return stopStation;
        }

        Detail setStopStation(String stopStation) {
            this.stopStation = stopStation;
            return this;

        }

        String getStartLine() {
            return startLine;
        }

        Detail setStartLine(String startLine) {
            this.startLine = startLine;
            return this;

        }

        String getMiddleLine() {
            return middleLine;
        }

        Detail setMiddleLine(String middleLine) {
            this.middleLine = middleLine;
            return this;

        }

        String getFirstCount() {
            return firstCount;
        }

        Detail setFirstCount(String firstCount) {
            this.firstCount = firstCount;
            return this;

        }

        String getSecondCount() {
            return secondCount;
        }

        Detail setSecondCount(String secondCount) {
            this.secondCount = secondCount;
            return this;

        }

        public Detail setType(String type) {
            this.type = type;
            return this;
        }

        public String getType() {
            return type;
        }
    }

}
