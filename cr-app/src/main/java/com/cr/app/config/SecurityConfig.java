package com.cr.app.config;

import com.cr.app.security.DefaultUserDetailsService;
import com.cr.common.model.Authority;
import com.cr.db.CompanyDao;
import com.cr.db.ResumeContainerDao;
import com.cr.db.UserDao;
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
    private ResumeContainerDao resumeContainerDao;

    @Override
    protected void configure(@Nonnull final HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
                .regexMatchers("/[a-z-]*", "/signup/.*", "/css/.*", "/js/.*").permitAll()
                .anyRequest().fullyAuthenticated()
                .antMatchers("/admin/**", "/api/admin/**").hasAuthority(Authority.ADMIN.name())
                .antMatchers("/employer/**", "/api/employer/**").hasAuthority(Authority.EMPLOYER.name())
                .antMatchers("/user/**", "/api/user/**").hasAuthority(Authority.USER.name())
            .and()
                .formLogin()
                .loginPage("/login").successForwardUrl("/user/resume").failureUrl("/login?error").permitAll()
            .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll();
        // @formatter:on
    }

    @Override
    @Nonnull
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService(userDao, companyDao, resumeContainerDao);
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
