package com.innowise.educationalsystem.configuration.hibernate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

@Component
public class SchemaDataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private final DataSource dataSource;

    private final Map<String, DataSource> tenantDataSources = new ConcurrentHashMap<>();


    public SchemaDataSourceBasedMultiTenantConnectionProviderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return dataSource;
    }

    @Override
    protected DataSource selectDataSource(String connectionURl) {

        if(tenantDataSources.containsKey(connectionURl)) {
            return tenantDataSources.get(connectionURl);
        } else {
            DataSource newDataSource = DataSourceBuilder.create()
                    .url(connectionURl)
                    .build();
            tenantDataSources.put(connectionURl, newDataSource);
            return newDataSource;
        }
    }
}
