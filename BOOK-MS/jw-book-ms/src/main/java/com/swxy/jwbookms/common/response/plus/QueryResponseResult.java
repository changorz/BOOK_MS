package com.swxy.jwbookms.common.response.plus;


import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.code.ResultCode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QueryResponseResult extends ResponseResult {

    QueryResult queryResult;


    public QueryResponseResult(ResultCode resultCode, QueryResult queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }

}
