package com.decojo.app;

import com.decojo.app.security.DefaultUserDetailsService;
import com.decojo.common.model.Authority;
import com.decojo.db.UserDao;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Used to run this application in test mode.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.decojo")
public class TestApplication {
    /**
     * Responsible for configuring security within this application during testing.
     */
    @Configuration
    @Order(2147483639)
    @Profile("test")
    public static class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private UserDao userDao;

        @Override
        protected void configure(@Nonnull final HttpSecurity http) throws Exception {
            // @formatter:off
            http.csrf().disable()
                .authorizeRequests()
                .regexMatchers("/[a-z-]*", "/css/.*", "/js/.*").permitAll()
                .anyRequest().fullyAuthenticated()
                .antMatchers("/admin/**", "/api/admin/**").hasAuthority(Authority.ADMIN.name())
                .antMatchers("/employer/**", "/api/employer/**").hasAuthority(Authority.EMPLOYER.name())
                .antMatchers("/user/**", "/api/user/**").hasAuthority(Authority.USER.name())
            .and()
                .httpBasic();
            // @formatter:on
        }

        @Override
        @Nonnull
        public UserDetailsService userDetailsService() {
            return new DefaultUserDetailsService(userDao);
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

    /**
     * The entry-point into running this application when running tests.
     *
     * @param args the command-line arguments
     */
    public static void main(@Nullable final String... args) {
        new SpringApplicationBuilder().profiles("test").sources(TestApplication.class).run(args);
    }
}
