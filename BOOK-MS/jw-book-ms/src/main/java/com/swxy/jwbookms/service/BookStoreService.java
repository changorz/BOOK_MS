package com.swxy.jwbookms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.pojo.BookStore;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chang
 * @since 2020-12-26
 */
public interface BookStoreService extends IService<BookStore> {

    void queryBookStoreByPage(Page page, String xqid, String query);

    /**
     * ISBN 书籍名 拼音码 作者 模糊查询（须选定学期） 前十条匹配
     * @param xqid
     * @param query
     * @return
     */
    void queryBookStoreTop10(Page page, String xqid, String query);

    void queryBookStoreAllByXqid(Page page, String xqid);

}
