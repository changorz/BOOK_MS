package com.swxy.jwbookms.controller;

import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.code.CommonCode;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.mapper.UserMapper;
import com.swxy.jwbookms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/massage")
public class UserController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/getUserbyid")
    public Response getUserbyid(){
        User user = userMapper.selectById(1);

        return new DataResponseResult(user, CommonCode.FIND_AC);
    }

}
