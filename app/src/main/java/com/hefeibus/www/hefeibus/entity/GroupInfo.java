package com.hefeibus.www.hefeibus.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Search Page 分组公交信息的实体类
 */
public class GroupInfo implements Serializable {
    /**
     * 当前分组下线路列表
     */
    private List<LineData> lineList;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 当前分组下线路数量
     */
    private int lineCount;


    public GroupInfo() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public List<LineData> getLineList() {
        return lineList;
    }

    public void setLineList(List<LineData> lineList) {
        this.lineList = lineList;
    }


}
