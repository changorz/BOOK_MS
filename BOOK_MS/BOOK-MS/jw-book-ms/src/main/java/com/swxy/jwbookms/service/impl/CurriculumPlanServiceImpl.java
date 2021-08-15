package com.swxy.jwbookms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.pojo.CurriculumPlan;
import com.swxy.jwbookms.mapper.CurriculumPlanMapper;
import com.swxy.jwbookms.service.CurriculumPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chang
 * @since 2020-12-26
 */
@Service
@RequiredArgsConstructor
public class CurriculumPlanServiceImpl extends ServiceImpl<CurriculumPlanMapper, CurriculumPlan> implements CurriculumPlanService {

    private final CurriculumPlanMapper curriculumPlanMapper;

    @Override
    public void queryCurriculumPlanByXqid(String xqid, Page page) {
        LambdaQueryWrapper<CurriculumPlan> eq = new LambdaQueryWrapper<CurriculumPlan>().eq(CurriculumPlan::getXqid, xqid);
        curriculumPlanMapper.selectPage(page, eq);
    }
}
