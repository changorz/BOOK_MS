package com.swxy.jwbookms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.code.CommonCode;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.enums.RedisKey;
import com.swxy.jwbookms.pojo.BookTotal;
import com.swxy.jwbookms.pojo.XqidBean;
import com.swxy.jwbookms.util.BMSUtil;
import com.swxy.jwbookms.util.BMSWriterExcelUtil;
import com.swxy.jwbookms.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final RedisUtil redisUtil;
    private final BMSWriterExcelUtil bmsWriterExcelUtil;

    /**
     * excel 下载
     *
     * @param clsaa 实体类
     * @param shellName shell名字
     * @param data 数据list
     */
    public void downloadExcel(Class<?> clsaa, String shellName, List<?> data) {
        // 自定义的header
        response.setHeader("requestType","file");
        // 设置这个header 可见
        response.setHeader("Access-Control-Expose-Headers", "requestType");
        try {
            ServletOutputStream out = response.getOutputStream();
            EasyExcel
                    .write(out, BookTotal.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(shellName)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .doWrite(data);
        } catch (IOException e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            try {
                response.getWriter().println(JSON.toJSONString(new ResponseResult(CommonCode.FILE_DOWNLOAD_ERR)));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


    /**
     * 自定义excel上传
     * @param map 填充字段
     * @param lists 数据列表
     */
    public void downloadClaOrderExcel(Map map, List<List<String>> lists) {
        try {
            bmsWriterExcelUtil.writerClaExcel(map,lists,response.getOutputStream());
        } catch (IOException e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            try {
                response.getWriter().println(JSON.toJSONString(new ResponseResult(CommonCode.FILE_DOWNLOAD_ERR)));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 获取学期信息
     * @return
     */
    public XqidBean getXqInfo() {
        Set<Object> objects = redisUtil.sGet(RedisKey.XQID_LIST.getValue());
        String xqid = (String) redisUtil.get(RedisKey.ACTIVE_XQID.getValue());
        if (StringUtils.isEmpty(xqid) || CollectionUtil.isEmpty(objects)) {
            return null;
        }
        String xqidValue = BMSUtil.xqidToZh(xqid);
        Set<String> strings = new TreeSet<>();
        objects.forEach(e -> strings.add((String) e));
        XqidBean bean = new XqidBean(xqid, xqidValue, strings);
        return bean;
    }

    /**
     * 导出所有出版社的订报单
     * @param xqid 学期ID
     */
    public void downloadAllPHOrder(String xqid){
        // TODO
        // 1. 获取所有合作的出版社
        // 2. 获取出版社补充
        // 3. 获取数据
        // 4. 写入Excel对象
        // 5. 导出

    }


}
