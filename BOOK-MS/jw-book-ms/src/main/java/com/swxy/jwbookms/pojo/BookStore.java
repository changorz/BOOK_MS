package com.swxy.jwbookms.pojo;

import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * <p>
 *
 * </p>
 *
 * @author chang
 * @since 2020-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BookStore对象", description = "")
public class BookStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @ApiModelProperty(value = "UUID", hidden = true)
    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    @ExcelProperty("ISBN")
    @NotBlank(message = "isbn: 参数不能为空。")
    @Pattern(regexp = "[0-9]{13}", message = "isbn: 参数错误")
    @ApiModelProperty(value = "ISBN", required = true)
    private String isbn;

    @ExcelProperty("书名")
    @NotBlank(message = "bookName: 参数不能为空。")
    @Length(max = 80, message = "bookName: 参数长度不能大于80。")
    @ApiModelProperty(value = "书名", required = true)
    private String bookName;

    @JsonIgnore
    @ApiModelProperty(value = "书名拼音码", hidden = true)
    private String bookPym;

    @ExcelProperty("出版社全称")
    @Length(max = 40, message = "publishingHouse: 参数长度不能大于40。")
    @ApiModelProperty(value = "出版社全称")
    private String publishingHouse;

    @ExcelProperty(value = "出版社补充")
    @ApiModelProperty(value = "出版社补充: 集团自编、其他类、英语类、两课类、马工程 （可以为空）")
    @Length(max = 6, message = "publishingHouseSupplement: 参数长度不能大于6。")
    private String publishingHouseSupplement;

    @ExcelProperty("作者")
    @Length(max = 70, message = "author: 参数长度不能大于70。")
    @ApiModelProperty(value = "作者")
    private String author;

    @ExcelProperty("定价")
    @NotNull(message = "pricing: 参数不能为空。")
    @DecimalMin(value = "0", message = "pricing：必须大于或等于0")
    @ApiModelProperty(value = "定价", required = true)
    private BigDecimal pricing;

    @NotBlank(message = "xqid: 参数不能为空。")
    @Pattern(regexp = "20[0-9]{2}-20[0-9]{2}-[1-2]", message = "xqid: 参数错误。 示例: 2020-2021-2")
    @ExcelProperty("学期ID")
    @ApiModelProperty(value = "学期ID")
    private String xqid;


    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
