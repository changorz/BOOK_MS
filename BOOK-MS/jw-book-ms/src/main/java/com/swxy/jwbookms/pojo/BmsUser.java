package com.swxy.jwbookms.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author chang
 * @since 2021-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "BmsUser对象", description = "")
public class BmsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号")
    @TableId(value = "username", type = IdType.NONE)
    private String username;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "密码")
    private String pwd;

    @ApiModelProperty(value = "别名（姓名）")
    private String alias;

    @ApiModelProperty(value = "头像地址")
    private String img;

    @ApiModelProperty(value = "账号描述")
    private String des;

    @ApiModelProperty(value = "权限，用，分开")
    private String authority;

    @ApiModelProperty(value = "是否开启")
    private Boolean enabled;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
