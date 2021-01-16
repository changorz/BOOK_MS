package com.swxy.jwbookms.common.response;

import com.swxy.jwbookms.common.response.code.CommonCode;
import com.swxy.jwbookms.common.response.code.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ResponseResult implements Response {

    //操作是否成功
    boolean success = SUCCESS;

    //操作代码
    int code = SUCCESS_CODE;

    //提示信息
    String message;

    /**
     * 消息返回
     *
     * @param resultCode
     */
    public ResponseResult(ResultCode resultCode) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    /**
     * jsr303校验错误返回 /  也可以用来提示变换的信息
     *
     * @param resultCode JSR_303(false, 30300, ""),
     * @param message    错误消息
     */
    public ResponseResult(ResultCode resultCode, String message) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = message;
    }


    public static ResponseResult SUCCESS() {
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public static ResponseResult SUCCESS(String msg) {
        return new ResponseResult(CommonCode.SUCCESS, msg);
    }

    public static ResponseResult FAIL() {
        return new ResponseResult(CommonCode.FAIL);
    }

    public static ResponseResult FAIL(String msg) {
        return new ResponseResult(CommonCode.FAIL, msg);
    }

}
