package com.example.sharding5.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DataMonitorServiceExecuteTest {

    @Resource
    private ISyncData dataMonitorServiceExecute;

    @Test
    void dataInterfaceTest() {
        try {
            dataMonitorServiceExecute.syncData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}