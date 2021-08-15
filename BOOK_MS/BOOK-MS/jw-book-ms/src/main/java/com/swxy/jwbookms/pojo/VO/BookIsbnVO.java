package com.swxy.jwbookms.pojo.VO;

import com.swxy.jwbookms.pojo.BookStore;
import lombok.Data;

import java.util.Map;

@Data
public class BookIsbnVO extends BookStore {

    private String title;
    private Map book_info;
    private String book_intro;
    private String author_intro;
    private String[] labels;
    private String cover_url;
    private String url;
    private Map rating;
    private String comments;

}
