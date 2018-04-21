package com.hefeibus.www.hefeibus.entity;

import java.util.List;

/**
 * Search Page 分组公交信息的实体类
 */
public class GroupDetail {
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 当前分组下线路数量
     */
    private int lineCount;
    /**
     * 当前分组下线路列表
     */
    private List<Line> lineList;

}