package com.examensbe.backend.Jwt;


import com.examensbe.backend.services.UserEntityDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenGenerator jwtTokenGenerator;
    private final UserEntityDetailsService userEntityDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenGenerator jwtTokenGenerator, UserEntityDetailsService userEntityDetailsService) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.userEntityDetailsService = userEntityDetailsService;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        // Kan va final, ska inte kunna ändras på
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // beginIndex 7 pga space efter "Bearer "
        }

        return null;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // Kan va final, ska inte kunna ändras på JWT = Json web token
        String jwt = getJwtFromRequest(request);

        if (StringUtils.hasText(jwt) && jwtTokenGenerator.validateToken(jwt)) {
            String username = jwtTokenGenerator.getUsernameFromJwt(jwt);

            UserDetails userDetails = userEntityDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(request, response);

    }

}