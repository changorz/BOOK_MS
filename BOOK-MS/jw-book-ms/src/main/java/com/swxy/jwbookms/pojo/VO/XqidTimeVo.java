package com.swxy.jwbookms.pojo.VO;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class XqidTimeVo implements Serializable {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    /**
     * 管理员是否开启过填报
     */
    private Boolean isFill;

}
