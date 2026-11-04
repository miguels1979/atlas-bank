package com.atlas.bank.shared.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        //Accounts
                        .requestMatchers(HttpMethod.POST, "/api/v1/accounts").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/accounts").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/accounts/{id}").hasAnyRole("USER", "ADMIN")
                        //Transactions
                        .requestMatchers(HttpMethod.POST, "/api/v1/transactions/transfer").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/transactions/{id}/transactions").hasAnyRole("USER", "ADMIN")
                        //H2
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(
                        oauth2 -> oauth2
                                .jwt(jwtConfigurer -> jwtConfigurer
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .headers(
                        headers->
                                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(
                jwt -> {
                    Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
                    if (realmAccess == null || !(realmAccess.get("roles") instanceof Collection<?> roles)) {
                        return List.of();
                    }

                    return roles.stream()
                            .map(Object::toString)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                }
        );
        return converter;
    }
}
