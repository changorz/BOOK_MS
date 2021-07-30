package com.swxy.jwbookms.controller;

import com.swxy.jwbookms.mapper.UserMapper;
import com.swxy.jwbookms.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/massage")
public class UserController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/getUserbyid")
    public User getUserbyid(Integer id){
        User user = userMapper.selectById(id);
        System.out.println(user);
        return user;
    }

}
