package edu.bowiestateuni.groupproj.foodpantry.services.security;

import edu.bowiestateuni.groupproj.foodpantry.utils.WebUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class JwtTokenFilter extends AbstractAuthenticationProcessingFilter {
    private final JWTService jwtService;

    public JwtTokenFilter(final RequestMatcher protectedRoutes, JWTService jwtService) {
        super(protectedRoutes);
        this.jwtService = jwtService;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String token = extractTokenFromCookiesOrHeader(request);
        AuthenticatedUser authenticatedUser = validateTokenAndGetUser(token);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticatedUser, null, authenticatedUser.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
        return authToken;
    }

    private String extractTokenFromCookiesOrHeader(final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (final Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.toLowerCase().startsWith("bearer ")) {
            return header.substring(7);
        }

        throw new BadCredentialsException("Missing or invalid Authorization token.");
    }

    private AuthenticatedUser validateTokenAndGetUser(String token) {
        Optional<AuthenticatedUser> authenticatedUserOpt = findUserByToken(token);
        if (authenticatedUserOpt.isEmpty()) {
            throw new BadCredentialsException("Invalid authorization token.");
        }
        return authenticatedUserOpt.get();
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        log.info("Attempted authentication failed from IP Address - {} - request {} , response {} ", WebUtils.extractClientRequestIP(request),  request, response);
    }
    private Optional<AuthenticatedUser> findUserByToken(final String token) {
        final Map<String, String> attributes = jwtService.verify(token);
        if (attributes.isEmpty()) {
            return Optional.empty();
        }
        if (!attributes.containsKey("userId")) {
            return Optional.empty();
        }
        final String userId = attributes.get("userId");
        final String email = attributes.get("email");
        final String name = attributes.get("name");
        final String role = attributes.getOrDefault("role", "");
        final AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUserId(Long.valueOf(userId));
        authenticatedUser.setEmail(email);
        authenticatedUser.setPassword(userId);
        authenticatedUser.setName(name);
        if (!role.isEmpty()) {
            Arrays.stream(role.split(",")).forEach(authenticatedUser::addAuthority);
        }

        return Optional.of(authenticatedUser);
    }
}
