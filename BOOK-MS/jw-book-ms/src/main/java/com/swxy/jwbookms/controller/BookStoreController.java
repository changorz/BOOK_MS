package com.swxy.jwbookms.controller;


import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.ResponseUtil;
import com.swxy.jwbookms.common.response.code.CommonCode;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.listener.excel.CommonListener;
import com.swxy.jwbookms.pojo.BookStore;
import com.swxy.jwbookms.service.BookStoreService;
import com.swxy.jwbookms.util.AssertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
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
@Api(description = "书籍库前端控制器")
@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@SuppressWarnings("all")
public class BookStoreController {

    private final BookStoreService bookStoreService;

    @PostMapping("/BookStore")
    @ApiOperation(value = "新增书籍", notes = "向书籍库中添加一本书", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response postBookStore(@RequestBody @Validated BookStore bookStore) {
        bookStore.setBookPym(PinyinUtil.getFirstLetter(bookStore.getBookName(), "").toLowerCase());
        boolean save = bookStoreService.save(bookStore);
        return save ? new DataResponseResult(bookStore) : ResponseResult.FAIL();
    }

    @DeleteMapping("/BookStore/{uuid}")
    @ApiOperation(value = "删除书籍", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "学期ID", required = true, paramType = "path")
    })
    public Response deleteBookStore(@PathVariable String uuid) {
        boolean remove = bookStoreService.removeById(uuid);
        return remove ? ResponseResult.SUCCESS() : ResponseResult.FAIL();
    }

    @PutMapping("/BookStore")
    @ApiOperation(value = "修改书籍", notes = "修改一本书", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response putBookStore(@RequestBody @Validated BookStore bookStore) {
        bookStore.setBookPym(PinyinUtil.getFirstLetter(bookStore.getBookName(), "").toLowerCase());
        if(StringUtils.isEmpty(bookStore.getPublishingHouseSupplement())){
            bookStore.setPublishingHouseSupplement("");
        }
        boolean updata = bookStoreService.updateById(bookStore);
        return updata ? new DataResponseResult(bookStore) : ResponseResult.FAIL();
    }

    @GetMapping("/BookStore/{uuid}")
    @ApiOperation(value = "查询书籍", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "uuid", required = true, paramType = "path")
    })
    public Response getBookStore(@PathVariable String uuid) {
        BookStore one = bookStoreService.getById(uuid);
        return new DataResponseResult(one, CommonCode.FIND_AC);
    }

    @Transactional(rollbackFor=Exception.class)
    @PostMapping("/BookStore/{xqid}/improt/excel")
    @ApiOperation(value = "导入excel，必须字段（ISBN，书名，出版社全称，作者，定价，出版社补充（选填）: 集团自编/其他类/英语类/两课类/马工程），表中不能存在公式！")
    public Response importBookStoreFile(@RequestParam MultipartFile file, @PathVariable String xqid) throws IOException {
        AssertUtil.isExcel(file);
        InputStream inputStream = file.getInputStream();
        CommonListener listener = new CommonListener<BookStore>(bookStoreService, xqid);
        EasyExcel.read(inputStream, BookStore.class, listener).sheet().doRead();
        return new DataResponseResult<>(new HashMap<String, Integer>() {{
            put("count", listener.getCount());
        }}, CommonCode.SUCCESS);
    }

    /**
     * 按书籍名或者拼音码，作者模糊分页查询（须选定学期）
     *
     * @param xqid 学期ID
     * @param str  查询条件
     * @return
     */
    @GetMapping("/BookStore/findByBookNameOrPym/{xqid}/{str}/{current}/{size}")
    @ApiOperation(value = "按书籍名或者拼音码，作者模糊分页查询（须选定学期）")
    public Response queryBookStoreByPym(
            @PathVariable String xqid,
            @PathVariable String str,
            Page page
    ) {
        bookStoreService.queryBookStoreByPage(page, xqid, str);
        return new DataResponseResult<Page>(page);
    }

    @GetMapping({"/BookStore/all/{xqid}/{current}/{size}", "/BookStore/all/{xqid}"})
    @ApiOperation(value = "按学期分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "起始也", required = false, paramType = "path"),
            @ApiImplicitParam(name = "size", value = "页大小", required = false, paramType = "path")
    })
    public Response queryBookStore(
            @PathVariable String xqid,
            Page page
    ) {
        bookStoreService.queryBookStoreAllByXqid(page, xqid);
        return new DataResponseResult<Page>(page);
    }


    @ApiOperation(value = "按学期批量删除")
    @DeleteMapping("/BookStore/all/{xqid}")
    public Response deleteAll(@PathVariable String xqid) {
        boolean remove = bookStoreService.remove(new LambdaQueryWrapper<BookStore>().eq(BookStore::getXqid, xqid));
        return ResponseUtil.toResult(remove);
    }

}

