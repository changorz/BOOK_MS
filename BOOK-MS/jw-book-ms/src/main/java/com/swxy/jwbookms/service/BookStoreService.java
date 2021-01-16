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

    boolean updateBookStoreByIsbnAndXqid(BookStore bookStore);

    Page queryBookStoreByPage(Page page, String xqid, String query);

    Page queryBookStoreAllByXqid(Page page, String xqid);

}
