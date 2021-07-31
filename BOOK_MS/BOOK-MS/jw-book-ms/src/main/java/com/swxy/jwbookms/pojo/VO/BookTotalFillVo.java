package com.swxy.jwbookms.pojo.VO;

import com.swxy.jwbookms.pojo.BookTotal;
import lombok.Data;

import java.util.List;

@Data
public class BookTotalFillVo extends BookTotal {

    private List<BookTotal> adds;

}
