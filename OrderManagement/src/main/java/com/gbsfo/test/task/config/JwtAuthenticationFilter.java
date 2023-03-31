package com.gbsfo.test.task.config;

import com.gbsfo.test.task.exception.JwtTokenException;
import com.gbsfo.test.task.util.Constant;
import com.gbsfo.test.task.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.gbsfo.test.task.util.Constant.AUTHORIZATION_HEADER_IS_INCORRECT_ERROR_MESSAGE;
import static com.gbsfo.test.task.util.Constant.JWT_TOKEN_IS_INVALID;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader(Constant.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(Constant.BEARER)) {
            try {
                String jwt = authHeader.substring(7);
                String username = jwtUtil.extractUsername(jwt);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (jwtUtil.isTokenValid(jwt, userDetails)) {
                        authenticateUser(request, userDetails);
                    } else {
                        handlerExceptionResolver.resolveException(request, response, null, new JwtTokenException(JWT_TOKEN_IS_INVALID));
                    }
                }
            } catch (Exception ex) {
                handlerExceptionResolver.resolveException(request, response, null, ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Put user's object in the SecurityContextHolder
     *
     * @param request
     * @param userDetails
     */
    private void authenticateUser(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
