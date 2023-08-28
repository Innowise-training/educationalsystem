package com.innowise.educationalsystem.security.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;

//@Component
public class JwtAuthenticationConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    private static final String AUTHORITIES_CLAIM = "authorities";
    private static final String SUBSCRIPTION_CLAIM = "subscription_id";
    private static final String AUTH_DETAILS_SUBSCRIPTION_KEY = "subscriptionId";

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        String username = jwt.getSubject();
        String subscriptionId = jwt.getClaimAsString(SUBSCRIPTION_CLAIM);
        List<SimpleGrantedAuthority> authorities = jwt.getClaimAsStringList(AUTHORITIES_CLAIM)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, authorities);
        Map<String, Object> authenticationDetails = singletonMap(AUTH_DETAILS_SUBSCRIPTION_KEY, subscriptionId);
        authentication.setDetails(authenticationDetails);

        return authentication;
    }
}
