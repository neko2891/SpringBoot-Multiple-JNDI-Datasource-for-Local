package com.example.demo.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "db1EntityManager",
        transactionManagerRef = "db1TransactionManager",
        basePackages = "com.example.demo.db1.repo"
)
@EnableConfigurationProperties({
        DatasourceProperties.class
})
public class Db1Config extends Database {

    @Autowired
    private DatasourceProperties datasourceProperties;

    @Bean(name = "db1DataSource", destroyMethod = "")
    @Primary
    public DataSource dataSource() throws NamingException {
        return createDataSource(datasourceProperties.getDb1().getJndiName());
    }

    @Bean
    public EntityManagerFactory db1EntityManager() throws NamingException {
        return createEntityManagerFactory(dataSource(), new String[]{"com.example.demo.db1.entity"});
    }

    @Bean
    public PlatformTransactionManager db1TransactionManager() throws NamingException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(db1EntityManager());
        return txManager;
    }

    @Bean(name = "flywayDb1")
    @ConfigurationProperties(prefix = "datasource.db1.flyway")
    public Flyway flywayDb1(@Qualifier("db1DataSource") DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        return flyway;
    }

    @Bean
    @Primary
    public FlywayMigrationInitializer flywayInitializerDb1(@Qualifier("flywayDb1") Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, null);
    }

}
