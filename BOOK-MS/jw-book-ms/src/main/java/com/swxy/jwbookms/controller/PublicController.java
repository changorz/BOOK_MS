package com.swxy.jwbookms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseUtil;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.enums.RedisKey;
import com.swxy.jwbookms.pojo.*;
import com.swxy.jwbookms.pojo.VO.CountVo;
import com.swxy.jwbookms.pojo.VO.XqidTimeVo;
import com.swxy.jwbookms.service.*;
import com.swxy.jwbookms.service.impl.CommonService;
import com.swxy.jwbookms.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 公共资源获取类
 * @author chang
 */


@Api(description = "公共的控制器: 包括学期id,下拉列表值的获取")
@Slf4j
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final CommonService commonService;
    private final RedisUtil redisUtil;
    private final BookTotalService bookTotalService;
    private final PublishingHouseService publishingHouseService;
    private final BookStoreService bookStoreService;
    private final CurriculumPlanService curriculumPlanService;
    private final StudentInfoService studentInfoService;

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
        TreeSet<String> strings = new TreeSet<>();
        objects.forEach(e -> {
            strings.add(e.toString());
        });
        return new DataResponseResult<>(strings);
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

    @GetMapping("/FillTimel/{xqid}")
    @ApiOperation(value = "获取当前学期填报的开启时间与结束时间")
    public Response getFillTimel(@PathVariable String xqid) {
        String strTime = (String)redisUtil.get(xqid + RedisKey.XQID_Time);
        if (StringUtils.isEmpty(strTime)){
            return new DataResponseResult<>(XqidTimeVo.builder().isFill(false).build());
        }
        String[] split = strTime.split("-");
        LocalDateTime startTime = LocalDateTime.parse(split[0]);
        LocalDateTime endTime = LocalDateTime.parse(split[1]);
        return new DataResponseResult<>(XqidTimeVo.builder().startTime(startTime).endTime(endTime).isFill(true));
    }

    @GetMapping("/dbCount/all/{xqid}")
    @ApiOperation(value = "获取当前学期填报的统计信息")
    public Response getCount(@PathVariable String xqid) {
        int bookStoreCount = bookStoreService.count(new LambdaQueryWrapper<BookStore>().eq(BookStore::getXqid, xqid));
        int curriculumPlanCount = curriculumPlanService.count(new LambdaQueryWrapper<CurriculumPlan>().eq(CurriculumPlan::getXqid, xqid));
        int studentInfoCount = studentInfoService.count(new LambdaQueryWrapper<StudentInfo>().eq(StudentInfo::getXqid, xqid));
        //0：未填报 （默认）
        //1：已填报，
        //2： 被打回
        // TODO 其实这里可以一次性查出
        int bookTotalCount_0 = bookTotalService.count(new LambdaQueryWrapper<BookTotal>().eq(BookTotal::getXqid, xqid).eq(BookTotal::getSubmitState, 0));
        int bookTotalCount_1 = bookTotalService.count(new LambdaQueryWrapper<BookTotal>().eq(BookTotal::getXqid, xqid).eq(BookTotal::getSubmitState, 1));
        int bookTotalCount_2 = bookTotalService.count(new LambdaQueryWrapper<BookTotal>().eq(BookTotal::getXqid, xqid).eq(BookTotal::getSubmitState, 2));
        CountVo build = CountVo.builder()
                .bookStoreCount(bookStoreCount)
                .curriculumPlanCount(curriculumPlanCount)
                .studentInfoCount(studentInfoCount)
                .bookTotalCount_0(bookTotalCount_0)
                .bookTotalCount_1(bookTotalCount_1)
                .bookTotalCount_2(bookTotalCount_2)
                .build();
        return new DataResponseResult<>(build);
    }


}
