package com.decojo.app.controller;

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
     * Display the index page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/")
    @Nonnull
    public String index(@Nonnull final Map<String, Object> model) {
        return "index";
    }
}
