package com.swxy.jwbookms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.pojo.BookTotal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swxy.jwbookms.pojo.VO.BookTotalCountVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chang
 * @since 2020-12-26
 */
public interface BookTotalService extends IService<BookTotal> {

    public int insertBookTotalByCurriclumPlan(String xqid);

    void querybookTotalServiceByXqid(String xqid, Page page);

    BookTotalCountVo getBookTotalCountVo(String xqid);

}
