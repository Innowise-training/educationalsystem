package com.innowise.educationalsystem.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.innowise.educationalsystem.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_PREFIX = "Bearer ";
    private static final String AUTHORITIES_CLAIM = "authorities";
    private static final String SUBSCRIPTION_CLAIM = "subscription_id";
    private static final String AUTH_DETAILS_SUBSCRIPTION_KEY = "subscriptionId";

    private final SecurityProperties securityProperties;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String authHeader = request.getHeader(AUTHORIZATION);

        if (nonNull(authHeader) && authHeader.startsWith(AUTHORIZATION_PREFIX)) {
            try {
                String token = authHeader.substring(AUTHORIZATION_PREFIX.length());

                Algorithm algorithm = Algorithm.HMAC256(securityProperties.getSecretKey());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJwt = jwtVerifier.verify(token);

                String username = decodedJwt.getSubject();
                String subscriptionId = decodedJwt.getClaim(SUBSCRIPTION_CLAIM).asString();
                List<SimpleGrantedAuthority> authorities = Arrays
                        .stream(decodedJwt.getClaim(AUTHORITIES_CLAIM).asArray(String.class))
                        .map(SimpleGrantedAuthority::new)
                        .collect(toList());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                Map<String, Object> authenticationDetails = singletonMap(AUTH_DETAILS_SUBSCRIPTION_KEY, subscriptionId);
                authentication.setDetails(authenticationDetails);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException e) {
                response.setHeader("error", "Invalid JWT token or token expired.");
                response.setStatus(FORBIDDEN.value());
            } catch (Exception e) {
                log.error("Error during authorization: {}.", e.getMessage());
                response.setStatus(INTERNAL_SERVER_ERROR.value());
            }
        }

        filterChain.doFilter(request, response);
    }
}
