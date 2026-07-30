package com.flightbooking.identity.security.filter;

import com.flightbooking.identity.security.jwt.JwtService;
import com.flightbooking.identity.security.principal.CustomUserPrincipal;
import com.flightbooking.identity.user.entity.User;
import com.flightbooking.identity.user.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Read Authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        //  Extract access token
        String accessToken = authHeader.substring(BEARER_PREFIX.length());

        if (accessToken.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Extract user identity
        String email;
        try {
            email = jwtService.extractSubject(accessToken);

        } catch (JwtException | IllegalArgumentException exception) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //  Load current user from Oracle
        User user = userRepository.findByEmail(email)
                                 .orElse(null);

        if (user == null) {
            response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
            return;
        }

        CustomUserPrincipal principal = new CustomUserPrincipal(user);

        // Validate JWT
        boolean valid;

        try {
            valid = jwtService.validateToken(accessToken, principal);

        } catch (JwtException | IllegalArgumentException exception) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (!valid) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Check current user status
        if (!principal.isEnabled() || !principal.isAccountNonLocked()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Create Authentication
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(principal, null, principal.getAuthorities());

        // Store Authentication
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
