package edu.bowiestateuni.groupproj.foodpantry.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bowiestateuni.groupproj.foodpantry.controller.error.APIErrorResponse;
import edu.bowiestateuni.groupproj.foodpantry.services.security.JWTService;
import edu.bowiestateuni.groupproj.foodpantry.services.security.JwtTokenFilter;
import edu.bowiestateuni.groupproj.foodpantry.services.security.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig{

    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/v1/register"),
            new AntPathRequestMatcher("/api/v1/employee/register"),
            new AntPathRequestMatcher("/api/v1/login"),
            new AntPathRequestMatcher("/api/v1/game/tic-tac-toe/play"),
            new AntPathRequestMatcher("/api/v1/game/tic-tac-toe/reset")
    );

    private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);

    private final JwtTokenProvider jwtTokenProvider;
    private final JWTService jwtService;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, JWTService jwtService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(PUBLIC_URLS)
                        .permitAll().requestMatchers(PROTECTED_URLS).authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.cors((t) -> t.configurationSource(corsConfigurationSource()));
        return http.build();

    }

    public JwtTokenFilter restAuthenticationFilter() throws Exception {
        final JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(PROTECTED_URLS, jwtService);
        jwtTokenFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        jwtTokenFilter.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
        return jwtTokenFilter;
    }
    private static class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        }
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtTokenProvider.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    private static class JwtAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            APIErrorResponse apiResponse = new APIErrorResponse(exception.getMessage());
            response.setHeader("Content-Type", "application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().write(new ObjectMapper().writeValueAsString(apiResponse).getBytes());
        }
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://10.0.0.192:3000", "http://10.0.0.189:3000"));
        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
