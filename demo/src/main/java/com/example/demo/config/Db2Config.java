package com.example.demo.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "db2EntityManager",
        transactionManagerRef = "db2TransactionManager",
        basePackages = "com.example.demo.db2.repo"
)
@EnableConfigurationProperties({
        DatasourceProperties.class
})
public class Db2Config extends Database {

    @Autowired
    private DatasourceProperties datasourceProperties;

    @Bean(name = "db2DataSource", destroyMethod = "")
    public DataSource dataSource() throws IllegalArgumentException, NamingException {
        return createDataSource(datasourceProperties.getDb2().getJndiName());
    }

    @Bean
    public EntityManagerFactory db2EntityManager() throws IllegalArgumentException, NamingException {
        return createEntityManagerFactory(dataSource(), new String[]{"com.example.demo.db2.entity"});
    }

    @Bean
    public PlatformTransactionManager db2TransactionManager() throws IllegalArgumentException, NamingException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(db2EntityManager());
        return txManager;
    }

    @Bean(name = "flywayDb2")
    @ConfigurationProperties(prefix = "datasource.db2.flyway")
    public Flyway flywayDb2(@Qualifier("db2DataSource") DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        return flyway;
    }

    @Bean
    public FlywayMigrationInitializer flywayInitializerDb2(@Qualifier("flywayDb2") Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, null);
    }

}

