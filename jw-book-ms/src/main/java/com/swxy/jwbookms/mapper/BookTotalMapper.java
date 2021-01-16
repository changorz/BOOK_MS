package com.swxy.jwbookms.mapper;

import com.swxy.jwbookms.pojo.BookTotal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swxy.jwbookms.pojo.VO.BookTotalCountVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chang
 * @since 2020-12-26
 */
public interface BookTotalMapper extends BaseMapper<BookTotal> {

    int insertBookTotalByCurriclumPlan(String xqid);

    BookTotalCountVo getBookTotalCountVo(String xqid);
}
