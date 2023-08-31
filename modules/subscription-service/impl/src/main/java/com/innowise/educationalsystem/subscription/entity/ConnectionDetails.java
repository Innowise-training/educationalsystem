package com.innowise.educationalsystem.subscription.entity;

import com.innowise.educationalsystem.subscription.connection.ConnectionKey;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "connection_details")
public class ConnectionDetails {
    @Id
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "subscription_id")
    private String subscriptionId;

    @Column(name = "connection_key")
    private ConnectionKey connectionKey;

    @Column(name = "connection_url")
    private String connectionUrl;

}