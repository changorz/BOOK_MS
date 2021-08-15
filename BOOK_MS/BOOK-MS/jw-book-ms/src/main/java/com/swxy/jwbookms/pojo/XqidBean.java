package com.swxy.jwbookms.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Unbook对象", description = "")
public class XqidBean {

    private static final long serialVersionUID = 1L;

    private String activeXqid;

    private String activeXqidValue;

    private Set<String> xqidList;

}
