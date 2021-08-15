package com.swxy.jwbookms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.pojo.StudentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chang
 * @since 2020-12-26
 */
public interface StudentInfoService extends IService<StudentInfo> {

    /**
     * 分页查询学生信息
     * @param page 分页参数
     * @param xqid 学期ID
     * @param str 查询参数（学号，或者姓名）
     * @return
     */
    Page queryStudentInfo(Page page, String xqid, String str);

    void getAllClasspath(String xqid, String query, Page page);

    String getTwoLevelCollegeByCla(String cla);

}
