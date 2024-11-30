package edu.bowiestateuni.groupproj.foodpantry.services.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class JwtTokenProvider extends AbstractUserDetailsAuthenticationProvider {
    private final JWTService jwtService;

    public JwtTokenProvider(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void additionalAuthenticationChecks(final UserDetails userDetails, final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        log.info("additionalAuthenticationChecks called");
    }

    @Override
    protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final String token = String.valueOf(authentication.getCredentials());
        return findUserByToken(token).orElseThrow(() -> new UsernameNotFoundException("Invalid authorization token."));
    }
    public UserDetailsService userDetailsService() {
        return username -> {
            System.out.println("Trying to find token");
            String token = "";
            return findUserByToken(token).orElseThrow(() -> new UsernameNotFoundException("Invalid authorization token."));
        };
    }
    private Optional<AuthenticatedUser> findUserByToken(final String token) {
        final Map<String, String> attributes = jwtService.verify(token);
        if (attributes.isEmpty()) {
            return Optional.empty();
        }
        if (!attributes.containsKey("userId") || !attributes.containsKey("accountId")) {
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
            Arrays.stream(role.split(":")).forEach(authenticatedUser::addAuthority);
        }

        return Optional.of(authenticatedUser);
    }
}
