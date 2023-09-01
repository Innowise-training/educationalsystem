package com.innowise.educationalsystem.configuration.hibernate;

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
        entityManagerFactoryRef = "subscriptionDetailsEntityManagerFactory",
        transactionManagerRef = "subscriptionDetailsTransactionManager"
)
public class SubscriptionDatabaseConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.subscription")
    public DataSourceProperties subscriptionDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("subscriptionDetailsDataSource")
    public DataSource subscriptionDetailsDataSource() {
        return subscriptionDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean subscriptionDetailsEntityManagerFactory(
            @Qualifier("subscriptionDetailsDataSource") DataSource dataSource,
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
    public PlatformTransactionManager subscriptionDetailsTransactionManager(
            @Qualifier("subscriptionDetailsEntityManagerFactory") LocalContainerEntityManagerFactoryBean subscriptionDetailsEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(subscriptionDetailsEntityManagerFactory.getObject()));
    }


}
