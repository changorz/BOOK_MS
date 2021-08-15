package com.swxy.jwbookms.controller;


import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.pojo.Unbook;
import com.swxy.jwbookms.pojo.Unbook;
import com.swxy.jwbookms.service.UnbookService;
import com.swxy.jwbookms.service.UnbookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@Api(description = "缺书表控制器")
public class UnbookController {

    @Autowired
    private UnbookService unbookService;

    @PostMapping("/Unbook")
    @ApiOperation(value = "添加一条缺书记录")
    public Response postUnbook(@RequestBody @Validated Unbook unbook) {
        boolean save = unbookService.save(unbook);
        return save ? new DataResponseResult(unbook) : ResponseResult.FAIL();
    }

    @DeleteMapping("/Unbook/{uuid}")
    @ApiOperation(value = "删除一条缺书记录")
    public Response deleteUnbook(@PathVariable String uuid) {
        boolean remove = unbookService.removeById(uuid);
        return remove ? ResponseResult.SUCCESS() : ResponseResult.FAIL();
    }

    @PutMapping("/Unbook/{uuid}")
    @ApiOperation(value = "修改一条缺书记录")
    public Response putUnbook(@RequestBody @Validated Unbook unbook, @PathVariable String uuid) {
        unbook.setUuid(uuid);
        boolean update = unbookService.updateById(unbook);
        return update ? new DataResponseResult(unbook) : ResponseResult.FAIL();
    }

    @GetMapping("/Unbook/{uuid}")
    @ApiOperation(value = "按uuid查询一条缺书记录")
    public Response getUnbook(@PathVariable String uuid) {
        Unbook unbook = unbookService.getById(uuid);
        return new DataResponseResult(unbook);
    }


}

