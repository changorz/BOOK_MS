package com.swxy.jwbookms.common.response;


import com.swxy.jwbookms.common.response.code.CommonCode;
import com.swxy.jwbookms.common.response.code.ResultCode;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.common.response.plus.QueryResponseResult;
import com.swxy.jwbookms.common.response.plus.QueryResult;
import lombok.Data;

import java.util.List;

public class ResponseUtil {

    public enum Code {
        // 普通查询返回（非分页查询）
        ORDINARY(1L);

        private Long totalCode;

        private Code(Long i) {
            this.totalCode = i;
        }

        public Long getTotalCode() {
            return totalCode;
        }
    }

    /**
     * 根据操作是否成功返回状态
     *
     * @param flag 操作是否成功
     * @param ret  成功的Response
     * @return
     */
    public static Response toResult(boolean flag, Response ret) {
        return flag ? ret : ResponseResult.FAIL();
    }

    /**
     * 根据操作是否成功返回状态
     *
     * @param flag 操作是否成功
     * @return
     */
    public static Response toResult(boolean flag) {
        return flag ? ResponseResult.SUCCESS() : ResponseResult.FAIL();
    }

    /**
     * 根据flag返回操作成功信息或者失败信息
     * @param flag
     * @param successMsg
     * @param failMag
     * @return
     */
    public static Response toResult(boolean flag, String successMsg, String failMag) {
        return flag ? ResponseResult.SUCCESS(successMsg) : ResponseResult.FAIL(failMag);
    }

    /**
     * 根据flag返回操作成功查询的结果或者失败信息
     *
     * @param flag
     * @param data    查询数据成功
     * @param failMag 失败提示信息
     * @return
     */
    public static Response toResult(boolean flag, Object data, String failMag) {
        return flag ? new DataResponseResult(data, CommonCode.FIND_AC) : ResponseResult.FAIL(failMag);
    }

    /**
     * 查询结果集封装
     *
     * @param list  查询出来的结果集
     * @param total 总记录数; -1代表非分页查询
     * @return
     */
    public static Response toResult(List list, long total) {
        QueryResult<Object> query = new QueryResult<>();
        query.setList(list);
        query.setTotal(total);
        return new QueryResponseResult(CommonCode.FIND_AC, query);
    }

}
