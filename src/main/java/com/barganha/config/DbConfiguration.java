package com.barganha.config;

import com.barganha.util.DateTimeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.sql.DataSource;
import javax.validation.ValidatorFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.StringUtils.hasLength;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.barganha.dao"})
@EntityScan(basePackages={"com.barganha.model"})
@PropertySource("classpath:barganha-jpa.properties")
public class DbConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(DbConfiguration.class);

    @Autowired
    Environment env;

    protected DataSource dataSource() throws URISyntaxException {
        String driverClass = "org.postgresql.Driver";
        String jdbcUrl;
        String username;
        String password;

        //boolean isCiEnvironment = Boolean.parseBoolean(System.getenv(env.getProperty("CI")));
        String databaseUrl = System.getenv("DATABASE_URL");
        if (hasLength(databaseUrl)) {
//            username = env.getProperty("SNAP_DB_PG_USER");
//            password = env.getProperty("SNAP_DB_PG_PASSWORD");
//            jdbcUrl = env.getProperty("SNAP_DB_PG_JDBC_URL");

            URI dbUri = new URI(databaseUrl);
            username = dbUri.getUserInfo().split(":")[0];
            password = dbUri.getUserInfo().split(":")[1];
            jdbcUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        } else {
            jdbcUrl = "jdbc:postgresql://localhost:15432/barganha";
            username = "barganha";
            password = "barganha";
        }

        return DataSourceBuilder.create().url(jdbcUrl).username(username).password(password).driverClassName(driverClass).build();
    }

    @Bean
    public DataSource dataSourceProxy() throws URISyntaxException {
        LazyConnectionDataSourceProxy lazyDataSource = new LazyConnectionDataSourceProxy();
        lazyDataSource.setTargetDataSource(dataSource());
        return dataSource();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSourceProxy) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSourceProxy);
//        entityManagerFactory.setPackagesToScan("com.barganha.domain");
        entityManagerFactory.setJpaPropertyMap(getJpaProperties());
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        LOGGER.info("JPA Properties: {}", entityManagerFactory.getJpaPropertyMap().toString());
        return entityManagerFactory;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    //JSR303 validator
    @Bean
    public ValidatorFactory validator() {
        return new LocalValidatorFactoryBean();
    }

    protected Map<String, String> getJpaProperties() {
        Map<String, String> props = new HashMap<>();
        props.put(HibernateProperties.FORMAT_SQL, env.getProperty("hibernate.format_sql"));
        props.put(HibernateProperties.SHOW_SQL, env.getProperty("hibernate.show_sql"));
        props.put(HibernateProperties.USE_SQL_COMMENTS, env.getProperty("hibernate.use_sql_comments"));
        props.put(HibernateProperties.MAX_FETCH_DEPTH, env.getProperty("hibernate.max_fetch_depth"));
        props.put(HibernateProperties.GENERATE_STATISTICS, env.getProperty("hibernate.generate_statistics"));
        props.put(HibernateProperties.USE_SECOND_LEVEL_CACHE, env.getProperty("hibernate.use_second_level_cache"));
        props.put(HibernateProperties.USE_QUERY_CACHE, env.getProperty("hibernate.use_query_cache"));
        props.put(HibernateProperties.DIALECT, env.getProperty("hibernate.dialect"));
        props.put(HibernateProperties.HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
        props.put(HibernateProperties.DEFAULT_BATCH_FETCH_SIZE, env.getProperty("hibernate.default_batch_fetch_size"));

        props.put("jadira.usertype.autoRegisterUserTypes", "true");
        props.put("jadira.usertype.databaseZone", DateTimeHelper.GMT);
        props.put("jadira.usertype.javaZone", DateTimeHelper.GMT);

        return props;
    }
}
