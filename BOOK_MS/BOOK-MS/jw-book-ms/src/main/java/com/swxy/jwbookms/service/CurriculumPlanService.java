package com.swxy.jwbookms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.pojo.CurriculumPlan;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chang
 * @since 2020-12-26
 */
public interface CurriculumPlanService extends IService<CurriculumPlan> {

    public void queryCurriculumPlanByXqid(String xqid, Page page);

}
