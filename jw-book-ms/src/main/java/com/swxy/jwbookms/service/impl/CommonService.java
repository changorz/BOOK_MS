package com.swxy.jwbookms.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.code.CommonCode;
import com.swxy.jwbookms.pojo.BookTotal;
import com.swxy.jwbookms.util.CommonStringEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private final HttpServletRequest request;
    private final HttpServletResponse response;


    public void downloadExcel(Class<?> clsaa, String shellName, List<?> data) {
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


}
