package com.cr.app.controller.web;

import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the index page.
 */
@Controller
public class IndexController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    /**
     * Display the index page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/")
    @Nonnull
    public String index(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "index";
    }
}
