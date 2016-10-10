package com.decojo.app.config;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Responsible for configuring security within this application.
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(@Nonnull final HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**").permitAll()
                .anyRequest().fullyAuthenticated()
                .antMatchers("/admin", "/api/admin").hasAuthority("ADMIN")
            .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login?error").permitAll()
            .and()
                .logout().permitAll();
        // @formatter:on
    }

    @Override
    public void configure(@Nonnull final AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(this.dataSource).passwordEncoder(passwordEncoder());
    }

    /**
     * Use B-Crypt password encoding used when managing user passwords in the database.
     *
     * @return the password encoder to use when managing user passwords
     */
    @Bean
    @Nonnull
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
