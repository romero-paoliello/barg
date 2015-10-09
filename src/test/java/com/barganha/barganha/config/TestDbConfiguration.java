package com.barganha.barganha.config;

import liquibase.integration.spring.SpringLiquibase;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

public class TestDbConfiguration extends DbConfiguration {

    protected DataSource dataSource() {
        DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test1;MODE=Oracle;AUTOCOMMIT=false", "sa", "");
        return dataSource;
    }

    //@Bean(initMethod = "start", destroyMethod = "stop") //Method used for tooling purposes
    public Server h2WebServer() throws SQLException {
        // embedded H2 db viewer at http://localhost:8082
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    }

    //@Bean(initMethod = "start", destroyMethod = "stop") //Method used for tooling purposes
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSourceProxy());
        liquibase.setChangeLog("classpath:database/master-test.xml");
        liquibase.setContexts("unit-test");
        return liquibase;
    }
}
