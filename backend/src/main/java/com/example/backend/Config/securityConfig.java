package com.example.backend.Config;

/**
 * @author BruceChen
 * @date 2023/11/01 11:45
 **/
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class securityConfig {
    @Order(100)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        CorsConfiguration config = new CorsConfiguration();
        //Configure cross-domain
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        http.addFilterBefore(new CorsFilter(source),CorsFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .and()
                .formLogin()
                .successHandler((req, resp, authentication) -> {

                    Object principal = authentication.getPrincipal();
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(principal));
                    out.flush();
                    out.close();
                    HttpSession session = req.getSession();
                    session.setAttribute("user", authentication.getPrincipal());
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        Map<String, String> errorResponse = new HashMap<>();
                        errorResponse.put("enable", "false");
                        errorResponse.put("error","Invalid username or password");
                        String json = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(errorResponse);


                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");

                        response.getWriter().write(json);
                    }
                })
                .permitAll();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user1 =
                User.withUsername("admin")
                        .password(passwordEncoder.encode("Ebrainbest"))
                        .roles("Manger")
                        .build();
        UserDetails user2 =
                User.withUsername("user")
                        .password(passwordEncoder.encode("123"))
                        .roles("user")
                        .build();
        return new InMemoryUserDetailsManager(user1,user2);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
