package com.swxy.jwbookms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.pojo.StudentInfo;
import com.swxy.jwbookms.mapper.StudentInfoMapper;
import com.swxy.jwbookms.service.StudentInfoService;
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
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {

    private final StudentInfoMapper studentInfoMapper;

    @Override
    public Page queryStudentInfo(Page page, String xqid, String query) {
        LambdaQueryWrapper<StudentInfo> where = new LambdaQueryWrapper<StudentInfo>()
                .eq(StudentInfo::getXqid, xqid)
                .and(e -> {
                    e.like(StudentInfo::getXh, query).or().like(StudentInfo::getStudentName, query);
                });
        return studentInfoMapper.selectPage(page, where);
    }

}
