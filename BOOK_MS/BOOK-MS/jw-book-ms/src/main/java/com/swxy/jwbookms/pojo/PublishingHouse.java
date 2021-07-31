package com.swxy.jwbookms.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
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
@ApiModel(value = "PublishingHouse对象", description = "")
public class PublishingHouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @ApiModelProperty(value = "uuid", hidden = true)
    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    @NotBlank(message = "publishingHouse: 参数不能为空。")
    @Length(max = 40, message = "twoLevelCollege: 参数长度不能大于40。 示例：清华大学出版社")
    @ApiModelProperty(value = "出版社名称", required = true)
    private String publishingHouse;


}
