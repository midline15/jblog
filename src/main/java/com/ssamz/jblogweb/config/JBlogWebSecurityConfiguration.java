package com.ssamz.jblogweb.config;

import com.ssamz.jblogweb.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class JBlogWebSecurityConfiguration{

    //private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf
                        .disable())
                .formLogin(login->login
                        .loginPage("/auth/login"))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/auth/logout"))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/webjars/**"),
                                new AntPathRequestMatcher("/js/**"),
                                new AntPathRequestMatcher("/auth/**"),
                                new AntPathRequestMatcher("/"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/webjars/**"),
                                new AntPathRequestMatcher("/js/**"),
                                new AntPathRequestMatcher("/auth/**"),
                                new AntPathRequestMatcher("/"))
                        .anonymous()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
