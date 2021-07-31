package com.swxy.jwbookms.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
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
@ApiModel(value = "StudentInfo对象", description = "")
public class StudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @ApiModelProperty(value = "UUID", hidden = true)
    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    @ExcelProperty("学号")
    @NotBlank(message = "xh: 参数不能为空。")
    @Pattern(regexp = "20[0-9]{6}", message = "xh: 参数错误。 示例：20177583")
    @ApiModelProperty(value = "学号", required = true)
    private String xh;

    @ExcelProperty("姓名")
    @NotBlank(message = "studentName: 参数不能为空。")
    @Length(max = 6, message = "studentName: 参数长度不能大于6。 示例：马化腾")
    @ApiModelProperty(value = "姓名", required = true)
    private String studentName;

    @ExcelProperty("当前所在级")
    @Pattern(regexp = "[0-9]{4}", message = "grade: 参数错误。 示例：2017")
    @ApiModelProperty(value = "当前所在级", required = true)
    private String grade;

    @ExcelProperty("班级")
    @NotBlank(message = "cla: 参数不能为空。")
    @Length(max = 20, message = "cla: 参数长度不能大于20。 示例：计算机科学与技术1班")
    @ApiModelProperty(value = "班级", required = true)
    private String cla;

    @ExcelProperty("上课院系")
    @NotBlank(message = "faculty: 参数不能为空。")
    @Length(max = 20, message = "faculty: 参数长度不能大于20。 示例：信息与工程系")
    @ApiModelProperty(value = "上课院系", required = true)
    private String faculty;

    @ExcelProperty("专业名称")
    @NotBlank(message = "major: 参数不能为空。")
    @Length(max = 20, message = "major: 参数长度不能大于20。 示例：计算机科学与技术")
    @ApiModelProperty(value = "专业名称", required = true)
    private String major;

    @ExcelProperty(value = "在校状态")
    @NotBlank(message = "status: 参数不能为空。")
    @Length(max = 3, message = "status: 参数错误: 在校，离校，转专业")
    @ApiModelProperty(value = "在校状态(在校,离校,转专业)", required = true)
    private String status;

    @ExcelProperty("学期ID")
    @NotBlank(message = "xqid: 参数不能为空。")
    @Pattern(regexp = "20[0-9]{2}-20[0-9]{2}-[0-9]", message = "xqid: 参数错误。 示例: 2020-2021-1")
    @ApiModelProperty(value = "学期ID", required = true)
    private String xqid;

}
