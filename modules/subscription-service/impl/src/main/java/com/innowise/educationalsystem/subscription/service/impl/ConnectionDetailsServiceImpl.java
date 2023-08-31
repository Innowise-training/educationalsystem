package com.innowise.educationalsystem.subscription.service.impl;

import com.innowise.educationalsystem.crud.service.AbstractCrudService;
import com.innowise.educationalsystem.subscription.connection.ConnectionKey;
import com.innowise.educationalsystem.subscription.entity.ConnectionDetails;
import com.innowise.educationalsystem.subscription.repository.ConnectionDetailsRepository;
import com.innowise.educationalsystem.subscription.service.ConnectionDetailsService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ConnectionDetailsServiceImpl extends AbstractCrudService<ConnectionDetails, String, ConnectionDetailsRepository> implements ConnectionDetailsService {
    public ConnectionDetailsServiceImpl(ConnectionDetailsRepository crudRepository) {
        super(crudRepository);
    }

    @Override
    public Optional<ConnectionDetails> getTenantInfo(String subscriptionId, ConnectionKey connectionKey) {
        return crudRepository.findBySubscriptionIdAndConnectionKey(subscriptionId, connectionKey);
    }
}
