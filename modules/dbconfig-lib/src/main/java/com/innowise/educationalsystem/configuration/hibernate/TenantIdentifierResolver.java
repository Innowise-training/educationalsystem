package com.innowise.educationalsystem.configuration.hibernate;

import com.innowise.educationalsystem.configuration.TenantContext;
import java.util.Optional;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    private static final String UNKNOWN = "unknown";

    @Override
    public String resolveCurrentTenantIdentifier() {
        return Optional.ofNullable(TenantContext.getTenantInfo()).orElse(UNKNOWN);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
