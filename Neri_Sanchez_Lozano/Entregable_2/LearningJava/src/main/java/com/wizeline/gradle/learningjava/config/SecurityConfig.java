
package com.wizeline.gradle.learningjava.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/postAccount").permitAll()
            .antMatchers("/api/changeAccountCountry").permitAll()
            .antMatchers("/api/deleteAccounts").permitAll()
            .antMatchers("/api/getAccountByUser").permitAll()
            .antMatchers("/api/restTemplateConsume").permitAll()
            .antMatchers("/api/sayHello").permitAll()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/v3/api-docs/**").permitAll()
            .antMatchers("/health").permitAll()
            .antMatchers("/info").permitAll()
            .antMatchers("/actuator/**").permitAll()
            .anyRequest().authenticated().and()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
	
	@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
       List<UserDetails> userDetailsList = new ArrayList<>();
       userDetailsList.add(User.withUsername("user").password("password")
            .roles("USER").build());
       userDetailsList.add(User.withUsername("admin").password("password")
            .roles("ADMIN", "USER").build());
       userDetailsList.add(User.withUsername("guest").password("password")
            .roles("GUEST").build());

       return new InMemoryUserDetailsManager(userDetailsList);
    }

}
