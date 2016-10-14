package com.cr.app.config;

import javax.annotation.Nonnull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Define the configuration for the web app.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(@Nonnull final ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}
