package com.swxy.jwbookms.enums;

public enum CommonStringEnum {
    SCHOOL("中南林业科技大学涉外学院"),
    Textbook_Subscription_Summary_Table("教材征订汇总表"),
    Summary_Sheet("汇总表"),
    ;


    private String str;

    private CommonStringEnum(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

}
