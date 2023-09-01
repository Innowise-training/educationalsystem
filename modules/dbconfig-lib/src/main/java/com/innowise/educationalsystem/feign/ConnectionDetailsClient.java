package com.innowise.educationalsystem.feign;

import com.innowise.educationalsystem.dto.ConnectionUrlDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "connection", url = "http://localhost:8080")
public interface ConnectionDetailsClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/subscription/{subscriptionId}/connection/{connectionKey}")
    ConnectionUrlDto findConnectionUrl(@PathVariable("subscriptionId") String subscriptionId, @PathVariable("connectionKey") String connectionKey);

}
