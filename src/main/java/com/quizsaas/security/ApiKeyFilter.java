package com.quizsaas.security;

import com.quizsaas.entity.ApiKey;
import com.quizsaas.repository.ApiKeyRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String apiKeyHeader = request.getHeader("x-api-key");

        if (apiKeyHeader != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<ApiKey> apiKeyOptional = apiKeyRepository.findByKey(apiKeyHeader);

            if (apiKeyOptional.isPresent()) {
                ApiKey apiKey = apiKeyOptional.get();
                if ("ACTIVE".equals(apiKey.getStatus())) {
                    // Create an authentication token for the API Key
                    // Assign a special role or authority for API Key access
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            apiKey.getOrganization().getName(), null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_CLIENT")));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
