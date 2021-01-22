package com.swxy.jwbookms.common.exception;

import com.alibaba.excel.exception.ExcelCommonException;
import com.google.common.collect.ImmutableMap;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.code.CommonCode;
import com.swxy.jwbookms.common.response.code.ResultCode;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOError;
import java.io.IOException;

@RestControllerAdvice
public class ExeceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExeceptionHandler.class);

    // 定义map，配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    // 义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    // 捕获MethodArgumentNotValidException此类异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult customException(MethodArgumentNotValidException exception) {
        // 记录日志
        LOGGER.error("catch exception:{}", exception.getMessage());
        return new ResponseResult(CommonCode.JSR_303, exception.getBindingResult().getFieldError().getDefaultMessage());
    }

    // 捕获CustomException此类异常
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public ResponseResult customException(MyException customException) {
        //记录日志
        LOGGER.error("catch exception:{}", customException.getMessage());
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }


    // 捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        // 记录日志
        LOGGER.error("catch exception:{}", exception.getMessage());
        if (EXCEPTIONS == null) {
            //EXCEPTIONS构建成功
            EXCEPTIONS = builder.build();
        }
        // 从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if (resultCode != null) {
            return new ResponseResult(resultCode);
        } else {
            // 返回99999异常
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    static {
        // 定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
        builder.put(MaxUploadSizeExceededException.class, CommonCode.FILE_SIZE_LIMIT_10M);
        builder.put(ExcelCommonException.class, CommonCode.Excel_Common_Exception);
        builder.put(HttpRequestMethodNotSupportedException.class, CommonCode.HttpRequest_Method_Not_Supported_Exception);


        builder.put(IOException.class, CommonCode.FILE_IO_Exception);
    }
}
