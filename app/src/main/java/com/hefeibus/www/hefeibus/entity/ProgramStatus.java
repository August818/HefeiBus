package com.hefeibus.www.hefeibus.entity;

/**
 * 标记状态
 */
public enum ProgramStatus {
    SUCCESS_DATA_FROM_LOCAL("数据来源本地"),
    SUCCESS_DATA_FROM_NETWORK("数据来自网络"),
    SUCCESS_DATA_EMPTY("数据查询为空"),
    IS_LOADING("等待状态"),
    NORMAL("正常状态"),
    ERROR_NETWORK_UNREACHED("网络问题请求失败");


    private String description;

    ProgramStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
