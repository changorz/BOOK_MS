package com.swxy.jwbookms.pojo.VO;

import com.swxy.jwbookms.pojo.DTO.PublishingHouseOrderDTO;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PublishingHouseOrderVo implements Serializable {

    private List<PublishingHouseOrderDTO> phOrderlist;

    /**
     * 书本总数量
     */
    private Integer phTotal;

    /**
     * 总码洋
     */
    private BigDecimal phTotalPricing;

}
