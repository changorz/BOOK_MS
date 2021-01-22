package com.swxy.jwbookms.listener.excel;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swxy.jwbookms.pojo.BookStore;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于BookStore Excel导入
 */

@Slf4j
public class CommonListener<T> extends AnalysisEventListener<T> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;
    private int count;
    List list = new ArrayList<T>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private IService service;
    private String xqid;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param service
     */
    public CommonListener(IService service, String xqid) {
        this.service = service;
        this.xqid = xqid;
    }

    /**
     * 获取成功的条数
     *
     * @return
     */
    public int getCount() {
        return this.count;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param oneData 一条数据 as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T oneData, AnalysisContext context) {
        // 如果是书库表
        if (oneData instanceof BookStore) {
            BookStore bookStore = (BookStore) oneData;
            // 处理拼音码
            bookStore.setBookPym(PinyinUtil.getFirstLetter(bookStore.getBookName(), "").toLowerCase());
            // 处理ISBN
            String s = bookStore.getIsbn().replaceAll("\\D", "");
            if (s.length() < 12) {
                log.error("excel书籍库添加失败,ISBN=" + bookStore.getIsbn());
            } else if (s.length() == 12) {
                s += 'X';
            }
            bookStore.setIsbn(s.substring(0, 13));
        }
        ReflectUtil.invoke(oneData, "setXqid", xqid);
        log.info("解析到一条数据:{}", JSON.toJSONString(oneData));
        list.add(oneData);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        boolean b = service.saveBatch(list);
        if (b) {
            this.count += list.size();
        }
        log.info("存储数据库成功！");
    }
}
