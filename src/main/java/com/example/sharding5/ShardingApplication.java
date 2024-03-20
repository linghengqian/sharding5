package com.example.sharding5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author：noroadzh
 * @Description: TODO
 * @Date: Create in 2024/3/20 10:55
 * @Modified by:
 **/
@SpringBootApplication
@MapperScan("com.example.sharding5.dao.mapper*")
public class ShardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingApplication.class, args);
    }
}
