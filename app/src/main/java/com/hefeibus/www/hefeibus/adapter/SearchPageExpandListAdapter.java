package com.hefeibus.www.hefeibus.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hefeibus.www.hefeibus.R;
import com.hefeibus.www.hefeibus.entity.GroupInfo;
import com.hefeibus.www.hefeibus.entity.LineData;

import java.util.HashMap;
import java.util.List;

public class SearchPageExpandListAdapter extends ExpandListAdapter {

    private static final String TAG = "ExpandListAdapter";
    /**
     * String 分组名称
     * GroupInfo 当前分组下的线路列表
     */
    private HashMap<String, GroupInfo> map;
    /**
     * groupNameIndex 分组名称索引
     */
    private List<String> groupNameIndex;
    private OnItemClickListener listener;
    private Context mContext;


    public SearchPageExpandListAdapter(Context context) {
        this.mContext = context;
    }

    public void setMap(HashMap<String, GroupInfo> map) {
        this.map = map;
    }

    public void setGroupNameIndex(List<String> groupNameIndex) {
        this.groupNameIndex = groupNameIndex;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 返回分组的数量
     */
    @Override
    public int getGroupCount() {
        return groupNameIndex == null ? 0 : groupNameIndex.size();
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.component_group_line_father, null);
        ((TextView) view.findViewById(R.id.group_name)).setText(((GroupInfo) getGroup(groupPosition)).getGroupName());
        ((TextView) view.findViewById(R.id.line_count)).setText(((GroupInfo) getGroup(groupPosition)).getLineCount() + "条");
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.component_group_line_son, null);
        GroupInfo groupInfo = (GroupInfo) getGroup(groupPosition);
        final LineData line = groupInfo.getLineList().get(childPosition);
        ((TextView) view.findViewById(R.id.line_name)).setText(line.getLineName() + "路");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(line.getLineName());
                }
                Log.d(TAG, "onClick: " + line.getLineName());
            }
        });
        return view;
    }
}

