package com.eeerrorcode.pilllaw.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.eeerrorcode.pilllaw.security.filter.CustomLoginFilter;
import com.eeerrorcode.pilllaw.security.filter.SignCheckFilter;
import com.eeerrorcode.pilllaw.security.handler.LoginFailHandler;
import com.eeerrorcode.pilllaw.security.handler.LoginSuccessHandler;
import com.eeerrorcode.pilllaw.security.util.JWTUtil;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  @Value("${jwt.secret}")
  private String secretKey;
  
  private final JWTUtil jwtUtil;

  @Autowired
  private UserDetailsService userDetailsService;

  public SecurityConfig(@Value("${jwt.secret}") String secretKey) {
    this.secretKey = secretKey;
    this.jwtUtil = new JWTUtil(secretKey);
  }

  @Bean
  public JWTUtil jwtUtil() {
    return this.jwtUtil;
  }


  @Autowired
  public PasswordEncoder encoder;

  @Bean
  public SignCheckFilter signCheckFilter(){
    return new SignCheckFilter("/api/**", this.jwtUtil);
  }

  @Bean
  public CustomLoginFilter customLoginFilter(AuthenticationManager authenticationManager) {
    CustomLoginFilter customLoginFilter = new CustomLoginFilter("/api/member/signin", this.jwtUtil);
    customLoginFilter.setAuthenticationManager(authenticationManager);
    customLoginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(encoder));
    customLoginFilter.setAuthenticationFailureHandler(new LoginFailHandler());
    return customLoginFilter;
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.userDetailsService(userDetailsService)
      .passwordEncoder(encoder).setBuilder(builder);
  
    AuthenticationManager authenticationManager = builder.build();
    return authenticationManager;
  }

  @Bean 
  public CorsConfigurationSource configurationSource() {
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowCredentials(true);
    config.setAllowedOrigins(List.of("http://localhost:3000"));
    config.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setExposedHeaders(List.of("*"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager
  , CustomLoginFilter customLoginFilter, LoginSuccessHandler loginSuccessHandler) throws Exception {
    http
      .cors(c -> c.configurationSource(configurationSource()))
      .csrf(csrf -> csrf.disable())
      // .oauth2Login(o -> o.successHandler(loginSuccessHandler()))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/swagger-ui.html").permitAll()
        .requestMatchers("/api/member/**").permitAll()
        .anyRequest().permitAll()
        )
      .addFilterBefore(signCheckFilter(), UsernamePasswordAuthenticationFilter.class)
      .addFilterBefore(customLoginFilter(authenticationManager(http)), UsernamePasswordAuthenticationFilter.class);
    
      customLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
      return http.build();
  }

  @Bean
  public LoginSuccessHandler loginSuccessHandler() {
    return new LoginSuccessHandler(encoder);
  }
}