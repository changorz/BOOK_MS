package com.swxy.jwbookms.common.response.code;

import lombok.ToString;

@ToString
public enum CommonCode implements ResultCode {
    // 基本操作 1
    SUCCESS(true, 11111, "操作成功！"),
    FAIL(false, 00000, "操作失败！"),
    UNAUTHENTICATED(false, 10001, "此操作需要登陆系统！"),
    UNAUTHORISE(false, 10002, "权限不足，无权操作！"),
    INVALID_PARAM(false, 10003, "非法参数！"),
    // 成功 2
    FIND_AC(true, 20001, "查询成功"),
    // JSR 303 校验
    JSR_303(false, 30300, ""),
    // 找不到 4
    HttpRequest_Method_Not_Supported_Exception(false, 40001, "HttpRequest方法不支持异常"),
    // 错误 5
    FILE_UPLOAD_ERR(false, 50001, "文件上传错误"),
    FILE_DOWNLOAD_ERR(false, 50001, "文件下载"),
    FILE_SIZE_LIMIT_10M(false, 50002, "上传失败，文件最大为10M"),
    FILE_TYPE_ERR(false, 50003, "文件类型错误！"),
    FILE_IO_Exception(false, 50004, "文件IO错误！"),


    Excel_Common_Exception(false, 50010, "Excel解析错误，请确认列是否正确"),


    Parameter_Xqid_to_Err(false, 50020, "学期ID错误！"),
    // 兜底 9
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！"),
    ;

    /**
     * 操作是否成功
     */
    boolean success;

    /**
     * 操作代码
     */
    int code;

    /**
     * 提示信息
     */
    String message;

    private CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
