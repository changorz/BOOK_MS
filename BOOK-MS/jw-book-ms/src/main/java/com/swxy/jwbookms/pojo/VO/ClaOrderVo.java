package com.swxy.jwbookms.pojo.VO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ClaOrderVo implements Serializable {

    private int serialNumber;
    private String course_title;
    private String isbn;
    private String book_name;
    private String publishing_house;
    private BigDecimal pricing;
    private int student_book_count;

}
