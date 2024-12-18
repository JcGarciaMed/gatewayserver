package com.greymatter.gatewayserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity){
        log.debug("Configuring SecurityWebFilterChain");
        return serverHttpSecurity.authorizeExchange(exchanges-> exchanges.pathMatchers(HttpMethod.GET).permitAll()
                        .pathMatchers("/greymatter/accounts/**").authenticated()
                        .pathMatchers("/greymatter/cards/**").authenticated()
                        .pathMatchers("/greymatter/loans/**").authenticated())
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()))
                .csrf(csrfSpec -> csrfSpec.disable()).build();
                //.oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()));
        //serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable());
    }
}
