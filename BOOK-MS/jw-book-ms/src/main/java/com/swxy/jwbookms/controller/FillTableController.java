package com.swxy.jwbookms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swxy.jwbookms.common.response.Response;
import com.swxy.jwbookms.common.response.ResponseResult;
import com.swxy.jwbookms.common.response.ResponseUtil;
import com.swxy.jwbookms.common.response.plus.DataResponseResult;
import com.swxy.jwbookms.pojo.BookStore;
import com.swxy.jwbookms.pojo.BookTotal;
import com.swxy.jwbookms.pojo.StudentInfo;
import com.swxy.jwbookms.pojo.VO.BookIsbnVO;
import com.swxy.jwbookms.pojo.VO.BookTotalFillVo;
import com.swxy.jwbookms.service.BookStoreService;
import com.swxy.jwbookms.service.BookTotalService;
import com.swxy.jwbookms.service.StudentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO 添加角色控制
@Api(description = "填报的控制器，拥有教师角色才能访问")
@Slf4j
@RestController
@RequestMapping("/fill")
public class FillTableController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BookTotalService bookTotalService;
    @Autowired
    private BookStoreService bookStoreService;
    @Autowired
    private StudentInfoService studentInfoService;


    /**
     * 按学期id分页查询 总表 列表
     * @param xqid
     * @param page
     * @param map  选择的查询条件，可以为空
     * @return
     */
    @GetMapping({"/BookTotal/all/{xqid}/{current}/{size}"})
    @ApiOperation(value = "按学期id分页查询 总表 map为过滤条件")
    public Response queryBookTotalByXqid(
            @PathVariable String xqid,
            Page page,
            @RequestParam Map<String, String> map
    ) {
        bookTotalService.queryBookTotalServiceByXqid(xqid, page, map);
        return new DataResponseResult<Page>(page);
    }

    @GetMapping("/BookTotal/{xqid}/{uuid}")
    @ApiOperation(value = "按uuid查询同专业同课程报记录")
    public Response getBookTotal(@PathVariable String xqid, @PathVariable String uuid) {
        BookTotal bookTotal = bookTotalService.getById(uuid);
        BookTotalFillVo bookTotalFillVo = new BookTotalFillVo();
        BeanUtils.copyProperties(bookTotal, bookTotalFillVo);
        List<BookTotal> list = bookTotalService.list(new LambdaQueryWrapper<BookTotal>()
                .eq(BookTotal::getGrade, bookTotal.getGrade())
                .eq(BookTotal::getMajor, bookTotal.getMajor())
                .eq(BookTotal::getCourseTitle, bookTotal.getCourseTitle())
                .orderByAsc(BookTotal::getCla)
        );
        list.forEach(e -> {
            // 只有在没有值时才去计算人数
            if (e.getStudentBookCount() == null || e.getStudentBookCount() <= 0){
                int count = studentInfoService.count(new LambdaQueryWrapper<StudentInfo>()
                        .eq(StudentInfo::getXqid, xqid)
                        .eq(StudentInfo::getCla, e.getCla())
                        .eq(StudentInfo::getStatus, "在校"));
                e.setStudentBookCount(count);
            }
            if (e.getTeacherBookCount() == null || e.getTeacherBookCount() <= 0){
                e.setTeacherBookCount(0);
            }

        });
        bookTotalFillVo.setAdds(list);
        return new DataResponseResult(bookTotalFillVo);
    }

    /**
     * ISBN 书籍名 拼音码 作者 模糊查询（须选定学期） 前十条匹配
     *
     * @param xqid 学期ID
     * @param str  查询条件
     * @return
     */
    @GetMapping("/BookStore/findByBook/{xqid}/{str}")
    @ApiOperation(value = "ISBN 书籍名 拼音码 作者 模糊查询（须选定学期） 前十条匹配")
    public Response queryBookStoreByPym(@PathVariable String xqid, @PathVariable String str, Page page) {
        bookStoreService.queryBookStoreTop10(page, xqid, str);
        return new DataResponseResult<Page>(page);
    }

    @GetMapping("/BookStore/findBook/{uuid}")
    @ApiOperation(value = "按ISBN查询数据，可爬取其他网站书籍图片")
    public Response getBookStoreOne(@PathVariable String uuid){
        BookStore bookStore = bookStoreService.getById(uuid);
        BookIsbnVO bookIsbnVO = new BookIsbnVO();
        BeanUtils.copyProperties(bookStore, bookIsbnVO);
        // 远程获取信息
        String url = "https://book.feelyou.top/isbn/" + bookStore.getIsbn();
        System.out.println(url);
        Map forObject = restTemplate.getForObject(url, Map.class);
        // TODO 获取图片
        bookIsbnVO.setCover_url((String) forObject.getOrDefault("cover_url", ""));
        System.out.println((String) forObject.getOrDefault("cover_url", ""));

        return  new DataResponseResult(bookIsbnVO);
    }

    @Transactional
    @PutMapping("/BookTotal")
    @ApiOperation(value = "填报一条记录，add中包含多个班级的填报。")
    public Response postBookTotal(@RequestBody @Validated BookTotalFillVo bookTotalFillVo) {
        List<BookTotal> adds = bookTotalFillVo.getAdds();
        adds.forEach(e -> {
            // 设置一些值
            e.setSubmitState(1);
            e.setTotalPricing(e.getPricing().multiply(new BigDecimal(e.getTotalBook()), MathContext.DECIMAL32));
            e.setWriteId(SecurityContextHolder.getContext().getAuthentication().getName());
        });
        boolean update = bookTotalService.updateBatchById(adds);
        return ResponseUtil.toResult(update);
    }

}
