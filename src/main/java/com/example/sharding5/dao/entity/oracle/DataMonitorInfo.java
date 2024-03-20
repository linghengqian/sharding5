package com.example.sharding5.dao.entity.oracle;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author：noroadzh
 * @Description: TODO
 * @Date: Create in 2024/3/20 10:59
 * @Modified by:
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("V_DATACENTER_MONITOR_INFO_KSH")
public class DataMonitorInfo implements Serializable {
    /**
     * 日期
     */
    @TableField("ADDUP_CYCLE")
    private BigDecimal addupCycle;
    /**
     * 当日接口次数
     */
    @TableField("TOTAL_COUNT")
    private BigDecimal totalCount;
    /**
     * 当日数据量
     */
    @TableField("SUCCESS_QUANTITY")
    private BigDecimal successQuantity;
    /**
     * 接口名称
     */
    @TableField("ACCESS_SERVICE_NAME")
    private String accessServiceName;
    /**
     * 接口状态
     */
    @TableField("SERVICE_STATUS")
    private String serviceStatus;
    /**
     * 接口状态描述
     */
    @TableField("SERVICE_STATUS_DESCRIPTION")
    private String serviceStatusDescription;
}
