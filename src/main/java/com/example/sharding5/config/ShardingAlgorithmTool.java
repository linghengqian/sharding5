package com.example.sharding5.config;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.core.env.StandardEnvironment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * @Author：noroadzh
 * @Description: TODO
 * @Date: Create in 2024/3/20 13:36
 * @Modified by:
 **/
@Slf4j
public class ShardingAlgorithmTool {
    private static final HashSet<String> tableNameCache = new HashSet<>();

    /**
     * 判断 分表获取的表名是否存在 不存在则自动建表
     *
     * @param logicTableName  逻辑表名(表头)
     * @param resultTableName 真实表名
     * @return 确认存在于数据库中的真实表名
     */
    public static String shardingTablesCheckAndCreatAndReturn(String logicTableName, String resultTableName) {
        synchronized (logicTableName.intern()) {
            // 缓存中有此表 返回
            if (tableNameCache.contains(resultTableName)) {
                return resultTableName;
            }
            // 缓存中无此表 建表 并添加缓存
            List<String> sqlList = selectTableCreateSql(logicTableName);
            sqlList.replaceAll(s -> s.replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS").replace(logicTableName, resultTableName));
            executeSql(sqlList);
            tableNameCache.add(resultTableName);
        }
        return resultTableName;
    }
    /**
     * 缓存重载方法
     */
    public static void tableNameCacheReload() {
        // 读取数据库中所有表名
        List<String> tableNameList = getAllTableNameBySchema();
        // 删除旧的缓存(如果存在)
        ShardingAlgorithmTool.tableNameCache.clear();
        // 写入新的缓存
        ShardingAlgorithmTool.tableNameCache.addAll(tableNameList);
    }
    private static void executeSql(List<String> sqlList) {
        Environment env = SpringUtil.getApplicationContext().getEnvironment();
        try (Connection conn = DriverManager.getConnection(
                Objects.requireNonNull(env.getProperty("spring.datasource.dynamic.datasource.business.url")),
                env.getProperty("spring.datasource.dynamic.datasource.business.username"),
                env.getProperty("spring.datasource.dynamic.datasource.business.password"))) {
            try (Statement st = conn.createStatement()) {
                conn.setAutoCommit(false);
                for (String sql : sqlList) {
                    st.execute(sql);
                }
                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
            }
        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }

    private static List<String> selectTableCreateSql(String tableName) {
        List<String> res = new ArrayList<>();
        if (tableName.equals("data_interface_status")) {
            res.add("CREATE TABLE `data_interface_status` (\n" +
                    "    `id` VARCHAR(64) DEFAULT (UUID()) COMMENT '主键',\n" +
                    "    ADDUP_CYCLE BIGINT(20) NOT NULL COMMENT '日期',\n" +
                    "    TOTAL_COUNT BIGINT(20) DEFAULT 0 COMMENT '当日调用接口次数',\n" +
                    "    SUCCESS_QUANTITY BIGINT(20) DEFAULT 0 COMMENT '当日数据量',\n" +
                    "    ACCESS_SERVICE_NAME VARCHAR(255) NOT NULL COMMENT '访问服务名',\n" +
                    "    SERVICE_STATUS VARCHAR(20) DEFAULT '' COMMENT '服务状态',\n" +
                    "    SERVICE_STATUS_DESCRIPTION VARCHAR(4000) DEFAULT '' COMMENT '服务状态描述',\n" +
                    "    PRIMARY KEY (id)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据接口状态表';");
        }


        return res;
    }

    public static List<String> getAllTableNameBySchema() {
        List<String> res = new ArrayList<>();
        Environment env = SpringUtil.getApplicationContext().getEnvironment();

        PropertySources propertySources = ((StandardEnvironment) env).getPropertySources();
        for (PropertySource<?> source : propertySources) {
            if (source.getSource() instanceof Map) {
                Map<String, Object> propertyMap = (Map<String, Object>) source.getSource();
                for (String key : propertyMap.keySet()) {
                    System.out.println(key + "=" + propertyMap.get(key));
                }
            }
        }


        try (Connection connection =
                     DriverManager.getConnection(
                             Objects.requireNonNull(env.getProperty("spring.datasource.dynamic.datasource.business.url")),
                             env.getProperty("spring.datasource.dynamic.datasource.business.username"),
                             env.getProperty("spring.datasource.dynamic.datasource.business.password"));
             Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery("show TABLES like 'data_interface_status%'")) {
                while (rs.next()) {
                    res.add(rs.getString(1));
                }
            }
        } catch (Exception e) {
//            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return res;
    }

    public static HashSet<String> cacheTableNames() {
        return tableNameCache;
    }
}
