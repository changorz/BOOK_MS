package com.swxy.jwbookms.pojo.VO;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CountVo implements Serializable {

    private int bookStoreCount;
    private int curriculumPlanCount;
    private int studentInfoCount;
    /**
     * 未填报
     */
    private int bookTotalCount_0;
    /***
     * 已填报
     */
    private int bookTotalCount_1;
    /**
     * 被打回
     */
    private int bookTotalCount_2;

}
