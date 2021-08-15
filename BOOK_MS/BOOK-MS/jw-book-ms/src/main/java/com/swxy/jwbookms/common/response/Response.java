package com.swxy.jwbookms.common.response;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "公共返回接口")
public interface Response {
    public static final boolean SUCCESS = true;
    public static final int SUCCESS_CODE = 10000;
}
