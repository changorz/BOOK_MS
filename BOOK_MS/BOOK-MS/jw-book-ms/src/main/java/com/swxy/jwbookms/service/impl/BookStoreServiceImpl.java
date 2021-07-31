package com.swxy.jwbookms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.pojo.BookStore;
import com.swxy.jwbookms.mapper.BookStoreMapper;
import com.swxy.jwbookms.service.BookStoreService;
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
public class BookStoreServiceImpl extends ServiceImpl<BookStoreMapper, BookStore> implements BookStoreService {

    private final BookStoreMapper bookStoreMapper;

    @Override
    public void queryBookStoreByPage(Page page, String xqid, String query) {
        LambdaQueryWrapper<BookStore> where = new LambdaQueryWrapper<BookStore>()
                .eq(BookStore::getXqid, xqid)
                .and(e -> {
                    e.like(BookStore::getBookPym, query).or().like(BookStore::getBookName, query);
                });
        bookStoreMapper.selectPage(page, where);
    }

    @Override
    public void queryBookStoreTop10(Page page, String xqid, String query) {
        LambdaQueryWrapper<BookStore> where = new LambdaQueryWrapper<BookStore>()
                .eq(BookStore::getXqid, xqid)
                .and(e -> {
                    e
                            .like(BookStore::getIsbn, query)
                            .or()
                            .like(BookStore::getBookPym, query)
                            .or()
                            .like(BookStore::getBookName, query)
                            .or()
                            .like(BookStore::getAuthor, query);
                });
        bookStoreMapper.selectPage(page, where);
    }

    @Override
    public void queryBookStoreAllByXqid(Page page, String xqid) {
        bookStoreMapper.selectPage(page, new LambdaQueryWrapper<BookStore>().eq(BookStore::getXqid, xqid));
    }

}
