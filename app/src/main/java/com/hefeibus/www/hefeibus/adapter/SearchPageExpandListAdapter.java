package com.hefeibus.www.hefeibus.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.entity.GroupDetail;
import com.hefeibus.www.hefeibus.entity.Line;

import java.util.HashMap;
import java.util.List;

public class SearchPageExpandListAdapter implements ExpandableListAdapter {


    /**
     * String -> 分组名称
     * List<GroupDetail> 当前分组下的线路列表
     */
    private HashMap<String, GroupDetail> map;
    private List<String> list;
    private onLineItemClickListener listener;
    private Context mContext;

    public SearchPageExpandListAdapter(HashMap<String, GroupDetail> map, List<String> list, Context context) {
        this.map = map;
        this.list = list;
        this.mContext = context;
    }

    public void setListener(onLineItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    /**
     * 返回 分组名称索引的数量
     *
     * @return lise.size()
     */
    @Override
    public int getGroupCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /**
     * @param groupPosition 位置 - 分组名 - line count
     * @return 子项数目
     */
    @Override
    public int getChildrenCount(int groupPosition) {

        if (map == null || list == null) {
            return 0;
        }

        return map.get(list.get(groupPosition)).getLineList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (map == null || list == null) {
            return null;
        }
        return map.get(list.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (map == null || list == null) {
            return null;
        }
        return map.get(list.get(groupPosition)).getLineList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.component_group_line_father, null);
        ((TextView) view.findViewById(R.id.group_name)).setText(((GroupDetail) getGroup(groupPosition)).getGroupName());
        ((TextView) view.findViewById(R.id.line_count)).setText(((GroupDetail) getGroup(groupPosition)).getLineCount() + "条");
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.component_group_line_son, null);
        GroupDetail groupDetail = (GroupDetail) getGroup(groupPosition);
        final Line line = groupDetail.getLineList().get(childPosition);
        ((TextView) view.findViewById(R.id.line_name)).setText(line.getLineName() + "路");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(line);
                }
                Toast.makeText(mContext, "查询" + line.getLineName() + "线路", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }


    /**
     * 将adapter中点击的某个线路分发到 fragment 中去
     */
    interface onLineItemClickListener {
        void onClick(Line line);
    }
}

