package com.example.sharding5.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.sharding5.dao.entity.mysql.DataInterfaceStatus;
import com.example.sharding5.dao.mapper.mysql.DataInterfaceStatusMapper;
import com.example.sharding5.service.DataInterfaceStatusService;
import org.springframework.stereotype.Service;

/**
 * @Author：noroadzh
 * @Description: TODO
 * @Date: Create in 2024/3/20 11:06
 * @Modified by:
 **/
@Service("dataInterfaceStatusService")
public class DataInterfaceStatusServiceImpl extends ServiceImpl<DataInterfaceStatusMapper, DataInterfaceStatus> implements DataInterfaceStatusService {
}
