package com.innowise.educationalsystem.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SuppressWarnings("SpringComponentScan")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.innowise.educationalsystem.subscription.repository"},
        entityManagerFactoryRef = "connectionDetailsEntityManagerFactory",
        transactionManagerRef = "connectionDetailsTransactionManager"
)
public class ConnectionDetailsConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.connection")
    public DataSourceProperties connectionDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("connectionDetailsDataSource")
    public DataSource connectionDetailsDataSource() {
        return connectionDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean connectionDetailsEntityManagerFactory(
            @Qualifier("connectionDetailsDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        return builder
                .dataSource(dataSource)
                .packages("com.innowise.educationalsystem.subscription.entity")
                .properties(properties)
                .build();
    }

    @Bean
    public PlatformTransactionManager connectionDetailsTransactionManager(
            @Qualifier("connectionDetailsEntityManagerFactory") LocalContainerEntityManagerFactoryBean connectionDetailsEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(connectionDetailsEntityManagerFactory.getObject()));
    }


}
