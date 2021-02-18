package com.swxy.jwbookms.pojo.VO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ClaOrderVo implements Serializable {

    private int serialNumber;
    private String courseTitle;
    private String isbn;
    private String bookName;
    private String publishingHouse;
    private BigDecimal pricing;
    private int studentBookCount;

}
