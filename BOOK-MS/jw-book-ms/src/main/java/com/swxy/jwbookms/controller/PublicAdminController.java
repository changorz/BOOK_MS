package com.swxy.jwbookms.controller;

import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseUtil;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.enums.RedisKey;
import com.swxy.jwbookms.pojo.XqidBean;
import com.swxy.jwbookms.service.impl.CommonService;
import com.swxy.jwbookms.util.AssertUtil;
import com.swxy.jwbookms.util.BMSUtil;
import com.swxy.jwbookms.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Api(description = "公共的控制器(管理员设置接口): 包括学期id,下拉列表值的获取")
@Slf4j
@RestController
@RequestMapping("/admin/public")
public class PublicAdminController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CommonService commonService;

    @ApiOperation("增加一个新学期")
    @PostMapping("/addXq")
    public Response addXq(String xqid) {
        AssertUtil.isXqid(xqid);
        String xqidValue = BMSUtil.xqidToZh(xqid);
        redisUtil.sSet(RedisKey.XQID_LIST.getValue(), xqid);
        redisUtil.set(RedisKey.ACTIVE_XQID.getValue(), xqid);
        // 提交成功返回完整的学期信息
        return new DataResponseResult<XqidBean>(commonService.getXqInfo());
    }

    @ApiOperation("删除一个学期,表中数据另外处理")
    @DeleteMapping("/deleteXq/{xqid}")
    public Response deleteXq(@PathVariable String xqid) {
        AssertUtil.isXqid(xqid);
        String xqidValue = BMSUtil.xqidToZh(xqid);
        redisUtil.setRemove(RedisKey.XQID_LIST.getValue(), xqid);
        // 移除之后判断是不是当前活跃学期，如果是就修改成最后一个元素
        String s = (String) redisUtil.get(RedisKey.ACTIVE_XQID.getValue());
        long count = redisUtil.lGetListSize(RedisKey.XQID_LIST.getValue());
        if (s.equals(xqid) && count > 0) {
            redisUtil.set(RedisKey.ACTIVE_XQID.getValue(), redisUtil.lGetIndex(RedisKey.XQID_LIST.getValue(), count - 1));
        }
        // 提交成功返回完整的学期信息
        return new DataResponseResult<XqidBean>(commonService.getXqInfo());
    }

    @ApiOperation("设置活跃学期")
    @PutMapping("/setActiveXq/{xqid}")
    public Response setActiveXq(@PathVariable String xqid) {
        AssertUtil.isXqid(xqid);
        String xqidValue = BMSUtil.xqidToZh(xqid);
        redisUtil.set(RedisKey.ACTIVE_XQID.getValue(), xqid);
        // 提交成功返回完整的学期信息
        Set<Object> objects = redisUtil.sGet(RedisKey.XQID_LIST.getValue());
        Set<String> strings = new HashSet<>();
        AtomicBoolean flag = new AtomicBoolean(false);
        objects.forEach(e -> {
            // 判断活跃学期有没有在学期列表里面
            strings.add((String) e);
            if (xqid.equals((String) e)) {
                flag.set(true);
            }
        });
        XqidBean bean = new XqidBean(xqid, xqidValue, strings);
        return ResponseUtil.toResult(flag.get(), bean, "设置错误，学期不存在。");
    }
}
