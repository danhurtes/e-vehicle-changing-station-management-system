package com.netvalue.evehicleschargemeter.config;

import com.netvalue.evehicleschargemeter.config.properties.BasicAuthUserProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final BasicAuthUserProperties basicAuthUserProperties;
    private final AppBasicAuthEntryPoint appBasicAuthEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .antMatchers("/e-cars-charger/v1/appVersion", "/e-cars-charger/v1/dbSchemaVersion").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(appBasicAuthEntryPoint);
        http.headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.addExposedHeader(HttpHeaders.CONTENT_DISPOSITION);
            return corsConfiguration;
        };
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        var manager = new InMemoryUserDetailsManager();
        basicAuthUserProperties.getUsers().forEach((k, user) -> manager.createUser(User
                .withUsername(user.getUsername())
                .password(encoder().encode(user.getPassword()))
                .roles(user.getRoles()).build()));
        return manager;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
