# sharding5

- For https://github.com/apache/shardingsphere/issues/30551 .

- Under Ubuntu 22.04.3 LTS.

```shell
sdk install java 21.0.2-graalce
sdk use java 21.0.2-graalce

git clone git@github.com:linghengqian/sharding5.git
cd ./sharding5/
./mvnw clean test
```

- Log as follows.

```shell
Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException: Syntax error in SQL statement "\000a\000aCREATE TABLE `data_interface_status_202402`\000a(\000a    `id` VARCHAR(64) PRIMARY KEY,\000a    `ADDUP_CYCLE` BIGINT[*](20),\000a    `TOTAL_COUNT` BIGINT(20),\000a    `SUCCESS_QUANTITY` BIGINT(20),\000a    `ACCESS_SERVICE_NAME` VARCHAR(255),\000a    `SERVICE_STATUS` VARCHAR(20) ,\000a    `SERVICE_STATUS_DESCRIPTION` VARCHAR(4000)\000a)"; expected "ARRAY, INVISIBLE, VISIBLE, NOT NULL, NULL, AS, DEFAULT, GENERATED, ON UPDATE, NOT NULL, NULL, AUTO_INCREMENT, DEFAULT ON NULL, NULL_TO_DEFAULT, SEQUENCE, SELECTIVITY, COMMENT, CONSTRAINT, COMMENT, PRIMARY KEY, UNIQUE, NOT NULL, NULL, CHECK, REFERENCES, AUTO_INCREMENT, ,, )"; SQL statement:


CREATE TABLE `data_interface_status_202402`
(
    `id` VARCHAR(64) PRIMARY KEY,
    `ADDUP_CYCLE` BIGINT(20),
    `TOTAL_COUNT` BIGINT(20),
    `SUCCESS_QUANTITY` BIGINT(20),
    `ACCESS_SERVICE_NAME` VARCHAR(255),
    `SERVICE_STATUS` VARCHAR(20) ,
    `SERVICE_STATUS_DESCRIPTION` VARCHAR(4000)
) [42001-214]
        at org.h2.message.DbException.getJdbcSQLException(DbException.java:502)
        at org.h2.message.DbException.getJdbcSQLException(DbException.java:477)
        at org.h2.message.DbException.getSyntaxError(DbException.java:261)
        at org.h2.command.Parser.getSyntaxError(Parser.java:900)
        at org.h2.command.Parser.read(Parser.java:5675)
        at org.h2.command.Parser.readIfMore(Parser.java:1268)
        at org.h2.command.Parser.parseCreateTable(Parser.java:9272)
        at org.h2.command.Parser.parseCreate(Parser.java:6784)
        at org.h2.command.Parser.parsePrepared(Parser.java:763)
        at org.h2.command.Parser.parse(Parser.java:689)
        at org.h2.command.Parser.parse(Parser.java:666)
        at org.h2.command.Parser.prepare(Parser.java:537)
        at org.h2.engine.SessionLocal.prepare(SessionLocal.java:581)
        at org.h2.engine.SessionLocal.prepare(SessionLocal.java:565)
        at org.h2.command.dml.RunScriptCommand.execute(RunScriptCommand.java:103)
        at org.h2.command.dml.RunScriptCommand.update(RunScriptCommand.java:70)
        at org.h2.command.CommandContainer.update(CommandContainer.java:169)
        at org.h2.command.Command.executeUpdate(Command.java:252)
        at org.h2.engine.Engine.openSession(Engine.java:279)
        at org.h2.engine.Engine.createSession(Engine.java:201)
        at org.h2.engine.SessionRemote.connectEmbeddedOrServer(SessionRemote.java:338)
        at org.h2.jdbc.JdbcConnection.<init>(JdbcConnection.java:122)
        at org.h2.Driver.connect(Driver.java:59)
        at com.zaxxer.hikari.util.DriverDataSource.getConnection(DriverDataSource.java:138)
        at com.zaxxer.hikari.pool.PoolBase.newConnection(PoolBase.java:364)
        at com.zaxxer.hikari.pool.PoolBase.newPoolEntry(PoolBase.java:206)
        at com.zaxxer.hikari.pool.HikariPool.createPoolEntry(HikariPool.java:476)
        at com.zaxxer.hikari.pool.HikariPool.checkFailFast(HikariPool.java:561)
        at com.zaxxer.hikari.pool.HikariPool.<init>(HikariPool.java:115)
        at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:112)
        at org.apache.shardingsphere.infra.datasource.pool.CatalogSwitchableDataSource.getConnection(CatalogSwitchableDataSource.java:46)
        at org.apache.shardingsphere.infra.state.datasource.DataSourceStateManager.checkState(DataSourceStateManager.java:86)
        ... 160 more


```