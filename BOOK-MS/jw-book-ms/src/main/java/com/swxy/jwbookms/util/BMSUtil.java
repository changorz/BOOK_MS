package com.swxy.jwbookms.util;

/**
 * Book_MS工具类
 */
public class BMSUtil {

    public static void test() {
    }

    /**
     * 学期转换成汉字
     *
     * @param xqid
     * @return
     */
    public static String xqidToZh(String xqid) {
        AssertUtil.isXqid(xqid);
        int i = Integer.parseInt(xqid.substring(xqid.length() - 1, xqid.length()));
        if (i == 1) {
            return xqid.substring(0, 4) + "年秋季";
        } else {
            return xqid.substring(5, 9) + "年春季";
        }
    }

}
