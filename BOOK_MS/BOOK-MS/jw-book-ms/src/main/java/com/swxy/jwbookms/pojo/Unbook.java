package com.swxy.jwbookms.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;

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
@ApiModel(value = "Unbook对象", description = "")
public class Unbook implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @ApiModelProperty(value = "UUID", hidden = true)
    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    @ApiModelProperty(value = "提交生成总表的UUID", required = true)
    private String totalUuid;

    @NotBlank(message = "major: 参数不能为空。 示例: 20177583;20177574;20175685")
    @ApiModelProperty(value = "学号（用;隔开 Ps:英文状态下的;）", required = true)
    private String studentXh;

    @NotBlank(message = "xqid: 参数不能为空。")
    @Pattern(regexp = "20[0-9]{2}-20[0-9]{2}-[0-9]]", message = "xqid: 参数错误。 示例: 2020-2021-1")
    @ApiModelProperty(value = "学期ID", required = true)
    private String xqid;

}
