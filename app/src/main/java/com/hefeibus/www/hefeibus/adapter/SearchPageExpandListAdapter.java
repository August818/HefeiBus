package com.hefeibus.www.hefeibus.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.entity.GroupDetail;
import com.hefeibus.www.hefeibus.entity.Line;

import java.util.HashMap;
import java.util.List;

public class SearchPageExpandListAdapter implements ExpandableListAdapter {

    private static final String TAG = "ExpandListAdapter";
    /**
     * String 分组名称
     * GroupDetail 当前分组下的线路列表
     */
    private HashMap<String, GroupDetail> map;
    /**
     * groupNameIndex 分组名称索引
     */
    private List<String> groupNameIndex;
    private onLineItemClickListener listener;
    private Context mContext;

    public SearchPageExpandListAdapter(HashMap<String, GroupDetail> map, List<String> list, Context context) {
        this.map = map;
        this.groupNameIndex = list;
        this.mContext = context;
    }

    public SearchPageExpandListAdapter(Context context) {
        this.mContext = context;
    }

    public void setMap(HashMap<String, GroupDetail> map) {
        this.map = map;
    }

    public void setGroupNameIndex(List<String> groupNameIndex) {
        this.groupNameIndex = groupNameIndex;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
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
     * 返回分组的数量
     */
    @Override
    public int getGroupCount() {
        if (groupNameIndex == null) {
            return 0;
        }
        return groupNameIndex.size();
    }

    /**
     * @param groupPosition 父组索引
     * @return 子项目数量
     */
    @Override
    public int getChildrenCount(int groupPosition) {

        if (map == null || groupNameIndex == null) {
            return 0;
        }

        return map.get(groupNameIndex.get(groupPosition)).getLineList().size();
    }

    /**
     * @param groupPosition 父分组索引
     * @return 分组对象
     */
    @Override
    public Object getGroup(int groupPosition) {
        if (map == null || groupNameIndex == null) throw new RuntimeException("获取父项目出错!");
        return map.get(groupNameIndex.get(groupPosition));
    }

    /**
     * @param groupPosition 父分组索引
     * @param childPosition 子项索引
     * @return 子项对象
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (map == null || groupNameIndex == null) throw new RuntimeException("获取子项目出错！");
        return map.get(groupNameIndex.get(groupPosition)).getLineList().get(childPosition);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.component_group_line_father, null);
        ((TextView) view.findViewById(R.id.group_name)).setText(((GroupDetail) getGroup(groupPosition)).getGroupName());
        ((TextView) view.findViewById(R.id.line_count)).setText(((GroupDetail) getGroup(groupPosition)).getLineCount() + "条");
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.component_group_line_son, null);
        GroupDetail groupDetail = (GroupDetail) getGroup(groupPosition);
        final Line line = groupDetail.getLineList().get(childPosition);
        ((TextView) view.findViewById(R.id.line_name)).setText(line.getLineName() + "路");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(line);
                }
                Log.d(TAG, "onClick: " + line.getLineName());
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
    public interface onLineItemClickListener {
        void onClick(Line line);
    }
}

