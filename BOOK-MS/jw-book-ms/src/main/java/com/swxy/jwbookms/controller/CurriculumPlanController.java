package com.swxy.jwbookms.controller;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.ResponseUtil;
import com.swxy.jwbookms.common.response.code.CommonCode;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.listener.excel.CommonListener;
import com.swxy.jwbookms.pojo.CurriculumPlan;
import com.swxy.jwbookms.service.CurriculumPlanService;
import com.swxy.jwbookms.util.AssertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chang
 * @since 2020-12-26
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@Api(description = "开课计划控制器")
public class CurriculumPlanController {

    @Autowired
    private CurriculumPlanService curriculumPlanService;

    @PostMapping("/CurriculumPlan")
    @ApiOperation(value = "添加开课计划")
    public Response postCurriculumPlan(@RequestBody @Validated CurriculumPlan curriculumPlan) {
        boolean save = curriculumPlanService.save(curriculumPlan);
        return save ? new DataResponseResult(curriculumPlan) : ResponseResult.FAIL();
    }

    @DeleteMapping("/CurriculumPlan/{uuid}")
    @ApiOperation(value = "删除开课计划")
    public Response deleteCurriculumPlan(@PathVariable String uuid) {
        boolean remove = curriculumPlanService.removeById(uuid);
        return remove ? ResponseResult.SUCCESS() : ResponseResult.FAIL();
    }
    @DeleteMapping("/CurriculumPlanAll/{xqid}")
    @ApiOperation(value = "删除学期全部开课计划")
    public Response deleteCurriculumAllPlan(@PathVariable String xqid) {
        boolean remove = curriculumPlanService.remove(new LambdaQueryWrapper<CurriculumPlan>().eq(CurriculumPlan::getXqid, xqid));
        return ResponseUtil.toResult(remove);
    }

    @PutMapping("/CurriculumPlan")
    @ApiOperation(value = "修改开课计划")
    public Response putCurriculumPlan(@RequestBody @Validated CurriculumPlan curriculumPlan) {
        boolean update = curriculumPlanService.updateById(curriculumPlan);
        return update ? new DataResponseResult(curriculumPlan) : ResponseResult.FAIL();
    }

    @GetMapping("/CurriculumPlan/{uuid}")
    @ApiOperation(value = "查询开课计划")
    public Response getCurriculumPlan(@PathVariable String uuid) {
        CurriculumPlan curriculumPlan = curriculumPlanService.getById(uuid);
        return new DataResponseResult(curriculumPlan);
    }

    /**
     * 按学期ID查询（分页）
     *
     * @param xqid
     * @param page
     * @return
     */
    @GetMapping({"/CurriculumPlan/{xqid}/{current}/{size}"})
    @ApiOperation(value = "按学期ID查询（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "起始也", required = false, paramType = "path"),
            @ApiImplicitParam(name = "size", value = "页大小", required = false, paramType = "path")
    })
    public Response queryCurriculumPlanByXqid(
            @PathVariable String xqid,
            Page page
    ) {
        curriculumPlanService.queryCurriculumPlanByXqid(xqid, page);
        return new DataResponseResult<Page>(page);
    }

    @PostMapping("/CurriculumPlan/{xqid}/improt/excel")
    @ApiOperation(value = "导入excel，必须字段（二级学院(选填)，专业，年级，课程名称，学期ID），表中不能存在公式！")
    public Response importCurriculumPlanFile(@RequestParam MultipartFile file, @PathVariable String xqid) throws IOException {
        AssertUtil.isExcel(file);
        InputStream inputStream = file.getInputStream();
        CommonListener listener = new CommonListener<CurriculumPlan>(curriculumPlanService, xqid);
        EasyExcel.read(inputStream, CurriculumPlan.class, listener).sheet().doRead();
        return new DataResponseResult<>(new HashMap<String, Integer>() {{
            put("count", listener.getCount());
        }}, CommonCode.SUCCESS);
    }

}

