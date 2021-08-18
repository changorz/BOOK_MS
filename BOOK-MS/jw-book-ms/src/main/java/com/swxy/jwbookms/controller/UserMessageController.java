package com.swxy.jwbookms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.ResponseUtil;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.pojo.BmsUser;
import com.swxy.jwbookms.pojo.StudentInfo;
import com.swxy.jwbookms.service.BmsUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/message")
public class UserMessageController {

    @Autowired
    private BmsUserService bmsUserService;

    @GetMapping("/User")
    @ApiOperation(value = "按用户的uuid查询")
    public Response getStudentInfo() {
        //SecurityContextHolder上下文存储策略，用户登录信息被存储在这里
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        BmsUser user = bmsUserService.getById(name);

        return new DataResponseResult(user);
    }

    @PutMapping("/User")
    @ApiOperation(value = "修改用户")
    public Response putStudentInfo(@RequestBody @Validated BmsUser bmsUser) {
        boolean update = bmsUserService.updateById(bmsUser);
        return update ? new DataResponseResult(bmsUser) : ResponseResult.FAIL();
    }

}
