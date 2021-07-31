package com.swxy.jwbookms.config.security;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Payload<T> implements Serializable {
    private String id;
    private T userInfo;
    private Date expiration;
}