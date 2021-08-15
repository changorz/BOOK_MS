package com.swxy.jwbookms.pojo.DTO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PublishingHouseOrderDTO implements Serializable {

    private int id;
    private String shool;
    private String isbn;
    private String bookName;
    private String publishingHouse;
    private String author;
    private BigDecimal pricing;

    private String remark;
    /**
     * 总数量（全部班级）
     */
    private int totalBook;
    /**
     * 总价格
     */
    private BigDecimal totalPricing;

}
