package com.bus.reservationbus.security;

import com.bus.reservationbus.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JWTEntryPoint jwtEntryPoint;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.
                csrf(AbstractHttpConfigurer::disable) //csrf is specially token from spring it's important in banking things and when there is a session but in our case we have stateless session so we should do disable
                .authorizeHttpRequests((authReq) -> authReq.requestMatchers(HttpMethod.GET).permitAll()//permit all Get methods i had on my project
                                .requestMatchers("/api/auth/**")
                                .permitAll()
                                //
                                .requestMatchers(HttpMethod.POST,
                                        "/api/bus/add",
                                        "/api/schedule/add",
                                        "/api/route/add")
                                .authenticated()
                                //
                                .requestMatchers(HttpMethod.POST,
                                        "/api/reservation/add").permitAll()
                        //
                        // .requestMatchers("/api/reservation/add").authenticated()//that's mean this path needs authentication
                        //.anyRequest().authenticated() //that's mean all other requests need authentication)
                )
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
