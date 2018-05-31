package com.hefeibus.www.hefeibus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.entity.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class DisplayHistoryAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<T> dataList = new ArrayList<>();
    private OnItemClickListener<T> listener;

    private int empty = 0;
    private int nonNull = 1;

    public DisplayHistoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_histroy_empty, parent, false);
            return new EmptyViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.histroy_record_item, parent, false);
            return new NonNullViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DisplayHistoryAdapter.NonNullViewHolder) {
            NonNullViewHolder var = (NonNullViewHolder) holder;
            if (dataList.get(position) instanceof String) {
                var.text.setText((CharSequence) dataList.get(position));
            } else {
                Wrapper wrapper = (Wrapper) dataList.get(position);
                var.text.setText(wrapper.getStart() + " 前往 " + wrapper.getStop());
            }
        }
    }

    /**
     * @return 如果数据为0，那么显示无数据的 item
     * 否则显示 数据项
     */
    @Override
    public int getItemCount() {
        return dataList.size() == 0 ? 1 : dataList.size();
    }

    class NonNullViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        NonNullViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(dataList.get(getAdapterPosition()));
                    }
                }
            });
            text = (TextView) itemView.findViewById(R.id.line_name);
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
