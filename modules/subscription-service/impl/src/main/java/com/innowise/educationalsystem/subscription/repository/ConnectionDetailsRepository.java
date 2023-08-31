package com.innowise.educationalsystem.subscription.repository;

import com.innowise.educationalsystem.crud.repository.CrudRepository;
import com.innowise.educationalsystem.subscription.connection.ConnectionKey;
import com.innowise.educationalsystem.subscription.entity.ConnectionDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionDetailsRepository extends JpaRepository<ConnectionDetails, String>, CrudRepository<ConnectionDetails, String> {
    Optional<ConnectionDetails> findBySubscriptionIdAndConnectionKey(String subscriptionId, ConnectionKey connectionKey);

}