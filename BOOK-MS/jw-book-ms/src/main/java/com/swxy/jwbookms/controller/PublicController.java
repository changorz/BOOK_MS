package com.swxy.jwbookms.controller;

import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.service.impl.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "公共的控制器: 包括学期id,下拉列表值的获取")
@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private CommonService commonService;

    // 获取学期信息：user
    @ApiOperation("获取学期信息")
    @GetMapping("/xqInfo")
    public Response getXqInfo() {
        return new DataResponseResult<>(commonService.getXqInfo());
    }

}
