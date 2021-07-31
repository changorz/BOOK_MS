package com.swxy.jwbookms.controller;


import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.ResponseUtil;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.common.response.plus.QueryResponseResult;
import com.swxy.jwbookms.pojo.PublishingHouse;
import com.swxy.jwbookms.service.PublishingHouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@Api(description = "出版社控制器")
public class PublishingHouseController {

    @Autowired
    private PublishingHouseService publishingHouseService;

    @PostMapping("/PublishingHouse")
    @ApiOperation(value = "新增出版社")
    public Response postPublishingHouse(@RequestBody @Validated PublishingHouse publishingHouse) {
        boolean save = publishingHouseService.save(publishingHouse);
        return save ? new DataResponseResult(publishingHouse) : ResponseResult.FAIL();
    }

    @DeleteMapping("/PublishingHouse/{uuid}")
    @ApiOperation(value = "删除出版社")
    public Response deletePublishingHouse(@PathVariable String uuid) {
        boolean delete = publishingHouseService.removeById(uuid);
        return ResponseUtil.toResult(delete);
    }

    @PutMapping("/PublishingHouse")
    @ApiOperation(value = "修改出版社")
    public Response putPublishingHouse(@RequestBody @Validated PublishingHouse publishingHouse) {
        boolean update = publishingHouseService.updateById(publishingHouse);
        return ResponseUtil.toResult(update, new DataResponseResult(publishingHouse));
    }

    @GetMapping("/PublishingHouse/all")
    @ApiOperation(value = "查询全部出版社（list）")
    public Response putPublishingHouse() {
        List<PublishingHouse> list = publishingHouseService.list();
        return ResponseUtil.toResult(list, list.size());
    }

}

