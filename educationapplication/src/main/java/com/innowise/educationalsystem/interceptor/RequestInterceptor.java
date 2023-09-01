package com.innowise.educationalsystem.interceptor;

import com.innowise.educationalsystem.configuration.TenantContext;
import com.innowise.educationalsystem.dto.ConnectionUrlDto;
import com.innowise.educationalsystem.feign.ConnectionDetailsClient;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    private static final String AUTH_DETAILS_SUBSCRIPTION_KEY = "subscriptionId";

    private static final String CONNECTION_KEY_REGEX = ".+/(.+)/api/v1/.+";

    private static final int CONNECTION_KEY_REGEX_GROUP = 1;

    private static final Pattern CONNECTION_KEY_PATTERN = Pattern.compile(CONNECTION_KEY_REGEX);

    final ConnectionDetailsClient connectionDetailsClient;

    public RequestInterceptor(ConnectionDetailsClient connectionDetailsClient) {
        this.connectionDetailsClient = connectionDetailsClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws MalformedURLException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> authDetails = (Map<String, Object>) authentication.getDetails();
        String subscriptionId = authDetails.get(AUTH_DETAILS_SUBSCRIPTION_KEY).toString();

        String requestURI = request.getRequestURI();
        Matcher connectionKeyMatcher = CONNECTION_KEY_PATTERN.matcher(requestURI);
        String connectionKey;
        try{
            connectionKey = connectionKeyMatcher.group(CONNECTION_KEY_REGEX_GROUP);
        } catch (IndexOutOfBoundsException e) {
            throw new MalformedURLException("No matching connection key group found for URL " + requestURI);
        }
        //TODO: exception handling in case if no suitable connection url found
        ConnectionUrlDto connectionUrlDto = connectionDetailsClient.findConnectionUrl(subscriptionId, connectionKey);
        TenantContext.setTenantInfo(connectionUrlDto.getConnectionUrl());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
}
