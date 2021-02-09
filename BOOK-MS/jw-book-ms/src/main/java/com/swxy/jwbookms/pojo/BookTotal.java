package com.swxy.jwbookms.pojo;

import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swxy.jwbookms.converter.publishingHouseSupplementIntgerStringConverter;
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
@ApiModel(value = "BookTotal对象", description = "")
public class BookTotal implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @ApiModelProperty(value = "UUID", hidden = true)
    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    @NotBlank(message = "twoLevelCollege: 参数不能为空。")
    @Length(max = 20, message = "twoLevelCollege: 参数长度不能大于20。 示例：信息与工程学院")
    @ExcelProperty("二级学院")
    @ApiModelProperty(value = "二级学院", required = true)
    private String twoLevelCollege;

    @Length(max = 20, message = "major: 参数长度不能大于20。 示例：计算机科学与技术")
    @ExcelProperty("专业")
    @ApiModelProperty(value = "专业", required = true)
    private String major;

    @Pattern(regexp = "[0-9]{4}", message = "grade: 参数错误。 示例：2017")
    @ExcelProperty("年级")
    @ApiModelProperty(value = "年级", required = true)
    private String grade;

    @ExcelProperty("班级")
    @NotBlank(message = "cla: 参数不能为空。")
    @Length(max = 20, message = "cla: 参数长度不能大于20。 示例：计算机科学与技术1班")
    @ApiModelProperty(value = "班级", required = true)
    private String cla;

    @Length(max = 30, message = "grade: 参数长度不能大于30。 示例：数据结构")
    @ExcelProperty("课程名称")
    @ApiModelProperty(value = "课程名称", required = true)
    private String courseTitle;

    @NotBlank(message = "isbn: 参数不能为空。")
    @Pattern(regexp = "[0-9]{13}", message = "isbn: 参数错误")
    @ExcelProperty("ISBN")
    @ApiModelProperty(value = "ISBN", required = true)
    private String isbn;

    @NotBlank(message = "bookName: 参数不能为空。")
    @Length(max = 80, message = "bookName: 参数长度不能大于80。")
    @ExcelProperty("书名")
    @ApiModelProperty(value = "书名", required = true)
    private String bookName;

    @Length(max = 40, message = "publishingHouse: 参数长度不能大于40。")
    @ExcelProperty("出版社全称")
    @ApiModelProperty(value = "出版社全称", required = true)
    private String publishingHouse;

    @ExcelProperty(value = "出版社补充", converter = publishingHouseSupplementIntgerStringConverter.class)
    @ApiModelProperty(value = "出版社补充: 集团自编、其他类、英语类、两课类、马工程 （可以为空）")
    @Length(max = 6, message = "publishingHouseSupplement: 参数长度不能大于6。")
    private String publishingHouseSupplement;

    @ExcelProperty(value = "年份")
    @ApiModelProperty(value = "年份，可以为空")
    @Pattern(regexp = "[0-9]{4}", message = "bookYear: 请输入正确年份：例如：2021")
    private String bookYear;

    @NotNull(message = "bookVersion: 参数不能为空,可以填最新。")
    @ExcelProperty(value = "版次")
    @ApiModelProperty(value = "版次，默认最新")
    @Length(max = 6, message = "bookVersion: 参数长度不能大于6。")
    private String bookVersion;

    @NotNull(message = "bookType: 参数不能为空,可以填无。")
    @ExcelProperty(value = "教程类型")
    @ApiModelProperty(value = "教材类型：无、教育部国家级规划教材、省部级规划教材、教育部国家级精品教材、省部级精品教材")
    @Length(max = 25, message = "bookType: 参数长度不能大于25。")
    private String bookType;

    @NotNull(message = "cooperativePublishingHouse: 参数不能为空,默认true。")
    @ApiModelProperty(value = "是否是合作出版社")
    private boolean cooperativePublishingHouse;

    @ExcelProperty(value = "所属教研室")
    @ApiModelProperty(value = "所属教研室")
    @NotBlank(message = "staffRoom: 参数不能为空。")
    @Length(max = 25, message = "staffRoom: 参数长度不能大于25。")
    private String staffRoom;

    @ExcelIgnore
    @ApiModelProperty(value = "提交状态:教材是否打回\n" +
            "0：默认0）未填报\n" +
            "1：已填报，\n" +
            "2： 被打回")
    private int submitState;

    @Length(max = 70, message = "author: 参数长度不能大于70。")
    @ExcelProperty("作者")
    @ApiModelProperty(value = "作者")
    private String author;

    @NotNull(message = "pricing: 参数不能为空。")
    @DecimalMin(value = "0", message = "pricing：必须大于或等于0")
    @ExcelProperty("定价")
    @ApiModelProperty(value = "定价", required = true)
    private BigDecimal pricing;

    @Min(value = 0, message = "studentBookCount：必须大于等于0")
    @ExcelProperty("学生用书数")
    @ApiModelProperty(value = "学生用书数")
    private Integer studentBookCount;

    @Min(value = 0, message = "teacherBookCount：必须大于等于0")
    @ExcelProperty("教师用书数")
    @ApiModelProperty(value = "教师用书数")
    private Integer teacherBookCount;

    @Min(value = 0, message = "teacherBookCount：totalBook")
    @ExcelProperty("总计")
    @ApiModelProperty(value = "总计")
    private Integer totalBook;

    @ExcelProperty("码洋")
    @ApiModelProperty(value = "码洋(总计金额)")
    private BigDecimal totalPricing;

    @ExcelProperty("备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    @ExcelProperty("授课老师姓名")
    @ApiModelProperty(value = "授课老师姓名")
    private String lecturer;

    @ExcelProperty("联系方式")
    @ApiModelProperty(value = "联系方式")
    private String tel;

    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ExcelIgnore
    @ApiModelProperty(value = "填入账号ID", hidden = true)
    private String writeId;

    @NotBlank(message = "xqid: 参数不能为空。")
    @Pattern(regexp = "20[0-9]{2}-20[0-9]{2}-[0-9]", message = "xqid: 参数错误。 示例: 2020-2021-1")
    @ExcelProperty("学期ID")
    @ApiModelProperty(value = "学期ID")
    private String xqid;


}
