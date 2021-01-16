package com.swxy.jwbookms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.pojo.BookTotal;
import com.swxy.jwbookms.mapper.BookTotalMapper;
import com.swxy.jwbookms.pojo.VO.BookTotalCountVo;
import com.swxy.jwbookms.service.BookTotalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
public class BookTotalServiceImpl extends ServiceImpl<BookTotalMapper, BookTotal> implements BookTotalService {

    private final BookTotalMapper bookTotalMapper;

    @Override
    public int insertBookTotalByCurriclumPlan(String xqid) {
        Integer integer = bookTotalMapper.selectCount(new LambdaQueryWrapper<BookTotal>().eq(BookTotal::getXqid, xqid));
        return integer > 0 ? 0 - integer : bookTotalMapper.insertBookTotalByCurriclumPlan(xqid);
    }

    @Override
    public void querybookTotalServiceByXqid(String xqid, Page page) {
        bookTotalMapper.selectPage(page, new LambdaQueryWrapper<BookTotal>().eq(BookTotal::getXqid, xqid));
    }

    @Override
    public BookTotalCountVo getBookTotalCountVo(String xqid) {
        return bookTotalMapper.getBookTotalCountVo(xqid);
    }
}
