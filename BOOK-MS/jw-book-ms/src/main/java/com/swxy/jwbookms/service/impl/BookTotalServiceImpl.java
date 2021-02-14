package com.swxy.jwbookms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.pojo.BookTotal;
import com.swxy.jwbookms.mapper.BookTotalMapper;
import com.swxy.jwbookms.pojo.VO.BookTotalCountVo;
import com.swxy.jwbookms.service.BookTotalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public void queryBookTotalServiceByXqid(String xqid, Page page, Map<String, String> map) {
        // TODO 自己写 这里需要填充 page (现在可以通过 年级，学院，专业 查找)
        List<BookTotal> bookTotals = bookTotalMapper.queryBookTotalByMap(xqid, map, page);
        page.setRecords(bookTotals);
    }

    @Override
    public BookTotalCountVo getBookTotalCountVo(String xqid) {
        return bookTotalMapper.getBookTotalCountVo(xqid);
    }

    @Override
    public Map<String, List> getSelectAllByBookTotal(String xqid) {
        List<BookTotal> grade = bookTotalMapper.selectList(new LambdaQueryWrapper<BookTotal>()
                .eq(BookTotal::getXqid, xqid)
                .groupBy(BookTotal::getGrade)
                .select(BookTotal::getGrade)
        );
        List<String> grades = grade.stream().filter(Objects::nonNull).map(BookTotal::getGrade).collect(Collectors.toList());

        List<BookTotal> major = bookTotalMapper.selectList(new LambdaQueryWrapper<BookTotal>()
                .eq(BookTotal::getXqid, xqid)
                .groupBy(BookTotal::getMajor)
                .select(BookTotal::getMajor)
        );
        List<String> majors = major.stream().filter(Objects::nonNull).map(BookTotal::getMajor).collect(Collectors.toList());

        List<BookTotal> twoLevelCollege = bookTotalMapper.selectList(new LambdaQueryWrapper<BookTotal>()
                .eq(BookTotal::getXqid, xqid)
                .groupBy(BookTotal::getTwoLevelCollege)
                .select(BookTotal::getTwoLevelCollege)
        );
        List<String> twoLevelColleges = twoLevelCollege.stream().filter(Objects::nonNull).map(BookTotal::getTwoLevelCollege).collect(Collectors.toList());
        HashMap<String, List> map = new HashMap<>();
        map.put("bookTotal_grades", grades);
        map.put("bookTotal_majors", majors);
        map.put("bookTotal_twoLevelColleges", twoLevelColleges);
        return map;
    }
}
