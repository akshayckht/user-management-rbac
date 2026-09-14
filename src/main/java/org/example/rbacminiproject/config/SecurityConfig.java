package org.example.rbacminiproject.config;

import org.example.rbacminiproject.service.impl.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers("/", "/signup", "/login", "/css/**", "/js/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .formLogin(
                        form ->
                                form.loginPage("/login")
                                        .defaultSuccessUrl("/dashboard", true)
                                        .permitAll())
                .logout(
                        logout ->
                                logout.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll())

        .headers(headers -> headers
                .cacheControl(cache -> {})
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
}
