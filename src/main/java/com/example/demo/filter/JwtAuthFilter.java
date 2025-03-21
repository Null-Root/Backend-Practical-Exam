package com.example.demo.filter;

import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        // Check if Authorization header exists and is valid
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get jwt token
        String jwtTokenString = authorizationHeader.substring(7);

        // Check if valid
        if (!jwtService.isTokenValid(jwtTokenString)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get the subject id (this is the userId)
        String userId = jwtService.extractSubject(jwtTokenString);

        // Check if Jwt has subject and no auth obj yet
        if (userId == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get User Object from id
        Optional<User> optionalUser = userRepository.findById(Long.parseLong(userId));

        // Check if user is empty
        if (optionalUser.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // Create authentication token with authorities
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                optionalUser.get(),
                null,
                new ArrayList<>()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Call doFilter
        filterChain.doFilter(request, response);
    }
}