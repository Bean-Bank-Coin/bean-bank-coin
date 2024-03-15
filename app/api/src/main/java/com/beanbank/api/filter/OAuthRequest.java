package com.beanbank.api.filter;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.beanbank.api.controller.UserController;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthRequest extends OncePerRequestFilter {
    private static String githubUserLink = "https://api.github.com/user";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized\"}");

            return;
        }
        try {
            token = authHeader.substring(7);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> restResponse = restTemplate.exchange(
                    githubUserLink, HttpMethod.GET, entity, String.class);

            JSONObject jsonObject = new JSONObject(restResponse.getBody());
            String username = jsonObject.getString("login");
            UserController.currentUser = username;
            filterChain.doFilter(request, response);
        } catch (HttpClientErrorException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized: Bad credentials\"}");
            UserController.currentUser = "";
            return;
        }

    }
}
