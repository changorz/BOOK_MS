package com.swxy.jwbookms.common.response.plus;

import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.code.CommonCode;
import com.swxy.jwbookms.common.response.code.ResultCode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DataResponseResult<E> extends ResponseResult {

    private E data;

    public DataResponseResult(E e) {
        super(CommonCode.SUCCESS);
        this.data = e;
    }

    public DataResponseResult(E e, ResultCode resultCode) {
        super(resultCode);
        this.data = e;
    }

}
