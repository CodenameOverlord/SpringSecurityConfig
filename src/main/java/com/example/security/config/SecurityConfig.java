package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Autowired
    DataSource dataSource;
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) ->
                //allow requests with pattern /h2-console/**
                        requests.requestMatchers("/h2-console/**").permitAll().
                anyRequest().authenticated());
//        http.formLogin(withDefaults());
        http.sessionManagement(session->{
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        http.httpBasic(withDefaults());
        //disable csrf
        http.csrf(csrf-> csrf.disable());
        // allow frame options from the same origin
        http.headers(headers->headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return http.build();
    }


    // our functionality here would use inmemory userdetails to fetch and retrieve the data
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user =  User.withUsername("user1")
                .password("{noop}user123")
                .roles("USER")
                .build();

        UserDetails admin =  User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        UserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(admin);
        jdbcUserDetailsManager.createUser(user);
//        return new InMemoryUserDetailsManager(admin, user);

        return jdbcUserDetailsManager;
    }
}
