package com.swxy.jwbookms.enums;


public enum RedisKey {
    // 当前活跃xqid
    ACTIVE_XQID("activeXqid"),
    // 所有xqid
    XQID_LIST("xqidList"),
    // 学期id时间
    XQID_Time("xqidTime"),
    // 出版社补充
    PUBLIS_HINGHOU_SESERVICE("publishingHouseSupplements");

    private String value;

    private RedisKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
