package com.swxy.jwbookms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.ResponseUtil;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.pojo.BookTotal;
import com.swxy.jwbookms.pojo.VO.BookTotalCountVo;
import com.swxy.jwbookms.service.BookTotalService;
import com.swxy.jwbookms.service.impl.CommonService;
import com.swxy.jwbookms.enums.CommonStringEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

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
@Api(description = "总表控制器")
public class BookTotalController {

    @Autowired
    private BookTotalService bookTotalService;
    @Autowired
    private CommonService commonService;

    @PostMapping("/BookTotal")
    @ApiOperation(value = "填报一条记录")
    public Response postBookTotal(@RequestBody @Validated BookTotal bookTotal) {
        boolean save = bookTotalService.save(bookTotal);
        return save ? new DataResponseResult(bookTotal) : ResponseResult.FAIL();
    }

    @DeleteMapping("/BookTotal/{uuid}")
    @ApiOperation(value = "删除一条填报记录")
    public Response deleteBookTotal(@PathVariable String uuid) {
        boolean remove = bookTotalService.removeById(uuid);
        return remove ? ResponseResult.SUCCESS() : ResponseResult.FAIL();
    }

    @PutMapping("/BookTotal/{uuid}")
    @ApiOperation(value = "修改一条填报记录")
    public Response putBookTotal(@RequestBody @Validated BookTotal bookTotal, @PathVariable String uuid) {
        bookTotal.setUuid(uuid);
        boolean update = bookTotalService.updateById(bookTotal);
        return update ? new DataResponseResult(bookTotal) : ResponseResult.FAIL();
    }

    @GetMapping("/BookTotal/{uuid}")
    @ApiOperation(value = "按uuid查询一条填报记录")
    public Response getBookTotal(@PathVariable String uuid) {
        BookTotal bookTotal = bookTotalService.getById(uuid);
        return new DataResponseResult(bookTotal);
    }


    @ApiOperation(value = "按开课计划和学期id去生成总表")
    @PostMapping("/BookTotal/insertBookTotalByCurriclumPlan/{xqid}")
    public Response insertBookTotalByCurriclumPlan(@PathVariable String xqid) {
        int i = bookTotalService.insertBookTotalByCurriclumPlan(xqid);
        String successMsg = "操作成功，更新行数：" + i;
        String failMsg = "操作失败，可能表中以存在记录:" + "[" + i + "]";
        return ResponseUtil.toResult(i > 0, successMsg, failMsg);
    }

    /**
     * 按学期id分页查询 总表
     * @param xqid
     * @param page
     * @param map  选择的查询条件，可以为空
     * @return
     */
    @GetMapping({"/BookTotal/all/{xqid}/{current}/{size}"})
    @ApiOperation(value = "按学期id分页查询 总表 map为过滤条件")
    public Response queryBookTotalByXqid(
            @PathVariable String xqid,
            Page page,
            @RequestParam Map<String, String> map
    ) {
        bookTotalService.queryBookTotalServiceByXqid(xqid, page, map);
        return new DataResponseResult<Page>(page);
    }

    /**
     * 按课程名模糊分页查询（须选定学期）
     *
     * @param xqid 学期ID
     * @param str  查询条件
     * @param map  选择的查询条件，可以为空
     * @return
     */
    @GetMapping("/BookTotal/findBookTotalByTitle/{xqid}/{str}/{current}/{size}")
    @ApiOperation(value = "按学期id和课程名称分页查询 总表 map为过滤条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "起始也", required = false, paramType = "path"),
            @ApiImplicitParam(name = "size", value = "页大小", required = false, paramType = "path")
    })
    public Response queryBookTotalByMap(
            @PathVariable String xqid,
            @PathVariable String str,
            Page page,
            @RequestBody Map map
    ) {
        System.out.println(map);
        return  null;
    }


    @ApiOperation(value = "填报比例")
    @GetMapping("/BookTotal/getBookTotalCountVo/{xqid}")
    public Response getBookTotalCountVo(@PathVariable String xqid) {
        BookTotalCountVo bookTotalCountVo = bookTotalService.getBookTotalCountVo(xqid);
        return new DataResponseResult<>(bookTotalCountVo);
    }

    @ApiOperation(value = "总表 Excel 导出")
    @GetMapping("/BookTotal/download/{xqid}")
    public void getBookTotalDownload(@PathVariable String xqid, HttpServletResponse response) throws IOException {
        List<BookTotal> list = bookTotalService.list(new LambdaQueryWrapper<BookTotal>().eq(BookTotal::getXqid, xqid));
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(CommonStringEnum.SCHOOL.getStr() + xqid + CommonStringEnum.Textbook_Subscription_Summary_Table.getStr(), "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        commonService.downloadExcel(BookTotal.class, CommonStringEnum.Summary_Sheet.getStr(), list);
    }
}

