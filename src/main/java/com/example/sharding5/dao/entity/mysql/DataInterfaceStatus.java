package com.example.sharding5.dao.entity.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author：noroadzh
 * @Description: TODO
 * @Date: Create in 2024/3/20 10:58
 * @Modified by:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("data_interface_status")
public class DataInterfaceStatus implements Serializable {

    // 序列化版本号
    private static final long serialVersionUID = 1L;

    /**
     * 主键key
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 累计周期
     */
    @TableField(value = "ADDUP_CYCLE")
    private BigDecimal addupCycle;

    /**
     * 当日调用接口次数
     */
    @TableField("TOTAL_COUNT")
    private BigDecimal totalCount;

    /**
     * 当日成功处理的数据量
     */
    @TableField("SUCCESS_QUANTITY")
    private BigDecimal successQuantity;

    /**
     * 访问的服务名称
     */
    @TableField("ACCESS_SERVICE_NAME")
    private String accessServiceName;

    /**
     * 服务状态
     */
    @TableField("SERVICE_STATUS")
    private String serviceStatus;

    /**
     * 服务状态描述
     */
    @TableField("SERVICE_STATUS_DESCRIPTION")
    private String serviceStatusDescription;
}
