package com.swxy.jwbookms.controller;


import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseUtil;
import com.swxy.jwbookms.pojo.BmsUser;
import com.swxy.jwbookms.service.BmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chang
 * @since 2021-01-19
 */
@RestController
@RequestMapping("/user")
public class BmsUserController {

    @Autowired
    private BmsUserService bmsUserService;

    @GetMapping("/info")
    public Response getUserInfo() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        BmsUser user = bmsUserService.getById(name);
        return ResponseUtil.toResult(null != user, user, "用户不存在。");
    }

}

