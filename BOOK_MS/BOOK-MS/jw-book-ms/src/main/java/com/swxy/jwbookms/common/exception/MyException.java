package com.swxy.jwbookms.common.exception;


import com.swxy.jwbookms.common.response.code.ResultCode;

public class MyException extends RuntimeException {

    // 错误代码
    ResultCode resultCode;

    public MyException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }


}
