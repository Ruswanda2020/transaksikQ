package com.Onedev.transaksiQ.security;


import com.Onedev.transaksiQ.dto.GenericResponse;
import com.Onedev.transaksiQ.exception.TransactionApiException;
import com.Onedev.transaksiQ.util.ExcludedUrls;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService userDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        // Jika URL termasuk dalam daftar dikecualikan, lanjutkan tanpa memvalidasi token
        if (isExcluded(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get JWT token from HTTP request
        String token = getTokenFromRequest(request);

        try {
            // Handle empty token
            if (!StringUtils.hasText(token)) {
                throw new TransactionApiException(HttpStatus.UNAUTHORIZED, "Token tidak valid atau kadaluwarsa", 108);
            }

            // Validate token
            if (jwtTokenProvider.validateToken(token)) {
                // Get username from token
                String username = jwtTokenProvider.getEmail(token);

                // Load the user associated with token
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (TransactionApiException ex) {
            // Handle exception and respond with custom error
            GenericResponse<?> genericResponse = new GenericResponse<>(
                    ex.getErrorStatus(),
                    ex.getMessage(),
                    null
            );
            ObjectMapper objectMapper = new ObjectMapper();

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(genericResponse));
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isExcluded(String requestURI) {
        return ExcludedUrls.URLS.stream().anyMatch(requestURI::startsWith);
    }

}
