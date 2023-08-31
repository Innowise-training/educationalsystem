package com.innowise.educationalsystem.subscription.controller;

import com.innowise.educationalsystem.subscription.connection.ConnectionKey;
import com.innowise.educationalsystem.subscription.dto.ConnectionUrlDto;
import com.innowise.educationalsystem.subscription.entity.ConnectionDetails;
import com.innowise.educationalsystem.subscription.mapper.ConnectionUrlMapper;
import com.innowise.educationalsystem.subscription.service.ConnectionDetailsService;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscription")
public class ConnectionDetailsController {
    private final ConnectionDetailsService connectionDetailsService;

    private final ConnectionUrlMapper connectionUrlMapper;

    @GetMapping("/{subscriptionId}/connection/{connectionKey}")
    public ResponseEntity<ConnectionUrlDto> getConnectionURL(@PathVariable String subscriptionId,
                                                             @PathVariable ConnectionKey connectionKey) {
        ConnectionDetails connectionDetails = connectionDetailsService.getTenantInfo(subscriptionId, connectionKey)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Connection URL not found for specified parameters: subscriptionId=" + subscriptionId
                        + ", connectionKey=" + connectionKey));
        return ResponseEntity.ok().body(connectionUrlMapper.rawConnectionUrlToDto(connectionDetails.getConnectionUrl()));
    }

}
