package com.mint.other.db;

/**
 * @author ：cw
 * @date ：Created in 2020/6/10 下午12:04
 * @description：
 * @modified By：
 * @version: $
 */

public enum DBTypeEnum {

    DATASOURCE_ONE("source-one"),
    DATASOURCE_TWO("source-one");
    private String value;

    DBTypeEnum(String value) {
        this.value = value;
    }

    public String Value() {
        return value;
    }
}
