package com.flightbooking.identity.security.filter;

import com.flightbooking.identity.security.jwt.JwtService;
import com.flightbooking.identity.security.principal.CustomUserPrincipal;
import com.flightbooking.identity.user.entity.User;
import com.flightbooking.identity.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {//Request authentication flow

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ROLE_CLAIM = "role";

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //  Read Authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        //  Extract the raw JWT
        String accessToken = authHeader.substring(BEARER_PREFIX.length());

        if (accessToken.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //  Validate the JWT and extract its claims once
        Claims claims;

        try {
            claims = jwtService.validateAndExtractClaims(accessToken);

        } catch (JwtException | IllegalArgumentException exception) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //  Extract user identity and role from the validated token
        String email = claims.getSubject();

        String tokenRole =
                claims.get(ROLE_CLAIM, String.class);
        if (email == null || email.isBlank() || tokenRole == null || tokenRole.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //  Load the current user state from Oracle
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        if (!user.getRole().name().equals(tokenRole)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //  Adapt the User Entity to Spring Security UserDetails
        CustomUserPrincipal principal = new CustomUserPrincipal(user);

        // Check the current account status from Oracle
        if (!principal.isEnabled() || !principal.isAccountNonLocked()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        //  Create an authenticated Authentication object
        Authentication authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        principal,
                        null,
                        principal.getAuthorities()
                );

        // Store the authenticated user in the SecurityContext
        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        //  Continue the Spring Security filter chain
        filterChain.doFilter(request, response);
    }
}