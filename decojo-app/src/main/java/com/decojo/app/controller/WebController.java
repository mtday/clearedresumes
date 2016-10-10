package com.decojo.app.controller;

import java.util.Date;
import java.util.Map;
import javax.annotation.Nonnull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage web pages.
 */
@Controller
public class WebController {
    /**
     * Display the home page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/")
    @Nonnull
    public String home(@Nonnull final Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Home");
        model.put("date", new Date());
        return "home";
    }
}
