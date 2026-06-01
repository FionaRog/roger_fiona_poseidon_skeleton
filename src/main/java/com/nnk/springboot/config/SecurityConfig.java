package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
   public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return  http.authorizeRequests(auth -> {
                        auth.requestMatchers("/app/error","/app/login", "/user/list").permitAll();
                        auth.requestMatchers("/css/**", "/js/**").permitAll();
                        auth.anyRequest().authenticated();
                })
                .formLogin(form -> form
                        .loginPage("/app/login").loginProcessingUrl("/app/login")
                        .usernameParameter("username").passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/app/error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/app-logout")
                        .logoutSuccessUrl("/app/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .build();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();}
}
