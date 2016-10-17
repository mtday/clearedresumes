package com.cr.app.controller;

import com.cr.db.PriceDao;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the employers page.
 */
@Controller
public class EmployersController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployersController.class);

    @Nonnull
    private final PriceDao priceDao;

    /**
     * Create an instance of this controller.
     *
     * @param priceDao the {@link PriceDao} used to retrieve pricing data from the database
     */
    @Autowired
    public EmployersController(@Nonnull final PriceDao priceDao) {
        this.priceDao = priceDao;
    }

    /**
     * Display the employers page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employers")
    @Nonnull
    public String employers(@Nonnull final Map<String, Object> model) {
        this.priceDao.getAll().forEach(price -> model.put(price.getType().name(), price.getPrice().toString()));

        setCurrentAccount(model);
        return "employers";
    }
}
