package com.decojo.app.config;

import com.decojo.app.security.DefaultUserDetailsService;
import com.decojo.common.model.Authority;
import com.decojo.db.CompanyDao;
import com.decojo.db.ResumeDao;
import com.decojo.db.UserDao;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Responsible for configuring security within this application.
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ResumeDao resumeDao;

    @Override
    protected void configure(@Nonnull final HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
                .regexMatchers("/[a-z-]*", "/css/.*", "/js/.*").permitAll()
                .anyRequest().fullyAuthenticated()
                .antMatchers("/admin/**", "/api/admin/**").hasAuthority(Authority.ADMIN.name())
                .antMatchers("/employer/**", "/api/employer/**").hasAuthority(Authority.EMPLOYER.name())
                .antMatchers("/user/**", "/api/user/**").hasAuthority(Authority.USER.name())
            .and()
                .formLogin()
                .loginPage("/login").successForwardUrl("/user/actions").failureUrl("/login?error").permitAll()
            .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll();
        // @formatter:on
    }

    @Override
    @Nonnull
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService(userDao, companyDao, resumeDao);
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
