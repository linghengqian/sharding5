package com.example.sharding5.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.sharding5.dao.entity.mysql.DataInterfaceStatus;
import com.example.sharding5.dao.entity.oracle.DataMonitorInfo;
import com.example.sharding5.dao.mapper.mysql.DataInterfaceStatusMapper;
import com.example.sharding5.dao.mapper.oracle.DataMonitorInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author：noroadzh
 * @Description: TODO
 * @Date: Create in 2024/3/20 11:07
 * @Modified by:
 **/
@Slf4j
@Service("dataMonitorServiceExecute")
public class DataMonitorServiceExecute implements ISyncData {
    /**
     * 从oracle中获取数据监测设备状态数据
     */
    @Resource
    private DataMonitorInfoMapper dataMonitorInfoMapper;

    /**
     * 数据接口状态数据
     */
    @Resource
    private DataInterfaceStatusService dataInterfaceStatusService;

    /**
     * 数据库直接mapper
     */
    @Resource
    private DataInterfaceStatusMapper dataInterfaceStatusMapper;

    @Override
    public void syncData() throws Exception {

        // 获取mysql对应表中是否有数据，如果有数据，取最大统计时间，如果没有数据直接进入下一步
        QueryWrapper<DataInterfaceStatus> queryWrapperMysql = new QueryWrapper<>();
        queryWrapperMysql.lambda().orderByDesc(DataInterfaceStatus::getAddupCycle);

        List<DataInterfaceStatus> dataInterfaceStatusList = dataInterfaceStatusMapper.selectList(queryWrapperMysql);

        DataInterfaceStatus dataInterfaceStatus = null;

        if (dataInterfaceStatusList != null && !dataInterfaceStatusList.isEmpty()) {
            dataInterfaceStatus = dataInterfaceStatusList.get(0);
        }

        if (dataInterfaceStatus != null ) {
            // 创建一个格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            // 获取当前日期
            LocalDate now = LocalDate.now();

            String formatDate = now.format(formatter);

            if (new BigDecimal(now.format(formatter)).compareTo(dataInterfaceStatus.getAddupCycle()) >= 0) {
                formatDate = dataInterfaceStatus.getAddupCycle().toString();
            }

            List<DataMonitorInfo> dataMonitorInfoList =
                    dataMonitorInfoMapper.selectList(new QueryWrapper<DataMonitorInfo>().lambda().ge(DataMonitorInfo::getAddupCycle
                            , formatDate));

            if (dataMonitorInfoList != null && !dataMonitorInfoList.isEmpty()) {
                dataMonitorInfoList.forEach(entity ->{
                    // 查询条件
                    LambdaQueryWrapper<DataInterfaceStatus> queryWrapperMysql1 =
                            new QueryWrapper<DataInterfaceStatus>().lambda()
                                    .eq(DataInterfaceStatus::getAddupCycle, entity.getAddupCycle())
                                    .eq(DataInterfaceStatus::getAccessServiceName, entity.getAccessServiceName());

                    // 先尝试查询是否存在
                    List<DataInterfaceStatus> existEntityList = dataInterfaceStatusService.list(queryWrapperMysql1);

                    if (existEntityList != null && !existEntityList.isEmpty()) {

                        DataInterfaceStatus existEntity = existEntityList.get(0);
                        String id = existEntity.getId();
                        BeanUtils.copyProperties(entity, existEntity);
                        existEntity.setId(id);
                        // 存在则更新
                        dataInterfaceStatusMapper.updateById(existEntity);
                    } else {
                        DataInterfaceStatus existEntity = new DataInterfaceStatus();
                        BeanUtils.copyProperties(entity, existEntity);
                        // 不存在则插入
                        dataInterfaceStatusService.save(existEntity);
                    }
                });
            }
        } else {
            // 如果没有最大统计时间，取oracle表中所有数据
            List<DataMonitorInfo> dataMonitorInfoList1 = dataMonitorInfoMapper
                    .selectList(new QueryWrapper<>());

            if (dataMonitorInfoList1 != null && !dataMonitorInfoList1.isEmpty()) {
                // 往mysql数据库中插入数据
                for (DataMonitorInfo dataMonitorInfo : dataMonitorInfoList1) {
                    DataInterfaceStatus dataInterfaceStatusTmp = new DataInterfaceStatus();
                    BeanUtils.copyProperties(dataMonitorInfo, dataInterfaceStatusTmp);
                    dataInterfaceStatusService.saveOrUpdate(dataInterfaceStatusTmp);
                }
            }
        }
    }
}
