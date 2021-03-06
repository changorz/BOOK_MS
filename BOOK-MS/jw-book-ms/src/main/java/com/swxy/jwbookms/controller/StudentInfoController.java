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
import com.swxy.jwbookms.pojo.StudentInfo;
import com.swxy.jwbookms.service.StudentInfoService;
import com.swxy.jwbookms.util.AssertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chang
 * @since 2020-12-26
 */
@RestController
@RequestMapping("/admin")
@Api(description = "学生表控制器")
public class StudentInfoController {

    @Autowired
    private StudentInfoService studentInfoService;

    @PostMapping("/StudentInfo")
    @ApiOperation(value = "添加学生")
    public Response postStudentInfo(@RequestBody @Validated StudentInfo studentInfo) {
        boolean save = studentInfoService.save(studentInfo);
        return save ? new DataResponseResult(studentInfo) : ResponseResult.FAIL();
    }

    @DeleteMapping("/StudentInfo/{uuid}")
    @ApiOperation(value = "删除学生")
    public Response deleteStudentInfo(@PathVariable String uuid) {
        boolean remove = studentInfoService.removeById(uuid);
        return remove ? ResponseResult.SUCCESS() : ResponseResult.FAIL();
    }

    @DeleteMapping("/StudentInfo/all/{xqid}")
    @ApiOperation(value = "按学期删除全部学生")
    public Response deleteStudentInfoAll(@PathVariable String xqid) {
        boolean remove = studentInfoService.remove(new LambdaQueryWrapper<StudentInfo>().eq(StudentInfo::getXqid, xqid));
        return remove ? ResponseResult.SUCCESS() : ResponseResult.FAIL();
    }

    @PutMapping("/StudentInfo")
    @ApiOperation(value = "修改学生")
    public Response putStudentInfo(@RequestBody @Validated StudentInfo studentInfo) {
        boolean update = studentInfoService.updateById(studentInfo);
        return update ? new DataResponseResult(studentInfo) : ResponseResult.FAIL();
    }

    @GetMapping("/StudentInfo/{uuid}")
    @ApiOperation(value = "按学生的uuid查询学生")
    public Response getStudentInfo(@PathVariable String uuid) {
        StudentInfo studentInfo = studentInfoService.getById(uuid);
        return new DataResponseResult(studentInfo);
    }

    @GetMapping({"/StudentInfo/{xqid}/{xh}"})
    @ApiOperation(value = "按学期ID、学号查询，查询学生")
    public Response getCurriculumPlan(@PathVariable String xh, @PathVariable(required = false) String xqid) {
        LambdaQueryWrapper<StudentInfo> eq = new LambdaQueryWrapper<StudentInfo>().eq(StudentInfo::getXh, xh);
        if (StringUtils.isNotEmpty(xqid)) {
            eq.eq(StudentInfo::getXqid, xqid);
        }
        List<StudentInfo> list = studentInfoService.list(eq);
        return ResponseUtil.toResult(list, ResponseUtil.Code.ORDINARY.getTotalCode());
    }

    @GetMapping({"/StudentInfo/List/{xqid}/{cla}"})
    @ApiOperation(value = "按学期ID和班级名称查询班级学生名单")
    public Response getCurriculumPlanClaByXqid(@PathVariable String xqid, @PathVariable String cla) {
        LambdaQueryWrapper<StudentInfo> eq = new LambdaQueryWrapper<StudentInfo>().eq(StudentInfo::getXqid, xqid).eq(StudentInfo::getCla, cla);
        List<StudentInfo> list = studentInfoService.list(eq);
        list.sort(Comparator.comparing(StudentInfo::getXh));
        return ResponseUtil.toResult(list, list.size());
    }

    @Transactional(rollbackFor=Exception.class)
    @PostMapping("/StudentInfo/{xqid}/improt/excel")
    @ApiOperation(value = "导入excel，必须字段（学号，姓名，班级，上课院系，专业名称，在校状态，学期ID），表中不能存在公式！")
    public Response importCurriculumPlanFile(@RequestParam MultipartFile file, @PathVariable String xqid) throws IOException {
        AssertUtil.isExcel(file);
        InputStream inputStream = file.getInputStream();
        CommonListener listener = new CommonListener<StudentInfo>(studentInfoService, xqid);
        EasyExcel.read(inputStream, StudentInfo.class, listener).sheet().doRead();
        return new DataResponseResult<>(new HashMap<String, Integer>() {{
            put("count", listener.getCount());
        }}, CommonCode.SUCCESS);
    }

    /**
     * 分页查询（须选定学期）
     * @param xqid 学期ID
     * @return
     */
    @GetMapping("/StudentInfo/findStudentInfo/{xqid}/{current}/{size}")
    @ApiOperation(value = "按姓名或者学号，分页查询（须选定学期）")
    public Response findStudentInfo(
            @PathVariable String xqid,
            Page page
    ) {
        studentInfoService.page(page, new LambdaQueryWrapper<StudentInfo>().eq(StudentInfo::getXqid, xqid));
        return new DataResponseResult<Page>(page);
    }

    /**
     * 按姓名或者学号，分页查询（须选定学期）
     *
     * @param xqid 学期ID
     * @param str  查询条件
     * @return
     */
    @GetMapping("/StudentInfo/findStudentInfoByXhOrName/{xqid}/{current}/{size}/{str}")
    @ApiOperation(value = "按姓名或者学号，分页查询（须选定学期）")
    public Response findStudentInfoByXhOrName(
            @PathVariable String xqid,
            @PathVariable String str,
            Page page
    ) {
        studentInfoService.queryStudentInfo(page, xqid, str);
        return new DataResponseResult<Page>(page);
    }

    @GetMapping("/StudentInfo/{xqid}/getAllClasspath")
    @ApiOperation(value = "获取当前学期的所有班级")
    public Response getAllClasspath(@PathVariable String xqid) {
        LambdaQueryWrapper<StudentInfo> select = new LambdaQueryWrapper<StudentInfo>().eq(StudentInfo::getXqid, xqid).groupBy(StudentInfo::getCla).select(StudentInfo::getCla);
        List<StudentInfo> list = studentInfoService.list(select);
        List<String> collect = list.stream().map(StudentInfo::getCla).collect(Collectors.toList());
        return new DataResponseResult<>(collect);
    }

    @GetMapping("/StudentInfo/{xqid}/{query}/getAllClasspath")
    @ApiOperation(value = "获取当前学期的所有班级 - 分页")
    public Response getAllClasspath(@PathVariable String xqid, @PathVariable String query, Page page) {
        studentInfoService.getAllClasspath(xqid, query, page);
        return new DataResponseResult<>(page);
    }



}

