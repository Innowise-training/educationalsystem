package com.innowise.educationalsystem.subscription.service;

import com.innowise.educationalsystem.crud.service.CrudService;
import com.innowise.educationalsystem.subscription.connection.ConnectionKey;
import com.innowise.educationalsystem.subscription.entity.ConnectionDetails;
import java.util.Optional;

public interface ConnectionDetailsService extends CrudService<ConnectionDetails, String> {
    Optional<ConnectionDetails> getTenantInfo(String subscriptionId, ConnectionKey connectionKey);
}
