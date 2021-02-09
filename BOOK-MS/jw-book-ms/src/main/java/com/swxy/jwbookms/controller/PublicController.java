package com.swxy.jwbookms.controller;

import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseUtil;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.enums.RedisKey;
import com.swxy.jwbookms.pojo.PublishingHouse;
import com.swxy.jwbookms.service.BookTotalService;
import com.swxy.jwbookms.service.PublishingHouseService;
import com.swxy.jwbookms.service.impl.CommonService;
import com.swxy.jwbookms.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 公共资源获取类
 * @author chang
 */


@Api(description = "公共的控制器: 包括学期id,下拉列表值的获取")
@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private CommonService commonService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BookTotalService bookTotalService;
    @Autowired
    private PublishingHouseService publishingHouseService;

    // 获取学期信息：user
    @ApiOperation("获取学期信息")
    @GetMapping("/xqInfo")
    public Response getXqInfo() {
        return new DataResponseResult<>(commonService.getXqInfo());
    }

    @ApiOperation("获取下拉列表的值")
    @GetMapping("/selects/{str}")
    public Response getSelects(@PathVariable String str) {
        Set<Object> objects = redisUtil.sGet(str);
        return new DataResponseResult<>(objects);
    }

    @ApiOperation("获取总表的所有选择值")
    @GetMapping("/selects/allByBookTota/{xqid}")
    public Response getSelectsAllByBookTota(@PathVariable String xqid) {
        Map<String, List> selectAllByBookTotal = bookTotalService.getSelectAllByBookTotal(xqid);
        return new DataResponseResult<>(selectAllByBookTotal);
    }

    @GetMapping("/PublishingHouse/all")
    @ApiOperation(value = "查询全部出版社（list）- public")
    public Response putPublishingHouse() {
        List<PublishingHouse> list = publishingHouseService.list();
        return ResponseUtil.toResult(list, list.size());
    }

}
