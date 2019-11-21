package se.experis.MeFitBackend.security;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-08
 * Project: MeFit-Backend
 */





@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("auth0.apiAudience")
    private String apiAudience;
    @Value("${auth0.issuer}")
    private String issuer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // redirects http to https (heroku)
        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();

        http.cors();
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .authorizeRequests()
                .mvcMatchers("api/private/").authenticated()
                .anyRequest().permitAll();

        //        .antMatchers(HttpMethod.GET, "/public/*").permitAll()
          //      .antMatchers(HttpMethod.GET, "/private/*").authenticated()
            //    .antMatchers(HttpMethod.GET, "/api/private-scoped").hasAuthority("admin")
        ;
    }

}