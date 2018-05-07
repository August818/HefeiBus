package com.hefeibus.www.hefeibus.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.entity.LineData;
import com.hefeibus.www.hefeibus.entity.StationData;
import com.hefeibus.www.hefeibus.utils.Parameters;
import com.hefeibus.www.hefeibus.view.station_detail.StationDetailActivity;

import java.util.List;

public class LineDetailAdapter extends RecyclerView.Adapter<LineDetailAdapter.ViewHolder> {
    private static final String TAG = "LineDetailAdapter";
    private List<LineData.StationBean> stations;
    private onItemClickListener listener;

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_line_detail_recycler_item, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.number.setText(String.valueOf(position + 1));
        holder.name.setText(stations.get(position).getStationName());
    }

    @Override
    public int getItemCount() {
        return stations == null ? 0 : stations.size();
    }

    public void setStations(List<LineData.StationBean> stations) {
        this.stations = stations;
        this.notifyDataSetChanged();
    }

    public interface onItemClickListener {
        void onClick(StationData station);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView number, name;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        Intent intent = new Intent(v.getContext(), StationDetailActivity.class);
                        intent.putExtra(Parameters.INTENT_STATION_KEY, stations.get(getAdapterPosition()).getStationName());
                        v.getContext().startActivity(intent);
                    }
                    Log.d(TAG, "onClick: " + stations.get(getAdapterPosition()).getStationName());
                }
            });
            number = (TextView) itemView.findViewById(R.id.station_number);
            name = (TextView) itemView.findViewById(R.id.station_name);
        }
    }
}
