package com.example.sharding5.dao.mapper.oracle;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sharding5.dao.entity.oracle.DataMonitorInfo;

/**
 * @Author：noroadzh
 * @Description: TODO
 * @Date: Create in 2024/3/20 11:01
 * @Modified by:
 **/
@DS("dataOracle")
public interface DataMonitorInfoMapper extends BaseMapper<DataMonitorInfo> {
}
