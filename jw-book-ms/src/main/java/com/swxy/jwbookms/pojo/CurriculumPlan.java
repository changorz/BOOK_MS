package com.swxy.jwbookms.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
@ApiModel(value = "CurriculumPlan(开课计划)对象", description = "")
public class CurriculumPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @ApiModelProperty(value = "uuid")
    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    @ExcelProperty("二级学院")
    @Length(max = 20, message = "twoLevelCollege: 参数长度不能大于20。 示例：信息与工程学院")
    @ApiModelProperty(value = "二级学院", required = true)
    private String twoLevelCollege;

    @ExcelProperty("专业")
    @NotBlank(message = "major: 参数不能为空。")
    @Length(max = 20, message = "major: 参数长度不能大于20。 示例：计算机科学与技术")
    @ApiModelProperty(value = "专业", required = true)
    private String major;

    @ExcelProperty("年级")
    @Pattern(regexp = "[0-9]{4}", message = "grade: 参数错误。 示例：2017")
    @ApiModelProperty(value = "年级", required = true)
    private String grade;

    @ExcelProperty("课程名称")
    @NotBlank(message = "courseTitle: 参数不能为空。")
    @Length(max = 30, message = "courseTitle: 参数长度不能大于30。 示例：数据结构")
    @ApiModelProperty(value = "课程名称", required = true)
    private String courseTitle;

    @ExcelProperty("学期ID")
    @NotBlank(message = "xqid: 参数不能为空。")
    @Pattern(regexp = "20[0-9]{2}-20[0-9]{2}-[0-9]]", message = "xqid: 参数错误。 示例: 2020-2021-1")
    @ApiModelProperty(value = "学期ID", required = true)
    private String xqid;


}
