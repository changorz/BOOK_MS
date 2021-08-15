package com.swxy.jwbookms.common.exception;


import com.swxy.jwbookms.common.response.code.ResultCode;

public class ExceptionCast {

    public static void cast(ResultCode resultCode) {
        throw new MyException(resultCode);
    }
}
