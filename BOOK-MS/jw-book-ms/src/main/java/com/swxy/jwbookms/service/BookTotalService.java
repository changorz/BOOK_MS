package com.swxy.jwbookms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.pojo.BookTotal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swxy.jwbookms.pojo.VO.BookTotalCountVo;
import com.swxy.jwbookms.pojo.VO.ClaOrderVo;
import com.swxy.jwbookms.pojo.DTO.PublishingHouseOrderDTO;

import java.util.List;
import java.util.Map;

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

    void queryBookTotalServiceByXqid(String xqid, Page page, Map<String, String> map);

    BookTotalCountVo getBookTotalCountVo(String xqid);

    Map<String, List> getSelectAllByBookTotal(String xqid);

    /**
     * 获取班级的订单，即发放表
     * @param xqid
     * @param claName
     */
    List<ClaOrderVo> getClaOrder(String xqid, String claName);

    /**
     * 获取订报单
     * @param xqid
     * @param phName 出版社名称
     * @param phNames 出版社补充
     * @return
     */
    List<PublishingHouseOrderDTO> getPublishingHouseOrder(String xqid, String phName, String phNames);

}
